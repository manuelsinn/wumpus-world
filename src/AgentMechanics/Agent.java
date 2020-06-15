package AgentMechanics;

import GameMechanics.*;

public abstract class Agent {
    public Position currentPos;
    public boolean hasArrow;
    public int score;
    Cave cave;

    public Agent(Position currentPos, Cave cave) {
        this.currentPos = currentPos;
        this.score = 0;
        hasArrow = true;
        this.cave = cave;
    }

    /** returns true if agent chose move() over shoot()*/
    public abstract boolean goForMove();

    /** returns whether the agent has moved or not */
    public abstract boolean move();

    /** returns the target */
    public abstract Position shoot();
}
