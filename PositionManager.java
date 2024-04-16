import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class PositionManager {
    private List<Point> availablePositions;

    public PositionManager() {
        availablePositions = new ArrayList<>();
    }

    public void addPosition(int x, int y) {
        availablePositions.add(new Point(x, y));
    }

    public void removePosition(int index) {
        availablePositions.remove(getPosition(index));
    }

    public Point getPosition(int index) {
        return availablePositions.get(index+1);
    }
}
