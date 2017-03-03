package org.dvare.test.aggregation;

import junit.framework.TestCase;
import org.dvare.binding.data.DataRow;
import org.dvare.binding.rule.RuleBinding;
import org.dvare.config.RuleConfiguration;
import org.dvare.evaluator.RuleEvaluator;
import org.dvare.exceptions.interpreter.InterpretException;
import org.dvare.exceptions.parser.ExpressionParseException;
import org.dvare.expression.Expression;
import org.dvare.util.ValueFinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConditionTest extends TestCase {

    public void testApp() throws ExpressionParseException, InterpretException {

        RuleConfiguration factory = new RuleConfiguration();

        Map<String, String> aggregationTypes = new HashMap<>();
        aggregationTypes.put("A0", "IntegerType");
        aggregationTypes.put("A1", "IntegerType");

        Map<String, String> validationTypes = new HashMap<>();
        validationTypes.put("V1", "IntegerType");


        Expression aggregate = factory.getParser().fromString("IF data.V1 > 5 THEN self.A1 := data.V1->sum () ELSE self.A1 := data.V1->maximum () ENDIF", aggregationTypes, validationTypes);

        RuleBinding rule = new RuleBinding(aggregate);
        List<RuleBinding> rules = new ArrayList<>();
        rules.add(rule);


        Map<String, Object> aggregation = new HashMap<>();
        aggregation.put("A0", 5);
        aggregation.put("A1", 0);

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

        RuleEvaluator evaluator = factory.getEvaluator();
        Object resultModel = evaluator.aggregate(rules, new DataRow(aggregation), dataSet);

        boolean result = ValueFinder.findValue("A1", resultModel).equals(70);

        assertTrue(result);
    }


    public void testApp2() throws ExpressionParseException, InterpretException {

        RuleConfiguration factory = new RuleConfiguration();

        Map<String, String> aggregationTypes = new HashMap<>();
        aggregationTypes.put("A0", "IntegerType");
        aggregationTypes.put("A1", "IntegerType");

        Map<String, String> validationTypes = new HashMap<>();
        validationTypes.put("V1", "IntegerType");

        Expression aggregate = factory.getParser().fromString("IF self.A0 <= 5 10 THEN self.A1 := data.V1->sum () ELSEIF self.A0 > 10 THEN self.A1 := data.V1->maximum () ENDIF", aggregationTypes, validationTypes);

        RuleBinding rule = new RuleBinding(aggregate);
        List<RuleBinding> rules = new ArrayList<>();
        rules.add(rule);


        Map<String, Object> aggregation = new HashMap<>();
        aggregation.put("A0", 12);
        aggregation.put("A1", 0);

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

        RuleEvaluator evaluator = factory.getEvaluator();
        Object resultModel = evaluator.aggregate(rules, new DataRow(aggregation), dataSet);

        boolean result = ValueFinder.findValue("A1", resultModel).equals(40);

        assertTrue(result);
    }


    public void testApp3() throws ExpressionParseException, InterpretException {

        RuleConfiguration factory = new RuleConfiguration();

        Map<String, String> aggregationTypes = new HashMap<>();
        aggregationTypes.put("A0", "IntegerType");
        aggregationTypes.put("A1", "IntegerType");

        Map<String, String> validationTypes = new HashMap<>();
        validationTypes.put("V1", "IntegerType");

        Expression aggregate = factory.getParser().fromString("IF self.A0 <= 5  THEN A1 := data.V1->sum () ELSEIF self.A0 > 10 THEN A1 := data.V1->maximum () ELSE A1 := data.V1->minimum () ENDIF", aggregationTypes, validationTypes);

        RuleBinding rule = new RuleBinding(aggregate);
        List<RuleBinding> rules = new ArrayList<>();
        rules.add(rule);

        Map<String, Object> aggregation = new HashMap<>();
        aggregation.put("A0", 7);
        aggregation.put("A1", 0);

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

        RuleEvaluator evaluator = factory.getEvaluator();
        Object resultModel = evaluator.aggregate(rules, new DataRow(aggregation), dataSet);

        boolean result = ValueFinder.findValue("A1", resultModel).equals(10);

        assertTrue(result);
    }

}
