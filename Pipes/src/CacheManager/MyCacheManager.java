package CacheManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import CacheManager.CacheManager;

/*
 * 
 * Responsible to save the answers in files.
 * Needs to return if the solution is already exists
 * in O(1);
 * 
 * I think that the file class is an chaceManager type and not 
 * a single file.
 * 
 * */


public class MyCacheManager implements CacheManager {

	// Data members
	private HashMap<String,FileReader> savedSolutions;
	
	
	// CTOR , define public so that MyCHandler could create an instance of File.
	public MyCacheManager() {
		savedSolutions = new HashMap<String,FileReader>();
	}
	
	// Setters and Getters define private for Encapsulation
	private FileReader getSavedSoultion(String Problem) {
		return savedSolutions.get(Problem);
	}
	
	private void enterNewSoultion(String problem, FileReader file) {
		// save the solution to savedSolutions HashMap
		savedSolutions.put(problem,file);
	}
	
	// methods
	
	public boolean isThereSavedSolution(String problem) { 
		// need to check if there is saved solution in O(1) with the HashMap
		if (savedSolutions.containsKey(problem))
			return true;
		return false;
	}
	
	@Override
	public void save(String problem, String solution) throws IOException{
		 try {
		// Check maybe a solution already exist
		if (isThereSavedSolution(problem))
			System.out.println("");//"There is already saved solution to the problem.");
		 else {
			// write the solution to a file
			PrintWriter writer = new PrintWriter(new FileWriter(problem + ".txt"));
			writer.println(solution);
			writer.close();
			
			// save the solution to savedSolutions HashMap
			enterNewSoultion(problem, new FileReader(problem + ".txt"));
			}
		 } catch (IOException e) {
	// 	System.out.println("Problem saving solution for the problem");
		 } finally {
		// 	System.out.println("safe exit");
		 }
	}

	@Override
	public String load(String problem) throws IOException {
		// need to load and return a solution
		try {
		// Check first is there a solution	
			if (isThereSavedSolution(problem)) {
				String solution = null;
				String line;
				System.out.println("A saved solution as been found.\nNow extracting solution.");
				BufferedReader reader = new BufferedReader(getSavedSoultion(problem));
				while((line = reader.readLine()) != null) // Maybe unneeded in one line solution
					solution += line;
				reader.close();
				return solution;
			} //else System.out.println("There is no saved solution to the problem");
		} catch (IOException e) {
		//	System.out.println("Problem loading solution for the problem");
		} finally {
		//	System.out.println("safe exit");
		}
		return null;
	}
}
