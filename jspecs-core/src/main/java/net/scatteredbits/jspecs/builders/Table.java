package net.scatteredbits.jspecs.builders;

import java.util.stream.IntStream;

/**
 * A table with the mark up header 'tableHeader', somewhere in the block of text 'textBlock'
 */
public class Table {
    private static final int  NO_TABLE_ROWS_FOUND = -1;
    private String tableHeader;
    private String textBlock;

    public Table(String tableHeader, String textBlock) {
        assertTableHeaderIsInTextBlock(tableHeader, textBlock);

        this.tableHeader = tableHeader.trim();
        this.textBlock = textBlock.trim();
    }


    public int getNumberOfRows() {
        String[] lines = textBlock.split("\n");
        int headerLineIndex = findHeaderLineindex(lines, tableHeader);
        int lastLineindexInTable = lastLineIndexInTable(lines, headerLineIndex);

        if(lastLineindexInTable == NO_TABLE_ROWS_FOUND){
            return 0;
        }

        return lastLineindexInTable - headerLineIndex;
    }

    public Table cloneWithCell(int rowNumber, int columnIndex, String cellContent) {

        if(columnIndex < 0 || columnIndex > numberOfColumns(tableHeader)){
            throw new IllegalArgumentException("Column " + columnIndex + " does not exists");
        }

        Table table = new Table(new String(tableHeader), getAsString());

        String[] lines = table.textBlock.split("\n");
        int headerLineIndex = table.findHeaderLineindex(lines, table.tableHeader);
        int rowIndex = headerLineIndex + 2 + rowNumber;
        lines[rowIndex] = setCellContent(lines[rowIndex], columnIndex, cellContent);

        return new Table(table.tableHeader, getAsString(lines));
    }

    private String setCellContent(String line, int columnIndex, String cellContent) {
        int[] indicies = getAllIndicies('|', line);
//        String paddedCellcontent = padRight(cellContent, indicies[columnIndex + 1] - indicies[columnIndex] -1);
        String paddedCellcontent = cellContent;
        return  line.substring(0, indicies[columnIndex] +1 )  +
                paddedCellcontent +
                line.substring(indicies[columnIndex + 1]);
    }

    private int[] getAllIndicies(char charToFind, String line) {
        return  IntStream.range(0, line.length())
                .filter(i -> line.charAt(i) == charToFind)
                .toArray();
    }

    public static String padRight(String StringToPad, int noOfSpaces) {
        return String.format("%1$-" + noOfSpaces + "s", StringToPad);
    }

    public String getAsString() {
        String[] lines = textBlock.split("\n");
        return getAsString(lines);
    }

    private String getAsString(String[] lines) {
        int headerLineIndex = findHeaderLineindex(lines, tableHeader);
        int lastLineIndexInTable = lastLineIndexInTable(lines, headerLineIndex);

        if(lastLineIndexInTable == NO_TABLE_ROWS_FOUND){
            return lines[headerLineIndex];
        }


        return IntStream.rangeClosed(headerLineIndex, lastLineIndexInTable +1)
                .mapToObj(i -> lines[i] + "\n")
                .reduce("", (s1, s2) -> s1 + s2);
    }

    private void assertTableHeaderIsInTextBlock(String tableHeader, String textBlock) {
        if(textBlock.indexOf(tableHeader) == -1){
            throw new IllegalArgumentException("Couldn't find table header in text");
        }
    }

    private int lastLineIndexInTable(String[] lines, int headerLineIndex) {
        int nbrOfColumnsInHeader = numberOfColumns(tableHeader);

        return (IntStream.range(headerLineIndex, lines.length)
                .filter(i -> numberOfColumns(lines[i]) != nbrOfColumnsInHeader)
                .findFirst()
                .orElse(lines.length)) -2; // -2 => (one dummy row after header) and (we want the last row, not the first that's NOT a row)
    }


    private int numberOfColumns(String line) {
        return line.chars().filter(c -> c == '|').sum() -1 ;
    }
    private int findHeaderLineindex(String[] lines, String tableHeader) {
        return IntStream.range(0, lines.length)
                .filter(i -> lines[i].trim().equals(tableHeader))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Didn't find the header in among the lines. Should have faild before!"));
    }

}
