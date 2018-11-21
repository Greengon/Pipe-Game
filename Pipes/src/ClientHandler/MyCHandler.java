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
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

import CacheManager.CacheManager;
import CacheManager.MyCacheManager;
import Solver.PGS;
import Solver.Solver;

public class MyCHandler<T> implements ClientHandler {
	
	// Data Members
	private Solver<String> solver;
	private CacheManager cacheManager;
	private String problem = "";
	private Socket Client;
	// CTOR
	public MyCHandler(){ 
		this.cacheManager = new MyCacheManager();
		this.solver = null;
		
	}
	public MyCHandler(Socket Client){ 
		this.cacheManager = new MyCacheManager();
		this.solver = null;
		this.Client=Client;
	}
	
	// Getters and setters
	Solver<String> getSolver(){return this.solver;}
	
	public Socket getClient() {
		return Client;
	}
	public void setClient(Socket client) {
		Client = client;
	}
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

	@Override
	public void recive(InputStream in) throws Exception {
		BufferedReader inFClient = new BufferedReader(new InputStreamReader(in));
		try {
			// see that its a chat where the user talk to the server and just 
			// write to the server the problem 
			// this need to be more generic
			String line = "";
			System.out.println("Now starts to get user puzzle");
			while (!(line = inFClient.readLine()).equals("done")) {
				System.out.println("adds line" + line);
				this.problem += new StringBuilder(line);
				System.out.println("Added line");
				this.problem += "\n";
				System.out.println(this.problem);
				System.out.println("flused");
			}}catch(IOException E) {
				E.printStackTrace();
			}
		this.problem = this.problem.substring(0, this.getProblemLength() - 1 );
			
		
	}

	@Override
	public void send(OutputStream out) throws Exception {
		long start = new Date().getTime();
		try {
			String solution;
			PrintWriter outTC = new PrintWriter(out);
			System.out.println("This is how the server got the problem: " + problem );
			if ((solution = sendToSolver(problem)) != null){
				System.out.println("Problem send to solver");
				Thread.sleep(1000);
				System.out.println("solution: " + solution);
				outTC.println(solution.replaceAll("\n",","));
		     	long end = new Date().getTime();
		     	outTC.println("Started: " + start + " Ended: " + end );
				outTC.println("done");
			}
			outTC.close();	
	}catch(IOException E) {
		E.printStackTrace();
	}
	}
	public int getProblemLength() {
		// TODO Auto-generated method stub
		return this.problem.length();
	}

}