import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AI extends Agent {

    KnowledgeBase kb;
    Random rnd;
    private Direction lastDir = Direction.UP; //random init
    private int loopCounter = 0;

    public AI(Position currentPos, Cave cave) {
        super(currentPos, cave);
        kb = new KnowledgeBase(cave.size);
        kb.update(currentPos, cave.hasStench(currentPos), cave.hasBreeze(currentPos));
        rnd = new Random();
    }

    @Override
    KnowledgeBase getKB() {
        return kb;
    }


    @Override
    public boolean goForMove() {
        Direction wumpusDir = kb.getWumpusDir();
        if(wumpusDir == null)
            return true;
        else
            return false;
    }




    @Override
    public boolean move() {
        //update knowledge base
        kb.update(currentPos, cave.hasStench(currentPos), cave.hasBreeze(currentPos));

        // chose walking direction
        Direction chosenDir = getRandomSafeDirection();
        if(chosenDir == null)
            chosenDir = getRandomPossibleDirection(); //if no safe direction, choose random possible one

        // change position
        Position before = currentPos;
        currentPos = currentPos.moveTowards(chosenDir);

        lastDir = chosenDir;

        // return whether agent has moved
        return !before.equals(currentPos);
    }

    private Direction getRandomSafeDirection(){
        List<Direction> safe = new ArrayList<>();

        for (Direction dir : Direction.values())
            if(kb.isSafe(currentPos.moveTowards(dir)) && movePossible(dir, currentPos))
                safe.add(dir);

        var cameFrom = lastDir.opposite();
        if(safe.size() > 1)
            safe.remove(cameFrom);

        if(safe.size() == 1 && safe.get(0).equals(cameFrom) && ++loopCounter > 10) return getRandomPossibleDirection();

        return safe.size() == 0? null : safe.get(rnd.nextInt(safe.size()));
    }

    private Direction getRandomPossibleDirection() {
        List<Direction> possible = new ArrayList<>();

        for (Direction dir : Direction.values())
            if(movePossible(dir, currentPos))
                possible.add(dir);

        var cameFrom = lastDir.opposite();
        if(possible.size() > 1)
            possible.remove(cameFrom);

        return possible.get(rnd.nextInt(possible.size()));
    }

    private boolean movePossible(Direction dir, Position currentPos) {
        Position newPos = currentPos.moveTowards(dir);
        boolean inRowBounds = newPos.y >= 0 && newPos.y <= cave.size-1;
        boolean inColBounds = newPos.x >= 0 && newPos.x <= cave.size-1;
        return inRowBounds && inColBounds;
    }

    @Override
    public Position shoot() {
        return currentPos.moveTowards(kb.getWumpusDir());
    }
}
