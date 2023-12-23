import java.awt.*;

import javax.swing.JOptionPane;


public class ConnectFourGrid {
  // Square size in the visual representation
	public final int SQ = 40;
  private boolean blacksTurn = true;
  private int checkersPlayed = 0;
  private boolean gameOver = false;
  private String winner;
  private int blackWins = 0;
  private int redWins = 0;
  /* Our grid of checkers.  Initially all are null.
     Row 0 is the top row, row 5 is the bottom row.  Col 0 is the left column, and Col 6 is the rightmost column.*/
  
	private Checker[][] grid = new Checker[6][7];
	


	/**
	 * This method is called when a column is clicked. It processes the click in the following order.  
	 * 1.  The column is  checked to see if it is full.  
	 * If it is full, then the click is ignored.
	 * If not full, then the correct color checker is placed in the lowest 
	 * row possible (biggest row number) in that column.  
	 * 
	 * If a checker was placed, then we need to check to see if there is 
	 * four in a row. I recommend checking to see if there is a four
	 * in a row vertically from the latest checker placed, then four in a row horizontally
	 * then four in a row along each diagonal from the latest checker location.  I have 
	 * created one function below called fourVerts which is intended to return true if the latest 
	 * checker is part of four in a row, vertically.  I recommend making 3 other methods, at least.
	 * 
	 * If the game is not over, then the turn is switched so that the next click will 
	 * place a checker of the opposite color.  If the game is over, then a message is 
	 * displayed and the game should reset on the next click.
	 * 
	 * @param col The column that the user clicked
	 */
	protected void colClicked(int col) {
    Checker newChecker;
    if (gameOver){
      clearBoard();
      checkersPlayed=0;
      gameOver=false;
      return;
    }
    if (blacksTurn){
      newChecker = new BlackChecker();
    }
    else{
      newChecker = new RedChecker();
    }
		if(colFull(col)) {
			System.out.println("Column "+col+ " is full!!");
			return;
		}
    else {
      int newRow=lowestEmptyLoc(col).getRow();
      Location newLoc = new Location(newRow,col);
      grid[newRow][col]=newChecker;
      
    
    if (checkGameOver(newLoc)){
      gameOver=true;
      if(blacksTurn){
        winner="BLACK";
        blackWins++;
      }
      else {
        winner="RED";
        redWins++;
      }
    }
    else {
      blacksTurn = !blacksTurn;
      checkersPlayed++;

    }
    }
    


	}
	
	
	// prints the board contents in the console and prints who won
	private void displayStateInConsole() {
		


	}
	/**
	 * This method checks to see if the game is over.  It checks to see if the game has been 
	 * won by either player.  More advanced groups should consider how to determine if the 
	 * game can't be won (tie game)
	 * @param loc This is the latest Location where a Checker was added.
	 * @return This returns true if the game is over.  More advanced groups may change this
	 *  to return an int, where 0 means keep going , 1 means latest player won, 2 means tie game
	 *  Alternatively, you can write a gameTied method that is called AFTER checkGameOver...
	 */
	private boolean checkGameOver(Location loc) {

		return fourVerts(loc) ||fourHoriz(loc) || fourDiagonal(loc) ;
	}


	/**
	 * Checks to see if there are four checkers in a row that match each other starting 
	 * with loc and going to the South (because loc was the last checker played).  What is the
	 * maximum value of loc.getRow() such that you don't need to even check the places below?
	 * @param loc The Location of the latest Checker added to the Grid
	 * @return true if loc is the top of a four-in-row (all the same color)
	 */
	private boolean fourVerts(Location loc) {
		int row = loc.getRow();
    if(row > 2){
      return false;
    }
    Checker top = grid[row][loc.getCol()];
    for(int x = 0; x<3; x++){
      Checker below = grid[row+1+x][loc.getCol()];
      if(top.isRed()!= below.isRed()){
        return false;
      }
    }

		return true;
	}


  private boolean fourHoriz(Location loc){
    
    int count = 1; 
    int row = loc.getRow();
    boolean isRed = grid[row][loc.getCol()].isRed();
    for(int col = loc.getCol() - 1; col >= 0; col--)
    {
      Checker piece = grid[row][col];
      if(piece != null && piece.isRed() == isRed)
      {
        count++;
      }
      else
      {
        break;
      }
    }

    for(int col = loc.getCol() + 1; col < grid[0].length; col++)
    {
      Checker piece = grid[row][col];
      if(piece != null && piece.isRed() == isRed)
      {
        count++;
      }
      else
      {
        break;
      }
    }

    if(count >= 4)
    {
      return true;
    }

    return false;
  }

