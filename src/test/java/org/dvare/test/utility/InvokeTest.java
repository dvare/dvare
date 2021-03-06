package org.dvare.test.utility;


import org.dvare.binding.model.ContextsBinding;
import org.dvare.binding.model.TypeBinding;
import org.dvare.binding.rule.RuleBinding;
import org.dvare.config.RuleConfiguration;
import org.dvare.evaluator.RuleEvaluator;
import org.dvare.exceptions.interpreter.InterpretException;
import org.dvare.exceptions.parser.ExpressionParseException;
import org.dvare.expression.Expression;
import org.dvare.parser.ExpressionParser;
import org.dvare.test.dataobjects.EqualOperation;
import org.dvare.util.ClassUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

public class InvokeTest {

    @Test
    public void testApp1() throws ExpressionParseException, InterpretException {
        RuleConfiguration factory = new RuleConfiguration();


        String exp = " invoke (Variable2#toString) = '5'";


        TypeBinding typeBinding = ExpressionParser.translate(EqualOperation.class);
        ContextsBinding contexts = new ContextsBinding();
        contexts.addContext("self", typeBinding);

        Expression expression = factory.getParser().fromString(exp, contexts);
        RuleBinding rule = new RuleBinding(expression);

        EqualOperation equalOperation = new EqualOperation();
        equalOperation.setVariable1("dvare");
        equalOperation.setVariable2(5);


        RuleEvaluator evaluator = factory.getEvaluator();
        boolean result = (Boolean) evaluator.evaluate(rule, equalOperation);
        Assertions.assertTrue(result);
    }

    @Test
    public void testApp2() throws ExpressionParseException, InterpretException {
        RuleConfiguration factory = new RuleConfiguration();


        String exp = "invoke (Variable1#substring, 0,2) = 'dv'";


        TypeBinding typeBinding = ExpressionParser.translate(EqualOperation.class);
        ContextsBinding contexts = new ContextsBinding();
        contexts.addContext("self", typeBinding);

        Expression expression = factory.getParser().fromString(exp, contexts);
        RuleBinding rule = new RuleBinding(expression);

        EqualOperation equalOperation = new EqualOperation();
        equalOperation.setVariable1("dvare");


        RuleEvaluator evaluator = factory.getEvaluator();
        boolean result = (Boolean) evaluator.evaluate(rule, equalOperation);
        Assertions.assertTrue(result);
    }

    @Test
    public void testApp3() {

        Method method = ClassUtils.getAccessibleMethod(String.class, "substring", int.class, int.class);
        Assertions.assertNotNull(method);
    }


}
