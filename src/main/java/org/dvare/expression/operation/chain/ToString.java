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


package org.dvare.expression.operation.chain;

import org.dvare.annotations.Operation;
import org.dvare.exceptions.interpreter.InterpretException;
import org.dvare.expression.datatype.StringType;
import org.dvare.expression.literal.LiteralExpression;
import org.dvare.expression.literal.LiteralType;
import org.dvare.expression.literal.NullLiteral;
import org.dvare.expression.operation.ChainOperationExpression;
import org.dvare.expression.operation.OperationType;
import org.dvare.expression.operation.validation.Date;

@Operation(type = OperationType.TO_STRING)
public class ToString extends ChainOperationExpression {


    public ToString() {
        super(OperationType.TO_STRING);
    }

    public ToString copy() {
        return new ToString();
    }


    private Object toString(Object selfRow, Object dataRow) throws InterpretException {
        leftValueOperand = super.interpretOperand(this.leftOperand, leftOperandType, selfRow, dataRow);
        LiteralExpression literalExpression = toLiteralExpression(leftValueOperand);
        if (literalExpression != null && !(literalExpression instanceof NullLiteral)) {


            if (literalExpression.getValue() == null) {
                return new NullLiteral();
            }

            Object value = literalExpression.getValue();
            String valueString;


            if (value instanceof Date) {
                Date date = (Date) value;
                valueString = LiteralType.dateFormat.format(date);
            } else {
                valueString = value.toString();
            }

            if (valueString != null) {
                return LiteralType.getLiteralExpression(valueString, new StringType());
            }


        }

        return new NullLiteral<>();
    }

    @Override
    public Object interpret(Object dataRow) throws InterpretException {


        return toString(dataRow, null);

    }

    @Override
    public Object interpret(Object selfRow, Object dataRow) throws InterpretException {

        return toString(selfRow, dataRow);
    }


}