package Searchable;

import java.util.ArrayList;

/*
 * 
 *
 * 
 * */

public class PipeGameBoard extends State<char[][]> {
	// Date members
	int[] pressedRowCol = {-1,-1};
	int numOfRows;
	int numOfCols;
	int[] SColAndRow =new int[2];
	int[] GColAndRow= new int[2];
	
	//CTOR
	public PipeGameBoard(String problem) {
		super();
		int counter = 0;
		// counts the numbers of chars in a line for numOfCol
		while(counter < problem.length() && problem.charAt(counter) != '\n' && problem.charAt(counter) != '\r') {
			counter++;
		}
		 // counting "/n" in string + 1 for the end line that as no '\n'.
		this.numOfRows = 1 + problem.length() - problem.replace("\n", "").length();
		this.numOfCols = counter;
		this.state = new char[numOfRows][numOfCols];
		FillBricks(problem);
		setPressedRowCol(SColAndRow[1], SColAndRow[0]);
	}
	
	public PipeGameBoard(PipeGameBoard pipeGameBoard) {
		super();
		this.state = new char[pipeGameBoard.numOfRows][numOfRows];
		for (int i = 0 ; i < numOfRows ; ++i) {
			for (int j =0 ; j < numOfCols; ++j)
				this.state[i][j] = pipeGameBoard.state[i][j];
		}
		this.cost = pipeGameBoard.cost;
		this.visted = pipeGameBoard.visted;
		this.cameFrom = pipeGameBoard.cameFrom;
		numOfRows = pipeGameBoard.numOfRows;
		numOfCols = pipeGameBoard.numOfCols;
		SColAndRow = pipeGameBoard.SColAndRow;
		GColAndRow = pipeGameBoard.GColAndRow;
	}
	
	// Getters and setters
	public char getBrickAt(int BrickRow, int BrickCol) {return state[BrickRow][BrickCol];}

	public PipeGameBoard(char[][] state, int cost,Searchable<char[][]> cameFrom ,boolean visted,int[] SColAndRow,int[] GColAndRow,int numOfRows,int numOfCols,int pressedRow,int pressedCol) {
		super(state,cost,cameFrom,visted);
		this.numOfRows = numOfRows;
		this.numOfCols = numOfCols;
		this.SColAndRow = SColAndRow;
		this.GColAndRow = GColAndRow;
		this.pressedRowCol[0] = pressedRow;
		this.pressedRowCol[1] = pressedCol;
	}
	
	public int getNumOfRows() {return numOfRows;}
	public int getNumOfCols() {return numOfCols;}
	public int getPressedRow() {return pressedRowCol[0];}
	public int getPressedCol() {return pressedRowCol[1];}
	
	public void setPressedRowCol(int row,int col) {
		pressedRowCol[0] = row;
		pressedRowCol[1] = col;
	}

	
	// TransFrom methods

	// This method takes a valid string (doesn't check for exception) and makes a board
	// works don't touch.
	private void FillBricks(String problem) {
		int k=0;
		String temp = problem.replace("\n", "");
		temp = temp.replace("\r", "");
		for (int i = 0 ; i <= numOfRows-1; ++i) {
			for (int j = 0; j <= numOfCols-1 ; ++j) {
				state[i][j] = temp.charAt(k);
				if(temp.charAt(k) == 'g') {
					GColAndRow[1] = i;
					GColAndRow[0] = j;
				} else if(temp.charAt(k) == 's') {
					SColAndRow[1] = i;
					SColAndRow[0] = j;
				}
				++k;
			}
		}
		
	}


	// methods
	@Override
	public boolean isGoal() {
		// Lets start from s
		for (int i = 0; i < 4 ; i++) {
				if(validMove(SColAndRow[1],SColAndRow[0],i))
					return true;
		}
		return false;
	}
	
