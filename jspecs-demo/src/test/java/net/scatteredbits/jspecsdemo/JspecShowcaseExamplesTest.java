package net.scatteredbits.jspecsdemo;


import net.scatteredbits.jspecs.DefaultKeyExampleExecuter;
import net.scatteredbits.jspecs.ExecutableStepDefinitionSorter;
import net.scatteredbits.jspecs.builders.*;
import net.scatteredbits.jspecs.junitspecific.KeyExample;
import net.scatteredbits.jspecs.junitspecific.Specification;
import net.scatteredbits.jspecs.junitspecific.SpecificationCounter;
import net.scatteredbits.jspecs.junitspecific.SpecificationLogger;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static net.scatteredbits.jspecs.builders.SearchPatterns.PlaceHolderType.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;

@Specification(
        name= "Basic features",
        description =
                "JSpecs should let the developer map Integer and String values inside\n" +
                "key example texts, to method calls. Lists and tables of the above types is also supported.\n" +
                "Both lists and tables are written in Markdown(ish) style, lists with a * preceding each row \n" +
                "and tables using | to separate columns.\n" +
                "If many values are used in an example, they can each be mapped to each own method taking a single parameter,\n" +
                "or you can let two values map to a method taking two parameters.")
public class JspecShowcaseExamplesTest {

    @Rule
    public SpecificationLogger logger = new SpecificationLogger();

    @AfterClass
    public void beforeClass(){
        SpecificationCounter.INSTANCE.inc();
    }

    @AfterClass
    public void afterClass(){
        SpecificationCounter.INSTANCE.dec();
    }

    private DefaultKeyExampleExecuter defaultKeyExampleExecuter = new DefaultKeyExampleExecuter(new ExecutableStepDefinitionSorter());
    private String string1;
    private String string2;
    private Integer integer;
    private List<String> stringList = new ArrayList<>();
    private List<Integer> integerList = new ArrayList<>();


    @Test
    @KeyExample(description =
            "Starting with the list " +
            "* Apple " +
            "* Pear " +
            "If we invoke the method size on it, we will get the result 2")
    public void callingSizeOnANonEmptyList(){

        List<String> cut = new ArrayList<>();

        SearchPatterns sps = new SearchPatterns()
                .add(
                        new SearchPattern("Starting with the list {list}")
                                .binding("{list}", asStringList)
                                .to(new OneArgMethod<List<String>>(cut::addAll))
                ).add(
                        new SearchPattern("method size on it, we will get the result {integer}")
                                .binding("{integer}", asInteger)
                                .to(new OneArgMethod<Integer>(i -> assertThat(cut.size(), is(i))))
                );

        ExecutableKeyExample
                .forKeyExample(logger.getKeyExample())
                .withSearchPatterns(sps)
                .run(new DefaultKeyExampleExecuter(new ExecutableStepDefinitionSorter()));
    }

    @Test
    @KeyExample(description = "Starting with the list " +
            "* Apple " +
            "* Pear " +
            "If we add the String Tomato, the list will look like " +
            "* Apple " +
            "* Pear " +
            "* Tomato")
    public void addingAnElementShouldPutTheElementLast(){

        List<String> cut = new ArrayList<>();

        SearchPatterns sps = new SearchPatterns()
                .add(
                        new SearchPattern("Starting with the list {list}")
                            .binding("{list}", asStringList)
                            .to(new OneArgMethod<List<String>>(cut::addAll))
                ).add(
                        new SearchPattern("add the String {string}")
                            .binding("{string}", asString)
                            .to(new OneArgMethod<String>(cut::add))
                );

        ExecutableKeyExample
                .forKeyExample(logger.getKeyExample())
                .withSearchPatterns(sps)
                .run(new DefaultKeyExampleExecuter(new ExecutableStepDefinitionSorter()));

        assertThat(cut, contains("Apple", "Pear", "Tomato"));
    }



    @Test
    @KeyExample(description =
            "It should be possible to map a string like Hello to one single arg method\n" +
                    "and an integer like 5 to another single arg method.")
    public void twoSearchPatternEachWithASingleBindingToOneMethod(){


        SearchPatterns sps = new SearchPatterns()
                .add(
                        new SearchPattern("a string like {string}")
                                .binding("{string}", asString)
                                .to(new OneArgMethod<>(this::setString1))
                ).add(
                        new SearchPattern("an integer like {integer}")
                                .binding("{integer}", asInteger)
                                .to(new OneArgMethod<>(this::setInteger))
                );

        ExecutableKeyExample
                .forKeyExample(logger.getKeyExample())
                .withSearchPatterns(sps)
                .run(defaultKeyExampleExecuter);

        assertThat(string1, is("Hello"));
        assertThat(integer, is(5));
    }

    @Test
    @KeyExample(description = "It should be possible to map a string like Hello and an integer like 5 as parameters to a two args method.")
    public void singleSearchPatternWithTwoBindingsToOneMethod(){

        SearchPatterns sps = new SearchPatterns()
                .add(
                        new SearchPattern("a string like {string} and an integer like {integer}")
                        .binding("{string}", asString)
                        .binding("{integer}", asInteger)
                        .to(new TwoArgMethod<>(this::setStringAndInteger))
                );

        ExecutableKeyExample
                .forKeyExample(logger.getKeyExample())
                .withSearchPatterns(sps)
                .run(defaultKeyExampleExecuter);

        assertThat(string1, is("Hello"));
        assertThat(integer, is(5));
    }


