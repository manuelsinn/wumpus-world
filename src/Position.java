import java.util.Objects;

public class Position {
    public int row;
    public int col;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }


    @Override
    public String toString() {
        return "(" + row + "|" + col + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if(toString().equals(obj.toString())) return true;
        else return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    /** moves this currentPos one room in the specified direction */
    public Position moveTowards(Direction dir){
        int rowCopy = row; int colCopy = col;
        if(dir == Direction.UP)  rowCopy--;
        if(dir == Direction.DOWN)  rowCopy++;
        if(dir == Direction.RIGHT)  colCopy++;
        if(dir == Direction.LEFT)  colCopy--;
        return new Position(rowCopy, colCopy);
    }
}
