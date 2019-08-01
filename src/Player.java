import java.util.Scanner;

public class Player extends Agent {
    Scanner sc = new Scanner(System.in);

    public Player(Position currentPos, Cave cave) {
        super(currentPos, cave);
    }


    @Override
    public boolean goForMove() {
        if(hasArrow) System.out.println("Move or Shoot? [m | s]");
        else return true;

        String input = sc.nextLine();
        if(input.equals("m")) return true;
        else return false;
    }


    @Override
    public boolean move(){
        System.out.println("Where do you want to move? [u,d,l,r]");
        Direction dir = getDirectionFromLetter(sc.nextLine());

        Position before = currentPos;
        currentPos = currentPos.moveTowards(dir);
        return before == currentPos;
    }


    @Override
    public Position shoot(){
        System.out.println("Where do you want to shoot? [u,d,l,r]");
        return currentPos.moveTowards(getDirectionFromLetter(sc.nextLine()));
    }


    private Direction getDirectionFromLetter(String d) {
        d = d.toLowerCase();
        if(d.equals("u")) return Direction.UP;
        if(d.equals("d")) return Direction.DOWN;
        if(d.equals("r")) return Direction.RIGHT;
        if(d.equals("l")) return Direction.LEFT;
        return null;
    }

}
