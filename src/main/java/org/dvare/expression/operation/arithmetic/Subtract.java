package org.dvare.expression.operation.arithmetic;

import org.dvare.annotations.Operation;
import org.dvare.expression.datatype.DataType;
import org.dvare.expression.operation.ArithmeticOperationExpression;
import org.dvare.expression.operation.OperationType;

/**
 * @author Muhammad Hammad
 * @since 2016-06-30
 */
@Operation(type = OperationType.SUBTRACT, dataTypes = {DataType.FloatType, DataType.IntegerType, DataType.DateType})
public class Subtract extends ArithmeticOperationExpression {
    public Subtract() {
        super(OperationType.SUBTRACT);
    }


}