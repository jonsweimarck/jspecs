package net.scatteredbits.jspecs.builders;

import net.scatteredbits.jspecs.stepdefinition.ParserCombiner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SearchPatterns {

    public enum PlaceHolderType{
        asString,
        asStringList,
        asInteger,
        asIntegerList
    }

    private List<ISearchPattern> searchPatternList = new ArrayList<>();
    private List<ITablePattern> tablePatternList = new ArrayList<>();

    public SearchPatterns() {
    }



    public SearchPatterns add(SearchPattern searchPattern) {
        searchPatternList.add(searchPattern);
        return this;
    }

    public SearchPatterns add(TablePattern tablePattern) {
        tablePatternList.add(tablePattern);
        return this;
    }

    protected Collection<ParserCombiner> getParserCombiners(){
        return searchPatternList.stream()
                .map(s -> s.getParserCombiners())
                .flatMap(l -> l.stream())
                .collect(Collectors.toList());
    }

    protected Collection<TableSearch> getTableSearches(){
        return tablePatternList.stream()
                .map(s -> s.getTableSearches())
                .flatMap(l -> l.stream())
                .collect(Collectors.toList());
    }

}

