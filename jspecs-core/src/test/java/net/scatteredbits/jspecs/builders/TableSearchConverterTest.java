package net.scatteredbits.jspecs.builders;

import net.scatteredbits.jspecs.stepdefinition.ParserCombiner;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static net.scatteredbits.jspecs.builders.SearchPatterns.PlaceHolderType.asString;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class TableSearchConverterTest {

    @Test
    public void tableSearchForOneColumnInTwoRowTableResultsInOneParserCombinerWIthTwoParsers(){

        String keyExampleText =
                "# Concatination\n"+
                "\n"+
                " Concatination of two strings should result in a new string that is the first string followed by the second one" +
                "\n"+
                "\n"+
                "| First string | Second string  | Expected result |\n"+
                "|--------------|----------------|-----------------|\n"+
                "| Ban          |ana             | Banana          |\n"+
                "| App          |le              | Apple           |\n";

        String header =  "| First string | Second string  | Expected result |";
        TableSearch tableSearch = new TableSearch(header, 1, asString, null);

        TableSearchConverter cut = new TableSearchConverter();
        List<ParserCombiner> result = cut.convert(keyExampleText, Arrays.asList(tableSearch));

        assertThat(result, hasSize(1)); // We look for result in only 1 column
        assertThat(result.get(0).getParsers(), hasSize(2)); // and that column has two rows
    }
}