 private boolean fourDiagonal(Location loc)
  {
    int count1 = 0; 
    int count2 = 0;
    int row = loc.getRow();
    int col = loc.getCol();
    boolean isRed = grid[row][col].isRed();
  
    for(int i = 0; i <= 3; i++)
    {
      if(row+i>5)
      {
        break;
      }
      if(col+i>6)
      {
        break;
      }
      Checker piece = grid[row+i][col+i];
      if(piece != null && piece.isRed() == isRed)
      {
        count1++;
      }
      else
      {
        break;
      }
    }
    for(int i = 0; i <= 3; i++)
    {
      if(row-i<0)
      {
        break;
      }
      if(col-i<0)
      {
        break;
      }
      Checker piece = grid[row-i][col-i];
      if(piece != null && piece.isRed() == isRed)
      {
        count1++;
      }
      else
      {
        break;
      }
    }
    if(count1 >= 5)
    {
      count1=0;
      return true;
    }


    for(int i = 0; i <= 3; i++)
    {
      if(row+i>5)
      {
        break;
      }
      if(col-i<0)
      {
        break;
      }
      Checker piece = grid[row+i][col-i];
      if(piece != null && piece.isRed() == isRed)
      {
        count2++;
      }
      else
      {
        break;
      }
    }
    for(int i = 0; i <= 3; i++)
    {
      if(row-i<0)
      {
        break;
      }
      if(col+i>6)
      {
        break;
      }
      Checker piece = grid[row-i][col+i];
      if(piece != null && piece.isRed() == isRed)
      {
        count2++;
      }
      else
      {
        break;
      }
    }
    if(count2 >= 5)
    {
      
      count2=0;
      return true;
    }

    return false; 
    
  }

   
 



	/** Finds the lowest empty Location in the specified column or null if the column is full
	 *  The "lowest" Location is the Location with the largest row (or furthest South)with the specified column.
	 * @param col Column to scan
	 * @return Location that is lowest in the column or null if the column is full
	 */
	private Location lowestEmptyLoc(int col) {
    Location Lel;
		int x = grid.length-1;
    while (x>=0 && grid[x][col]!=null){
      x--;
    }
    if (x<0){
      return null;
    }
    else{
      Lel = new Location(x,col);
      return Lel;
    }
	}


	// checks to see that the specified column is full.
	// you can call lowestLoc to help you
	private boolean colFull(int col) {
    if(lowestEmptyLoc(col)==null){
      return true;
    }


		return false;
	}

	/**
	 * This method will be called when it is time to start a new game.
	 */
	public void clearBoard() {
		for(int rr=0; rr<grid.length; rr++){
      for(int cc=0; cc<grid[0].length; cc++){
        grid[rr][cc]=null;
      }
    }
		
	}

	public String toString(){
		String s = "";

		return s;
	}

/**
  Draw this board on the specified Graphics.  Do this by filling a blue rectangle the size of each space.  Then draw a circle with a color that matches the state of this space:  white circle if empty (null Checker there), red circle if the Checker is red, black circle if the Checker is not red*/
	public void draw(Graphics g) {
    for (int row=0; row<grid.length; row++){
      for (int col=0; col<grid[0].length; col++){
        g.setColor(Color.blue);
        g.fillRect(col * SQ, row * SQ, SQ, SQ);
        if (grid[row][col]==null){
          g.setColor(Color.white);
          g.fillOval(col * SQ, row * SQ, SQ-2, SQ-2);
        }
        else if(grid[row][col].isRed()){
          g.setColor(Color.red);
          g.fillOval(col * SQ, row * SQ, SQ-2, SQ-2);
        }
        else {
          g.setColor(Color.black);
          g.fillOval(col * SQ, row * SQ, SQ-2, SQ-2);
        }
      }
    }
    if (gameOver){
      g.setColor(Color.black);
      g.drawString(winner+" WINS!",300,100);
    }

    

		

	}
  // ask this grid which column is associated with the specified x coord.
  public int colFromX(int x){
    return x/SQ;
  }

  public boolean getTurn(){
    return blacksTurn;
  }

  public int getCheckersPlayed(){
    return checkersPlayed;
  }

  public int getBlackWins(){
    return blackWins;
  }
  
  public int getRedWins(){
    return redWins;
  }

}
