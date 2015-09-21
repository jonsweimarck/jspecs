package se.bitbybit.jspecs.builders;

import java.util.regex.Pattern;

public class PatternContainer {

    private String originalPattern;
    private String regexpPatternWithGroupName;
    private String regexpPatternWithoutGroupName;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PatternContainer)) return false;

        PatternContainer that = (PatternContainer) o;

        if (!originalPattern.equals(that.originalPattern)) return false;
        return regexpPatternWithGroupName.equals(that.regexpPatternWithGroupName);

    }

    @Override
    public int hashCode() {
        int result = originalPattern.hashCode();
        result = 31 * result + regexpPatternWithGroupName.hashCode();
        return result;
    }
}
