package Searcher;

import java.util.HashSet;
import java.util.Stack;

import Searchable.Searchable;

public class DFS<T> implements Searcher<T> {

	@Override
	public Searchable<T> search(Searchable<T> tosearch) {
		Stack<Searchable<T>> stack = new Stack<Searchable<T>>();
		HashSet<String> seenSet = new HashSet<String>();
		stack.add(tosearch.initState());
		
		while(!stack.isEmpty()) {

			Searchable<T> state = stack.pop();

            if(!seenSet.contains(state.toString())){
            	if (state.isGoal())
            		return state;
            	seenSet.add(state.toString());
           
            for (Searchable<T> v :state.getAllState(state.getState())) {
				   if (!seenSet.contains(v.toString())) {
					   stack.push(v);
				   }
			   }
		   }
		 }
		return null;
           
            }
	}
