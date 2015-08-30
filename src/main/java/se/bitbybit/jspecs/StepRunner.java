package se.bitbybit.jspecs;

import se.bitbybit.jspecs.stepdefinition.OrderedExecutableStepDefinition;
import se.bitbybit.jspecs.stepdefinition.StepDefinitionRegexpParser;
import se.bitbybit.jspecs.stepdefinition.StepDefinitionRegexpParserForList;
import se.bitbybit.jspecs.stepdefinition.StepDefinitionRegexpParserForObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static se.bitbybit.jspecs.stepdefinition.StepDefinition.StepDefinitionForList;
import static se.bitbybit.jspecs.stepdefinition.StepDefinition.StepDefinitionForObject;

public class StepRunner {


    private Map<Pattern, StepDefinitionRegexpParser> pattern2Stepdef = new HashMap<>();

    public StepRunner(){
    }

    public void addStepDef(String pattern, StepDefinitionForObject stepDef) {
        StepDefinitionRegexpParserForObject stepDefparser = new StepDefinitionRegexpParserForObject(pattern, stepDef);
        pattern2Stepdef.put(stepDefparser.patternAsRegexp(), stepDefparser);
    }

    public void addStepDef(String pattern, StepDefinitionForList stepDef) {
        StepDefinitionRegexpParserForList stepDefParser = new StepDefinitionRegexpParserForList(pattern, stepDef);
        pattern2Stepdef.put(stepDefParser.patternAsRegexp(), stepDefParser );
    }

    public StepRunnerResult runStepsOnSpecExample(SpecificationSupplier sh) {

        List<OrderedExecutableStepDefinition> orderedExecutableStepDefs = new ArrayList<>();
        for(Map.Entry<Pattern, StepDefinitionRegexpParser> entry : pattern2Stepdef.entrySet()){


            Matcher matcher = entry.getKey().matcher(sh.getKeyExample());
            // check all occurance
            while (matcher.find()) {
                System.out.print("Start index: " + matcher.start());
                System.out.print(" End index: " + matcher.end() + " ");
                System.out.println(matcher.group());

                orderedExecutableStepDefs.add(new OrderedExecutableStepDefinition(
                        matcher.start(),
                        entry.getValue().parse(matcher.group())));
            }

            for(OrderedExecutableStepDefinition orderedExecutableStepDef : orderedExecutableStepDefs){
                orderedExecutableStepDef.execute();
            }

        }
        return new StepRunnerResult();
    }
}
