package Searchable;

import java.util.ArrayList;

public interface Searchable<T> {
	Searchable<T> initState();
	boolean isGoal();
	T getState();
	ArrayList<Searchable<T>> getAllState(T state);
	
	// functions for searchers maybe unneeded
	int getCost();
	public boolean getVisted();
	public void setVisted(boolean Visted);
	public void setCameFrom(Searchable<T> cameFrom);
	public Searchable<T> getCameFrom();
	public int getLengthFromStart();
	public int getNumOfCols();
	public int getNumOfRows();
}
