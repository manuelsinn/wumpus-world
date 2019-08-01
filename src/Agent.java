public abstract class Agent {
    Position currentPos;
    boolean hasArrow = true;
    int score = 0;
    Cave cave;

    public Agent(Position currentPos, Cave cave) {
        this.currentPos = currentPos;
        this.cave = cave;
    }

    abstract KnowledgeBase getKB();

    /** returns true if agent chose move() over shoot()*/
    abstract boolean goForMove();

    /** returns whether the agent has moved or not */
    abstract boolean move();

    /** returns the target */
    abstract Position shoot();
}
