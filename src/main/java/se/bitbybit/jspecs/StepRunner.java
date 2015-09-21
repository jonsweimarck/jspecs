package se.bitbybit.jspecs;

import se.bitbybit.jspecs.stepdefinition.StepDefinitionParser;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class StepRunner {


    private Map<Pattern, StepDefinitionParser> pattern2Stepdef = new HashMap<>();

    public StepRunner(){
    }


//    public StepRunnerResult runStepsOnSpecExample(SpecificationSupplier sh) {
//
//        List<ComparableExecutableStepDefinition> orderedExecutableStepDefs = new ArrayList<>();
//        for(Map.Entry<Pattern, StepDefinitionParser> entry : pattern2Stepdef.entrySet()){
//
//
//            Matcher matcher = entry.getKey().matcher(sh.getKeyExample());
//            // check all occurance
//            while (matcher.find()) {
//                System.out.print("Start index: " + matcher.start());
//                System.out.print(" End index: " + matcher.end() + " ");
//                System.out.println(matcher.group());
//
//                orderedExecutableStepDefs.add(new ComparableExecutableStepDefinition(
//                        matcher.start(),
//                        entry.getValue().parse(matcher.group())));
//            }
//
//            for(ComparableExecutableStepDefinition orderedExecutableStepDef : orderedExecutableStepDefs){
//                orderedExecutableStepDef.execute();
//            }
//
//        }
//        return new StepRunnerResult();
//    }
}
