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
package org.dvare.expression.operation.relational;

import org.dvare.annotations.Operation;
import org.dvare.binding.model.ContextsBinding;
import org.dvare.exceptions.parser.ExpressionParseException;
import org.dvare.exceptions.parser.IllegalOperationException;
import org.dvare.expression.Expression;
import org.dvare.expression.literal.ListLiteral;
import org.dvare.expression.literal.LiteralExpression;
import org.dvare.expression.operation.OperationType;
import org.dvare.expression.operation.RelationalOperationExpression;
import org.dvare.expression.veriable.ListVariable;
import org.dvare.expression.veriable.VariableExpression;
import org.dvare.parser.ExpressionTokenizer;

import java.util.Stack;

@Operation(type = OperationType.IN)
public class In extends RelationalOperationExpression {
    public In() {
        super(OperationType.IN);
    }


    public In(OperationType operationType) {
        super(operationType);
    }

    @Override
    public Integer parse(final String[] tokens, int pos, Stack<Expression> stack, ContextsBinding contexts) throws ExpressionParseException {
        if (pos - 1 >= 0 && tokens.length >= pos + 1) {
            pos = parseOperands(tokens, pos, stack, contexts);
            testInOperation(tokens, pos);
            stack.push(this);
            return pos;
        }
        throw new ExpressionParseException("Cannot assign literal to variable");
    }


    private void testInOperation(String[] tokens, int pos) throws ExpressionParseException {
        Expression left = this.leftOperand;
        Expression right = this.rightOperand;

        String message = null;

        if (right instanceof VariableExpression && !(right instanceof ListVariable)) {
            VariableExpression variableExpression = (VariableExpression) right;

            message = String.format("List OperationExpression %s not possible on type %s near %s", this.getClass().getSimpleName(), toDataType(variableExpression.getType()), ExpressionTokenizer.toString(tokens, pos + 2));


        } else if (right instanceof LiteralExpression) {
            LiteralExpression literalExpression = (LiteralExpression) right;
            if (!(literalExpression instanceof ListLiteral)) {
                message = String.format("List OperationExpression %s not possible on type %s near %s", this.getClass().getSimpleName(), toDataType(literalExpression.getType()), ExpressionTokenizer.toString(tokens, pos + 2));

            }
        }

        if (message != null) {
            logger.error(message);
            throw new IllegalOperationException(message);

        }

/*
        if (dataTypeExpression != null && !isLegalOperation(dataTypeExpression.getDataType())) {

            String message2 = String.format("OperationExpression %s not possible on type %s near %s", this.getClass().getSimpleName(), left.getClass().getSimpleName(), ExpressionTokenizer.toString(tokens, pos + 2));
            logger.error(message2);
            throw new IllegalOperationException(message2);
        }*/
        if (logger.isDebugEnabled()) {
            logger.debug("OperationExpression Call Expression : {}", getClass().getSimpleName());
        }
    }
}