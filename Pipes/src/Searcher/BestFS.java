package Searcher;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;


import Searchable.Searchable;
import Searchable.SearchableComparator;

public class BestFS<T> implements Searcher<T> {
	@Override
	public Searchable<T> search(Searchable<T> p) {
		// creating an empty PriortyQueue
		Comparator<Searchable<T>> comparator =  new SearchableComparator<T>();
		PriorityQueue<Searchable<T>> pq = new PriorityQueue<Searchable<T>>(1,comparator);
		HashSet<String> seenSet = new HashSet<String>();
		// insert "start" in PriorityQueue
		pq.add(p.initState());
		/*
		 * Until PriorityQueue is empty
		 * u = PriorityQueue.DeleteMin
		 * For each neighbor v of u , If v "Unvisited" , Mark v "Visited", pq.insert(v)
		 */
		while (pq.size() > 0) {
			Searchable<T> u = pq.poll();
	      //If u is the goal Exit
			if (u.isGoal())
				return u;
			else
				for(Searchable<T> v :u.getAllState(u.getState())){
					if (!seenSet.contains(v.toString())) {
						seenSet.add(v.toString());
						pq.add(v);
					}
				}
			//Mark v "Examined"
			u.setVisted(true);
			
		}
		return null;
		}
}
