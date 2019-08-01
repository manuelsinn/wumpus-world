public abstract class Agent {
    Position currentPos;
    boolean hasArrow;
    int score;
    Cave cave;

    public Agent(Position currentPos, Cave cave) {
        this.currentPos = currentPos;
        this.score = 0;
        hasArrow = true;
        this.cave = cave;
    }

    /** returns true if agent chose move() over shoot()*/
    abstract boolean goForMove();

    /** returns whether the agent has moved or not */
    abstract boolean move();

    /** returns the target */
    abstract Position shoot();
}
