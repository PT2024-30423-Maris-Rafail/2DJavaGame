package Display;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MazeGenerator {
    // Directions for movement (up, down, left, right)
    private static final int[][] DIRECTIONS = {
            {-2, 0}, {2, 0}, {0, -2}, {0, 2}
    };

    public static int[][] generateMaze(int size) {
        Random rand = new Random(System.currentTimeMillis());

        int[][] maze = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                maze[i][j] = 10 + rand.nextInt(3); // Wall1, wall2, wall3
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
        int startX = 1, startY = 1;
        maze[startX][startY] = 1; // Path - grass tile

        //random DFS - FA ahh bullshi-
        minePath(startX, startY, maze, size);
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
            if (dx > 0 && dx < size - 1 && dy > 0 && dy < size - 1 && !isVisit(maze[dx][dy])) {
                // Carve a path between current cell and the new cell
                maze[x + dir[0] / 2][y + dir[1] / 2] = 1; // Path
                maze[dx][dy] = 1; // Path
                minePath(dx, dy, maze, size);
            }
        }
    }

    public static void main() {
        int size = 50; // Size of the maze
        int[][] maze = generateMaze(size);
        try {
            FileWriter fw = new FileWriter("map2.txt");
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    //System.out.print(maze[i][j] == 1 ? "  " : "██");
                    fw.write(maze[i][j] + " ");
                    //System.out.print(maze[i][j] + " ");
                }
                //System.out.println();
                fw.write("\n");
                //System.out.println("");
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Error writing file");
            e.printStackTrace();
        }

        // Print the maze

    }
}
