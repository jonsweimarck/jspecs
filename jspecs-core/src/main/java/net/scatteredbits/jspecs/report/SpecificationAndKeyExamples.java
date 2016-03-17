package net.scatteredbits.jspecs.report;

import java.util.List;

public class SpecificationAndKeyExamples {
    private final String specificationName;
    private final String specificationDesc;
    private final List<String> keyExampleDescriptions;

    public SpecificationAndKeyExamples(
            String specificationName,
            String specificationDesc,
            List<String> keyExampleDescriptions) {

        this.specificationName = specificationName;
        this.specificationDesc = specificationDesc;
        this.keyExampleDescriptions = keyExampleDescriptions;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("---" + specificationName + "---\n");
        sb.append(specificationDesc + "\n\n");
        keyExampleDescriptions.forEach(ked -> sb.append(ked + "\n"));
        return sb.toString();
    }
}
