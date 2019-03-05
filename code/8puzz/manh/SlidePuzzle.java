import java.util.Random;
import java.util.LinkedList;

public class SlidePuzzle
{   
    // 2D int array, represents the puzzle
    // and the puzzle after movement.
    private int[][] puzzle = new int[3][3];
    private int[][] tempPuzzle = new int[3][3];
    
    // Answer key, direction the node came from,
    // and the solution path (for A* search).
    private final int[][] key = {{1,2,3},{4,5,6},{7,8,0}};
    public String direction = "";

    /* - Constructor, calls buildPuzzle(str) to
    initialize the puzzle with values.
    - Input is validated before object is created. */
    public SlidePuzzle(String str, String dir)
    {
        buildPuzzle(str);
        direction = dir;
    }

    public SlidePuzzle(String dir)
    {
        buildRandomPuzzle();
        direction = dir;
    }

/* Build Puzzle, Get Puzzle */

    /*
    Name: buildPuzzle(str)
    Parameters: String str
    Function: populates the 9-puzzle using
        numeric values from a 9 char string
    */
    private void buildPuzzle(String str)
    {
        int count = 0;
        for (int i = 0; i < puzzle.length; i++)
        {
            for (int j = 0; j < puzzle[i].length; j++)
            {
                puzzle[i][j] = Character.getNumericValue(str.charAt(count));
                count++;
            }
        }

    }

        /*
    Name: buildRandomPuzzle()
    Parameters: n/a
    Function: populates the 9-puzzle with
        random values
    */
    private void buildRandomPuzzle()
    {
        Random random = new Random();
        boolean[] digits = new boolean[9];
        int num = -1;

        for (int i = 0; i < puzzle.length; i++)
        {
            for (int j = 0; j < puzzle[i].length; j++)
            {
                while(true)
                {
                    num = random.nextInt(9);
                    if (digits[num] == false)
                    {
                        puzzle[i][j] = num;
                        digits[num] = true;
                        break;
                    }
                }
            }
        }

    }

    /* 
    Name: getPuzzleState, getTempPuzzleState
    Parameters: n/a
    Function: returns a 6 character string that
        represents the puzzle's current state
    */
    public String getPuzzleState()
    {
        String state = "";
        for (int i = 0; i < puzzle.length; i++)
        {
            for (int j = 0; j < puzzle[i].length; j++) 
            {
                state += Integer.toString(puzzle[i][j]);
            }
        }
        return state;
    }
    public String getTempPuzzleState()
    {
        String state = "";
        for (int i = 0; i < tempPuzzle.length; i++)
        {
            for (int j = 0; j < tempPuzzle[i].length; j++) 
            {
                state += Integer.toString(tempPuzzle[i][j]);
            }
        }
        return state;
    }

    // getPuzzle() returns the 2D puzzle array
    public int[][] getPuzzle()
    {
        return puzzle;
    }

    // getTempPuzzle() returns the puzzle after the movement
    public int[][] getTempPuzzle()
    {
        return tempPuzzle;
    }

    // Print the puzzle and tempPuzzle
    public void printPuzzle()
    {
        System.out.println("=========");
        for (int i = 0; i < puzzle.length; i++)
        {
            for (int j = 0; j < puzzle[i].length; j++)
            {
                System.out.printf("[%d]", puzzle[i][j]);
            }
            System.out.println();
        }
        System.out.println("=========");
    }
    public void printTempPuzzle()
    {
        System.out.println("=========");
        for (int i = 0; i < tempPuzzle.length; i++)
        {
            for (int j = 0; j < tempPuzzle[i].length; j++)
            {
                System.out.printf("[%d]", tempPuzzle[i][j]);
            }
            System.out.println();
        }
        System.out.println("=========");
    }

/*
Name: isSolved
Parameters: n/a
Function: returns whether or not the puzzle
    matches the goal state
*/

    public boolean isSolved()
    {
        for (int i=0; i < puzzle.length; i++)
        {
            for (int j=0; j < puzzle[i].length; j++)
            {
                if (puzzle[i][j] != key[i][j])
                {
                    return false;
                }
            }
        }
        return true;
    }

/*
Name: numWrongTiles
Parameters: n/a
Function: returns the number of tiles that do
    not match the goal state
*/
    public int numWrongTiles()
    {
        int count = 0;
        for (int i=0; i < puzzle.length; i++)
        {
            for (int j=0; j < puzzle[i].length; j++)
            {
                if ((puzzle[i][j] != key[i][j]) && puzzle[i][j] != 0)
                {
                    count++;
                }
            }
        }
        return count;
    }

/*
Name: manhattanDistance
Parameters: int key
Function: returns how far off a given tile
    is from its proper position
*/
    public int manhattanDistance(int key)
    {
        if (key < 0 || key > 9)
            return -1;

        int row = findKeyRow(key);
        int col = findKeyCol(key);

        int correctRow = -1;
        int correctCol = -1;

        if (key == 0) {
            return -1;
        }
        else if (key == 1) {
            correctRow = 0; correctCol = 0;
        }
        else if (key == 2) {
            correctRow = 0; correctCol = 1;
        }
        else if (key == 3) {
            correctRow = 0; correctCol = 2;
        }
        else if (key == 4) {
            correctRow = 1; correctCol = 0;
        }
        else if (key == 5) {
            correctRow = 1; correctCol = 1;
        }
        else if (key == 6) {
            correctRow = 1; correctCol = 2;
        }
        else if (key == 7) {
            correctRow = 2; correctCol = 0;
        }
        else if (key == 8) {
            correctRow = 2; correctCol = 1;
        }

        return Math.abs(correctRow-row) + Math.abs(correctCol-col);
    }
    // Sum of all tile displacements
    public int totalManhattanDistance(){
        int total = 0;
        for (int i = 1; i <= 8; i++)
        {
            total += manhattanDistance(i);
        }
        return total;
    }

/* 
Name: findKeyRow, findKeyCol
Parameters: int key
Function: returns the coordinates of a
    number in the puzzle if it can be
    found, returns -1 otherwise
*/
    private int findKeyRow(int key)
    {
        for (int i = 0; i < puzzle.length; i++)
        {
            for (int j = 0; j < puzzle[i].length; j++)
            {
                if (puzzle[i][j] == key)
                {
                    return i;
                }
            }
        }
        return -1;
    }

