package net.scatteredbits.jspecsdemo;


import net.scatteredbits.jspecs.DefaultKeyExampleExecuter;
import net.scatteredbits.jspecs.ExecutableStepDefinitionSorter;
import net.scatteredbits.jspecs.builders.ExecutableKeyExample;
import net.scatteredbits.jspecs.builders.OneArgMethod;
import net.scatteredbits.jspecs.builders.SearchPatterns;
import net.scatteredbits.jspecs.builders.TablePattern;
import net.scatteredbits.jspecs.junitspecific.KeyExample;
import net.scatteredbits.jspecs.junitspecific.Specification;
import net.scatteredbits.jspecs.junitspecific.SpecificationLogger;
import org.junit.Rule;
import org.junit.Test;

import static net.scatteredbits.jspecs.builders.SearchPatterns.PlaceHolderType.asString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Specification(
        name= "Table examples",
        description =
                "Jspec can work with simple tables in the key example texts.\n" +
                        "The tables are created in a Markdown(ish) way\n" +
                        "with | used as the divider between cells.\n" +
                        "Some notes about tables (not shown in the following examples):\n" +
                        "* A table must end with the new line character.\n" +
                        "* The whole header string, including whitespaces, should be used when creating the SearchPattern object.\n")
public class TableTests {

    @Rule
    public SpecificationLogger logger = new SpecificationLogger();

    private DefaultKeyExampleExecuter defaultKeyExampleExecuter = new DefaultKeyExampleExecuter(new ExecutableStepDefinitionSorter());
    private String string1;
    private String string2;


    @Test
    @KeyExample(description =
                    "The simplest form of a table is just one column and one row, with the single cell left align.\n" +
                            "Note how the header cell is separated from the data cell with a line of -" +
                    "\n"+
                    "\n"+
                    "|COLUMN 1     |\n"+
                    "|-------------|\n"+
                    "|AAA          |\n"
    )
    public void oneColumnOneRowAlignedCelltext(){
        setString1("NOT SET");

        SearchPatterns sps = new SearchPatterns()
                .add(
                        new TablePattern("|COLUMN 1     |")
                                .bindingEachCellInColumn(0, asString)
                                .to(new OneArgMethod<>(this::setString1))
                );
        ExecutableKeyExample
                .forKeyExample(logger.getKeyExample())
                .withSearchPatterns(sps)
                .run(defaultKeyExampleExecuter);

        assertThat(string1, is("AAA"));
    }

    @Test
    @KeyExample(description =
                    "A single column table with multiple rows should work." +
                    "\n"+
                    "\n"+
                    "|First string |\n"+
                    "|-------------|\n"+
                    "|BBB          |\n"+
                    "|AAA          |\n"
    )
    public void oneColumnMultipleRowsAlignedCelltext(){
        setString1("NOT SET");

        SearchPatterns sps = new SearchPatterns()
                .add(
                        new TablePattern("|First string |")
                                .bindingEachCellInColumn(0, asString)
                                .to(new OneArgMethod<>(this::setString1))
                );
        ExecutableKeyExample
                .forKeyExample(logger.getKeyExample())
                .withSearchPatterns(sps)
                .run(defaultKeyExampleExecuter);

        assertThat(string1, is("AAA"));
    }

    @Test
    @KeyExample(description =
                    "The text inside the cells are trimmed so text alignment doesn't matter" +
                    "\n"+
                    "\n"+
                    "|Aligned text  |Parsed     |\n"+
                    "|--------------|-----------|\n"+
                    "|aa            |aa         |\n"+
                    "|        bbbbbb|bbbbbb     |\n"+
                    "|  aaaaaaaaaa  |aaaaaaaaaa |\n"
    )
    public void cellsAreTrimmed(){
        SearchPatterns sps = new SearchPatterns()
                .add(
                        new TablePattern("|Aligned text  |Parsed     |")
                                .bindingEachCellInColumn(0, asString).to(new OneArgMethod<>(this::setString1))
                                .bindingEachCellInColumn(1, asString).to(new OneArgMethod<>(parsed -> assertThat(parsed, is(string1))))
                );

        ExecutableKeyExample
                .forKeyExample(logger.getKeyExample())
                .withSearchPatterns(sps)
                .run(defaultKeyExampleExecuter);

    }

    @Test
    @KeyExample(description =
                    "One of the columns might work as the 'expected result' column, but it's of course also \n" +
                    "possible to do the asserts outside the table, like in an ordinary junit test." +
                    "\n"+
                    "\n"+
                    "| First string | Second string  | Expected concatination |\n"+
                    "|--------------|----------------|------------------------|\n"+
                    "|aa            |bb              |aabb                    |\n"+
                    "|   Pine       |apple           |Pineapple               |\n"
    )
    public void usingAColumnForAssertionsAsWellAsExternalAsserts(){
        SearchPatterns sps = new SearchPatterns()
                .add(
                        new TablePattern("| First string | Second string  | Expected concatination |")
                                .bindingEachCellInColumn(0, asString).to(new OneArgMethod<>(this::setString1))
                                .bindingEachCellInColumn(1, asString).to(new OneArgMethod<>(this::setString2))
           /* assert here -> */ .bindingEachCellInColumn(2, asString).to(new OneArgMethod<>(expectedResult -> assertThat(string1 + string2, is(expectedResult))))
                );

        ExecutableKeyExample
                .forKeyExample(logger.getKeyExample())
                .withSearchPatterns(sps)
                .run(defaultKeyExampleExecuter);

        // ... or here.
        assertThat(string1 + string2, is("Pineapple"));
    }

    public void setString1(String string){
        this.string1 = string;
    }
    public void setString2(String string){
        this.string2 = string;
    }
}