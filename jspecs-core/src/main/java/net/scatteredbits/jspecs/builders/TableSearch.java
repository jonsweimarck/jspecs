package net.scatteredbits.jspecs.builders;

import net.scatteredbits.jspecs.builders.SearchPatterns.PlaceHolderType;

public class TableSearch {

    private String tableHeader;
    private int firstArgColumnIndex;
    private PlaceHolderType firstArgType;
    private OneArgMethod oneArgMethod;

    public <T> TableSearch(String tableHeader,
                           int firstArgColumnIndex,
                           PlaceHolderType firstArgType,
                           OneArgMethod<T> oneArgMethod) {

        this.tableHeader = tableHeader;
        this.firstArgColumnIndex = firstArgColumnIndex;
        this.firstArgType = firstArgType;
        this.oneArgMethod = oneArgMethod;

    }

    public String getTableHeader() {
        return tableHeader;
    }

    public int getFirstArgColumnIndex() {
        return firstArgColumnIndex;
    }

    public PlaceHolderType getFirstArgType() {
        return firstArgType;
    }

    public OneArgMethod getOneArgMethod() {
        return oneArgMethod;
    }
}
