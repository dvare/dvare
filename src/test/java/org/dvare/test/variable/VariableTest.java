package org.dvare.test.variable;


import org.dvare.binding.model.ContextsBinding;
import org.dvare.binding.rule.RuleBinding;
import org.dvare.config.RuleConfiguration;
import org.dvare.evaluator.RuleEvaluator;
import org.dvare.exceptions.interpreter.InterpretException;
import org.dvare.exceptions.parser.ExpressionParseException;
import org.dvare.expression.Expression;
import org.dvare.test.dataobjects.AllArrayVariable;
import org.dvare.test.dataobjects.AllListVariable;
import org.dvare.test.dataobjects.AllVariable;
import org.dvare.util.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

public class VariableTest {

    @Test
    public void variableLiteralComparisonTest() throws ExpressionParseException, InterpretException, ParseException {

        RuleConfiguration factory = new RuleConfiguration();

        String exp = "Variable1 = 'dvare' " +
                "and Variable2 = 2016 " +
                "and Variable3 = 20.16 " +
                "and Variable4 = true " +
                "and Variable5 -> toDate() = date ( 12-05-2016 , dd-MM-yyyy ) " +
                "and Variable6  = date ( 12-05-2016 , dd-MM-yyyy ) " +
                "and Variable7 = dateTime ( 12-05-2016-15:30:00 , dd-MM-yyyy-HH:mm:ss ) " +
                "and Variable8 = toPair('dvare' , 'framework') ";


        ContextsBinding contexts = new ContextsBinding();
        contexts.addContext("self", AllVariable.class);

        Expression expression = factory.getParser().fromString(exp, contexts);
        RuleBinding rule = new RuleBinding(expression);

        AllVariable allVariable = new AllVariable();
        allVariable.setVariable1("dvare");
        allVariable.setVariable2(2016);
        allVariable.setVariable3(20.16f);
        allVariable.setVariable4(true);

        allVariable.setVariable5(Date.from(LocalDate.of(2016, 5, 12)
                .atStartOfDay(ZoneId.systemDefault()).toInstant()));
        allVariable.setVariable6(LocalDate.of(2016, 5, 12));
        allVariable.setVariable7(LocalDateTime.of(2016, 5, 12, 15, 30, 0));
        allVariable.setVariable8(new Pair.PairImpl<>("dvare", "framework"));

        RuleEvaluator evaluator = factory.getEvaluator();
        boolean result = (Boolean) evaluator.evaluate(rule, allVariable);
        Assertions.assertTrue(result);
    }


