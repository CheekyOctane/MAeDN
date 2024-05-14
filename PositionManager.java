import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class PositionManager {
    private List<Point> availablePositions;

    public PositionManager() {
        availablePositions = new ArrayList<>();
    }

    public void addPosition(int x, int y) {
        availablePositions.add(new Point(x-25, y-25));
    }

    public void removePosition(int index) {
        availablePositions.remove(getPosition(index));
    }

    public Point getPosition(int index) {
        return availablePositions.get(index-1);
    }

    public List<Integer> getColorPath(int color) { //will return the path for the pieces of each color, starting at their first home position, going through the path and ending at their last finish position
        List<Integer> colorPathIndex = new ArrayList<>();
        int start, end, finishStart, finishEnd, startPath, endPath;
        if (color == 1) {
            start = 1;
            end = 4;
            finishStart = 17;
            finishEnd = 20;
            startPath = 33;
            endPath = 72;
        } else if (color == 2) {
            start = 5;
            end = 8;
            finishStart = 21;
            finishEnd = 24;
            startPath = 43;
            endPath = 42;
        } else if (color == 3) {
            start = 9;
            end = 12;
            finishStart = 25;
            finishEnd = 28;
            startPath = 53;
            endPath = 52;
        } else if (color == 4) {
            start = 13;
            end = 16;
            finishStart = 29;
            finishEnd = 32;
            startPath = 63;
            endPath = 62;
        } else {
            return colorPathIndex;
        }

        for (int i = start; i <= end; i++) { //add the home positions
            colorPathIndex.add(i);
        }

        for (int i = 1; i <= availablePositions.size()-32; i++) { //add the path positions
            int j = startPath + i - 1;
            if (j > availablePositions.size()) {
                //if the index is bigger than the size of the availablePositions list, add the position at the index - the size of the list + 32 (because the first 32 positions in availablePositions are home and finish positions)
                colorPathIndex.add((j - availablePositions.size()) + 32);
            } else {
                colorPathIndex.add(j);
            }
        }

        for (int i = finishStart; i <= finishEnd; i++) { //add the finish positions
            colorPathIndex.add(i);
        }
        return colorPathIndex;
    }
}
