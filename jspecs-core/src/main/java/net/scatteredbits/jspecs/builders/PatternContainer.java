package net.scatteredbits.jspecs.builders;

import java.util.regex.Pattern;

import static net.scatteredbits.jspecs.builders.SearchPatterns.PlaceHolderType;

public class PatternContainer {

    private static final String regexpForString ="\\w+";
    private static final String regexpForInteger ="\\w+";
    private static final String regExpForStringList = "\\s*\\n?\\r?(\\* \\w+\\s*\\n?\\r?)+";
    private static final String regExpForIntegerList = "\\s*\\n?\\r?(\\* \\w+\\s*\\n?\\r?)+";

    private String originalPattern;
    private String regexpPatternWithGroupName;
    private String regexpPatternWithoutGroupName;

    public PatternContainer(PlaceHolderType argumentType, String placeholder, String pattern, String groupName){
        this.originalPattern = pattern;

        pattern = pattern.replace("|", "\\s*\\|\\s*"); // For tables

        regexpPatternWithGroupName = createRegExpPatternWithGroupName(pattern, argumentType, placeholder, groupName);
        regexpPatternWithoutGroupName = createRegExpPatternWithoutGroupName(pattern, argumentType, placeholder, groupName);

//        switch(argumentType){
//            case asString:
//            case asInteger:
//                regexpPatternWithGroupName = pattern.replace(placeholder, "(?<"+ groupName +">" + regexpForString+ ")");
//                regexpPatternWithoutGroupName = pattern.replace(placeholder, "(" + regexpForString+ ")");
//                break;
//            case asStringList:
//            case asIntegerList:
//                regexpPatternWithGroupName = pattern.replace(placeholder, "(?<"+ groupName +">" + regExpForStringList+ ")");
//                regexpPatternWithoutGroupName = pattern.replace(placeholder, "(" + regExpForStringList+ ")");
//                break;
//            default: throw new IllegalArgumentException("Unknown argument type: " + argumentType);
//        }

    }

    private String createRegExpPatternWithGroupName(String pattern, PlaceHolderType argumentType, String placeholder, String groupName) {
        switch(argumentType){
            case asString:
            case asInteger:
                return pattern.replace(placeholder, "(?<"+ groupName +">" + regexpForString+ ")");
            case asStringList:
            case asIntegerList:
                return pattern.replace(placeholder, "(?<"+ groupName +">" + regExpForStringList+ ")");
            default: throw new IllegalArgumentException("Unknown argument type: " + argumentType);
        }
    }

    private String createRegExpPatternWithoutGroupName(String pattern, PlaceHolderType argumentType, String placeholder, String groupName) {
        switch(argumentType){
            case asString:
            case asInteger:
                return pattern.replace(placeholder, "(" + regexpForString+ ")");
            case asStringList:
            case asIntegerList:
                return pattern.replace(placeholder, "(" + regExpForStringList+ ")");
            default: throw new IllegalArgumentException("Unknown argument type: " + argumentType);
        }
    }


    public PatternContainer(String pattern,
                            PlaceHolderType argumentType1,
                            PlaceHolderType argumentType2,
                            String placeholder1,
                            String placeholder2,
                            String groupName1,
                            String groupName2) {
        this.originalPattern = pattern;

        String regexpPatternWithFirstGroupName = createRegExpPatternWithGroupName(pattern, argumentType1, placeholder1, groupName1);
        regexpPatternWithGroupName = createRegExpPatternWithGroupName(regexpPatternWithFirstGroupName, argumentType2, placeholder2, groupName2);

        String regexpPatternWithoutFirstGroupName = createRegExpPatternWithoutGroupName(pattern, argumentType1, placeholder1, groupName1);
        regexpPatternWithoutGroupName = createRegExpPatternWithoutGroupName(regexpPatternWithoutFirstGroupName, argumentType2, placeholder2, groupName2);
//
//        String replacedWithGroupName = pattern.
//                replace(placeholder1, "(?<"+ groupName1 +">\\w+)").
//                replace(placeholder2, "(?<"+ groupName2 +">\\w+)");
//        String replacedWithoutGroupName = pattern.
//                replace(placeholder1, "(\\w+)").
//                replace(placeholder2, "(\\w+)");
    }


    public PatternContainer(String originalPattern, String regexpPatternWithGroupName, String regexpPatternWithoutGroupName) {
        this.originalPattern = originalPattern;
        this.regexpPatternWithGroupName = regexpPatternWithGroupName;
        this.regexpPatternWithoutGroupName = regexpPatternWithoutGroupName;
    }

    public String getOriginalPattern() {
        return originalPattern;
    }

    public String getRegexpPatternWithGroupName(){return regexpPatternWithGroupName;}

    public String getRegexpPatternWithoutGroupName(){return regexpPatternWithoutGroupName;}

    public Pattern getCompiledRegexpPattern() {
        return Pattern.compile(regexpPatternWithGroupName);
    }

}
