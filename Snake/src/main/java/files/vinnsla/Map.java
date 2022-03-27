package files.vinnsla;


// ! allt þetta er hugsanlega óþarft, það skiptir ekki alltof mikilu máli hvort þeir geti farið yfir eða ekki

public class Map {
    private int gridX = 32;
    private int gridY = 32;
    private int totalEnemies;
    private int totalApples;

    /**
     * * 0 = ekkert
     * * 1 = playersnákur head
     * * 2 = playersnákur body
     * * 3 = playersnákur tail
     * * 4 = eitursnákur head
     * * 5 = eitursnákur body
     * * 6 = eitursnákur tail
     * * 7 = matur
     */

    private int[][] map = new int[gridX][gridY];

    public int getMapSquare(int x, int y) {
        return this.map[x][y];
    }

    public void setMapSquare(int val, int x, int y) {
        if (x > 31 || x < 0 || y > 31 || y < 0) return;
        map[x][y] = val;
    }

    public void clearMap() {
        map = new int[gridX][gridY];
    }

    public String toString() {
        String out = "";
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                out += map[j][i] + " ";
            }
            out += "\n";
        }
        return out;
    }
}
