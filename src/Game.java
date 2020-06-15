import AgentMechanics.Agent;

import java.util.Scanner;

import GameMechanics.*;
import AgentMechanics.*;

public class Game {
    static Cave cave;
    static Agent agent;
    static Scanner sc;
    static boolean gameOver;


    public static void main(String[] args) {
        cave = new Cave(true);  //change here for own cave vs random cave
        agent = new AI(cave.getAgentPos(), cave); //change here for player vs ai
//        agent = new Player(new Position(2,2), cave);
        gameOver = false;
        sc = new Scanner(System.in);
        int rounds = 0;

        while (!gameOver && rounds < 1000) {
            if (agent.goForMove())

                if (agent.move())
                    reactToMove(agent.currentPos);
                else
                    System.out.println("That did not work out. Try again!");

            else
                reactToShot(agent.shoot());


            System.out.println("Round: " + rounds++ + "\tPosition: " + agent.currentPos + "\t\tScore: " + agent.score);
            cave.printCave();
        }
    }

    private static void reactToShot(Position target) {
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

