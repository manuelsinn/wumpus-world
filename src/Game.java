public class Game {
    static Cave cave = new Cave(true); //change here for own cave vs random cave
    static Agent agent = new AI(cave.getAgentPos(), cave); //change here for player vs ai
    static boolean gameOver = false;


    public static void main(String[] args) {
        int rounds = 0;

        while (!gameOver && rounds < 100) {


            if (agent.goForMove())

                if (agent.move())
                    reactToMove(agent.currentPos);
                else
                    System.out.println("That did not work out. Try again!");

            else
                reactToShot(agent.shoot());


            System.out.println("Round: " + rounds++ + "\tPosition: " + agent.currentPos + "\t\tScore: " + agent.score);
            //cave.printCave();
            agent.getKB().printCave();
        }
    }


    private static void reactToShot(Position target) {
        Hazard h = cave.getHazard(target);
        if (cave.getHazard(target) == Hazard.WUMPUS) {
            System.out.println("Yeehaa! You killed the wumpus.");
            agent.score += 1000;
            gameOver = true;
        } else {
            System.out.println("That shot went nowhere.");
        }
        agent.hasArrow = false;
    }


    private static void reactToMove(Position position) {
        cave.changeAgentPos(agent.currentPos);

        agent.score--;
        Hazard hazard = cave.getHazard(position);

        if (hazard == Hazard.CHERRY) {
            agent.score += 1000;
            System.out.println("Mhhm, a tasty cherry!");
            cave.removeCherry(position);
        }

        if (cave.hasBreeze(position)) System.out.println("Oh, quite a breeze. Watch out for pits.");
        if (cave.hasStench(position)) System.out.println("What a stench! There must be a Wumpus nearby...");

        if (hazard == Hazard.PIT || hazard == Hazard.WUMPUS) {
            agent.score -= 1000;
            gameOver = true;
            System.out.println("You have fallen victim to a hazard. Game Over!");
        }
    }
}