	// Helping method for isGoal()
	private boolean pathToGoal(int BrickRow, int BrickCol,int enterNode) {
		// Don't return to S
//		System.out.println(this.getBrickAt(BrickRow, BrickCol));
		if (BrickCol == SColAndRow[0] && BrickRow == SColAndRow[1])
			return false;
		// if we have reached G return true
		if (BrickCol == GColAndRow[0] && BrickRow == GColAndRow[1])
			return true;
		for (int i = 0; i < 4 ; i++) {
			// Checks we aren't returning back
			char go = this.state[BrickRow][BrickCol];
			if (i != enterNode) {
				if (PossibleExit(go,i)) {
					if(validMove(BrickRow,BrickCol,i))
						return true;
			
				}
		}
		}
		return false;
	}
	
	private boolean PossibleExit(char go, int enterNode) {
		if (go == 's' || go == 'g')
			return true;
		 switch(go) {
			case '|':
				if(enterNode==2)
						return true;
				if(enterNode==0)
						return true;
				break;
			case 'L':
				if(enterNode==0)
						return true;
				if(enterNode==1)
						return true;
				break;
			case 'F':
				if(enterNode==1)
						return true;
				if(enterNode==2)
						return true;
				break;
			case '7':
				if(enterNode==3)
						return true;
				if(enterNode==2)
						return true;
				break;
			case 'J':
				if(enterNode==0)
						return true;
				if(enterNode==3)
						return true;
				break;
			case '-':
				if(enterNode==3)
						return true;
				if(enterNode==1)
						return true;
				break;
					
			}
		return false;
	}

	// lets try valid enters
	private boolean validMove(int BrickRow,int BrickCol,int enterNode) {
		switch(enterNode) {
		case 0:
			// checks if there's an above brick
			if (BrickRow-1 >= 0 && validTouch(state[BrickRow][BrickCol],state[BrickRow-1][BrickCol],2))
				return pathToGoal(BrickRow-1,BrickCol,2);
			break;
		case 1:
			// check if there's an right brick
			if (BrickCol+1 <= numOfCols - 1 && validTouch(state[BrickRow][BrickCol],state[BrickRow][BrickCol+1],3))
				return pathToGoal(BrickRow,BrickCol+1,3);	
			break;
		case 2:
			// check if there's an down brick
			if (BrickRow+1 <= numOfRows - 1 && validTouch(state[BrickRow][BrickCol],state[BrickRow+1][BrickCol],0))
				return pathToGoal(BrickRow+1,BrickCol,0);
			break;
		case 3:
			// check if there's an left brick
			if (BrickCol-1 >= 0 && validTouch(state[BrickRow][BrickCol],state[BrickRow][BrickCol-1],1))
				return pathToGoal(BrickRow,BrickCol-1,1);	
			break;
		}
		return false;
	}


