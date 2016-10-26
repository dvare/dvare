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


package org.dvare.util;

import org.dvare.expression.datatype.*;

import java.util.Date;

public class DataTypeMapping {

    public static Class getDataTypeClass(String type) {
        return getDataTypeClass(DataType.valueOf(type));
    }

    public static Class getDataTypeClass(DataType type) {

        switch (type) {
            case BooleanType: {
                return BooleanType.class;

            }
            case FloatType: {

                return FloatType.class;
            }
            case IntegerType: {
                return IntegerType.class;

            }
            case StringType: {

                return StringType.class;
            }
            case DateTimeType: {

                return DateTimeType.class;
            }
            case DateType: {
                return DateType.class;

            }
            case RegexType: {

                return RegexType.class;
            }

        }
        return null;

    }

    public static DataType getTypeMapping(Class type) {
        return getTypeMapping(type.getSimpleName());
    }

    public static DataType getTypeMapping(String type) {

        switch (type) {
            case "Boolean": {
                return DataType.BooleanType;
            }

            case "boolean": {
                return DataType.BooleanType;
            }


            case "Integer": {
                return DataType.IntegerType;
            }


            case "int": {
                return DataType.IntegerType;
            }


            case "Float": {
                return DataType.FloatType;
            }


            case "float": {
                return DataType.FloatType;
            }

            case "String": {
                return DataType.StringType;
            }

            case "Date": {
                return DataType.DateType;
            }


        }
        return null;

    }

    public static Class getDataTypeMapping(String type) {
        return getDataTypeMapping(DataType.valueOf(type));
    }

    public static Class getDataTypeMapping(DataType type) {

        switch (type) {
            case BooleanType: {
                return Boolean.class;

            }
            case BooleanListType: {
                return Boolean[].class;

            }
            case FloatType: {

                return Float.class;
            }
            case FloatListType: {

                return Float[].class;
            }
            case IntegerType: {
                return Integer.class;

            }
            case IntegerListType: {
                return Integer[].class;

            }
            case StringType: {

                return String.class;
            }
            case StringListType: {

                return String[].class;
            }
            case DateTimeType: {

                return Date.class;
            }
            case DateTimeListType: {

                return Date[].class;
            }
            case DateType: {
                return Date.class;

            }
            case DateListType: {
                return Date[].class;

            }
            case RegexType: {

                return String.class;
            }

        }
        return null;

    }


    public static Class getDataTypeMappingArray(String type) {
        return getDataTypeMapping(DataType.valueOf(type));
    }

    public static Class getDataTypeMappingArray(DataType type) {

        switch (type) {
            case BooleanType:
            case BooleanListType: {
                return Boolean[].class;
            }

            case FloatType:
            case FloatListType: {
                return Float[].class;
            }

            case IntegerType:
            case IntegerListType: {
                return Integer[].class;
            }

            case StringType:
            case StringListType: {
                return String[].class;
            }

            case DateTimeType:
            case DateTimeListType: {
                return Date[].class;
            }

            case DateType:
            case DateListType: {
                return Date[].class;
            }

            case RegexType: {
                return String[].class;
            }

        }
        return null;

    }


}