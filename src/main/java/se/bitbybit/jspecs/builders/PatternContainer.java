package se.bitbybit.jspecs.builders;

import java.util.regex.Pattern;

public class PatternContainer {

    private String originalPattern;
    private String regexpPattern;

    public PatternContainer(String originalPattern, String regexpPattern) {
        this.originalPattern = originalPattern;
        this.regexpPattern = regexpPattern;
    }

    public String getOriginalPattern() {
        return originalPattern;
    }

    public String getRegexpPattern(){return regexpPattern;}

    public Pattern getCompiledRegexpPattern() {
        return Pattern.compile(regexpPattern);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PatternContainer)) return false;

        PatternContainer that = (PatternContainer) o;

        if (!originalPattern.equals(that.originalPattern)) return false;
        return regexpPattern.equals(that.regexpPattern);

    }

    @Override
    public int hashCode() {
        int result = originalPattern.hashCode();
        result = 31 * result + regexpPattern.hashCode();
        return result;
    }
}
