package io.tapack.satisfy.steps.table;

import com.google.common.collect.Lists;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import io.tapack.satisfy.steps.spi.TableSteps;
import io.tapack.satisfy.steps.spi.WebPage;
import io.tapack.satisfy.steps.spi.WebStepsFactory;
import org.jbehave.core.model.ExamplesTable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.core.IsNot.not;

public class BaseTableSteps implements TableSteps {

    private static final String TABLE_TAG = "table";
    private final WebPage webPage;
    private final By tableIdentity;
    private WebElement tableElement;
    private TableWebElement table;

    public BaseTableSteps(By identity) {
        tableIdentity = identity;
        webPage = WebStepsFactory.getPage();
    }

    @Override
    public boolean accept(By identity) {
        return false;
    }

    @Override
    public void inTableClickOnIdentityInRowsWithParameters(By elementIdentity, ExamplesTable params) {
        List<WebElement> matchedRows = getMatchedRows(params);
        if (matchedRows.isEmpty()) {
            throw new AssertionError("You should define at list one existing " +
                    "record value");
        }
        for (WebElement matchedRow : matchedRows) {
            WebElement elementToClick = matchedRow.findElement
                    (elementIdentity);
            webPage.clickOn(elementToClick);
        }
    }

    @Override
    public void tableContainsRowWithParameters(ExamplesTable params) {
        assertThat(getMatchedRows(params), is(not(empty())));
    }

    @Override
    public void tableDoesNotContainRowWithParameters(ExamplesTable params) {
        assertThat(getMatchedRows(params), is(empty()));
    }

    private List<WebElement> getMatchedRows(ExamplesTable params) {
        Map<String, String> rowParams = params.getRow(0);
        List<RowWebElement> tableRows = Arrays.asList(getTable().getRows());
        return findMatchedRows(tableRows, rowParams);
    }

    private TableWebElement getTable() {
        if (table == null) {
            getTableElement();
            parseTable();
        }
        return table;
    }

    private List<WebElement> findMatchedRows(List<RowWebElement> matchedRows,
                                             Map<String, String> rowToFind) {
        List<WebElement> resultedRows = new ArrayList<WebElement>();
        for (RowWebElement rowCandidate : matchedRows) {
            Map<String, String> cellsCandidates = cellsFromRow(rowCandidate);
            MapDifference<String, String> diff = Maps.difference
                    (cellsCandidates, rowToFind);
            if (diff.areEqual()) {
                resultedRows.add(rowCandidate.getRow());
            } else if (diff.entriesOnlyOnRight().isEmpty() && diff
                    .entriesDiffering().isEmpty()) {
                resultedRows.add(rowCandidate.getRow());
            } else if (rowCandidate.hasSubRows()) {
                for (String commonKey : diff.entriesInCommon().keySet()) {
                    rowToFind.remove(commonKey);
                }
                return findMatchedRows(getSubRowsFor(rowCandidate), diff
                        .entriesOnlyOnRight().isEmpty() ? rowToFind : diff
                        .entriesOnlyOnRight());
            }
        }
        return resultedRows;
    }

    private List<RowWebElement> getSubRowsFor(RowWebElement rowCandidate) {
        int rowIndex = rowCandidate.getRowIndex();
        List<RowWebElement> subRows = Lists.newArrayList();
        for (RowWebElement row : table.getRows()) {
            if (row.getParentRowIndex() == rowIndex) {
                subRows.add(row);
            }
        }
        return subRows;
    }

    private Map<String, String> cellsFromRow(RowWebElement rowCandidate) {
        Map<String, String> cells = Maps.newHashMap();
        int rowIndex = rowCandidate.getRowIndex();
        ColumnWebElement[] columns = table.getColumns();
        CellWebElement[][] cellsTable = table.getTable();
        for (int j = 0; j < columns.length; j++) {
            cells.put(columns[j].getColumnKey(), cellsTable[rowIndex][j]
                    .getCellAsString());
        }
        return cells;
    }

    private List<WebElement> headingElements() {
        return tableElement.findElements(By.xpath(".//thead/tr/th"));
    }

    private WebElement getTableElement() {
        WebElement rootElement = webPage.element(tableIdentity);
        tableElement = rootElement;
        if (!rootElement.getTagName().contentEquals(TABLE_TAG))
            tableElement = rootElement.findElement(By.tagName(TABLE_TAG));
        return tableElement;
    }

    private List<WebElement> rowElements() {
        return tableElement.findElements(By.xpath(".//tr[td]"));
    }

    private boolean isSubRowElement(WebElement row) {
        return row.findElements(By.xpath(".//td[@colspan>1]")).isEmpty();
    }

    private List<WebElement> getCellsFor(WebElement row) {
        return row.findElements(By.xpath("./td"));
    }

    private void parseTable() {
        table = new TableWebElement();
        table.setColumns(parseColumns());
        table.setRows(parseRows());
        RowWebElement[] rows = table.getRows();
        ColumnWebElement[] columns = table.getColumns();
        CellWebElement[][] cells = new CellWebElement[rows.length][columns
                .length];
        for (int i = 0; i < rows.length; i++) {
            List<WebElement> cellsInRow = getCellsFor(rows[i].getRow());
            for (int j = 0; j < columns.length; j++) {
                cells[i][j] = new CellWebElement();
                WebElement cell = j >= cellsInRow.size() ? cellsInRow.get
                        (cellsInRow.size() - 1) : cellsInRow.get(j);
                cells[i][j].setCell(cell);
                cells[i][j].setCellAsString(cell.getText());
                cells[i][j].setRowIndex(i);
                cells[i][j].setColumnIndex(j);
            }
        }
        table.setTable(cells);
    }

    private RowWebElement[] parseRows() {
        List<WebElement> rowsElements = rowElements();
        RowWebElement[] rows = new RowWebElement[rowsElements.size()];
        int rowIndex = 0;
        int parentRowIndex = -1;
        boolean workWithSubRows = false;
        for (WebElement row : rowsElements) {
            rows[rowIndex] = new RowWebElement();
            rows[rowIndex].setRow(row);
            rows[rowIndex].setRowKey(row.getText());
            rows[rowIndex].setRowIndex(rowIndex);
            rows[rowIndex].setParentRowIndex(parentRowIndex);
            if (isSubRowElement(row)) {
                if (!workWithSubRows) {
                    workWithSubRows = true;
                    parentRowIndex = rowIndex != 0 ? rowIndex - 1 : rowIndex;
                    rows[parentRowIndex].hasSubRows(true);
                }
                rows[rowIndex].setSubRow(true);
            } else if (!isSubRowElement(row) && workWithSubRows) {
                workWithSubRows = false;
                parentRowIndex = -1;
            }
            rowIndex++;
        }
        return rows;
    }

    private ColumnWebElement[] parseColumns() {
        List<WebElement> headings = headingElements();
        ColumnWebElement[] columns = new ColumnWebElement[headings.size()];
        int columnIndex = 0;
        for (WebElement heading : headings) {
            columns[columnIndex] = new ColumnWebElement();
            columns[columnIndex].setColumn(heading);
            columns[columnIndex].setColumnKey(heading.getText());
            columns[columnIndex].setColumnIndex(columnIndex);
            columnIndex++;
        }
        return columns;
    }

}
