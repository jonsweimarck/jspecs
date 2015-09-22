package net.scatteredbits.jspecs.builders;

import net.scatteredbits.jspecs.stepdefinition.ParserCombiner;
import net.scatteredbits.jspecs.stepdefinition.StepDefinitionParser;
import net.scatteredbits.jspecs.stepdefinition.StepDefinitionParserForOneArg;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;


public class TableSearchConverter {

    public List<ParserCombiner> convert(String keyExampleText, Collection<TableSearch> tableSearches) {
        return tableSearches.stream()
                .map(ts -> toParserCombiner(keyExampleText, ts))
                .collect(toList());
    }

    private ParserCombiner toParserCombiner(String keyExampleText, TableSearch tableSearch){
        ParserCombiner result = new ParserCombiner();

        Table table = new Table(tableSearch.getTableHeader(), keyExampleText);
        List<StepDefinitionParser> list = IntStream.range(0, table.getNumberOfRows())
                .mapToObj(rowNumber -> createStepDefParser(table, rowNumber, tableSearch.getFirstArgColumnIndex(), tableSearch.getFirstArgType(), tableSearch.getOneArgMethod()))
                .collect(Collectors.toList());

        result.setParsers(list);
        return result;
    }


    private StepDefinitionParser createStepDefParser(Table table, int rowNumber, int columnIndex, SearchPatterns.PlaceHolderType placeHolderType, OneArgMethod oneArgMethod) {
        String placeholder = "{" + rowNumber + ":" + columnIndex + "}";
        Table tableWithPlaceholder = table.cloneWithCell(rowNumber, columnIndex, placeholder);

        return new StepDefinitionParserForOneArg<>(tableWithPlaceholder.getAsString(), placeholder, placeHolderType, oneArgMethod);
    }

}
