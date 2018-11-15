package Searcher;

import java.util.*;

import Searchable.Searchable;

public class BFS<T> implements Searcher<T>{
	
	@Override
	public Searchable<T> search(Searchable<T> tosearch) {
		Searchable<T> theState;
		LinkedList<Searchable<T>> list = new LinkedList<Searchable<T>>();
		HashSet<String> seenSet = new HashSet<String>();
		list.add(tosearch.initState());
		
		  while(!list.isEmpty())
		   {
			   theState = list.poll();
			   if(!(theState.getVisted()) && !(seenSet.contains(theState.toString()))) {
				   theState.setVisted(true);
				   if(theState.isGoal())
					   return theState ;
				   seenSet.add(theState.toString());
				   for (Searchable<T> s :theState.getAllState(theState.getState())) {
					   if (!list.contains(s) && !seenSet.contains(s.toString())) {
						   list.add(s);
					   }
				   }
			   }
		   }
		return null;
		
	}

}
