import javax.swing.*;
import java.util.*;

public class KnowledgeBase {

    private Set<Position> visited;
    Set<Position> wumpusPossibilities;
    private Set<Position> pitPossibilities;
    private Set<Position> breezePos;
    private Set<Position> stenchPos;
    Position agentPos;

    private int caveSize;



    public KnowledgeBase(int size) {
        caveSize = size;

        visited = new HashSet<>();

        wumpusPossibilities = new HashSet<>();
        wumpusPossibilities.addAll(entireCave(caveSize));

        pitPossibilities = new HashSet<>();
        pitPossibilities.addAll(entireCave(caveSize));

        breezePos = new HashSet<>();
        stenchPos = new HashSet<>();
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
        agentPos = pos;
        visited.add(pos);
        pitPossibilities.remove(pos);
        wumpusPossibilities.remove(pos);

        if(!breeze)
            for (Direction dir : Direction.values())
                pitPossibilities.remove(pos.moveTowards(dir));
        else breezePos.add(pos);


        if(!stench) {
            for (Direction dir : Direction.values())
                wumpusPossibilities.remove(pos.moveTowards(dir));
        } else {
            wumpusPossibilities.removeAll(butNeighborsOf(pos)); // removes all possible fields except the ones connected to field "pos"
            stenchPos.add(pos);
        }
    }

    private Collection<?> butNeighborsOf(Position pos) {
        var all = new HashSet<>(entireCave(caveSize));
        for (Direction dir : Direction.values())
            all.remove(pos.moveTowards(dir));
        return all;
    }


    public void printCave(){
        StringBuilder sb = new StringBuilder();

        //add hazards
        for (int row = 0; row < caveSize; row++) {
            for (int col = 0; col < caveSize; col++) {
                Position pos = new Position(row, col);
                String toAdd = "?  ";
                //if (isSafe(pos)) toAdd = "ok ";
                if (breezePos.contains(pos)) toAdd = "~  ";
                if (stenchPos.contains(pos)) toAdd = "s  ";
                if (getWumpusPos() != null && getWumpusPos().equals(pos)) toAdd = "W  ";
                if (pos.equals(agentPos)) toAdd = "A  ";
                sb.append(toAdd);
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    Direction getWumpusDir() {
        if (wumpusPossibilities.size() == 1) {
            Position wumpusPos = wumpusPossibilities.iterator().next();
            for (Direction dir : Direction.values())
                if (agentPos.moveTowards(dir).equals(wumpusPos))
                    return dir;
        }

        return null;
    }

    Position getWumpusPos(){
        if (wumpusPossibilities.size() == 1) return wumpusPossibilities.iterator().next();
        else return null;
    }
}
