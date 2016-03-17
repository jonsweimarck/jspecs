package net.scatteredbits.jspecsdemo;

import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaSource;
import net.scatteredbits.jspecs.report.SpecificationAndKeyExamples;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ReportTest {

    @Test
    public void createReport(){
        JavaProjectBuilder builder = new JavaProjectBuilder();
        builder.addSourceTree(new File(
                "/Users/jons/projects/jspecs/jspecs-demo/src/test/java/net/scatteredbits/jspecsdemo"));
        Collection<JavaSource> sources = builder.getSources();
//        sources.forEach(
//                s -> s.getClasses().forEach(
//                        c ->c.getMethods().forEach(
//                                m -> m.getAnnotations().forEach(
//                                        a -> System.out.println(a.getType().getName())
//                                )
//                        )));

        List<SpecificationAndKeyExamples> specs = new ArrayList<>();
//        sources.stream()
//                .flatMap(s -> s.getClasses().stream())
//                .filter(c -> containsSpecification(c.getAnnotations())).map(c -> specs.add(toSpec(c)));


        specs.addAll(
                sources.stream()
                .flatMap(s -> s.getClasses().stream())
                .filter(c -> containsSpecification(c.getAnnotations()))
//                .map(c -> specs.add(toSpec(c)));
                .map(c -> toSpec(c)).collect(Collectors.toList())
        );


        specs.forEach(System.out::println);

    }

    private SpecificationAndKeyExamples toSpec(JavaClass javaClass) {
        JavaAnnotation specAnnotation = javaClass.getAnnotations().stream()
                .filter(a -> a.getType().getName().equals("Specification")).findFirst().get();

        String specificationName = (String)specAnnotation.getProperty("name").toString();
        String specificationDesc= (String)specAnnotation.getProperty("description").getParameterValue();

        List<String> keyExampleDescriptions =
                javaClass.getMethods().stream()
                .flatMap(m -> m.getAnnotations().stream()
                        .filter(a -> a.getType().getName().equals("KeyExample")))
                        .map(a -> a.getProperty("description"))
                        .map(av -> ((String)av.getParameterValue()))
                        .collect(Collectors.toList());


        return new SpecificationAndKeyExamples(specificationName, specificationDesc, keyExampleDescriptions);
    }

    private boolean containsSpecification(List<JavaAnnotation> annotations) {

        return annotations.stream().anyMatch(a -> a.getType().getName().equals("Specification"));
    }
}
