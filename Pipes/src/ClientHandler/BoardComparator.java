package ClientHandler;

import java.util.Comparator;

import Searchable.Searchable;

public class BoardComparator<T> implements Comparator<MyCHandler<T>>{
		
		@Override
		public int compare(MyCHandler<T> arg0,MyCHandler<T> arg1) {
			if(arg0.getProblemLength() < arg1.getProblemLength())
				return -1;
			else 
				return 1;
}
}
