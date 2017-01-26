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


package org.dvare.expression.literal;

import org.dvare.exceptions.parser.IllegalValueException;
import org.dvare.expression.datatype.DataType;
import org.dvare.expression.datatype.DataTypeExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LiteralType {
    public final static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy-HH:mm:ss");
    public final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    public static String date = "\\s*(0[1-9]|1[0-9]|2[0-9]|3[0-1])-(0[1-9]|1[0-2])-([1-9][0-9][0-9][0-9])\\s*";
    public static String dateTime = "\\s*(0[1-9]|1[0-9]|2[0-9]|3[0-1])-(0[1-9]|1[0-2])-([1-9][0-9][0-9][0-9])\\-{1}(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])\\s*";
    private static Logger logger = LoggerFactory.getLogger(LiteralType.class);


    public static LiteralExpression<?> getLiteralExpression(String value) throws IllegalValueException {
        DataType type = computeDataType(value);
        return getLiteralExpression(value, type);
    }

    public static LiteralExpression<?> getLiteralExpression(Object value, DataType type) throws IllegalValueException {
        if (value == null) {
            throw new IllegalValueException("The Literal Expression is null");
        }

        String valueString = value.toString();


        DataType rightType = computeDataType(valueString);
        if (type != null && (rightType == DataType.RegexType)) {
            type = rightType;
        }

        LiteralExpression literalExpression = null;

      /*  if (type == null) {
            type = DataType.NullType;
        }*/
        if (type == null) {
            throw new IllegalValueException("Unable to parse Literal " + value + ". type is null");
        }
        switch (type) {


            case BooleanType: {
                if (value instanceof Boolean) {
                    literalExpression = new BooleanLiteral<>(value);
                } else {
                    literalExpression = new BooleanLiteral<>(Boolean.parseBoolean(valueString));
                }
                break;
            }
            case FloatType: {
                try {
                    if (value instanceof Float) {
                        literalExpression = new FloatLiteral<>(value);
                    } else {
                        literalExpression = new FloatLiteral<>(Float.parseFloat(valueString));
                    }
                } catch (NumberFormatException e) {
                    String message = String.format("Unable to Parse literal %s to Float", valueString);
                    logger.error(message);
                    throw new IllegalValueException(message, e);
                }
                break;
            }
            case IntegerType: {

                try {
                    if (value instanceof Integer) {
                        literalExpression = new IntegerLiteral<>(value);
                    } else {
                        literalExpression = new IntegerLiteral<>(Integer.parseInt(valueString));
                    }
                } catch (NumberFormatException e) {
                    String message = String.format("Unable to Parse literal %s to Integer", valueString);
                    logger.error(message);
                    throw new IllegalValueException(message, e);
                }
                break;
            }
            case StringType: {

                literalExpression = new StringLiteral<>(valueString);
                break;
            }
            case RegexType: {
                valueString = valueString.substring(1, valueString.length()).trim();
                literalExpression = new RegexLiteral<>(valueString);
                break;
            }

            case DateTimeType: {
                Date date;
                try {

                    if (value instanceof Date) {
                        date = (Date) value;
                    } else {
                        date = dateTimeFormat.parse(valueString);
                    }

                } catch (ParseException e) {
                    String message = String.format("Unable to Parse literal %s to Date Time", valueString);
                    logger.error(message);
                    throw new IllegalValueException(message, e);
                }
                literalExpression = new DateTimeLiteral<>(date);
                break;
            }

            case DateType: {

                Date date;
                try {
                    if (value instanceof Date) {
                        date = (Date) value;
                    } else {
                        date = dateFormat.parse(valueString);
                    }
                } catch (ParseException e) {
                    String message = String.format("Unable to Parse literal %s to Date", valueString);
                    logger.error(message);
                    throw new IllegalValueException(message, e);

                }
                literalExpression = new DateLiteral<>(date);
                break;
            }
            case NullType: {
                literalExpression = new NullLiteral();
                break;
            }


        }

        if (literalExpression != null) {
            logger.debug("{} Expression : {} [{}]", literalExpression.getClass().getSimpleName(), literalExpression.getType().getDataType(), literalExpression.getValue());
            return literalExpression;
        } else {
            throw new IllegalValueException("Literal Expression is Null");
        }
    }


    public static LiteralExpression<?> getLiteralExpression(Object value, DataTypeExpression type) {

        if (value == null) {
            return new NullLiteral();
        }
        return new LiteralExpression<>(value, type);
    }


    public static DataType computeDataType(String value) {

        if ("null".equals(value) || "NULL".equals(value)) {
            return DataType.NullType;
        }

        if ("true".equals(value) || "false".equals(value)) {
            return DataType.BooleanType;
        }
        if (value.startsWith("'") && value.endsWith("'")) {
            return DataType.StringType;
        }
        if (value.startsWith("R'")) {
            return DataType.RegexType;
        }
        if (value.matches(date)) {
            return DataType.DateType;
        }

        if (value.matches(dateTime)) {
            return DataType.DateTimeType;
        }

        if (value.contains(".")) {
            try {
                Float.parseFloat(value);
                return DataType.FloatType;
            } catch (NumberFormatException e) {
            }
        }


        try {
            Integer.parseInt(value);
            return DataType.IntegerType;
        } catch (NumberFormatException e) {
        }


      /*  return DataType.NullType;*/
        return null;
    }
}
