package GameMechanics;

public class Room {
    int id;
    public Hazard hazard;
    boolean hasBreeze;
    boolean hasStench;
    boolean hasAgent;

    public Room(int id, Hazard hazard) {
        this.id = id;
        this.hazard = hazard;
        hasBreeze = false;
        hasStench = false;
        hasAgent = false;
    }

}





