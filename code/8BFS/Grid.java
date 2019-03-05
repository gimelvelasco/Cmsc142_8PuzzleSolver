import java.util.ArrayList;
import java.util.Collections;

class Grid {

  //variables to hold intial state and goal state
  private static String initialState;
  private static String goalState;

  //variables hold move taken to get to object, its state and its parents state
  public String move;
  private String state;
  private Grid parent;

  public Grid(){
    this.state = Grid.getInitialState();
  }

  //set variables of new grid instance.
  //parent parameter gives state of parent grid
  //move parameter is which child the new grid is eg L, R, U or D
  public Grid(Grid parent, String move) {
    this.move = move;
    this.parent = parent;
    //getState applys each possible move to current grid, creating new grids.
    this.state = parent.getState();
  }

  //getters and setters
  public static String getInitialState(){
    return Grid.initialState;
  }

  public static void setInitialState(String initialState){
    Grid.initialState = initialState;
  }

  public static String getGoalState(){
    return Grid.goalState;
  }

  public static void setGoalState(String goalState){
    Grid.goalState = goalState;
  }

  //return state of parent if no move parameter is given
  public String getState(){
    if (this.move != null) {
      return this.getMovedState();
    } else {
      return this.state;
    }
  }

  public String getMovedState() {
    return this.applyMove();
  }


  //check possible move is available. eg if move is L, no possible move if the
  //empty("0") index is 0, 3 or 6 becasue they are the left side of the grid
  public boolean valid() {
    int emptyIndex = this.findEmptySpace();
    if (this.move == "U" && emptyIndex <= 2) return false;
    if (this.move == "D" && emptyIndex >= 6) return false;
    if (this.move == "L" && emptyIndex % 3 == 0) return false;
    if (this.move == "R" && emptyIndex % 3 == 2) return false;
    return true;
  }

  //find each child of current grid
  public String applyMove(){
    switch(this.move){
      case "L":
        return findLMove();
      case "R":
        return findRMove();
      case "U":
        return findUMove();
      case "D":
        return findDMove();
    }
    return "";
  }

  //method returns board with 0 piece moved 1 place left;
  public String findLMove(){
    int emptyIndex = this.findEmptySpace();
    if (this.valid() == false) {
      return null;
    } else {
      //if move is possible, swap 0 with item in index of 0 - 1
      return this.swapItems(emptyIndex-1);
    }
  }

  //method returns board with 0 piece moved 1 place right;
  public String findRMove(){
    int emptyIndex = this.findEmptySpace();
    if (this.valid() == false) {
      return null;
    } else {
      //if move is possible, swap 0 with item in index of 0 + 1
      return this.swapItems(emptyIndex+1);
    }
  }

  //method returns board with 0 piece moved 1 place up;
  public String findUMove(){
    int emptyIndex = this.findEmptySpace();
    if (this.valid() == false) {
      return null;
    } else {
      //if move is possible, swap 0 with item in index of 0 - 3
      return this.swapItems(emptyIndex-3);
    }
  }

  //method returns board with 0 piece moved 1 place down;
  public String findDMove(){
    int emptyIndex = this.findEmptySpace();
    if (this.valid() == false) {
      return null;
    } else {
      //if move is possible, swap 0 with item in index of 0 + 3
      return this.swapItems(emptyIndex+3);
    }
  }

  //finds index of 0 so that it can be swapped with item in space L R U or D of it
  public int findEmptySpace(){
    return this.state.indexOf("0");
  }

  //swap 0 item with item in L R D or U space of it
  public String swapItems(int index){
    //turn string in to array for swap of characters
    char[] str = this.state.toCharArray();
    char char1 = this.state.charAt(findEmptySpace());
    char char2 = this.state.charAt(index);
    //in the index of 0, put char2 (item to be swapped with)
    str[findEmptySpace()] = char2;
    //in the index of the item to be swapped with, put char1(0)
    str[index] = char1;
    return String.valueOf(str);
  }


//method finds children of Grid object and adds them to queue or ends program if
//goal state is reached.
public ArrayList<Grid> getChildren(){
  //for each grid, create array of each possible move that can be taken
  ArrayList<Grid> grids = new ArrayList<Grid>();
  for (String move: new String[]{"L", "R", "U", "D"}) {
    Grid newGrid = new Grid(this, move);
    //add grid to array if the move is valid
    if (newGrid.valid()) {
      grids.add(newGrid);
    }
  }
  return grids;
}

//method finds the moves taken to achieve the goal state
public void displayPath(){
  //create new arraylist to store each move taken to reach goal
  ArrayList<String> solutionMoves = new ArrayList();
  Grid currentGrid = this.parent;
  //for every parent, add its move to array
  while (currentGrid.state != Grid.initialState){
    solutionMoves.add(currentGrid.move);
    currentGrid = currentGrid.parent;
    System.out.println(currentGrid.state);
  }
  //reverse array so correct order is given
  System.out.println("before "+ solutionMoves);
  Collections.reverse(solutionMoves);

  System.out.println("before "+ solutionMoves);
  //add final move
  solutionMoves.add(this.move);
  System.out.println("moves taken: " + solutionMoves);
}

@Override
public boolean equals(Object obj) {
  if (!(obj instanceof Grid)) return false;
  Grid grid = (Grid) obj;
  return (grid.getState().equals(this.getState()));
}
//prints string out, used for testing and for outputs.
public String toString(){
  StringBuilder str = new StringBuilder();
  char[] arr =  this.getState().toCharArray();
  for( int i = 0; i<arr.length; i++ ){
    if (arr[i] == '0') {
      str.append(' ');
    } else {
      str.append(arr[i]);
    }
    if(i % 3 == 2){
      str.append('\n');
    }
  }
  return String.valueOf(str);
}

}
