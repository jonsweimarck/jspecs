package net.scatteredbits.jspecs.builders;

import net.scatteredbits.jspecs.stepdefinition.ParserCombiner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TablePattern implements ITablePattern{


    private Map<String, ParserCombiner> map = new HashMap<>();
    private List<TableSearch> searches = new ArrayList<>();
    private String pattern;
    private String tableHeader;

    public TablePattern(String tableHeader) {
        this.tableHeader = tableHeader;
    }

    @Override
    public List<TableSearch> getTableSearches() {
        return searches;
    }
//    private void add(StepDefinitionParser parser) {
//        PatternContainer patternContainer = parser.getStringPattern();
//        ParserCombiner pc = this.map.get(patternContainer.getOriginalPattern());
//        if(pc == null){
//            pc = new ParserCombiner();
//        }
//        this.map.put(patternContainer.getOriginalPattern(), pc.add(parser));
//    }

    public BindingOneArg bindingEachCellInColumn(int columnIndex, SearchPatterns.PlaceHolderType placeholderType) {
        switch(placeholderType){
            case asString : return new BindingOneArg<String>(columnIndex, placeholderType);
            case asInteger : return new BindingOneArg<Integer>(columnIndex, placeholderType);
            case asStringList : return new BindingOneArg<List<String>>(columnIndex, placeholderType);
            case asIntegerList : return new BindingOneArg<List<Integer>>(columnIndex, placeholderType);
        }

        throw new IllegalArgumentException("Not legal placeholdertype: " + placeholderType);
    }


    public class BindingOneArg<T> {
        private int firstArgColumnIndex;
        private SearchPatterns.PlaceHolderType firstArgType;

        public BindingOneArg(int firstArgColumnIndex, SearchPatterns.PlaceHolderType firstArgType){
            this.firstArgColumnIndex = firstArgColumnIndex;
            this.firstArgType = firstArgType;
        }

        public BindingTwoArgs bindingEachCellInColumn(int secondArgColumnIndex, SearchPatterns.PlaceHolderType secondArgType) {
            switch(secondArgType){
                case asString : return new BindingTwoArgs<T, String>(firstArgColumnIndex, secondArgColumnIndex,  firstArgType, secondArgType);
                case asInteger: return new BindingTwoArgs<T, Integer>(firstArgColumnIndex, secondArgColumnIndex,  firstArgType, secondArgType);
                case asStringList: return new BindingTwoArgs<T, List<String>>(firstArgColumnIndex, secondArgColumnIndex,  firstArgType, secondArgType);
                case asIntegerList: return new BindingTwoArgs<T, List<Integer>>(firstArgColumnIndex, secondArgColumnIndex,  firstArgType, secondArgType);

            }
            throw new IllegalArgumentException("Not legal placeholdertype: " + secondArgType);
        }

        public TablePattern to(OneArgMethod<T> oneArgMethod) {
//            Table table =  new Table(TablePattern.this.tableHeader);
//
//            List<T> columnValues = table.getColumn(firstArgColumnIndex);
//            List<String> rowStrings = table.getRows();
//
//
//            add(new StepDefinitionParserForOneArg<T>(TablePattern.this.pattern , firstArgColumnIndex, firstArgType, oneArgMethod));

            searches.add(new TableSearch(TablePattern.this.tableHeader, firstArgColumnIndex, firstArgType, oneArgMethod));
            return TablePattern.this;
        }

    }



    public class BindingTwoArgs<T, U> {
        private int firstArgColumnIndex;
        private int secondArgColumnIndex;
        private SearchPatterns.PlaceHolderType firstArgType;
        private SearchPatterns.PlaceHolderType secondArgType;

        public BindingTwoArgs(int firstArgColumnIndex, int secondArgColumnIndex, SearchPatterns.PlaceHolderType firstPlaceholderType, SearchPatterns.PlaceHolderType secondPlaceholderType){
            this.firstArgColumnIndex = firstArgColumnIndex;
            this.secondArgColumnIndex = secondArgColumnIndex;
            this.firstArgType = firstPlaceholderType;
            this.secondArgType = secondPlaceholderType;
        }

        public TablePattern to(TwoArgMethod<T, U> twoArgMethod) {
//            add(new StepDefinitionParserForTwoArg<T, U>(
//                    TablePattern.this.pattern ,
//                    firstArgColumnIndex,
//                    secondArgColumnIndex,
//                    firstArgType,
//                    secondArgType,
//                    twoArgMethod));
            return TablePattern.this;
        }

    }
}