	private boolean validTouch(char c, char d,int enterNodeTod) {
		if (c == ' ' || d == ' ')
			return false;
		
		if (c == '|') {
			switch(enterNodeTod) {
				case(2):
					if (d == '|' || d == '7' || d == 'F' || d == 's' || d == 'g')
						return true;
					break;
				case(0):
					if (d == '|' || d == 'L' || d == 'J' || d == 's' || d == 'g')
						return true;
					break;
				default:
					return false;
			}
		}
		if (c == '-') {
			switch(enterNodeTod) {
				case(3):
					if (d == '-' || d == '7' || d == 'J' || d == 's' || d == 'g')
						return true;
					break;
				case(1):
					if (d == '-' || d == 'L' || d == 'F' || d == 's' || d == 'g')
						return true;
					break;
				default:
					return false;
			}
		}
		if (c == 'L') {
			switch(enterNodeTod) {
				case(2):
					if (d == '|' || d == '7' || d == 'F' || d == 's' || d == 'g')
						return true;
					break;
				case(3):
					if (d == '-' || d == '7' || d == 'J' || d == 's' || d == 'g')
						return true;
					break;
				default:
					return false;
			}
		}
		if (c == 'J') {
			switch(enterNodeTod) {
				case(2):
					if (d == '|' || d == '7' || d == 'F' || d == 's' || d == 'g')
						return true;
					break;
				case(1):
					if (d == '-' || d == 'F' || d == 'L' || d == 's' || d == 'g')
						return true;
					break;
				default:
					return false;
			}
		}
		if (c == '7') {
			switch(enterNodeTod) {
				case(0):
					if (d == '|' || d == 'J' || d == 'L' || d == 's' || d == 'g')
						return true;
					break;
				case(1):
					if (d == '-' || d == 'F' || d == 'L' || d == 's' || d == 'g')
						return true;
					break;
				default:
					return false;
			}
		}
		if (c == 'F') {
			switch(enterNodeTod) {
				case(0):
					if (d == '|' || d == 'J' || d == 'L' || d == 's' || d == 'g')
						return true;
					break;
				case(3):
					if (d == '-' || d == 'J' || d == '7' || d == 's' || d == 'g')
						return true;
					break;
				default:
					return false;
			}
		}
		if (c == 's') {
			switch(enterNodeTod) {
			case(0):
				if (d == '|' || d == 'J' || d == 'L' || d == 'g')
					return true;
				break;
			case(1):
				if (d == '-' || d == 'F' || d == 'L' || d == 'g')
					return true;
				break;
			case(2):
					if (d == '|' || d == 'F' || d == '7' || d == 'g')
						return true;
					break;
			case(3):
					if (d == '-' || d == 'J' || d == '7' || d == 'g')
						return true;
					break;
				default:
					return false;
			}
		}
		if (c == 'g') {
			switch(enterNodeTod) {
			case(0):
				if (d == '|' || d == 'J' || d == 'L' || d == 'g')
					return true;
				break;
			case(1):
				if (d == '-' || d == 'F' || d == 'L' || d == 'g')
					return true;
				break;
			case(2):
					if (d == '|' || d == 'F' || d == '7' || d == 'g')
						return true;
					break;
			case(3):
					if (d == '-' || d == 'J' || d == '7' || d == 'g')
						return true;
					break;
				default:
					return false;
					}
		}
		return false;
	}

	public ArrayList<Searchable<char[][]>> getAllState(char[][] state) {
	// create an ArrayList to return
		ArrayList<Searchable<char[][]>> array = new ArrayList<Searchable<char[][]>>();
		int i = this.getPressedRow();
		int j = this.getPressedCol();
		if((!(state[i][j] == 's')) && (!(state[i][j] == 'g')) && (!(state[i][j] == ' ')))
			array.add(createNewBoard(i,j));
		// Upper bricks
		for (int r = 0 ; r < this.getPressedRow() ; ++r) {
			for(int c = 0 ; c < this.numOfCols ; ++c ) {
				if(smartMove(r,c))
					array.add(createNewBoard(r,c));
			}
		}
		
		// right bricks
			for(int c = this.getNumOfCols() + 1 ; c < this.numOfCols ; ++c ) {
				if(smartMove(this.getPressedRow(),c))
					array.add(createNewBoard(this.getPressedRow(),c));
			}
		
		// left bricks
			for(int c = 0 ; c < this.getNumOfCols() ; ++c ) {
				if(smartMove(this.getPressedRow(),c))
					array.add(createNewBoard(this.getPressedRow(),c));
			}
			
		// down bricks
			for (int r = this.getPressedRow() + 1 ; r < this.numOfRows ; ++r) {
				for(int c = 0 ; c < this.numOfCols ; ++c ) {
					if(smartMove(r, c))
						array.add(createNewBoard(r,c));
				}
			}
			return array;
			}
	
