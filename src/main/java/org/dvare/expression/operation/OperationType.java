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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum OperationType {

    ABSOLUTE("Abs", "abs"), COMBINATION("Combination", "combination", "comb"), FUNCTION("Function", "function", "fun"),//
    LEFT_PRIORITY("("), RIGHT_PRIORITY(")"), TO_DATE("ToDate", "toDate"),
    FOUND("Found", "found"), POWER("Pow", "pow", "^"),//
    NOT("NOT", "Not", "not", "!"), AND("AND", "And", "and", "&&"), OR("OR", "or", "||"), IMPLIES("Implies", "implies", "=>"),//
    EQUAL("eq", "="), NOT_EQUAL("ne", "!=", "<>"),//
    LESS("lt", "<"), LESS_EQUAL("le", "<="), GREATER("gt", ">"), GREATER_EQUAL("ge", ">="),//
    IN("IN", "In", "in"), BETWEEN("Between", "between"), //
    MUL("Mul", "mul", "*"), DIVIDE("Div", "div", "/"), ADD("Add", "add", "+"), SUBTRACT("Sub", "sub", "-"),//
    MAX("Max", "max"), MIN("Min", "min"),//
    SUBSTRING("Substring", "substring"), CONCAT("concat", "Concat"), CONTAINS("Contains", "contains"), STARTS_WITH("startsWith", "Startswith", "StartsWith", "startswith"), ENDS_WITH("endsWith", "Endswith", "EndsWith", "endswith"),//
    IF("IF", "if", "ELSEIF", "elseif"), THEN("THEN", "then"), ELSE("ELSE", "else"), ENDIF("ENDIF", "endif"),//
    ASSIGN(":=", "assign", "update"), COUNT("Count", "count"), FIRST("First", "first"), LAST("Last", "last"),//
    MAXIMUM("Maximum", "maximum"), MINIMUM("Minimum", "minimum"),//
    MEAN("Mean", "mean", "Avg", "avg"), MEDIAN("Median", "median"), MODE("Mode", "mode"),//
    SUM("Sum", "sum"), VALUE("Value", "value"), COLON(";");

    private List<String> symbols = new ArrayList<>();

    OperationType() {
    }

    OperationType(String... symbols) {
        this.symbols.addAll(Arrays.asList(symbols));
    }

    public List<String> getSymbols() {
        return symbols;
    }

    public void setSymbols(List<String> symbols) {
        this.symbols = symbols;
    }
}