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

import org.apache.commons.lang3.tuple.Pair;
import org.dvare.annotations.Operation;
import org.dvare.binding.data.InstancesBinding;
import org.dvare.exceptions.interpreter.InterpretException;
import org.dvare.expression.Expression;
import org.dvare.expression.datatype.DataType;
import org.dvare.expression.literal.ListLiteral;
import org.dvare.expression.literal.LiteralExpression;
import org.dvare.expression.literal.NullLiteral;
import org.dvare.expression.operation.ListOperationExpression;
import org.dvare.expression.operation.OperationType;
import org.dvare.util.DataTypeMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Operation(type = OperationType.VALUES)
public class ValuesOperation extends ListOperationExpression {
    private static Logger logger = LoggerFactory.getLogger(ValuesOperation.class);


    public ValuesOperation() {
        super(OperationType.VALUES);
    }


    @Override
    public LiteralExpression interpret(InstancesBinding instancesBinding) throws InterpretException {

        List<?> values = extractValues(instancesBinding, leftOperand);

        if (values != null) {

            if (isPairList(values)) {
                values = extractPairValues(values);
            }

            if (!rightOperand.isEmpty()) {
                if (rightOperand.size() == 1) {
                    Expression includeParam = rightOperand.get(0);
                    values = includedFilter(includeParam, instancesBinding, values);

                } else if (rightOperand.size() == 2) {
                    Expression includeParam = rightOperand.get(0);
                    Expression excludeParam = rightOperand.get(1);
                    values = excludedFilter(includeParam, excludeParam, instancesBinding, values);
                }
            }

            return new ListLiteral(values, dataTypeExpression);

        }
        return new NullLiteral();
    }

    private List<?> extractPairValues(List pairList) {
        List<Object> pairValues = new ArrayList<>();
        dataTypeExpression = null;
        for (Object pairObject : pairList) {
            if (pairObject instanceof Pair) {
                Pair pair = (Pair) pairObject;
                if (dataTypeExpression == null && pair.getValue() != null) {
                    DataType dataType = DataTypeMapping.getTypeMapping(pair.getValue().getClass());
                    dataTypeExpression = DataTypeMapping.getDataTypeClass(dataType);
                }
                pairValues.add(pair.getValue());
            }
        }
        return pairValues;
    }
}