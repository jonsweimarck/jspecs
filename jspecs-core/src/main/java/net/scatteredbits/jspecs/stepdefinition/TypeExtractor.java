package net.scatteredbits.jspecs.stepdefinition;

import net.scatteredbits.jspecs.builders.SearchPatterns.PlaceHolderType;

import java.util.ArrayList;
import java.util.List;

public class TypeExtractor<T> {


    public T extractArg(PlaceHolderType argumentType, String markup) {
        switch (argumentType){
            case asString: return (T) markup;
            case asInteger: return (T) new Integer(Integer.parseInt(markup));
            case asStringList: return (T) extractStringList(markup);
            case asIntegerList: return (T) extractIntegerList(markup);
            default: throw new IllegalArgumentException(argumentType + " not supported as type");
        }
    }

    private List<String> extractStringList(String markup) {
        final String regExpForList = "\\* ";

        List<String> result = new ArrayList<>();
        String[] splitted = markup.split(regExpForList);
        for(String s: splitted){
            if(! s.trim().isEmpty()) {
                result.add(s.trim());
            }
        }
        return result;
    }

    private List<Integer> extractIntegerList(String markup) {
        final String regExpForList = "\\* ";

        List<Integer> result = new ArrayList<>();
        String[] splitted = markup.split(regExpForList);
        for(String s: splitted){
            if(! s.trim().isEmpty()) {
                result.add(Integer.valueOf(s.trim()));
            }
        }
        return result;
    }
}
