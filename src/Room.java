public class Room {
    int id;
    public Hazard hazard;
    boolean hasBreeze = false;
    boolean hasStench = false;
    boolean hasAgent = false;

    public Room(int id, Hazard hazard) {
        this.id = id;
        this.hazard = hazard;
    }

}





