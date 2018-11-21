/*
 * This class will correspond with the client
 * base on the protocol of the app.
 * Given a problem it will operate the chaceManager 
 * and will return an answer if it was indeed 
 * saved in the chaceData.
 * If it is a new problem , it will order the solver
 * to solve it, then order the chaceManager to save it,
 * and then it will send the answer to the client. 
 * 
 * */


package ClientHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

import CacheManager.CacheManager;
import CacheManager.MyCacheManager;
import Solver.PGS;
import Solver.Solver;

public class MyCHandler<T> implements ClientHandler {
	
	// Data Members
	private Solver<String> solver;
	private CacheManager cacheManager;
	
	// CTOR
	public MyCHandler(){ 
		this.cacheManager = new MyCacheManager();
		this.solver = null;
	}
	
	
	// Getters and setters
	Solver<String> getSolver(){return this.solver;}
	
	// methods
	
	// send the problem to a solver and returns a solution
	// will save / load for any problem
	public String sendToSolver(String problem) throws Exception {
		String solution;
		if((solution = cacheManager.load(problem)) == null)
			this.solver = new PGS(problem);
			cacheManager.save(problem, (solution = solver.solve(problem)));
		return solution;
	}

	// main method to handle problems
	@Override
	public void handle(InputStream inFromClient, OutputStream outToClient) throws Exception {
		// TODO Auto-generated method stub
		PrintWriter outTC = new PrintWriter(outToClient);
		BufferedReader inFClient = new BufferedReader(new InputStreamReader(inFromClient));
		try {
			// see that its a chat where the user talk to the server and just 
			// write to the server the problem 
			// this need to be more generic
			String line;
			String solution;
			String problem = "";
			System.out.println("Now starts to get user puzzle");
			while (!(line = inFClient.readLine()).equals("done")) {
				System.out.println("adds line" + line);
				problem += new StringBuilder(line);
				System.out.println("Added line");
				System.out.println(problem);
				System.out.println("flused");
				outTC.flush();
			}
			
			System.out.println("This is how the server got the problem: " + problem );
			if ((solution = sendToSolver(problem)) != null){
				System.out.println("Problem send to solver");
				outTC.println(solution);
		     	outTC.println("done");
			}
			outTC.close();
			inFClient.close();
		} catch(IOException E) {
			E.printStackTrace();
		}
	}

}
