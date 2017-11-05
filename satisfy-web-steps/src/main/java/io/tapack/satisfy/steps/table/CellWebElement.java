package io.tapack.satisfy.steps.table;

import org.openqa.selenium.WebElement;

public class CellWebElement {

    private WebElement cell;
    private String cellAsString = "";
    private int columnIndex;
    private int rowIndex;

    public WebElement getCell() {
        return cell;
    }

    public void setCell(WebElement cell) {
        this.cell = cell;
    }

    public String getCellAsString() {
        return cellAsString;
    }

    public void setCellAsString(String cellAsString) {
        this.cellAsString = cellAsString;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

}