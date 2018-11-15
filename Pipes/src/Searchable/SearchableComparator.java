package Searchable;

import java.util.Comparator;

public class SearchableComparator<T> implements Comparator<Searchable<T>> {

		public int compare(Searchable<T> arg0, Searchable<T> arg1) {
			if (arg0.getCost() < arg1.getCost())
				return -1;
			else 
				return 1;
		}
}
