# Jspecs

Jspec is a tool for BDD, or rather for Specification by Example and Living Documentation, with these specific goals </br>
that hopefully distinguishes it from the rest of the pack:
* Tests/examples are standard Junit tests, and will be run as such
* No regular expressions needed
* No external config/feature/specification files

These goals are met by using Java annotations on the Junit test classes' and their methods,
and by binding the free form specification example texts to java code using Builders and Java 8 lambda expressions.

The examples will be executed just like ordinary Junit tests (because they are ...).
The only diffence is that at the end of the test run, a report file will be produced with the texts from all the
run specifications and their examples.

The project jspecs-demo contains some examples that show how Jspecs can be used. Take a look at, and run,
the junit test (AKA the specifications)!


## Basic Usage

### Annotations
* Annotate your Junit test class and provide a name and specification description of the examples (test cases) in the class.
```
@Specification(
        name="List behavior",
        description = "A List can be ordered or unordered and contain the same entry many times.")
```
* Enhance the Junit test class with a Junit Rule.
```
@Rule
public SpecificationLogger logger = new SpecificationLogger();
```

* Annotate each key example (test case) and provide a description.
```
@KeyExample(description =
            "Starting with the list " +
            "* Apple " +
            "* Pear " +
            "If we invoke the method size on it, we will get the result 2")
```

### Building key examples with Builders
In each key example, the key example text must be bound to executable code. In Jspecs, this is done with the Builder pattern.
Here's an example of a whole key example (in case you forgot: a test case)
```
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
```

Let's walk through the code. The most essential class is the SearchPattern.
This is the class that describes the binding between the key example text and code:
```
new SearchPattern("Starting with the list {list}")
        .binding("{list}", asStringList)
        .to(new OneArgMethod<List<String>>(cut::addAll))
```
This snippet can be read as
> Pattern match 'Starting with the list' and pick the what's coming after as a List of String:s.
> Use that list as parameter to cut.addAll"

In the same manner, the next snippet:
```
new SearchPattern("method size on it, we will get the result {integer}")
        .binding("{integer}", asInteger)
        .to(new OneArgMethod<Integer>(i -> assertThat(cut.size(), is(i))))
```
means
> Pattern match 'method size on it, we will get the result' and pick the what's coming after as an Integer.
> Use the integer in a Hamcrest assertion that checks the size of the list"


* Run the tests. When all the tests are run, the descriptions for both successful
and failed tests/examples will be logged.