    @Test
    @KeyExample(description =
            "It should be possible to map a list of strings like " +
                    "\n* Bob" +
                    "\n* Katie " +
                    "\nto a single arg method, taking a list of String:s.")
    public void mappingSingleLists(){

        SearchPatterns sps = new SearchPatterns()
                .add(
                        new SearchPattern("a list of strings like {StringList}")
                                .binding("{StringList}", asStringList)
                                .to(new OneArgMethod<>(this::setListOfString))
                );

        ExecutableKeyExample
                .forKeyExample(logger.getKeyExample())
                .withSearchPatterns(sps)
                .run(defaultKeyExampleExecuter);

        assertThat(stringList, hasSize(2));
        assertThat(stringList.get(0), is("Bob"));
        assertThat(stringList.get(1), is("Katie"));
    }


    @Test
    @KeyExample(description =
            "It should be possible to map a list of string like \n" +
                    "* Bob\n" +
                    "* Katie\n " +
                    "and a list of integers like \n" +
                    "* 5\n" +
                    "* 42\n " +
            "to a two arg method, taking a List of String:s as first argument, and a List of Integer:s as the second.")
    public void mappingTwoListsToSameMethod(){

        SearchPatterns sps = new SearchPatterns()
                .add(
                        new SearchPattern("a list of string like {StringList} and a list of integers like {IntList}")
                                .binding("{StringList}", asStringList)
                                .binding("{IntList}", asIntegerList)
                                .to(new TwoArgMethod<>(this::setListOfStringAndListOfInteger))
                );

        ExecutableKeyExample
                .forKeyExample(logger.getKeyExample())
                .withSearchPatterns(sps)
                .run(defaultKeyExampleExecuter);

        assertThat(stringList, hasSize(2));
        assertThat(stringList.get(0), is("Bob"));
        assertThat(stringList.get(1), is("Katie"));

        assertThat(integerList, hasSize(2));
        assertThat(integerList.get(0), is(5));
        assertThat(integerList.get(1), is(42));
    }


    @Test
    @KeyExample(description =
            "It should be possible to map a list of strings like \n" +
                    "* Bob\n" +
                    "* Katie\n " +
                    "and a list of integers like \n" +
                    "* 5\n" +
                    "* 42\n " +
                    "to two different one arg methods.")
    public void mappingTwoListsToDifferentMethod(){

        SearchPatterns sps = new SearchPatterns()
                .add(
                        new SearchPattern("a list of strings like {StringList} and a list of integers like {IntList}")
                                .binding("{StringList}", asStringList)
                                .to(new OneArgMethod<>(this::setListOfString))
                                .binding("{IntList}", asIntegerList)
                                .to(new OneArgMethod<>(this::setListOfInteger))
                );


        ExecutableKeyExample
                .forKeyExample(logger.getKeyExample())
                .withSearchPatterns(sps)
                .run(defaultKeyExampleExecuter);

        assertThat(stringList, hasSize(2));
        assertThat(stringList.get(0), is("Bob"));
        assertThat(stringList.get(1), is("Katie"));

        assertThat(integerList, hasSize(2));
        assertThat(integerList.get(0), is(5));
        assertThat(integerList.get(1), is(42));
    }



    @Test
    @KeyExample(description =
            "A Table is used to call the same method(s) many times, one time for every row.\n" +
                    "Here for example, for each row the values in column 1 and columns 2 are mapped to two simple setters. \n" +
                    "The value in column 3 is mapped to a method that asserts that the value is the concatenation of \n" +
                    "what was put in the setters." +
                    "\n"+
                    "\n"+
                    "| First string | Second string  | Expected result |\n"+
                    "|--------------|----------------|-----------------|\n"+
                    "|aa            |bb              |aabb             |\n"+
                    "|   Pine       |apple           |Pineapple        |\n"
    )
    public void mappingTableWithSingleArgument(){
        SearchPatterns sps = new SearchPatterns()
                .add(
                        new TablePattern("| First string | Second string  | Expected result |")
                                .bindingEachCellInColumn(0, asString).to(new OneArgMethod<>(this::setString1))
                                .bindingEachCellInColumn(1, asString).to(new OneArgMethod<>(this::setString2))
                                .bindingEachCellInColumn(2, asString).to(new OneArgMethod<>(expectedResult -> assertThat(string1 + string2, is(expectedResult))))
                );

        ExecutableKeyExample
                .forKeyExample(logger.getKeyExample())
                .withSearchPatterns(sps)
                .run(defaultKeyExampleExecuter);

        // Some sanity check to make sure the stings has been set at all ...
        assertThat(string1, is("Pine"));
        assertThat(string2, is("apple"));
    }

    public void setString1(String string){
        this.string1 = string;
    }

    public void setString2(String string){
        this.string2 = string;
    }

    private void setStringAndInteger(String string, Integer integer) {
        this.string1 = string;
        this.integer = integer;
    }

    private void setInteger(Integer integer) {
        this.integer = integer;
    }

    private void setListOfString(List<String> list){
        this.stringList = list;
    }

    private void setListOfInteger(List<Integer> list){
        this.integerList = list;
    }

    private void setListOfStringAndListOfInteger(List<String> list1, List<Integer> list2){
        this.stringList = list1;
        this.integerList = list2;

    }

}
