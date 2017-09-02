/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2016-2017 DVARE (Data Validation and Aggregation Rule Engine)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Sogiftware.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.dvare.expression.operation.list;

import org.dvare.annotations.Operation;
import org.dvare.binding.data.InstancesBinding;
import org.dvare.exceptions.interpreter.InterpretException;
import org.dvare.expression.Expression;
import org.dvare.expression.literal.LiteralExpression;
import org.dvare.expression.literal.LiteralType;
import org.dvare.expression.literal.NullLiteral;
import org.dvare.expression.operation.AggregationOperationExpression;
import org.dvare.expression.operation.OperationExpression;
import org.dvare.expression.operation.OperationType;
import org.dvare.expression.veriable.VariableExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Operation(type = OperationType.VALUE)
public class ValueOperation extends AggregationOperationExpression {
    static Logger logger = LoggerFactory.getLogger(ValueOperation.class);


    public ValueOperation() {
        super(OperationType.VALUE);
    }


    @Override
    public LiteralExpression interpret(InstancesBinding instancesBinding) throws InterpretException {

        if (!this.rightOperand.isEmpty()) {
            Expression right = this.rightOperand.get(0);
            if (right instanceof VariableExpression) {
                VariableExpression variableExpression = (VariableExpression) right;

                Object instance = instancesBinding.getInstance(variableExpression.getOperandType());
                if (instance instanceof List) {
                    List dataSet = (List) instance;
                    Object value = getValue(dataSet.get(0), variableExpression.getName());
                    return LiteralType.getLiteralExpression(value, variableExpression.getType());
                } else {
                    Object value = getValue(instance, variableExpression.getName());
                    return LiteralType.getLiteralExpression(value, variableExpression.getType());
                }


            } else if (right instanceof LiteralExpression) {
                return (LiteralExpression) right;
            } else if (right instanceof OperationExpression) {
                OperationExpression operation = (OperationExpression) right;
                Object result = operation.interpret(instancesBinding);
                if (result != null) {
                    return (LiteralExpression) result;
                }
            }
        }
        return new NullLiteral();
    }

}