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


package org.dvare.evaluator;


import org.dvare.binding.rule.RuleBinding;
import org.dvare.exceptions.interpreter.InterpretException;
import org.dvare.expression.literal.LiteralExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RuleEvaluator {
    static Logger logger = LoggerFactory.getLogger(RuleEvaluator.class);

    public Object evaluate(RuleBinding rule, Object object) throws InterpretException {
        Object result = null;
        Object ruleRawResult = rule.getExpression().interpret(object);
        if (ruleRawResult instanceof LiteralExpression) {
            LiteralExpression literalExpression = (LiteralExpression) ruleRawResult;
            if (literalExpression.getValue() != null) {
                result = literalExpression.getValue();
            }
        } else {
            result = ruleRawResult;
        }
        return result;
    }

    public Object evaluate(List<RuleBinding> rules, Object aggregate, List<Object> dataset) throws InterpretException {
        for (RuleBinding rule : rules) {
            aggregate = rule.getExpression().interpret(aggregate, dataset);
        }
        return aggregate;
    }

    public Object evaluate(List<RuleBinding> rules, Object aggregate, Object dataset) throws InterpretException {
        for (RuleBinding rule : rules) {
            aggregate = rule.getExpression().interpret(aggregate, dataset);
        }
        return aggregate;
    }

}