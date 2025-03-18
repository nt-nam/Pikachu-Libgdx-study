
public static boolean checkEdible(Animal a1, Animal a2) {
if (a1 == null || a2 == null) return false;

    int x1 = a1.getGridX();
    int y1 = a1.getGridY();
    int x2 = a2.getGridX();
    int y2 = a2.getGridY();
    List<Pair> pairList = new ArrayList<>();
    if (isStraightPath(x1, y1, x2, y2, pairList)) {

      drawLine(pairList);
      return true;
    }
    pairList.clear();
    if (isOneCornerPath(x1, y1, x2, y2, pairList)) {
      drawLine(pairList);
      return true;
    }
    pairList.clear();
    if (isTwoCornerPath(x1, y1, x2, y2, pairList)) {
      drawLine(pairList);
      return true;
    }
    return false;
}

private static void drawLine(List<Pair> pairList) {
if (pairList != null && !pairList.isEmpty()) {
StringBuilder pathFull = new StringBuilder("path full: ");

      // Ghi lại toàn bộ danh sách để debug
      for (Pair pair : pairList) {
        pathFull.append("- [").append(pair.getX()).append(",").append(pair.getY()).append("] ");
      }
      System.out.println(pathFull);

      // Duyệt qua tất cả các cặp Pair trong danh sách
      for (int i = 0; i < pairList.size(); i++) {
        Pair pair1 = pairList.get(i);
        for (int j = i + 1; j < pairList.size(); j++) { // Bắt đầu từ i+1 để tránh lặp lại cặp
          Pair pair2 = pairList.get(j);

          // Kiểm tra nếu hai Pair cách nhau 1 đơn vị x hoặc y
          boolean isVertical = pair1.getX() == pair2.getX() &&
              (Math.abs(pair1.getY() - pair2.getY()) == 1);
          boolean isHorizontal = pair1.getY() == pair2.getY() &&
              (Math.abs(pair1.getX() - pair2.getX()) == 1);

          if (isVertical || isHorizontal) {
            // Tính toán chiều rộng và cao của đường kẻ
            float width = isVertical ? (float) distance / 10 : distance;
            float height = isVertical ? distance : (float) distance / 10;

            // Tính toán vị trí của đường kẻ
            float x = (Math.min(pair1.getX(), pair2.getX()) + 0.5f) * distance + board.getX();
            float y = (Math.min(pair1.getY(), pair2.getY()) + 0.5f) * distance + board.getY();

            // Tạo và thêm đường kẻ vào stage
            Image line = new Image(new TextureRegionDrawable(ui.findRegion("line_red")));
            line.setBounds(x, y, width, height);
            stage.addActor(line);
            line.addAction(Actions.sequence(
                Actions.delay(1f),
                Actions.removeActor()
            ));
          }
        }
      }
    }
}


public static boolean isTwoCornerPath(int x1, int y1, int x2, int y2, List<Pair> pairList) {
for (int x = -1; x <= COLUMNS; x++) {
if (isOneCornerPath(x1, y1, x, y1, pairList) && isOneCornerPath(x, y1, x2, y2, pairList)) {
return true;
}
pairList.clear();
}
for (int y = -1; y <= ROWS; y++) {
if (isOneCornerPath(x1, y1, x1, y, pairList) && isOneCornerPath(x1, y, x2, y2, pairList)) {
return true;
}
pairList.clear();
}
return false;
}

public static boolean isOneCornerPath(int x1, int y1, int x2, int y2, List<Pair> pairList) {
if (isStraightPath(x1, y1, x1, y2, pairList) && isStraightPath(x1, y2, x2, y2, pairList) && animalHashMap.get(getKey(x1, y2)) == null) {
return true;
}
if (isStraightPath(x1, y1, x2, y1, pairList) && isStraightPath(x2, y1, x2, y2, pairList) && animalHashMap.get(getKey(x2, y1)) == null) {
return true;
}
return false;
}

public static boolean isStraightPath(int x1, int y1, int x2, int y2, List<Pair> pairList) {
List<Pair> pairList2 = new ArrayList<>();
if (x1 == x2) { // Cùng cột
int minY = Math.min(y1, y2);
int maxY = Math.max(y1, y2);
pairList2.add(new Pair(x1, minY));
pairList2.add(new Pair(x1, maxY));
for (int y = minY + 1; y < maxY; y++) {
if (animalHashMap.get(getKey(x1, y)) != null) {
return false; // Có vật cản
}
pairList2.add(new Pair(x1, y));
}
pairList.addAll(pairList2);
return true;
} else if (y1 == y2) { // Cùng hàng
int minX = Math.min(x1, x2);
int maxX = Math.max(x1, x2);
pairList2.add(new Pair(minX, y1));
pairList2.add(new Pair(maxX, y1));
for (int x = minX + 1; x < maxX; x++) {
if (animalHashMap.get(getKey(x, y1)) != null) {
return false; // Có vật cản
}
pairList2.add(new Pair(x, y1));
}
pairList.addAll(pairList2);
return true;
}
return false;
}




//  public static void drawMatrix() {
//    for (int j = ROWS; j >= -1; j--) {
//      for (int i = -1; i <= COLUMNS; i++) {
//        if (animalHashMap.get(getKey(i, j)) == null) {
//          System.out.print(" -");
//        } else {
//          System.out.print(" " + animalHashMap.get(getKey(i, j)).getId());
//        }
//      }
//      System.out.println(" ");
//    }
//    System.out.println("----------------------------------");
//  }
  

