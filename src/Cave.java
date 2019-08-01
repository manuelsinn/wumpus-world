import java.util.*;

public class Cave {
    Map<Position, Room> rooms;
    int size = 4;
    private int wumpusses = 1;
    Random rnd = new Random();

    public Cave(boolean random) {

        // create rooms
        rooms = new HashMap<>();
        int id = 1;
        for (int x = 0; x < size; x++)
            for (int y = size - 1; y >= 0; y--)
                rooms.put(new Position(x, y), new Room(id++, Hazard.UNDEF));

        // add hazards
        //rnd.setSeed(3); //1,3,180
        addAgent(new Position(0, 0));


        int pits = 3;
        for (int i = 0; i < pits; i++)
            while (!addPit(new Position(rnd.nextInt(size), rnd.nextInt(size))));

        for (int i = 0; i < wumpusses; i++)
            while (!addWumpus(new Position(rnd.nextInt(size), rnd.nextInt(size))));

        while (!addCherry(new Position(rnd.nextInt(size), rnd.nextInt(size))));

        System.out.println("Cave generated:");
        printCave();
    }


    public Position getAgentPos() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Position pos = new Position(x,y);
                if (rooms.get(pos).hasAgent)
                    return pos;
            }
        }
        return null;
    }

    private boolean addCherry(Position pos) {
        if (rooms.get(pos).hazard == Hazard.PIT || rooms.get(pos).hazard == Hazard.WUMPUS) return false;
        rooms.get(pos).hazard = Hazard.CHERRY;
        return true;
    }

    public void removeCherry(Position pos){
        rooms.get(pos).hazard = Hazard.UNDEF;
    }

    public void addAgent(Position pos) {
        if (!(rooms.get(pos).hazard == Hazard.PIT || rooms.get(pos).hazard == Hazard.WUMPUS || rooms.get(pos).hazard == Hazard.CHERRY))
            rooms.get(pos).hasAgent = true;
    }

    public boolean addWumpus(Position pos) {
        if (rooms.get(pos).hazard == Hazard.PIT || rooms.get(pos).hasAgent || (pos.x == 0 && pos.y == 1) || (pos.x == 1 && pos.y == 0))
            return false;
        else
            rooms.get(pos).hazard = Hazard.WUMPUS;

        for(Position p : getNeighbors(pos))
            rooms.get(p).hasStench = true;

        return true;
    }

    public boolean addPit(Position pos) {
        if (rooms.get(pos).hazard == Hazard.WUMPUS || rooms.get(pos).hasAgent || (pos.x == 0 && pos.y == 1) || (pos.x == 1 && pos.y == 0))
            return false;
        else
            rooms.get(pos).hazard = Hazard.PIT;

        for(Position p : getNeighbors(pos))
            rooms.get(p).hasBreeze = true;

        return true;
    }

    Set<Position> getNeighbors(Position pos){
        Set<Position> neighbors = new HashSet<>();

        if(pos.x > 0) neighbors.add(new Position(pos.x - 1, pos.y)); //add left
        if(pos.x < size - 1) neighbors.add(new Position(pos.x + 1, pos.y)); //add right

        if(pos.y > 0) neighbors.add(new Position(pos.x, pos.y - 1)); //add bottom
        if(pos.y < size - 1) neighbors.add(new Position(pos.x, pos.y + 1)); //add top

        return neighbors;
    }

    public void printCave() {
        StringBuilder sb = new StringBuilder();

        //add hazards
        for (int y = size - 1; y >= 0; y--) {
            for (int x = 0; x < size; x++) {
                String toAdd = "- ";
                Position pos = new Position(x,y);
                if (rooms.get(pos).hasBreeze) toAdd = "~ ";
                if (rooms.get(pos).hasStench) toAdd = "s ";
                if (rooms.get(pos).hazard == Hazard.CHERRY) toAdd = "C ";
                if (rooms.get(pos).hazard == Hazard.PIT) toAdd = "O ";
                if (rooms.get(pos).hazard == Hazard.WUMPUS) toAdd = "W ";
                if (rooms.get(pos).hasAgent) toAdd = "A ";
                sb.append(toAdd);
            }
            sb.append("\n");
        }
        System.out.println(sb.toString() + "\n");
    }


    public void changeAgentPos(Position position) {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Position p = new Position(x, y);
                rooms.get(p).hasAgent = false;
            }
        }
        rooms.get(position).hasAgent = true;
    }

    public Hazard getHazard(Position position) {
        return rooms.get(position).hazard;
    }

    public boolean hasBreeze(Position position) {
        return rooms.get(position).hasBreeze;
    }

    public boolean hasStench(Position position) {
        return rooms.get(position).hasStench;
    }
}
