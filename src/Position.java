import java.util.Objects;

public class Position {
    public int x;
    public int y;


    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }


    @Override
    public String toString() {
        return "(" + x + "|" + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if(toString().equals(obj.toString())) return true;
        else return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /** moves this currentPos one room in the specified direction */
    public Position moveTowards(Direction dir){
        int yCopy = y; int xCopy = x;
        if(dir == Direction.UP)  yCopy++;
        if(dir == Direction.DOWN)  yCopy--;
        if(dir == Direction.RIGHT)  xCopy++;
        if(dir == Direction.LEFT)  xCopy--;
        return new Position(xCopy, yCopy);
    }
}
