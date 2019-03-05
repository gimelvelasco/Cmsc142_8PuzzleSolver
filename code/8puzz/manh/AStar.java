import java.util.LinkedList;
import java.util.Stack;

public class AStar
{

    private SlidePuzzle puzzle;

    private SlidePuzzle solvedPuzzle;
    public SlidePuzzle getSolvedPuzzle() {
        return solvedPuzzle; 
    }

    private Stack<SlidePuzzle> solution = new Stack<>();


    public AStar(SlidePuzzle pzl)
    {
        puzzle = pzl;
    }

    public void printSolution()
    {
        for (int i = 1; i < solution.size()-1; i++)
        {
            System.out.printf("%s-", (solution.get(i).direction));
        }
        System.out.println(solution.get(solution.size()-1).direction);
        
    }

    private LinkedList<SlidePuzzle> expandNodes(SlidePuzzle pzl)
    {
        LinkedList<SlidePuzzle> nodes = new LinkedList<>();
        if(pzl.moveL())
            nodes.add(new SlidePuzzle(pzl.getTempPuzzleState(), "L"));
        if(pzl.moveD())
            nodes.add(new SlidePuzzle(pzl.getTempPuzzleState(), "D"));
        if(pzl.moveU())
            nodes.add(new SlidePuzzle(pzl.getTempPuzzleState(), "U"));
        if(pzl.moveR())
            nodes.add(new SlidePuzzle(pzl.getTempPuzzleState(), "R"));
        return nodes;
    }

    private SlidePuzzle getBestNode(LinkedList<SlidePuzzle> nodes)
    {
        // Assume first node is the best node
        int bestIndex = 0;
        int bestState = nodes.get(bestIndex).totalManhattanDistance();

        // Search for which of the expanded nodes
        // has the smallest Manhattan Distance
        for (int i = 0; i < nodes.size(); i++)
        {
            if (nodes.get(i).totalManhattanDistance() < bestState)
            {
                bestState = nodes.get(i).totalManhattanDistance();
                bestIndex = i;
            }
        }

        return nodes.get(bestIndex);
    }

    private boolean isInList(LinkedList<SlidePuzzle> nodes, String state)
    {
        for (SlidePuzzle node : nodes)
        {
            if (node.getPuzzleState().equals(state))
                return true;
        }
        return false;
    }

    // Returns the index of the node with the shortest path given a state
    private int bestPathIndex(LinkedList<SlidePuzzle> nodes, String state)
    {
        SlidePuzzle temp;
        int bestIndex = 0;
        int bestPath = Integer.MAX_VALUE;
        
        // For every node in the open list
        for (int i = 0; i < nodes.size(); i++)
        {
            temp = nodes.get(i);
            // If the node we're at matches the state
            if (temp.getPuzzleState().equals(state))
            {
                // If the node we're at has the shortest path
                if (temp.getPath().size() < bestPath)
                {
                    bestPath = temp.getPath().size();
                    bestIndex = i;
                }
            }
        }
        return bestIndex;
    }

    // Removes nodes that match a state but have a lower heuristic
    private LinkedList<SlidePuzzle> removeNotBestPath (LinkedList<SlidePuzzle> nodes, String state)
    {
        int bestIndex = bestPathIndex(nodes, state);
        for (int i = 0; i < nodes.size(); i++)
        {
            if (nodes.get(i).getPuzzleState().equals(state) && i != bestIndex)
                nodes.remove(i);
        }
        return nodes;
    }



    public SlidePuzzle aStarSearch()
    {
        LinkedList<SlidePuzzle> open = new LinkedList<>();
        LinkedList<String> closed = new LinkedList<>();
        LinkedList<SlidePuzzle> possibleMoves;

        SlidePuzzle prevNode = puzzle;
        SlidePuzzle bestNode;
        SlidePuzzle temp;


        String bestState;
        String tempState;

        // Begin searching
        open.add(puzzle);

        while(!(open.isEmpty()))
        {
            /* Find the best node and remove it from open*/
            bestNode = getBestNode(open);
            open.removeFirstOccurrence(bestNode);

            /* If this state's already been explored... */
            bestState = bestNode.getPuzzleState();
            if (closed.contains(bestState))
            {
                continue;
            }
            else
            {
                closed.add(bestState);
            }
            

            if (bestNode.isSolved())
                return bestNode;

            possibleMoves = expandNodes(bestNode);
            for (SlidePuzzle branch : possibleMoves)
            {
                // Copy the string that represents the state to tempPuzzle
                tempState = branch.getPuzzleState();

                // Don't explore states we've already closed
                if (closed.contains(tempState))
                    continue;

                // Don't explore states already in the open list
                if (isInList(open,tempState))
                    continue;

                // Never go backwards
                if (branch.getPuzzleState().equals(prevNode.getPuzzleState()))
                    continue;

                // Copy the path history to the current node
                branch.setPath(bestNode.getPath());

                // Add the branch to its own history
                branch.addToPath(branch);

                // Add the branch to the open list
                open.add(branch);

                // Remove all duplicates of the branch that have a lower heuristic
                open = removeNotBestPath(open, branch.getPuzzleState());
            }

            prevNode = bestNode;
        }

        return null;
    }

    public boolean solve()
    {
        return ((solvedPuzzle = aStarSearch()) != null);
    }


}