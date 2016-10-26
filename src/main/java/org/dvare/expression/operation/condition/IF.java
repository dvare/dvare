/*The MIT License (MIT)

Copyright (c) 2016 Muhammad Hammad

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Sogiftware.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.*/


package org.dvare.expression.operation.condition;

import org.dvare.annotations.Operation;
import org.dvare.binding.model.TypeBinding;
import org.dvare.config.ConfigurationRegistry;
import org.dvare.exceptions.interpreter.InterpretException;
import org.dvare.exceptions.parser.ExpressionParseException;
import org.dvare.expression.Expression;
import org.dvare.expression.operation.ConditionOperationExpression;
import org.dvare.expression.operation.OperationExpression;
import org.dvare.expression.operation.OperationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Stack;

@Operation(type = OperationType.IF)
public class IF extends ConditionOperationExpression {
    static Logger logger = LoggerFactory.getLogger(IF.class);


    public IF() {
        super(OperationType.IF);
    }

    public IF copy() {
        return new IF();
    }

    @Override
    public Integer parse(String[] tokens, int pos, Stack<Expression> stack, TypeBinding selfTypes, TypeBinding dataTypes) throws ExpressionParseException {
        int i = findNextExpression(tokens, pos + 1, stack, selfTypes, dataTypes);
        stack.push(this);
        return i;
    }

    @Override
    public Integer findNextExpression(String[] tokens, int pos, Stack<Expression> stack, TypeBinding selfTypes, TypeBinding dataTypes) throws ExpressionParseException {
        ConfigurationRegistry configurationRegistry = ConfigurationRegistry.INSTANCE;

        for (int i = pos; i < tokens.length; i++) {

            OperationExpression operation = configurationRegistry.getOperation(tokens[i]);
            if (operation != null) {


                if (operation instanceof IF || operation instanceof ELSE || operation instanceof THEN || operation instanceof ENDIF) {
                    operation = operation.copy();
                    if (operation instanceof THEN) {
                        i = operation.parse(tokens, i, stack, selfTypes, dataTypes);
                        this.thenOperand = stack.pop();
                        continue;
                    }

                    if (operation instanceof ELSE) {
                        i = operation.parse(tokens, i, stack, selfTypes, dataTypes);
                        this.elseOperand = stack.pop();
                        continue;
                    }

                    if (operation instanceof IF) {
                        i = operation.parse(tokens, i, stack, selfTypes, dataTypes);
                        this.elseOperand = stack.pop();
                        return i;
                    }

                    if (operation instanceof ENDIF) {
                        return i;
                    }

                } else {
                    operation = operation.copy();

                    if (condition != null) {
                        stack.push(condition);
                    }
                    i = operation.parse(tokens, i, stack, selfTypes, dataTypes);
                    this.condition = stack.pop();
                }
            }
        }
        return null;
    }


    @Override
    public Object interpret(Object aggregation, List<Object> dataSet) throws InterpretException {

        List<Object> newDataSet = new java.util.ArrayList<>();

        for (Object dataRow : dataSet) {
            Boolean result = (Boolean) condition.interpret(aggregation, dataRow);
            if (result) {
                newDataSet.add(dataRow);
            }
        }

        if (!newDataSet.isEmpty()) {
            return thenOperand.interpret(aggregation, dataSet);
        }
        if (elseOperand != null) {
            return elseOperand.interpret(aggregation, dataSet);
        } else {
            return aggregation;
        }
    }

}