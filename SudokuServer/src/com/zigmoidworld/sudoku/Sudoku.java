package com.zigmoidworld.sudoku;

import java.util.ArrayList;
import java.util.List;

/**
 * Sudoku table/board.
 */
public class Sudoku {
	private List<SudokuCell> cells;
	int size;
	int blockSize;

	public Sudoku(int size) {
		this.size = size;
		// TODO: Assert that size is perfect square
		this.blockSize = (int)(Math.sqrt(this.size));
		this.cells = new ArrayList<>();
	}

	public Sudoku(List<SudokuCell> cells) {
		this.cells = cells;
		this.size = (int)(Math.sqrt(this.cells.size()));
		this.blockSize = (int)(Math.sqrt(this.size));
	}
	
	public int getSize() {
		return this.size;
	}
	
	public List<SudokuCell> getCells() {
		return this.cells;
	}
	
	/**
	 * Returns the stringified sudoku with comma-separated cell values in row wise manner. The empty
	 *     values are represented by '0'
	 */
	public String print() {
		StringBuilder values = new StringBuilder();
		for (SudokuCell cell : getCells()) {
			// TODO: Consider using Joiner instead
			values.append("," + cell.getValue());
		}
		return values.substring(1);
	}
	
	/**
	 * Initializes cells on the Sudoku from provided values.
	 * @param valueArr cell values in row wise manner
	 */
	public void populateCells(String[] valueArr) {
		// Check the size of input array.
		if (valueArr.length != size * size) {
			throw new IllegalArgumentException(
				"Input values do not match expected size");
		}
		for (int i = 0; i < size; i++) {
			int row = i * size;
			for (int j = 0; j < size; j++) {
				int value = Integer.parseInt(valueArr[row + j]);
				if (value < 0 || value > size) {
					throw new IllegalArgumentException(
						"Invalid value " + value + " provided in input");
				}
				SudokuCell cell = new SudokuCell(i, j, value);
				getCells().add(cell);
			}
		}
	}
	
	/**
	 * Returns first empty {@code SudokuCell}. The cell with value 0 is treated as empty. It may
	 * return null if no such cell is found, which means Sudoku is completed. 
	 */
	public SudokuCell findFirstEmptyCell() {
		for (SudokuCell cell : getCells()) {
			if (cell.getValue() == 0) {
				return cell;
			}
		}
		return null;
	}

	/**
	 * Validates that the following Sudoku rules and returns {@code true} if value is valid for
	 * provided cell:
	 *
	 * <ul>
	 * <li>Target cell must be empty
	 * <li>Value should not already exist in same row as target cell
	 * <li>Value should not already exist in same column as target cell
	 * <li>Value should not already exist in same box as target cell
	 * </ul>
	 * @param value value to be filled in cell
	 * @param cellToValidate target sudoku cell
	 * @return true if the value is valid for target cell
	 */
	public boolean isValidForCell(int value, SudokuCell cellToValidate) {
		// Check if cell to validate is empty
		if (cellToValidate.getValue() != 0) {
			return false;
		}
		
		int row = cellToValidate.getRow();
		int column = cellToValidate.getColumn();

		// Check if value does not already exist in row, column or box
		return isValidForCellRow(value, row) &&
		    isValidForCellColumn(value, column) &&
		    isValidForCellBox(value, row, column);
	}

	/**
	 * Validates that the value does not already exist in the row.
	 * @param value value to be filled
	 * @param row row to be checked for
	 * @return true if the value is valid for the row
	 */
	private boolean isValidForCellRow(int value, int row) {
		for (SudokuCell cell : getCells()) {
			if (cell.getRow() == row && cell.getValue() == value) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Validates that the value does not already exist in the column.
	 * @param value value to be filled
	 * @param column column to be checked for
	 * @return true if the value is valid for the column
	 */
	private boolean isValidForCellColumn(int value, int column) {
		for (SudokuCell cell : getCells()) {
			if (cell.getColumn() == column && cell.getValue() == value) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Validates that the value does not already exist in the box covering provided row and column.
	 * @param value value to be filled
	 * @param row row to identify the box
	 * @param column column to identify the box
	 * @return true if the value is valid for the box
	 */
	private boolean isValidForCellBox(int value, int row, int column) {
		// Find box starting limits
		int blockRowStart = (int)Math.floor(row/this.blockSize) * this.blockSize;
		int blockColumnStart = (int)Math.floor(column/this.blockSize) * this.blockSize;
		
		for (SudokuCell cell : getCells()) {
			if (cell.getRow() >= blockRowStart &&
				cell.getRow() < blockRowStart + blockSize &&
			    cell.getColumn() >= blockColumnStart &&
			    cell.getColumn() < blockColumnStart + blockSize &&
			    cell.getValue() == value) {
					return false;
			}
		}
		return true;
	}
}
