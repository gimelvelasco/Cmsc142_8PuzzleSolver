import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        /* Initialization */
        Scanner input = new Scanner(System.in);
        SlidePuzzle ninePuzzle = null;
        String puzzleInput = ""; 

        /* Get a configuration for the puzzle */
        int userChoice = -1;
        while (userChoice != 1 && userChoice != 2)
        {
            System.out.println("1) Enter new puzzle --- 2) New random puzzle");
            System.out.print("Enter a choice: ");
                userChoice = input.nextInt();
                input.nextLine();
        }

        if (userChoice == 1)
        {
            while(true)
            {
                System.out.printf("Enter a puzzle config (xxxxxxxxx): ");
                    puzzleInput = input.nextLine();
                    puzzleInput = puzzleInput.trim();
                    if (validateInput(puzzleInput))
                    {
                        break;
                    }
            }

            /* Create a puzzle from the string */
            ninePuzzle = new SlidePuzzle(puzzleInput, "root");
        }
        
        if (userChoice == 2)
        {
            /* Randomly populate the 9 puzzle with values */
            ninePuzzle = new SlidePuzzle("root");
        }
		long start = System.currentTimeMillis();
        System.out.println("\n\nHEURISTIC USED: Manhattan Distance");
        ninePuzzle.printPuzzle();
        for (int i = 1; i <= 8; i++)
        {
            System.out.printf("md(%d) = %d\n", i, ninePuzzle.manhattanDistance(i));
        }
        System.out.println("TOTAL: " + ninePuzzle.totalManhattanDistance());



        // Begin search
        AStar search = new AStar(ninePuzzle);
        if(search.solve())
        {
            System.out.println("Puzzle is solved. Goal state is 123456780.");
            search.getSolvedPuzzle().printPath();
			long end = System.currentTimeMillis();
			long duration = end - start;
			System.out.print("Execution time is " + duration + " ms");
        }


        
    }





/****************************************************************/



    public static boolean validateInput (String str)
    {
        // Variable Initialization
        boolean[] digits = new boolean[9];
        int num = -1;

        // Return false if the string is not exactly 9 characters long.
        if (str.length() != 9) 
        {
            System.out.println("ERROR: Input must be 9 digits long.");
            return false;
        }

        // Return false if the string contains a non-digit.
        // Return false if a digit is repeated.
        for (int i = 0; i < str.length(); i++)
        {

            if (Character.isDigit(str.charAt(i)))
            {
                num = Character.getNumericValue(str.charAt(i));
                if (num < 0 || num > 9)
                {
                    System.out.println("ERROR: Input must only have nums 0-5.");
                    return false;
                }
                if (digits[num] == true)
                {
                    System.out.println("ERROR: Digits cannot be repeated.");
                    return false;
                }
                else
                {
                    digits[num] = true;
                }
            }
            else 
            {
                System.out.println("ERROR: Input has a non-numeric value.");
                return false;
            }
        }

        return true;

    }
}