package com.zigmoidworld.webservices;

import com.google.gson.Gson;
import com.zigmoidworld.sudoku.SudokuSolver;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * JAX-RS service for handling Sudoku.
 */
@Path("sudoku")
public class SudokuSolverService {

	/**
	 * Solves the Sudoku to completion and returns stringified JSON with solution. Error message is
	 * sent in response if Sudoku can not be completed.
	 * ?input may be comma-separated sudoku cell values in row wise manner. The empty values should
     *     be represented by x.
	 */
	@GET
	@Path("/solve")
	@Produces(MediaType.TEXT_PLAIN)
	public String handleSolveRequest(@QueryParam("input") String input) {
		ServiceResponse response = new ServiceResponse();
		try {
			SudokuSolver solver = new SudokuSolver();
			response.setSolution(solver.solve(input));
		} catch (Exception e) {
			response.setError("Sudoku cannot be completed." + e.getMessage());
		}
		return new Gson().toJson(response);
	}
	
	// TODO: Make it a proto.
	private class ServiceResponse {
		String solution;
		String error;
		public void setSolution(String solution) {
			this.solution = solution;
		}
		public void setError(String error) {
			this.error = error;
		}
	}
}