    private int findKeyCol(int key)
    {
        for (int i = 0; i < puzzle.length; i++)
        {
            for (int j = 0; j < puzzle[i].length; j++)
            {
                if (puzzle[i][j] == key)
                {
                    return j;
                }
            }
        }
        return -1;
    }


/*
Name: copyPuzzle
Parameters: int[][] a, int[][] b
Function: copies the contents of array b
    into array a, like how assignments
    set the value of left variable to
    the value of the right variable
*/
    private void copyPuzzle(int[][] a, int[][] b)
    {
        for (int i=0; i < a.length; i++)
        {
            for (int j=0; j < a[i].length; j++)
            {
                a[i][j] = b[i][j];
            }
        }

    }

/*
Name: moveL, moveD, moveU, moveR
Paramters: n/a
Function: does NOT modify the original puzzle!
    returns true/false if the move is possible
    and then executes the movement on the tempPuzzle
*/
    public boolean moveL()
    {
        // Copy puzzle to tempPuzzle
        copyPuzzle(tempPuzzle, puzzle);

        // Find the coordinates of the zero
        int row = findKeyRow(0);
        int col = findKeyCol(0);

        //Cannot move if zero is on the right wall.
        if (col == tempPuzzle[row].length-1) 
        {
            return false;
        }

        // Swap values with the space on the right.
        tempPuzzle[row][col]   = tempPuzzle[row][col+1];
        tempPuzzle[row][col+1] = 0;

        return true;
    }

    public boolean moveD()
    {
        // Copy puzzle to tempPuzzle
        copyPuzzle(tempPuzzle, puzzle);

        // Find the coordinates of the zero
        int row = findKeyRow(0);
        int col = findKeyCol(0);

        //Cannot move if zero is in the first row.
        if (row == 0) 
        {
            return false;
        }

        // Swap values with the space above.
        tempPuzzle[row][col]   = tempPuzzle[row-1][col];
        tempPuzzle[row-1][col] = 0;

        return true;
    }

    public boolean moveU()
    {
        // Copy puzzle to tempPuzzle
        copyPuzzle(tempPuzzle, puzzle);

        // Find the coordinates of the zero
        int row = findKeyRow(0);
        int col = findKeyCol(0);

        //Cannot move if zero is in the bottom row.
        if (row == tempPuzzle.length-1) 
        {
            return false;
        }

        // Swap values with the space below.
        tempPuzzle[row][col]   = tempPuzzle[row+1][col];
        tempPuzzle[row+1][col] = 0;

        return true;
    }

    public boolean moveR()
    {
        // Copy puzzle to tempPuzzle
        copyPuzzle(tempPuzzle, puzzle);

        // Find the coordinates of the zero
        int row = findKeyRow(0);
        int col = findKeyCol(0);

        //Cannot move if zero is on the left wall.
        if (col == 0) 
        {
            return false;
        }

        // Swap values with the space on the left.
        tempPuzzle[row][col]   = tempPuzzle[row][col-1];
        tempPuzzle[row][col-1] = 0;

        return true;
    }

/****************************************************************/


// Things for A* search

    private LinkedList<SlidePuzzle> path = new LinkedList<>();
    public void addToPath (SlidePuzzle pzl)
    {
        path.add(pzl);
    }
    public void removeFromPath ()
    {
        path.removeLast();
    }
    public LinkedList<SlidePuzzle> getPath ()
    {
        return path;
    }

    @SuppressWarnings("unchecked")
    public void setPath (LinkedList<SlidePuzzle> inPath)
    {
        path = (LinkedList<SlidePuzzle>)inPath.clone();
    }
    public void printPath ()
    {
        if (path.size() == 0)
        {
            System.out.println("MOVES: ");
        }
        else
        {
            System.out.print("MOVES: ");
            for (int i = 0; i < path.size()-1; i++){
                System.out.print(path.get(i).direction + "-");
            }
            System.out.println(path.get(path.size()-1).direction);
            System.out.println();
        }

    }

    
}