	private boolean smartMove(int row,int col) {
		if(!(state[row][col] == 's') && !(state[row][col] == 'g') && !(state[row][col] == ' ')) {
			if(row == this.numOfRows && state[row][col] == '-')
				return false;
			if (col == 0 && state[row][col] == '|')
				return false;
			if (col == this.numOfCols && state[row][col] == '|')
				return false;
			if (col == this.SColAndRow[0] && row == this.SColAndRow[1] - 1 && state[row][col] == '|')
				return false;
			if (col == this.SColAndRow[0] && row == this.SColAndRow[1] + 1 && state[row][col] == '|')
				return false;
			if (col == this.SColAndRow[0] - 1 && row == this.SColAndRow[1] && state[row][col] == '-')
				return false;
			if (col == this.SColAndRow[0] + 1 && row == this.SColAndRow[1] && state[row][col] == '-')
				return false;
			if (col == this.GColAndRow[0] && row == this.GColAndRow[1] - 1 && state[row][col] == '|')
				return false;
			if (col == this.GColAndRow[0] && row == this.GColAndRow[1] + 1 && state[row][col] == '|')
				return false;
			if (col == this.GColAndRow[0] - 1 && row == this.GColAndRow[1] && state[row][col] == '-')
				return false;
			if (col == this.GColAndRow[0] + 1 && row == this.GColAndRow[1] && state[row][col] == '-')
				return false;
			if (row == 0 && state[row][col] == '-')
				return false;
			if ((this.pressedRowCol[0] == row -1 || this.pressedRowCol[0] == row + 1) && (this.pressedRowCol[1] == col) &&(state[pressedRowCol[0]][pressedRowCol[1]] == '-' && state[row][col] == '-'))
				return false;
			if ((this.pressedRowCol[0] == row -1 || this.pressedRowCol[0] == row + 1) && (this.pressedRowCol[1] == col) &&(state[pressedRowCol[0]][pressedRowCol[1]] == '|' && state[row][col] == '|'))
				return false;
			if ((this.pressedRowCol[1] == col -1 || this.pressedRowCol[1] == col + 1) && (this.pressedRowCol[0] == row) &&(state[pressedRowCol[0]][pressedRowCol[1]] == '|' && state[row][col] == '|'))
				return false;
			if ((this.pressedRowCol[1] == col -1 || this.pressedRowCol[1] == col + 1) && (this.pressedRowCol[0] == row) &&(state[pressedRowCol[0]][pressedRowCol[1]] == '-' && state[row][col] == '-'))
				return false;
			if (this.state[row][col] == '-' && this.state[row][col+1] == '-' && this.state[row][col-1] == '-' && !(this.state[row-1][col] == '|' || this.state[row-1][col] == '|'))
				return false;
			return true;
		}
		
		return false;
	}
	
	private PipeGameBoard createNewBoard(int iToPress, int jToPress) {
		char[][] temp = new char[numOfRows][numOfCols];
		for (int r = 0 ; r <= numOfRows - 1 ; ++r) {
			 //System.arraycopy(state[r], 0, temp[r], 0, state[r].length);
			for (int c = 0 ; c <= numOfCols - 1; ++c) {
					temp[r][c] = state[r][c];
			}					
		}
		PipeGameBoard pipeTemp = new PipeGameBoard(temp, cost + 1, this, false, SColAndRow, GColAndRow, numOfRows, numOfCols,iToPress,jToPress);
		pipeTemp.pressFunc(iToPress, jToPress);
		return pipeTemp;
	}
	

// methods
public void pressFunc(int iToPress, int jToPress) {
	switch(this.state[iToPress][jToPress]) {
		case('-'):
			this.state[iToPress][jToPress] = '|';
			break;
		case('|'):
			this.state[iToPress][jToPress] = '-';
			break;
		case('L'):
			this.state[iToPress][jToPress] = 'F';
			break;
		case('F'):
			this.state[iToPress][jToPress] = '7';
			break;
		case('7'):
			this.state[iToPress][jToPress] = 'J';
			break;
		case('J'):
			this.state[iToPress][jToPress] = 'L';
			break;
		default:
			break;
	}
	
}

@Override
public boolean equals(Object arg0) {
	for (int i = 0 ; i < numOfRows ; ++i) {
		for (int j =0 ; j < numOfCols; ++j) {
			if(this.state[i][j] != ((PipeGameBoard)arg0).state[i][j])
				return false;
		}
	}
	return true;
}

@Override
public int hashCode() {
	return java.util.Arrays.deepHashCode(this.state);
	
}

@Override
public int getLengthFromStart() {
	// TODO Auto-generated method stub
	return this.SColAndRow[1] - this.getPressedRow() + this.SColAndRow[0] - this.getPressedCol();
}


}

