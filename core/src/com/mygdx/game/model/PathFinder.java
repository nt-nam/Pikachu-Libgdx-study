package com.mygdx.game.model;

import com.mygdx.game.view.Board;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class PathFinder {
  private Board board;
  private int rows;
  private int cols;

  // Directions: up, right, down, left
  private static final int[][] DIRECTIONS = {
      {-1, 0}, {0, 1}, {1, 0}, {0, -1}

  };

  // Class to store position, turns, direction, and path
  private static class Node {
    int row, col;
    int turns;
    int direction;
    List<int[]> path;

    Node(int row, int col, int turns, int direction, List<int[]> path) {
      this.row = row;
      this.col = col;
      this.turns = turns;
      this.direction = direction;
      this.path = path;
    }
  }

  //tạo khi mở 1 level bất kì tại vị trí show() Screen
  public PathFinder(Board board) {
    this.board = board;
    this.rows = board.getROWS();
    this.cols = board.getCOLS();
  }


  public List<int[]> findPath(Animal animalStart, Animal animalEnd) {

    if (animalStart == null || animalEnd == null || animalStart.getId() != animalEnd.getId()) {
//      System.out.println("finish top");
      return null;
    }

    int startRow = animalStart.getGridX();
    int startCol = animalStart.getGridY();
    int endRow = animalEnd.getGridX();
    int endCol = animalEnd.getGridY();

    Queue<Node> queue = new ArrayDeque<>();
    boolean[][] visited = new boolean[rows + 2][cols + 2];

    List<int[]> initialPath = new ArrayList<>();
    initialPath.add(new int[]{startRow, startCol});
    queue.add(new Node(startRow, startCol, 0, -1, initialPath));

    while (!queue.isEmpty()) {
      Node current = queue.poll();
      int row = current.row;
      int col = current.col;
      int turns = current.turns;
      int prevDir = current.direction;
      List<int[]> currentPath = current.path;

      visited[row + 1][col + 1] = true;

      // Đến đích
      if (row == endRow && col == endCol) {
        return currentPath;
      }


      for (int i = 0; i < 4; i++) {
        int newRow = row + DIRECTIONS[i][0];
        int newCol = col + DIRECTIONS[i][1];
        int newTurns = turns;

        // Kiểm tra điều kiện
        if (!isValid(newRow, newCol)) {
          continue;
        }
        if (visited[newRow + 1][newCol + 1]) {
          continue;
        }
        if (prevDir != -1 && prevDir != i) {
          newTurns++;
        }
        if (newTurns > 2) { // Giới hạn 2 lần rẽ
          continue;
        }
        if (!isEmpty(newRow, newCol) && !(newRow == endRow && newCol == endCol)) {
          continue;
        }

        List<int[]> newPath = new ArrayList<>(currentPath);
        newPath.add(new int[]{newRow, newCol});

        visited[newRow + 1][newCol + 1] = true;
        // Add new node to queue
        queue.add(new Node(newRow, newCol, newTurns, i, newPath));
      }

    }
//    System.out.println("the end");
    return null;
  }

  public List<int[]> findPath(int startRow, int startCol, int endRow, int endCol) {
    // Validate tiles
    Animal startAnimal = board.getAnimal(startRow, startCol);
    Animal endAnimal = board.getAnimal(endRow, endCol);
    if (startAnimal == null || endAnimal == null || startAnimal.getId() != endAnimal.getId()) {
      return null;
    }

    // Initialize BFS
    Queue<Node> queue = new ArrayDeque<>();
    boolean[][][] visited = new boolean[rows+2][cols+2][4];

    // Start node with initial path
    List<int[]> initialPath = new ArrayList<>();
    initialPath.add(new int[]{startRow, startCol});
    queue.add(new Node(startRow, startCol, 0, -1, initialPath));

    while (!queue.isEmpty()) {
      Node current = queue.poll();
      int row = current.row;
      int col = current.col;
      int turns = current.turns;
      int prevDir = current.direction;
      List<int[]> currentPath = current.path;

      // Reached the target
      if (row == endRow && col == endCol) {
//        currentPath.add(new int[]{endRow,endCol});
        return currentPath; // Return the full path
      }

      // Explore all directions
      for (int i = 0; i < DIRECTIONS.length; i++) {
        int newRow = row + DIRECTIONS[i][0]; // wtf AI làm rối đoạn này thật chứ
        int newCol = col + DIRECTIONS[i][1]; // nếu quên cố ghi tọa độ Oxy
        int newTurns = turns;

        // Increase turns if direction changes
        if (prevDir != -1 && prevDir != i) {
          newTurns++;
        }
        try {
          // Skip if invalid, visited, or too many turns
          if (!isValid(newRow, newCol) || newTurns > 2) {
            continue;
          }
          if(visited[newRow+1][newCol+1][i]) continue;

        } catch (RuntimeException e) {
          System.out.println("aa");
          System.out.println("ct: "+ e.getMessage());
        }

        // Skip if blocked (not empty and not the end tile)
        if (!isEmpty(newRow, newCol) && !(newRow == endRow && newCol == endCol)) {
          continue;
        }

        // Mark as visited
        visited[newRow+1][newCol+1][i] = true;

        // Create new path by copying current path and adding new position
        List<int[]> newPath = new ArrayList<>(currentPath);
        newPath.add(new int[]{newRow, newCol});

        // Add new node to queue
        queue.add(new Node(newRow, newCol, newTurns, i, newPath));
      }
    }

    return null; // No valid path found
  }

  private boolean isValid(int row, int col) {
    return row >= -1 && row <= rows && col >= -1 && col <= cols;
  }

  private boolean isEmpty(int row, int col) {
    Animal animal = board.getAnimal(row, col);
    if (row == -1 || col == -1 || row == rows || col == cols) {
      return true;
    }
    return animal == null || !animal.isVisible();
  }

  public void setBoard(Board board) {
    this.board = board;
    rows = board.getROWS();
    cols = board.getCOLS();
  }
}