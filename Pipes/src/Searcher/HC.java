package Searcher;

import java.util.HashSet;
import java.util.List;
import java.util.Stack;

import Searchable.Searchable;
import Searchable.SearchableHeuristic;

public class HC<T> implements Searcher<T> {	
	
	public Searchable<T> search(Searchable<T> tosearch) {
	Stack<Searchable<T>> stack = new Stack<Searchable<T>>();
	HashSet<String> seenSet = new HashSet<String>();
	SearchableHeuristic<T> Heuristic =  new SearchableHeuristic<T>();
	Searchable<T> position= tosearch.initState();
	stack.add(position);
	seenSet.add(position.toString());
	while (stack.size() != 0) {
		position= stack.pop();
		if (position.isGoal())
			return position;
	    List<Searchable<T>> allStateList = position.getAllState(position.getState());
		
	    if (allStateList.size() > 0) {
					double cost = 0;
					for (Searchable<T> v :allStateList) {
						double g = Heuristic.SearchableHeuristic(v);
						if (g < cost && stack.size() > 0) {
							Searchable<T> temp = stack.pop();
							if(!seenSet.contains(v.toString())) {
								stack.add(v);
								seenSet.add(v.toString());
							}
							stack.add(temp);
						} else {
							if(!seenSet.contains(v.toString())) {
								stack.add(v);
								seenSet.add(v.toString());
							}
							cost = g;
						}
				}
			}
	      
	}
	return null;
       
        }
}

/*
 * 
 * package algos;

import java.util.ArrayList;
import java.util.LinkedList;
//import java.util.PriorityQueue;
import java.util.Random;

import interfaces.Heuristic;
import interfaces.Searchable;
import utils.CommonSearcher;
import utils.State;

public class HillClimbing<T> extends CommonSearcher<T> {

	private Heuristic<T> heuristic;
	private int timeToRun;
	
	public HillClimbing(Heuristic<T> heu, int timeToRun){
		this.heuristic = heu;
		this.timeToRun = timeToRun;
		//openList = new PriorityQueue<State<T>>(new StateComperator<T>());
	}
	
	@Override
	public LinkedList<T> search(Searchable<T> s) {
		System.out.println("hill climbing");
		State<T> next = s.getInitialState();

		long time0 = System.currentTimeMillis();
		while (System.currentTimeMillis() - time0 < timeToRun) {
			ArrayList<State<T>> neighbors = s.getAllPossibleStates(next);

			if (s.isGoal(next)) {
				return backTrace(next, s.getInitialState());
			}

			if (neighbors.size() > 0) {
				if (Math.random() < 0.7) { 
					double grade = 0;
					for (State<T> step : neighbors) {
						double g = heuristic.calcHeuristicForState(step);
						if (g > grade) {
							grade = g;
							next = step;
						}
					}
				} else {
					next = neighbors.get(new Random().nextInt(neighbors.size()));
				}
			}
		}
		//System.out.println("HillClimbing: TimeOut");
		return null;
	}

}

 * 
 * 
 * */
