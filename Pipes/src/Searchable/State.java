/*
 * State is an abstract class that represent any state in a given game.
 * PipeGameBoard is state. in searchable.
 * 
 * */


package Searchable;

import java.util.ArrayList;

public abstract class State<T> implements Searchable<T> {
	
	// Data members
	protected T state; // the state represented by a string
	protected int cost;  // cost to reach this state
	protected Searchable<T> cameFrom; // the state we came from to this state
	protected boolean visted = false;
	
	// CTOR
	public State() {
		state = null;
		cost = 0;
		cameFrom = null;
	}
	public State(T state) {
		this.state = state;
	}
	
	public State(State<T> state) {
		this.state=state.state;
		this.cost =state.cost;
		if (state.cameFrom != null)
			this.cameFrom = state.cameFrom;
		else
			this.cameFrom = null;
		this.visted = state.visted;
	}
	
	public State(T state, int cost,Searchable<T> cameFrom ,boolean visted) {
		this.state=state;
		this.cost =cost;
		if (cameFrom != null)
			this.cameFrom = cameFrom;
		else
			this.cameFrom = null;
		this.visted = visted;
	}
	
	public State(Searchable<T> fatherState, T currentState) {
		this.cameFrom = fatherState;
		this.state = currentState;
		this.cost = fatherState.getCost() + 1;
	}
	
	// Setters and Getters define private for Encapsulation
	public int getCost() {
		return cost;
	}
	
	@Override
	public Searchable<T> getCameFrom() {
		return cameFrom;
	}
	
	public T getState() {return state;}
	
	public boolean getVisted() {return visted;}
	
	public void setVisted(boolean Visted) {this.visted = Visted;}
	
	@Override
	public void setCameFrom(Searchable<T> cameFrom) {this.cameFrom = cameFrom;}
	
	
	// Implements methods 
	
	@Override
	public Searchable<T> initState(){
		while (getCameFrom() != null)
			return getCameFrom().initState();
		return this;
	}

	@Override
	public abstract boolean isGoal();

	@Override
	public abstract ArrayList<Searchable<T>> getAllState(T state);
	
	public abstract int getLengthFromStart();
	
	
}
