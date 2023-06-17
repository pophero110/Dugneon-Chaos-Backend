package com.dungeonchaos.dungeonchaos.service.board;

import java.util.*;

public class MazeGenerator {
    private static final char WALL = 'W';
    private static final char PATH = 'P';

    private char[][] maze;
    private int width;
    private int height;
    private Random random;

    public MazeGenerator(int width, int height) {
        this.width = width;
        this.height = height;
        this.random = new Random();
        this.maze = new char[height][width];
    }

    /**
     * Generates a maze.
     * @return The generated maze as a 2D character array.
     */
    public char[][] generateMaze() {
        // Fill the maze with walls
        for (int i = 0; i < height; i++) {
            Arrays.fill(maze[i], WALL);
        }

        // Start at a random cell
        int startX = random.nextInt(width);
        int startY = random.nextInt(height);

        // Generate the maze starting from the initial cell
        generatePath(startX, startY);

        // Set the start and end points
        maze[startY][startX] = 'S';  // 'S' for start

        // Calculate the farthest possible position for the end point
        int endX = (startX + width / 2) % width;
        int endY = (startY + height / 2) % height;

        maze[endY][endX] = 'E';  // 'E' for end
        return maze;
    }


    /**
     * Generates a path in the maze starting from the specified coordinates (x, y).
     * @param x The starting x-coordinate.
     * @param y The starting y-coordinate.
     */
    private void generatePath(int x, int y) {
        maze[y][x] = PATH;

        // Generate a random direction order (up, down, left, right)
        List<Integer> directions = new ArrayList<>(Arrays.asList(0, 1, 2, 3));
        Collections.shuffle(directions, random);

        for (int direction : directions) {
            int newX = x;
            int newY = y;

            if (direction == 0) {  // Up
                newY -= 2;
            } else if (direction == 1) {  // Down
                newY += 2;
            } else if (direction == 2) {  // Left
                newX -= 2;
            } else if (direction == 3) {  // Right
                newX += 2;
            }

            if (isValidPath(newX, newY)) {
                // Remove the wall between the current cell and the next cell
                maze[(y + newY) / 2][(x + newX) / 2] = PATH;
                generatePath(newX, newY);
            }
        }
    }

    /**
     * Checks if the specified coordinates (x, y) represent a valid path in the maze.
     * @param x The x-coordinate to check.
     * @param y The y-coordinate to check.
     * @return True if the coordinates represent a valid path, false otherwise.
     */
    private boolean isValidPath(int x, int y) {
        return (isValidTile(x,y) && maze[y][x] == WALL);
    }

    /**
     * Checks if the specified coordinates (x, y) represent a valid tile in the maze.
     * @param x The x-coordinate to check.
     * @param y The y-coordinate to check.
     * @return True if the coordinates represent a valid tile, false otherwise.
     */
    private boolean isValidTile(int x, int y) {
        return x >= 0 && x < width && y >=0 && y < height;
    }

    public void printMaze() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
    }
}