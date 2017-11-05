package io.tapack.satisfy.steps.table;

import org.openqa.selenium.WebElement;

public class RowWebElement {

    private WebElement row;
    private String rowKey;
    private int rowIndex;
    private boolean isSubRow;
    private int parentRowIndex;
    private boolean hasSubRows;

    public WebElement getRow() {
        return row;
    }

    public void setRow(WebElement row) {
        this.row = row;
    }

    public String getRowKey() {
        return rowKey;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public boolean isSubRow() {
        return isSubRow;
    }

    public void setSubRow(boolean isSubRow) {
        this.isSubRow = isSubRow;
    }

    public int getParentRowIndex() {
        return parentRowIndex;
    }

    public void setParentRowIndex(int parentRowIndex) {
        this.parentRowIndex = parentRowIndex;
    }

    public boolean hasSubRows() {
        return hasSubRows;
    }

    public void hasSubRows(boolean hasSubRows) {
        this.hasSubRows = hasSubRows;
    }

}