package GameMechanics;

import java.util.*;

public class Cave {
    Room[][] rooms;
    public int size;
    int pits = 3;
    int wumpusses = 1;
    Random rnd = new Random();

    public Cave(boolean random) {
        Scanner sc = new Scanner(System.in);
        if(!random) {
            System.out.println("Enter World Size:");
            size = sc.nextInt();
        } else size = 4;

        //create rooms
        rooms = new Room[size][size];
        int id = 1;
        for (int row = 0; row < size; row++) {
            rooms[row] = new Room[size];
            for (int col = 0; col < size; col++) {
                rooms[row][col] = new Room(id++, Hazard.UNDEF);
            }
        }

        // add hazards
        if (random) {
//            rnd.setSeed(3); //1,3,180

            for (int i = 0; i < pits; i++) {
                while (!addPit(rnd.nextInt(size), rnd.nextInt(size))) ;
            }

            for (int i = 0; i < wumpusses; i++) {
                while (!addWumpus(rnd.nextInt(size), rnd.nextInt(size))) ;
            }

            while (!addCherry(rnd.nextInt(size), rnd.nextInt(size))) ;

            while (!addAgent(rnd.nextInt(size), rnd.nextInt(size))) ;

        } else {
            for (int i = 0; i < pits; i++) {
                do System.out.println("Add Pit " + i + " (row column):");
                while (!addPit(sc.nextInt(), sc.nextInt()));
            }

            for (int i = 0; i < wumpusses; i++) {
                do System.out.println("Add Wumpus (row column):");
                while (!addWumpus(sc.nextInt(), sc.nextInt()));
            }

            do System.out.println("Add Cherry (row column):");
            while (!addCherry(sc.nextInt(), sc.nextInt()));

            do System.out.println("Add Agent.Player (row column):");
            while (!addAgent(sc.nextInt(), sc.nextInt()));
        }

        System.out.println("Cave generated:");
        printCave();
    }


    public Position getAgentPos() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (rooms[row][col].hasAgent)
                    return new Position(row, col);
            }
        }
        return null;
    }

    private boolean addCherry(int row, int col) {
        if (rooms[row][col].hazard == Hazard.PIT || rooms[row][col].hazard == Hazard.WUMPUS) return false;
        rooms[row][col].hazard = Hazard.CHERRY;
        return true;
    }

    public void removeCherry(Position position){
        rooms[position.row][position.col].hazard = Hazard.UNDEF;
    }

    public boolean addAgent(int row, int col) {
        if (rooms[row][col].hazard == Hazard.PIT || rooms[row][col].hazard == Hazard.WUMPUS || rooms[row][col].hazard == Hazard.CHERRY)
            return false;
        rooms[row][col].hasAgent = true;
        return true;
    }

    public boolean addWumpus(int row, int col) {
        if (rooms[row][col].hazard == Hazard.PIT) return false;
        rooms[row][col].hazard = Hazard.WUMPUS;
        if (row >= 1) rooms[row - 1][col].hasStench = true; //not top row: stench above
        if (row <= (size - 2)) rooms[row + 1][col].hasStench = true; //not bottom row: stench below
        if (col >= 1) rooms[row][col - 1].hasStench = true; //not leftest column: stench left
        if (col <= (size - 2)) rooms[row][col + 1].hasStench = true; //not rightest column: stench right
        return true;
    }

    public boolean addPit(int row, int col) {
        if (rooms[row][col].hazard == Hazard.WUMPUS) return false;
        rooms[row][col].hazard = Hazard.PIT;
        if (row >= 1) rooms[row - 1][col].hasBreeze = true;
        if (row <= (size - 2)) rooms[row + 1][col].hasBreeze = true;
        if (col >= 1) rooms[row][col - 1].hasBreeze = true;
        if (col <= (size - 2)) rooms[row][col + 1].hasBreeze = true;
        return true;
    }



    public void printCave() {
        StringBuilder sb = new StringBuilder();

        //add hazards
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                String toAdd = "- ";
                if (rooms[row][col].hasBreeze) toAdd = "~ ";
                if (rooms[row][col].hasStench) toAdd = "s ";
                if (rooms[row][col].hazard == Hazard.CHERRY) toAdd = "C ";
                if (rooms[row][col].hazard == Hazard.PIT) toAdd = "O ";
                if (rooms[row][col].hazard == Hazard.WUMPUS) toAdd = "W ";
                if (rooms[row][col].hasAgent) toAdd = "X ";
                sb.append(toAdd);
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }

    public void changeAgentPos(Position position) {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                rooms[row][col].hasAgent = false;
            }
        }

        rooms[position.row][position.col].hasAgent = true;
    }

    public Hazard getHazard(Position position) {
        return rooms[position.row][position.col].hazard;
    }

    public boolean hasBreeze(Position position) {
        return rooms[position.row][position.col].hasBreeze;
    }

    public boolean hasStench(Position position) {
        return rooms[position.row][position.col].hasStench;
    }
}
