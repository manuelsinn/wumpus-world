public enum Direction {
    UP, DOWN, RIGHT, LEFT;

    public Direction opposite() {
        if(this.equals(UP)) return DOWN;
        if(this.equals(DOWN)) return UP;
        if(this.equals(RIGHT)) return LEFT;
        else return RIGHT;
    }
}
