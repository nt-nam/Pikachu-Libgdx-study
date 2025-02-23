package com.mygdx.game.control;

//import static com.mygdx.game.screen.PlayScreen.COLUMNS;
//import static com.mygdx.game.screen.PlayScreen.isOneCornerPath;
//import static com.mygdx.game.screen.PlayScreen.isStraightPath;
//import static com.mygdx.game.screen.PlayScreen.isTwoCornerPath;

import com.mygdx.game.model.Animal;

import java.util.ArrayList;
import java.util.List;

public class PathResult {
  private static final int COLUMNS = 0;
  private boolean isEdible;
  private String pathType; // "straight", "oneCorner", "twoCorners"
  private List<int[]> pathCoordinates; // List of coordinates in the path

  public PathResult(boolean isEdible, String pathType, List<int[]> pathCoordinates) {
    this.isEdible = isEdible;
    this.pathType = pathType;
    this.pathCoordinates = pathCoordinates;
  }

  public boolean isEdible() {
    return isEdible;
  }

  public String getPathType() {
    return pathType;
  }

  public List<int[]> getPathCoordinates() {
    return pathCoordinates;
  }
}
//  public static PathResult checkEdible(Animal a1, Animal a2) {
//    if (a1 == null || a2 == null) return new PathResult(false, null, null);
//
//    int x1 = a1.getGridX();
//    int y1 = a1.getGridY();
//    int x2 = a2.getGridX();
//    int y2 = a2.getGridY();
//    List<int[]> pathCoordinates = new ArrayList<>();
//
//    if (isStraightPath(x1, y1, x2, y2)) {
//      // Populate path coordinates for straight path
//      for (int y = Math.min(y1, y2) + 1; y < Math.max(y1, y2); y++) {
//        pathCoordinates.add(new int[]{x1, y});
//      }
//      return new PathResult(true, "straight", pathCoordinates);
//    }
//    if (isOneCornerPath(x1, y1, x2, y2)) {
//      // Populate path coordinates for one corner path
//      if (y1 != y2) {
//        for (int y = Math.min(y1, y2) + 1; y < Math.max(y1, y2); y++) {
//          pathCoordinates.add(new int[]{x1, y});
//        }
//        pathCoordinates.add(new int[]{x2, y1});
//      } else {
//        for (int x = Math.min(x1, x2) + 1; x < Math.max(x1, x2); x++) {
//          pathCoordinates.add(new int[]{x, y1});
//        }
//        pathCoordinates.add(new int[]{x1, y2});
//      }
//      return new PathResult(true, "oneCorner", pathCoordinates);
//    }
//    if (isTwoCornerPath(x1, y1, x2, y2)) {
//      // Populate path coordinates for two corner path
//      for (int x = -1; x <= COLUMNS; x++) {
//        if (isOneCornerPath(x1, y1, x, y1) && isOneCornerPath(x, y1, x2, y2)) {
//          // Add coordinates for the first corner
//          for (int y = Math.min(y1, y1) + 1; y < Math.max(y1, y1); y++) {
//            pathCoordinates.add(new int[]{x1, y});
//          }
//          pathCoordinates.add(new int[]{x, y1});
//          // Add coordinates for the second corner
//          for (int y = Math.min(y2, y2) + 1; y < Math.max(y2, y2); y++) {
//            pathCoordinates.add(new int[]{x, y});
//          }
//          return new PathResult(true, "twoCorners", pathCoordinates);
//        }
//      }
//    }
//    return new PathResult(false, null, null);
//  }
//}
//PathResult result = checkEdible(animal1, animal2);
//if (result.isEdible()) {
//    System.out.println("Path Type: " + result.getPathType());
//    System.out.println("Path Coordinates: " + result.getPathCoordinates());
//    } else {
//    System.out.println("Not edible");
//}