package net.scatteredbits.jspecs.builders;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;


public class TableTest {

    @Test
    public void shouldThrowIfHeaderNotInText(){
        try{
            new Table(
                    "| Header1      | Header2        | Header3         |",

                    "Some texts not at all belonging to the table           \n"+
                    "| Header55     | Header56       | Header57        |     \n"+
                    "|--------------|----------------|-----------------|   \n"+
                    "| Ban          |ana             | Banana          |        \n"+
                    "| App          |le              | Apple           |  \n");
            fail("Should have thrown because no matching table header");
        } catch (IllegalArgumentException e){
            assertThat(e.getMessage(), is("Couldn't find table header in text"));
        }
    }


    @Test
    public void getNumberOfRowsShouldWorkWithHappyPath() {

        Table table = new Table(
                "| Header1      | Header2        | Header3         |",

                "Some texts not at all belonging to the table           \n"+
                "| Header1      | Header2        | Header3         |     \n"+
                "|--------------|----------------|-----------------|   \n"+
                "| Ban          |ana             | Banana          |        \n"+
                "| App          |le              | Apple           |  \n" +
                " Some text not belonging to the table");

        assertThat(table.getNumberOfRows(), is(2));
    }

    @Test
    public void getNumberOfRowsShouldWorkWithNoRows() {

        Table table = new Table(
                "| Header1      | Header2        | Header3         |",

                "| Header1      | Header2        | Header3         | ");

        assertThat(table.getNumberOfRows(), is(0));
    }

    @Test
    public void getNumberOfRowsShouldWorkWhenTableIsWholeText() {

        Table table = new Table(
                "| Header1      | Header2        | Header3         |",

                "| Header1      | Header2        | Header3         |     \n"+
                "|--------------|----------------|-----------------|   \n"+
                "| Ban          |ana             | Banana          |        \n"+
                "| App          |le              | Apple           |  ");

        assertThat(table.getNumberOfRows(), is(2));
    }

    @Test
    public void getAsStringShouldOnlyReturnTheTablePart() {

        Table table = new Table(
                "| Header1      | Header2        | Header3         |",

                "Some texts not at all belonging to the table           \n"+
                        "| Header1      | Header2        | Header3         |     \n"+
                        "|--------------|----------------|-----------------|   \n"+
                        "| Ban          |ana             | Banana          |        \n"+
                        "| App          |le              | Apple           |  \n" +
                        " Some text not belonging to the table");

        String expectedAsString =
                "| Header1      | Header2        | Header3         |     \n"+
                "|--------------|----------------|-----------------|   \n"+
                "| Ban          |ana             | Banana          |        \n"+
                "| App          |le              | Apple           |  \n";

       assertThat(table.getAsString(), is(expectedAsString));
    }

    @Test
    public void getAsStringShouldWorkWithTableWithoutoutRows() {

        Table table = new Table(
                "| Header1      | Header2        | Header3         |",

                "Some texts not at all belonging to the table           \n"+
                        "| Header1      | Header2        | Header3         |     \n"+
                        "|--------------|----------------|-----------------|   \n"+
                        " Some text not belonging to the table");

        String expectedAsString =
                        "| Header1      | Header2        | Header3         |     \n"+
                        "|--------------|----------------|-----------------|   \n";

        assertThat(table.getAsString(), is(expectedAsString));
    }

    @Test
    public void cloneWithCellShouldWorkWithFirstCell() {

        Table table1 = new Table(
                "| Header1      | Header2        | Header3         |",

                "Some texts not at all belonging to the table           \n"+
                "| Header1      | Header2        | Header3         |     \n"+
                "|--------------|----------------|-----------------|   \n"+
                "| Ban          |ana             | Banana          |        \n"+
                "| App          |le              | Apple           |  \n" +
                " Some text not belonging to the table");

        Table table2 = table1.cloneWithCell(0,0, "Pear");

        String expectedAsString =
                        "| Header1      | Header2        | Header3         |     \n"+
                        "|--------------|----------------|-----------------|   \n"+
                        "|Pear|ana             | Banana          |        \n"+
                        "| App          |le              | Apple           |\n";

        assertThat(table2.getAsString(), is(expectedAsString));
    }

    @Test
    public void cloneWithCellShouldWorkWithLastCell() {

        Table table1 = new Table(
                "| Header1      | Header2        | Header3         |",

                "Some texts not at all belonging to the table           \n"+
                        "| Header1      | Header2        | Header3         |     \n"+
                        "|--------------|----------------|-----------------|   \n"+
                        "| Ban          |ana             | Banana          |        \n"+
                        "| App          |le              | Apple           |  \n" +
                        " Some text not belonging to the table");

        Table table2 = table1.cloneWithCell(1,2, "Pear");

        String expectedAsString =
                "| Header1      | Header2        | Header3         |     \n"+
                "|--------------|----------------|-----------------|   \n"+
                "| Ban          |ana             | Banana          |        \n"+
                "| App          |le              |Pear|\n";

        assertThat(table2.getAsString(), is(expectedAsString));
    }
}