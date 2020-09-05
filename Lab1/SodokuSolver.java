import java.util.*;

public class SodokuSolver{

	//grid for unsolved sodoku table
	public static int[][] grid = { //starting grid
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		{0,0,0,0,0,0,0,0,0},
		
	};

	private int[][] board; //own private board
	public static final int empty = 0; //variable that presents an empty cell
	public static final int gridSize = 9; //varaible for size of rows and columns of entire grid

	Scanner input = new Scanner(System.in); //Scanner to scan any input

	public SodokuSolver(int[][] board){
		this.board = new int[gridSize][gridSize];
		for(int i = 0; i < gridSize; i++){
			for(int j = 0; j < gridSize; j++){
				this.board[i][j] = board[i][j];
			}
		}
	}


	//check if number is in the row
	public Boolean isInRow(int row, int num){
		for (int i = 0; i < gridSize; i++){
			if (board[row][i] == num){
				return true;
			}
		}
		return false;
	}


	//checks if the number is in the column
	public Boolean isInCol(int col, int num){
		for (int i = 0; i < gridSize; i++){
			if (board[i][col] == num){
				return true;
			}
		}
		return false;
	}

	//checks if the number is in the box column
	public Boolean isInBox(int row, int col, int num){
		int rowBox = row - row % 3;
		int colBox = col - col % 3;

		for (int i = rowBox; i < rowBox + 3; i++){
			for (int j = colBox; i < colBox + 3; i++){
				if (board[i][j] == num){
					return true;
				}
			}
		}
		return false;
	}

	//checks if number is ok to put in the box
	public Boolean isOk(int row, int col, int num){
		return (!(isInCol(col, num) || isInRow(row, num) || isInBox(row, col, num)));
	}


	//is the grid solveable
	public Boolean solveable(){
		//start of the for loop in a for loop
		for(int row = 0; row < gridSize; row++){
			for(int col = 0; col < gridSize; col++){

				//try possible numbers for empty cell

				if (board[row][col] == empty){

					//put in possible number inputs

					for(int num = 1; num <= gridSize; num++){

						if (isOk(row, col, num)){ //if putting the number in the cell is acceptable
							board[row][col] = num; //assigns cell to number
							if (solveable()){ //recurion case
								return true;
							} else { //if the recursion returns false
								board[row][col] = empty; //makes the current number invalid and loops to the next number 
							}
						}
					}
				return false; //recursivly returns false if the cell can't accept the number or finally returns false if the grid 
				}
			}
		}
		return true; //returns true in the end if all cells are filled
	}






	//prints out the sodoku board in a fashionable way
	public static String showBoard(int[][] board){
		String res = " ----------------------- ";

		for(int row = 0; row < gridSize; row++){
			res += "\n| ";

			for (int col = 0; col < gridSize; col++){
				res += Integer.toString(board[row][col]) + " ";
				if ((col + 1) % 3 == 0)
					res += "| ";

			}

			if ((row + 1) % 3 == 0)
				res += "\n ----------------------- ";
		}

		return res;
	}

	//input function for a grid
	public void gridInput(int[][] grid){
		for(int row = 0; row < gridSize; row++){
			System.out.println("Please insert nine numbers to create a sodoku number row with '0' as and empty input.");
			String insert = input.nextLine(); // expects a nine digit number that will fill up a row in the table
			int insertInt = Integer.parseInt(insert); //turns the string into integers
			int dividen = 100000000; //starting dividen for each and every digit

			for(int col = 0; col < gridSize; col++){
				if (col != 0){ //when for loop is started to get the farmost left digit
					dividen = dividen / 10;
				}
				board[row][col] = insertInt / dividen; //integer is reduced by one digit
				insertInt = insertInt%dividen; //dividen is reduced to one digit smaller
			}		
			System.out.println(showBoard(board)); //prints the board
		}
	}

	public static void main (String agrs[]) {
		SodokuSolver solver = new SodokuSolver(grid);

		solver.gridInput(grid);

		Boolean solution = solver.solveable();

		if (solution){ //if the grid is solveable
			System.out.println("**********************************************");
			System.out.println("The Sodoku was solvable. Here is the result");
			System.out.println("**********************************************\n");
			System.out.println(showBoard(solver.board));
		} else{
			System.out.println("**********************************************");
			System.out.println("Sorry, the sodoku was unsolvable. Here is the grid");
			System.out.println("**********************************************\n");
			System.out.println(showBoard(solver.board));
		}
	}
}