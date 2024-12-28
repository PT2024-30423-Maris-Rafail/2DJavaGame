package Map;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MazeGeneratorBFS {
    /// This is pretty much a copy of the method using DFS because i was curious about how the maze would look if i did this
    /// It's nice that the FA course proved to be useful!

    // Directions for movement (up, down, left, right)
    static Queue<int[]> queue = new LinkedList<>();
    private static final int[][] DIRECTIONS = {
            {-2, 0}, {2, 0}, {0, -2}, {0, 2}
    };

    public static int[][] generateMaze(int size) {
        int[][] maze = MazeGeneratorDFS.prepareMaze(size);
        ///Mark visited
        queue.offer(new int[]{25, 25});
        //random DFS - FA ahh bullshi-
        minePath(maze, size);
        return maze;
    }

    private static boolean isVisit(int x) {
        return x != 10 && x != 11 && x != 12;
    }

    private static void minePath(int[][] maze, int size) {
        //randomize order of traversal
        int[] a = queue.poll();
        assert a != null;
        int x = a[0];
        int y = a[1];
        List<int[]> directions = new ArrayList<>(Arrays.asList(DIRECTIONS));//Intellij vazand ca scriu cod in 3 linii si dandu mi totul intr una:...
        Collections.shuffle(directions);//The java blessing
        Collections.shuffle(directions);

        for (int[] dir : directions) {
            int dx = x + dir[0];
            int dy = y + dir[1];

            //maze[a[0]+dx][a[0]+dy] = 1;
            // Check if the new cell is within bounds and unvisited
            if (dx > 0 && dx < size - 1 && dy > 0 && dy < size - 1 && !isVisit(maze[dx][dy])) {
                if (maze[dx][dy] != 1) queue.offer(new int[]{dx, dy});
                // Carve a path between current cell and the new cell
                maze[x + dir[0] / 2][y + dir[1] / 2] = 1; // Path
                maze[dx][dy] = 1; // Path
            }
            if (!queue.isEmpty()) minePath(maze, size);
        }
    }

    public static void makeMaze() {
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
