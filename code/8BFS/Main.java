import java.util.ArrayList;
import java.util.Scanner;

public class Main {

// *********** INITIALISING**************

// create scanner for inputs
static Scanner userInput = new Scanner(System.in);

// create arrays to store each node of BFS and the queue of nodes to be expanded
public static ArrayList<Grid> known_grids = new ArrayList<Grid>();
public static ArrayList<Grid> todo_grids = new ArrayList<Grid>();


  public static void main(String[] args){
    //inputs initial board and goal board states
    //String initialState = Main.inputBoard("enter initial");
    //String goalState = Main.inputBoard("enter goal");
    Grid.setInitialState("023745816");
    Grid.setGoalState("123456780");
//////////////////////////////////////////////////////////////////////////////
	
    //for initial board state
    //add each initial move to queue
    known_grids.add(new Grid());
    todo_grids.add(new Grid());

    Main.runThroughQueue();

  }

  //for each item in todo queue, add its parents to queue and knows_grids array
  public static void runThroughQueue(){
    //loop until length of the queue is < 1
	long start = System.currentTimeMillis();
    while (todo_grids.size()>0) {
      //grab first item in queue array
      Grid currentGrid = todo_grids.get(0);
      //remove item from queue
      todo_grids.remove(0);

      //stop program if current node is equal to goal state
      if (currentGrid.getState().equals(Grid.getGoalState())){
		  	long end = System.currentTimeMillis();
		long duration = end - start;
			System.out.println("Execution time is " + duration + " ms");
        Main.endProgram(currentGrid);
		
      }
      //find children of current node and add them to queue
      ArrayList<Grid> children = currentGrid.getChildren();
      for(Grid child: children){
        //do not add children if they are already in the array
        if(!known_grids.contains(child)){
          known_grids.add(child);
          todo_grids.add(child);
        }
      }
    }
    //if the while loop ends then no solution exists.
    System.out.println("no solution found");
  }
  //inputs code ensures inputs are not null and of length 9
  private static String inputBoard(String msg){
    String inp = null;
    do {
      System.out.println(msg);
      if (userInput.hasNextLine()) {
        inp = userInput.nextLine();
      }
    } while(inp.length() != 9);
    return inp;
  }

  //method stops program when goal state is reached
  public static void endProgram(Grid grid){
	
    System.out.println("state of winner: "+ grid.getState());
    grid.displayPath();

    System.exit(0);
  }
  
}
