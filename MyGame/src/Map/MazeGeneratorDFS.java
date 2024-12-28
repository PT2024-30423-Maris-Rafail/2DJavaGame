package Map;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MazeGeneratorDFS {
    // Directions for movement (up, down, left, right) - we need 2 since, if we had 1, we'd just move to every cell and that s not a maze...
    //we, instead, try to reach a certain point and build a path to it
    private static final int[][] DIRECTIONS = {
            {-2, 0}, {2, 0}, {0, -2}, {0, 2}
    };

    public static int[][] prepareMaze(int size) {
        Random rand = new Random(System.currentTimeMillis());
        int[][] maze = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                maze[i][j] = 10 + rand.nextInt(3); // Wall1, wall2, wall3 (indexes in the tile array)
            }
        }

        // Set the borders to 0
        for (int i = 0; i < size; i++) {
            maze[0][i] = 0;
            maze[size - 1][i] = 0;
            maze[i][0] = 0;
            maze[i][size - 1] = 0;
        }

        // Starting point for the maze
        int startX = 25, startY = 25;
        maze[startX][startY] = 1; // Path - grass tile
        return maze;
    }

    public static int[][] generateMaze(int size) {
        prepareMaze(size);
        int[][] maze = prepareMaze(size);

        //random DFS - FA ahh bullshi-
        minePath(25, 25, maze, size);
        return maze;
    }

    private static boolean isVisit(int x) {
        return x != 10 && x != 11 && x != 12;
    }

    private static void minePath(int x, int y, int[][] maze, int size) {
        //randomize order of traversal

        List<int[]> directions = new ArrayList<>(Arrays.asList(DIRECTIONS));//Intellij vazand ca scriu cod in 3 linii si dandu mi totul intr una:...
        Collections.shuffle(directions);//The java blessing
        Collections.shuffle(directions);

        for (int[] dir : directions) {
            int dx = x + dir[0];
            int dy = y + dir[1];

            // Check if the new cell is within bounds and unvisited
            if (dx > 0 && dx < size && dy > 0 && dy < size && !isVisit(maze[dx][dy])) {
                // Make a path between current cell and the new cell
                maze[x + dir[0] / 2][y + dir[1] / 2] = 1; // Path
                maze[dx][dy] = 1; // Current is visited
                minePath(dx, dy, maze, size);
            }
        }
    }

    public static void makeMaze() {
        int size = 50; // Size of the maze
        int[][] maze = generateMaze(size);
        try {
            FileWriter fw = new FileWriter("map2.txt");
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    System.out.print(maze[i][j] == 1 ? "  " : "██");
                    fw.write(maze[i][j] + " ");
                    //System.out.print(maze[i][j] + " ");
                }
                System.out.println();
                fw.write("\n");
                //System.out.println("");
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Error writing file");
            e.printStackTrace();
        }

    }
}
