# wumpus-world
Interactive object-oriented implementation of the widely known "Wumpus" game, playable through a basic CLI. Also features a knowledge-base AI to play the game which relies on propositional logic.

To learn more about this project, head over to: https://manuelsinn.netlify.app/posts/wumpus/

# The basic idea…
…of the game is this:
Armed only with a bow and (a single!) arrow, you are set to explore a cave. Do not fall into a pit! Your mission, should you choose to accept it, is to kill the wumpus before it has a chance to eat you alive.

Whenever you take a step (or move to a different dungeon in the original), a few options arise:

1. You have met the wumpus, the two of you got acquainted and a moment later you are dead.
2. You have stepped into a pit. Legend has it that you are still falling. No one knows what’s down there.
3. You have reached a position in which you are not in immediate danger. This leaves three options:
- You notice nothing but darkness. Instead of damning the boredom, you celebrate the richness of still being alive and continue your journey ecstatically.
- Your nose picks up an abominable stench, and you try not to panic, although this means that the wumpus is either directly in front of you, behind you or by your side. You are basically doomed and should probably head back to where you came from.
- Your hair gets all frizzy because a breeze just blew through: a pit is nearby. You remember your fear of heights and proceed with extra caution (and an increasing number of sweat drops on your forehead).

# Different agents at play
Two agents can play the game: you, via the command line interface, and the AI.
In the CLI version you can always choose between moving and shooting. Until you have shot away your arrow that is, in which case you have either won the game (by killing the wumpus) or are doomed, because you don’t have a weapon anymore (= game over).
The AI explores the world and in doing so continually builds up a knowledge base. The knowledge base is fed by the sensory input: after every step, information is received (e.g. stench, breeze, etc). In the next step, propositional logic is used to determine the right action to take. Put into simple terms, at some point the AI has walked around the cave for long enough to deduce the position of hazards, so it can dodge them smoothly and heroically shoot the wumpus to save the princess. Or something like that.
