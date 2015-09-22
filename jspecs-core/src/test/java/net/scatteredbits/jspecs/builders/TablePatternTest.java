package net.scatteredbits.jspecs.builders;

import org.junit.Test;

import java.util.List;

import static net.scatteredbits.jspecs.builders.SearchPatterns.PlaceHolderType.asString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TablePatternTest {

    @Test
    public void tablePatternWithSingleBindingForOneArgMethodProducesSingleTableSearch(){
        OneArgMethod oneArgMethod = new OneArgMethod<>(s -> System.out.println(s));

                TablePattern tp =
                new TablePattern("| First string | Second string  | Expected result |")
                .bindingEachCellInColumn(2, asString)
                .to(oneArgMethod);

        List<TableSearch> tablesSearches =  tp.getTableSearches();

        assertThat(tablesSearches.size(), is (1));

        TableSearch result = tablesSearches.get(0);
        assertThat(result.getTableHeader(), is("| First string | Second string  | Expected result |"));
        assertThat(result.getFirstArgColumnIndex(), is(2));
        assertThat(result.getFirstArgType(), is(SearchPatterns.PlaceHolderType.asString));
        assertThat(result.getOneArgMethod(), is(oneArgMethod));
    }

    @Test
    public void tablePatternWithTwoBindingsForOneArgMethodsProducesSingleTableSearch(){
        OneArgMethod oneArgMethod = new OneArgMethod<>(s -> System.out.println(s));

        TablePattern tp =
                new TablePattern("| First string | Second string  | Expected result |")
                        .bindingEachCellInColumn(1, asString)
                        .to(oneArgMethod)
                        .bindingEachCellInColumn(2, asString)
                        .to(oneArgMethod);;

        List<TableSearch> tablesSearches =  tp.getTableSearches();

        assertThat(tablesSearches.size(), is (2));

        TableSearch result1 = tablesSearches.get(0);
        assertThat(result1.getFirstArgColumnIndex(), is(1));
        assertThat(result1.getFirstArgType(), is(SearchPatterns.PlaceHolderType.asString));

        TableSearch result2 = tablesSearches.get(1);
        assertThat(result2.getFirstArgColumnIndex(), is(2));
        assertThat(result2.getFirstArgType(), is(SearchPatterns.PlaceHolderType.asString));
    }

}