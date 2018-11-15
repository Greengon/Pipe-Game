package Searcher;

import Searchable.Searchable;

public interface Searcher<T>{
	Searchable<T> search(Searchable<T> p);
}
