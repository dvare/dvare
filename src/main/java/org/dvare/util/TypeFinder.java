package org.dvare.util;


import org.apache.commons.lang3.reflect.FieldUtils;
import org.dvare.binding.model.TypeBinding;
import org.dvare.expression.datatype.DataType;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;

public class TypeFinder {
    public static DataType findType(String name, TypeBinding typeBinding) {
        DataType variableType = null;
        if (name.contains(".")) {

            String fields[] = name.split(".");

            Iterator<String> iterator = Arrays.asList(fields).iterator();

            TypeBinding childType = typeBinding;
            while (iterator.hasNext()) {
                String field = iterator.next();

                if (iterator.hasNext()) {

                    Object newType = childType.getDataType(field);
                    if (newType instanceof TypeBinding) {
                        childType = (TypeBinding) newType;
                    }


                } else {
                    Object newType = childType.getDataType(field);
                    if (newType instanceof DataType) {
                        variableType = (DataType) newType;
                    }
                }

            }


        } else {
            String field = name;
            Object newType = typeBinding.getDataType(field);
            if (newType instanceof DataType) {

                variableType = (DataType) newType;
            }
        }
        return variableType;
    }

    public static DataType findType(String name, Class type) {
        DataType variableType = null;
        if (name.contains(".")) {

            String fields[] = name.split(".");

            Iterator<String> iterator = Arrays.asList(fields).iterator();

            Class childType = type;
            while (iterator.hasNext()) {
                String field = iterator.next();

                if (iterator.hasNext()) {

                    Field newType = FieldUtils.getDeclaredField(childType, field, true);
                    childType = newType.getType();

                } else {
                    Field newType = FieldUtils.getDeclaredField(childType, field, true);
                    if (newType != null) {
                        variableType = typeMapping(newType.getType());
                    }
                }

            }


        } else {
            String field = name;
            Field newType = FieldUtils.getDeclaredField(type, field, true);
            if (newType != null) {
                variableType = typeMapping(newType.getType());

            }
        }
        return variableType;
    }


    private static DataType typeMapping(Class type) {

        String simpleName = type.getSimpleName();


        if (simpleName.equals("int")) {
            return DataType.IntegerType;
        }

        simpleName = simpleName.substring(0, 1).toUpperCase() +
                simpleName.substring(1).toLowerCase();
        simpleName = simpleName + "Type";

        DataType dataType = DataType.valueOf(simpleName);

        return dataType;
    }


}