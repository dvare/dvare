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


package org.dvare.expression.operation;

import org.dvare.binding.model.ContextsBinding;
import org.dvare.config.ConfigurationRegistry;
import org.dvare.exceptions.parser.ExpressionParseException;
import org.dvare.expression.Expression;
import org.dvare.expression.literal.LiteralExpression;
import org.dvare.expression.literal.NullLiteral;
import org.dvare.expression.operation.validation.RightPriority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;

public abstract class LogicalOperationExpression extends OperationExpression {
    protected static Logger logger = LoggerFactory.getLogger(LogicalOperationExpression.class);

    public LogicalOperationExpression(OperationType operationType) {
        super(operationType);
    }


    @Override
    public Integer parse(String[] tokens, int pos, Stack<Expression> stack, ContextsBinding contexts) throws ExpressionParseException {
        Expression left = stack.pop();

        pos = findNextExpression(tokens, pos + 1, stack, contexts);

        Expression right = stack.pop();

        this.leftOperand = left;
        this.rightOperand = right;

        logger.debug("OperationExpression Call Expression : {}", getClass().getSimpleName());

        stack.push(this);

        return pos;
    }


    @Override
    public Integer findNextExpression(String[] tokens, int pos, Stack<Expression> stack, ContextsBinding contexts) throws ExpressionParseException {
        ConfigurationRegistry configurationRegistry = ConfigurationRegistry.INSTANCE;
        for (; pos < tokens.length; pos++) {
            OperationExpression op = configurationRegistry.getOperation(tokens[pos]);
            if (op != null) {

                if (op instanceof RightPriority) {
                    return pos - 1;
                }


                pos = op.parse(tokens, pos, stack, contexts);


                if (pos + 1 < tokens.length) {
                    OperationExpression testOp = configurationRegistry.getOperation(tokens[pos + 1]);
                    if (testOp instanceof LogicalOperationExpression) {
                        return pos;
                    }
                }


                /*if (!stack.isEmpty() && stack.peek() instanceof ChainOperationExpression) {
                    continue;
                } else {
                    return pos;
                }*/

            }
        }
        return pos;
    }

    protected Boolean toBoolean(Object interpret) {
        Boolean result = false;
        if (interpret instanceof LiteralExpression) {


            if (!(interpret instanceof NullLiteral) && ((LiteralExpression) interpret).getValue() != null) {
                result = (Boolean) ((LiteralExpression) interpret).getValue();
            }

        } else if (interpret != null) {
            result = (Boolean) interpret;
        }
        return result;
    }

}