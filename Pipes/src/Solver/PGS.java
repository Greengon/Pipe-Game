package Solver;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Date;

import Searchable.PipeGameBoard;
import Searchable.Searchable;
import Searcher.BestFS;
import Searcher.Searcher;
import Server.MyServer;

public class PGS implements Solver<String> {

	// Data members
	// Don't change the T's!!!!
	private Searcher<char[][]> searcher;
	private Searchable<char[][]> pipeGameBoard;
	private int[][] solutionMetrix;
	private String solution = null;

	// CTOR
	public PGS(String problem){
		this.solution = solve(problem);
			}
	
	// getters and setters
	public Searcher<char[][]> getSearcher(){return searcher;}
	public Searchable<char[][]> getPipeGameBoard(){return pipeGameBoard;}
	public String getSolution() {return this.solution;} 
	
	// methods
	
	@Override
	public String solve(String problem) {
		String solution;
		this.pipeGameBoard = new PipeGameBoard(problem);
		this.searcher = new BestFS<char[][]>();
		this.pipeGameBoard = this.searcher.search(this.pipeGameBoard);
		if(this.pipeGameBoard != null) {
			solutionMetrix = new int[((PipeGameBoard) this.pipeGameBoard).getNumOfRows()][((PipeGameBoard) this.pipeGameBoard).getNumOfCols()];
			FillMetrix(this.pipeGameBoard);
			solution = metrixToSolution(this.solutionMetrix);
			return solution;
		}
		else
			return "No solution";
		
	}

	private String metrixToSolution(int[][] solutionMetrix) {
		String solution = "";
		for (int i = 0 ; i <= ((PipeGameBoard) this.pipeGameBoard).getNumOfRows() - 1; ++i ) {
			for (int j =0 ; j <= ((PipeGameBoard)pipeGameBoard).getNumOfCols() -1 ; ++j) {
				
				if(solutionMetrix[i][j] > 0)
					solution += i + "," + j + "," + solutionMetrix[i][j] + "\n";
			}
		}
		if(solution.length() > 0)
			solution=solution.substring(0, solution.length()-1);
		return solution;
	}

	private void FillMetrix(Searchable<char[][]> pipeGameBoard) {
		while(pipeGameBoard.getCameFrom() != null){
			solutionMetrix[((PipeGameBoard)pipeGameBoard).getPressedRow()][((PipeGameBoard)pipeGameBoard).getPressedCol()] += 1;
			pipeGameBoard = pipeGameBoard.getCameFrom();
		}
	}

	@Override
	public void createProblem() {};
	
	// Main for test
	public static void main(String[] args) throws UnknownHostException, IOException {
		
		/*String problem1 = "----\r\n" + 
				"---g\r\n" + 
				"s-|FJ";
			String problem2 = "s-7g\r\n" + 
				"--L7\r\n" + 
				"----";
		String problem3= "s-7g\r\n" + 
				"--L7\r\n" + 
				"----";
				*/
		//long start = new Date().getTime();
		//PGS test1 = new PGS(problem1);
		//PGS test2 = new PGS(problem2);
		//PGS test3 = new PGS(problem3);
		//long end = new Date().getTime();
		MyServer myserver = new MyServer(10000,4);
	/*	if (test1.getPipeGameBoard() != null) {
			if (test1.getPipeGameBoard().isGoal())
				System.out.println("There is a solution!");
			System.out.println("total: " + (end - start) + " mili seconds");
				System.out.println(test1.getSolution());
		}
		else System.out.println("no solution!");
	*/
	myserver.start();

	}

}
