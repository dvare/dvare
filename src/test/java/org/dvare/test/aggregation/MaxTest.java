package org.dvare.test.aggregation;

import org.dvare.binding.data.DataRow;
import org.dvare.binding.data.InstancesBinding;
import org.dvare.binding.model.ContextsBinding;
import org.dvare.binding.rule.RuleBinding;
import org.dvare.config.RuleConfiguration;
import org.dvare.exceptions.interpreter.InterpretException;
import org.dvare.exceptions.parser.ExpressionParseException;
import org.dvare.expression.Expression;
import org.dvare.parser.ExpressionParser;
import org.dvare.util.ValueFinder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaxTest {
    @Test
    public void dataSetMaxTest() throws ExpressionParseException, InterpretException {
        RuleConfiguration factory = new RuleConfiguration();
        final String exp = "self.A0 := data.V1 -> maximum ()";

        Map<String, String> aggregationTypes = new HashMap<>();
        aggregationTypes.put("A0", "IntegerType");
        Map<String, String> validationTypes = new HashMap<>();
        validationTypes.put("V1", "IntegerType");

        ContextsBinding contexts = new ContextsBinding();
        contexts.addContext("self", ExpressionParser.translate(aggregationTypes));
        contexts.addContext("data", ExpressionParser.translate(validationTypes));
        Expression aggregate = factory.getParser().fromString(exp, contexts);

        RuleBinding rule = new RuleBinding(aggregate);


        Map<String, Object> aggregation = new HashMap<>();
        aggregation.put("A0", 0);


        List<Object> dataSet = new ArrayList<>();
        Map<String, Object> d1 = new HashMap<>();
        d1.put("V1", 10);
        dataSet.add(new DataRow(d1));

        Map<String, Object> d2 = new HashMap<>();
        d2.put("V1", 20);
        dataSet.add(new DataRow(d2));

        Map<String, Object> d3 = new HashMap<>();
        d3.put("V1", 40);
        dataSet.add(new DataRow(d3));

        InstancesBinding instancesBinding = new InstancesBinding(new HashMap<>());
        instancesBinding.addInstance("self", new DataRow(aggregation));
        instancesBinding.addInstance("data", dataSet);
        Object resultModel = factory.getEvaluator().aggregate(rule, instancesBinding).getInstance("self");

        boolean result = ValueFinder.findValue("A0", resultModel).equals(40);

        Assertions.assertTrue(result);
    }

    @Test
    public void arrayMaxTest() throws ExpressionParseException, InterpretException {

        RuleConfiguration factory = new RuleConfiguration();


        Expression expression = factory.getParser().fromString("[1,9,5] -> maximum () = 9", new ContextsBinding());


        boolean result = (Boolean) factory.getEvaluator().evaluate(new RuleBinding(expression), new InstancesBinding());

        Assertions.assertTrue(result);
    }


}
