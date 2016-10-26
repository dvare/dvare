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


package org.dvare.expression.datatype;


import org.dvare.annotations.OperationMapping;
import org.dvare.exceptions.interpreter.InterpretException;
import org.dvare.expression.Expression;
import org.dvare.expression.literal.LiteralDataType;
import org.dvare.expression.literal.LiteralExpression;
import org.dvare.expression.literal.LiteralType;
import org.dvare.expression.literal.NullLiteral;
import org.dvare.expression.operation.OperationExpression;
import org.dvare.expression.operation.ValidationOperationExpression;
import org.dvare.expression.veriable.VariableExpression;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

public abstract class DataTypeExpression extends Expression {

    protected DataType dataType;

    public DataTypeExpression(DataType dataType) {
        this.dataType = dataType;


    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }


    public LiteralExpression evaluate(OperationExpression operationExpression, Expression leftExpression, Expression rightExpression) throws InterpretException {

        LiteralExpression left = toLiteralExpression(leftExpression);
        LiteralExpression right = toLiteralExpression(rightExpression);

        if (left instanceof NullLiteral && right instanceof NullLiteral) {
            return new NullLiteral();
        }


        if (left instanceof NullLiteral) {
            switch (right.getType().getDataType()) {

                case FloatType: {
                    left = LiteralType.getLiteralExpression(Float.valueOf(0f), right.getType());
                    break;
                }
                case IntegerType: {
                    left = LiteralType.getLiteralExpression(Integer.valueOf(0), right.getType());
                    break;
                }
                case StringType: {
                    left = LiteralType.getLiteralExpression("", right.getType());
                    break;
                }

            }
        }

        String methodName = getMethodName(operationExpression.getClass());
        LiteralExpression resultExpression = evaluate(methodName, left, right);
        return resultExpression;
    }


    private LiteralExpression evaluate(String methodName, Expression left, Expression right) throws InterpretException {
        try {

            Method method = this.getClass().getMethod(methodName, LiteralExpression.class, LiteralExpression.class);
            Object result = method.invoke(this, left, right);

            DataType type = LiteralDataType.computeDataType(result.toString());
            if (type == null) {
                type = this.getDataType();
            }
            return LiteralType.getLiteralExpression(result.toString(), type);


        } catch (Exception m) {
            throw new InterpretException(m);
        }
    }


    public Boolean compare(ValidationOperationExpression operationExpression, LiteralExpression left, LiteralExpression right) throws InterpretException {

        String methodName = getMethodName(operationExpression.getClass());
        try {

            Method method = this.getClass().getMethod(methodName, LiteralExpression.class, LiteralExpression.class);
            return (Boolean) method.invoke(this, left, right);

        } catch (Exception m) {
            throw new InterpretException(m);
        }

    }


    private String getMethodName(Class operation) {
        for (Method method : this.getClass().getMethods()) {
            Annotation annotation = method.getAnnotation(OperationMapping.class);
            if (annotation != null && annotation instanceof OperationMapping) {
                OperationMapping operationMapping = (OperationMapping) annotation;
                if (Arrays.asList(operationMapping.operations()).contains(operation)) {
                    return method.getName();
                }

            }
        }
        return null;
    }

    private LiteralExpression toLiteralExpression(Expression expression) {

        LiteralExpression leftExpression = null;
        if (expression instanceof LiteralExpression) {
            leftExpression = (LiteralExpression) expression;
        } else if (expression instanceof VariableExpression) {
            VariableExpression variableExpression = (VariableExpression) expression;
            leftExpression = LiteralType.getLiteralExpression(variableExpression.getValue(), variableExpression.getType());
        }
        return leftExpression;
    }


}