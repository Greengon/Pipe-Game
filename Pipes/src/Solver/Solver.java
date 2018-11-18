/*
 * Responsible to solve problem  
 * */


package Solver;

// Will be using a generic type <T> as for unknown problem type

public interface Solver<T> {
	String solve(T problem); // I decided any answer should always return as a String to be saved.
	public int SearchableSize();
	void createProblem(); // Unknown yet
}
