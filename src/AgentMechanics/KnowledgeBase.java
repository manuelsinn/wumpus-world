package AgentMechanics;

import java.util.*;
import GameMechanics.*;

public class KnowledgeBase {

    Set<Position> visited;
    Set<Position> wumpusPossibilities;
    Set<Position> stenchPositions;
    Set<Position> pitPossibilities;

    int caveSize;


    public KnowledgeBase(int size) {
        caveSize = size;

        visited = new HashSet<>();

        wumpusPossibilities = new HashSet<>();
        wumpusPossibilities.addAll(entireCave(caveSize));

        stenchPositions = new HashSet<>();

        pitPossibilities = new HashSet<>();
        pitPossibilities.addAll(entireCave(caveSize));
    }

    private Collection<? extends Position> entireCave(int size) {
        Set<Position> positions = new HashSet<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                positions.add(new Position(i, j));
            }
        }
        return positions;
    }

    public boolean isSafe(Position pos) {
        if(wumpusPossibilities.contains(pos) || pitPossibilities.contains(pos)) return false;
        else return true;
    }

    public void update(Position pos, boolean stench, boolean breeze) {
        visited.add(pos);
        pitPossibilities.remove(pos);
        wumpusPossibilities.remove(pos);

        if(!breeze)
            for (Direction dir : Direction.values())
                pitPossibilities.remove(pos.moveTowards(dir));


        if(!stench)
            for (Direction dir : Direction.values())
                wumpusPossibilities.remove(pos.moveTowards(dir));
        else wumpusPossibilities.removeAll(butNeighborsOf(pos)); // removes all possible fields except the ones connected to field "pos"
    }

    private Collection<?> butNeighborsOf(Position pos) {
        var all = new HashSet<>(entireCave(caveSize));
        for (Direction dir : Direction.values())
            all.remove(pos.moveTowards(dir));
        return all;
    }
}
