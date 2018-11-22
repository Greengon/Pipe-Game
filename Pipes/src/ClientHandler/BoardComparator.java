package ClientHandler;

import java.util.Comparator;

import Searchable.Searchable;
import Server.MyServer.solveThread;

public class BoardComparator<T> implements Comparator<solveThread>{
		
		@Override
		public int compare(solveThread arg0,solveThread arg1) {
			if(arg0.ch.getProblemLength() < arg1.ch.getProblemLength())
				return -1;
			else 
				return 1;
}
}