    @Test
    public void variableLiteralArrayComparisonTest() throws ExpressionParseException, InterpretException, ParseException {

        RuleConfiguration factory = new RuleConfiguration();

        String exp = "Variable1 = ['dvare','framework'] " +
                "and Variable2 = [ 2016 , 2017 ] " +
                "and Variable3 = [20.16 , 20.17] " +
                "and Variable4 = [ true , false ] " +
                "and Variable5  = [date ( 12-05-2016 , dd-MM-yyyy ), date ( 15-06-2017 , dd-MM-yyyy )] " +
                "and Variable6  = [date ( 12-05-2016 , dd-MM-yyyy ), date ( 15-06-2017 , dd-MM-yyyy )] " +
                "and Variable7 = [ dateTime ( 12-05-2016-15:30:00 , dd-MM-yyyy-HH:mm:ss ) ,dateTime ( 15-06-2017-15:30:00 , dd-MM-yyyy-HH:mm:ss ) ] " +
                "and Variable8 = [toPair('dvare' , 'framework') , toPair('rule' , 'engine')] ";


        ContextsBinding contexts = new ContextsBinding();
        contexts.addContext("self", AllListVariable.class);

        Expression expression = factory.getParser().fromString(exp, contexts);
        RuleBinding rule = new RuleBinding(expression);

        AllListVariable allVariable = new AllListVariable();
        allVariable.setVariable1(Arrays.asList("dvare", "framework"));
        allVariable.setVariable2(Arrays.asList(2016, 2017));
        allVariable.setVariable3(Arrays.asList(20.16f, 20.17f));
        allVariable.setVariable4(Arrays.asList(true, false));

        allVariable.setVariable5(Arrays.asList(
                Date.from(LocalDate.of(2016, 5, 12)
                        .atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(LocalDate.of(2017, 6, 15)
                        .atStartOfDay(ZoneId.systemDefault()).toInstant())
        ));

        allVariable.setVariable6(Arrays.asList(
                LocalDate.of(2016, 5, 12),
                LocalDate.of(2017, 6, 15)));

        allVariable.setVariable7(Arrays.asList(
                LocalDateTime.of(2016, 5, 12, 15, 30, 0),//
                LocalDateTime.of(2017, 6, 15, 15, 30, 0)));

        allVariable.setVariable8(Arrays.asList(
                new Pair.PairImpl<>("dvare", "framework"),
                new Pair.PairImpl<>("rule", "engine")
        ));

        RuleEvaluator evaluator = factory.getEvaluator();
        boolean result = (Boolean) evaluator.evaluate(rule, allVariable);
        Assertions.assertTrue(result);
    }

    @Test
    public void variableLiteralArrayNotInTest() throws ExpressionParseException, InterpretException, ParseException {

        RuleConfiguration factory = new RuleConfiguration();

        String exp = "Variable1 != ['dvare','engine'] " +
                "and Variable2 != [ 2016 , 2018 ] " +
                "and Variable3 != [20.16 , 20.18] " +
                "and Variable4 != [ true , true ] " +
                "and Variable5 != [date ( 12-05-2016 , dd-MM-yyyy ), date ( 15-06-2015 , dd-MM-yyyy )] " +
                "and Variable6 != [date ( 12-05-2016 , dd-MM-yyyy ), date ( 15-06-2015 , dd-MM-yyyy )] " +
                "and Variable7 != [ dateTime ( 12-05-2016-15:30:00 , dd-MM-yyyy-HH:mm:ss ) ,dateTime ( 15-06-2015-15:30:00 , dd-MM-yyyy-HH:mm:ss ) ] " +
                "and Variable8 != [toPair('dvare' , 'engine') , toPair('rule' , 'framework')] ";


        ContextsBinding contexts = new ContextsBinding();
        contexts.addContext("self", AllArrayVariable.class);

        Expression expression = factory.getParser().fromString(exp, contexts);
        RuleBinding rule = new RuleBinding(expression);

        AllArrayVariable allVariable = new AllArrayVariable();
        allVariable.setVariable1(new String[]{"dvare", "framework"});
        allVariable.setVariable2(new Integer[]{2016, 2017});
        allVariable.setVariable3(new Float[]{20.16f, 20.17f});
        allVariable.setVariable4(new Boolean[]{true, false});

        allVariable.setVariable5(new Date[]{
                Date.from(LocalDate.of(2016, 5, 12)
                        .atStartOfDay(ZoneId.systemDefault()).toInstant()),
                Date.from(LocalDate.of(2017, 6, 15)
                        .atStartOfDay(ZoneId.systemDefault()).toInstant())
        });

        allVariable.setVariable6(new LocalDate[]{
                LocalDate.of(2016, 5, 12),
                LocalDate.of(2017, 6, 15)});

        allVariable.setVariable7(new LocalDateTime[]{
                LocalDateTime.of(2016, 5, 12, 15, 30, 0),//
                LocalDateTime.of(2017, 6, 15, 15, 30, 0)
        });

        allVariable.setVariable8(new Pair[]{
                new Pair.PairImpl<>("dvare", "framework"),
                new Pair.PairImpl<>("rule", "engine")
        });

        RuleEvaluator evaluator = factory.getEvaluator();
        boolean result = (Boolean) evaluator.evaluate(rule, allVariable);
        Assertions.assertTrue(result);
    }


    @Test
    public void listVariableTest() throws ExpressionParseException, InterpretException, ParseException {

        RuleConfiguration factory = new RuleConfiguration();

        String exp = "Variable1 = ['dvare'] " +
                "and Variable2 = [2016] " +
                "and Variable3 = [20.16] " +
                "and Variable4 = [true] " +
                "and Variable5  = [date ( 12-05-2016 , dd-MM-yyyy )] " +
                "and Variable6  = [date ( 12-05-2016 , dd-MM-yyyy )] " +
                "and Variable7 = [dateTime ( 12-05-2016-15:30:00 , dd-MM-yyyy-HH:mm:ss )] " +
                "and Variable8 = [toPair('dvare' , 'framework')] ";


        ContextsBinding contexts = new ContextsBinding();
        contexts.addContext("self", AllArrayVariable.class);

        Expression expression = factory.getParser().fromString(exp, contexts);
        RuleBinding rule = new RuleBinding(expression);

        AllVariable allVariable = new AllVariable();
        allVariable.setVariable1("dvare");
        allVariable.setVariable2(2016);
        allVariable.setVariable3(20.16f);
        allVariable.setVariable4(true);

        allVariable.setVariable5(Date.from(LocalDate.of(2016, 5, 12)
                .atStartOfDay(ZoneId.systemDefault()).toInstant()));
        allVariable.setVariable6(LocalDate.of(2016, 5, 12));
        allVariable.setVariable7(LocalDateTime.of(2016, 5, 12, 15, 30, 0));
        allVariable.setVariable8(new Pair.PairImpl<>("dvare", "framework"));

        RuleEvaluator evaluator = factory.getEvaluator();
        boolean result = (Boolean) evaluator.evaluate(rule, allVariable);
        Assertions.assertTrue(result);
    }

}
