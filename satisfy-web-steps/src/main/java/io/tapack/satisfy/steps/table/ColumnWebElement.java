package io.tapack.satisfy.steps.table;

import org.openqa.selenium.WebElement;

public class ColumnWebElement {

    private WebElement column;
    private String columnKey;
    private int columnIndex;

    public WebElement getColumn() {
        return column;
    }

    public void setColumn(WebElement column) {
        this.column = column;
    }

    public String getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

}