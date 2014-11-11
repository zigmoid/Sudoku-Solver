package com.zigmoidworld.sudoku;

/**
 * Solves 9X9 Sudoku.
 */
public class SudokuSolver {
	private static final int SUPPORTED_SIZE = 9;
	
	/**
	 * Solves the Sudoku with stringified input.
	 * @param sudokuInput comma-separated stringified version of Sudoku inputs with 9X9 values in
	 *     row wise manner. The empty values can be represented by character 'x' or '0'
	 * @return stringified version of solved Sudoku values in row wise manner
	 */
	public String solve(String sudokuInput) {
		Sudoku sudoku = create(sudokuInput);
		if (solve(sudoku)) {
			return sudoku.print();
		}
		throw new RuntimeException();
	}

	/**
	 * Create a {@code Sudoku} from stringified input.
	 * @param sudokuInput comma-separated stringified version of Sudoku inputs with 9X9 values in
	 *     row wise manner. The empty values can be represented by character 'x' or '0'
	 * @return sudoku with initialized cells
	 */
	public Sudoku create(String sudokuInput) {
		// Internally treat 0 as empty place.
		// TODO: May be do this in service itself.
		sudokuInput = sudokuInput.replaceAll("x", "0");
		String[] inputs = sudokuInput.split(",");
		if (inputs.length != SUPPORTED_SIZE * SUPPORTED_SIZE) {
			throw new IllegalArgumentException("Invalid input, only 9X9 sudoku is supported");
		}

		Sudoku sudoku = new Sudoku(SUPPORTED_SIZE);
		sudoku.populateCells(inputs);
		return sudoku;
	}
	
	/**
	 * Solves the {@code Sudoku} provided.
	 * @param sudoku a sudko with initialized cells
	 * @return flag to indicate if Sudoku could be completed
	 */
	public boolean solve(Sudoku sudoku) {
		SudokuCell emptyCell = sudoku.findFirstEmptyCell();
		if (emptyCell == null) {
			// There is no empty cell, puzzle solved.
			return true;
		}
		// Try each value for the empty cell one by one unless sudko is solved.
		for (int value = 1; value <= sudoku.getSize(); value++) {
			if (sudoku.isValidForCell(value, emptyCell)) {
				emptyCell.setValue(value);
				// Recursive call to complete rest of sudoku assuming cell value s correct.
				if (solve(sudoku)) {
					return true;
				}
				// Sudoku not solved with the value, revert back.
				emptyCell.setValue(0);
			}
		}
		return false;
	}
}
