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


package org.dvare.config;

import org.dvare.binding.function.FunctionBinding;
import org.dvare.expression.operation.OperationExpression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum ConfigurationRegistry {

    INSTANCE;

    private final Map<String, FunctionBinding> functions = new HashMap<String, FunctionBinding>();

    private final Map<String, OperationExpression> operations = new HashMap<String, OperationExpression>();


    public List<String> tokens() {
        List<String> tokens = new ArrayList<>();
        for (String key : operations.keySet()) {
            tokens.add(key);
        }
        return tokens;
    }


    public void registerOperation(OperationExpression op) {
        for (String symbol : op.getSymbols()) {
            if (!operations.containsKey(symbol))
                operations.put(symbol, op);
        }
    }


    public void registerFunction(FunctionBinding binding) {
        // if (!functions.containsKey(binding.getMethodName()))
        functions.put(binding.getMethodName(), binding);
    }

    public OperationExpression getOperation(String symbol) {
        return this.operations.get(symbol);
    }


    public FunctionBinding getFunction(String name) {
        FunctionBinding functionBinding = this.functions.get(name);
        if (functionBinding != null) {
            return functionBinding.copy();
        }
        return null;
    }


    public List<String> getFunctionNames() {
        List<String> functionNames = new ArrayList<>();
        for (String key : functions.keySet()) {
            functionNames.add(key);
        }
        return functionNames;
    }
}
