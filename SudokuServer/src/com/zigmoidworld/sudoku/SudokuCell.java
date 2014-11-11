package com.zigmoidworld.sudoku;

/**
 * Cell on the Sudoku table/board
 */
public class SudokuCell {
	private int row;
	private int column;
	private int value;

	public SudokuCell(int row, int column, int value) {
		this.row =  row;
		this.column = column;
		this.value = value;
	}
	
	public SudokuCell setValue(int value) {
		this.value = value;
		return this;
	}

	public int getValue() {
		return this.value;
	}

	public int getRow() {
		return this.row;
	}

	public int getColumn() {
		return this.column;
	}
}
