package movetocorner;

import battlecode.common.*;
import battlecode.schema.RobotType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import java.util.EnumMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Stream;


/**
 * RobotPlayer is the class that describes your main robot strategy.
 * The run() method inside this class is like your main function: this is what we'll call once your robot
 * is created!
 */
public class RobotPlayer {
    /**
     * We will use this variable to count the number of turns this robot has been alive.
     * You can use static variables like this to save any information you want. Keep in mind that even though
     * these variables are static, in Battlecode they aren't actually shared between your robots.
     */
    static int turnCount = 0;


    /**
     * A random number generator.
     * We will use this RNG to make some random moves. The Random class is provided by the java.util.Random
     * import at the top of this file. Here, we *seed* the RNG with a constant number (6147); this makes sure
     * we get the same sequence of numbers every time this code is run. This is very useful for debugging!
     */
    static final Random rng = new Random(6147);

    /** Array containing all the possible movement directions. */
    public static final Direction[] directions = {
            Direction.NORTH,
            Direction.NORTHEAST,
            Direction.EAST,
            Direction.SOUTHEAST,
            Direction.SOUTH,
            Direction.SOUTHWEST,
            Direction.WEST,
            Direction.NORTHWEST,
    };

    /**
     * run() is the method that is called when a robot is instantiated in the Battlecode world.
     * It is like the main function for your robot. If this method returns, the robot dies!
     *
     * @param rc  The RobotController object. You use it to perform actions from this robot, and to get
     *            information on its current status. Essentially your portal to interacting with the world.
     **/
    @SuppressWarnings("unused")

    public static Direction opposite(Direction dir) {
        int index = -1;

        for (int i = 0; i < directions.length; i++) {
            if (directions[i] == dir) {
                index = i;
                break;
            }
        }

        // move 4 steps forward and wrap around
        return directions[(index + 4) % directions.length];
    }

    public static void run(RobotController rc) throws GameActionException {
        // Hello world! Standard output is very useful for debugging.
        // Everything you say here will be directly viewable in your terminal when you run a match!
        System.out.println("I'm alive");

        // You can also use indicators to save debug notes in replays.
        rc.setIndicatorString("Hello world!");

        while (true) {
            // This code runs during the entire lifespan of the robot, which is why it is in an infinite
            // loop. If we ever leave this loop and return from run(), the robot dies! At the end of the
            // loop, we call Clock.yield(), signifying that we've done everything we want to do.

            turnCount += 1;  // We have now been alive for one more turn!

            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode.
            try {
                // The same run() function is called for every robot on your team, even if they are
                // different types. Here, we separate the control depending on the UnitType, so we can
                // use different strategies on different robots. If you wish, you are free to rewrite
                // this into a different control structure!

                MapLocation closest = new MapLocation(0,0);
                int dist = rc.getLocation().distanceSquaredTo(closest);

                int d = rc.getLocation().distanceSquaredTo(new MapLocation(rc.getMapWidth(), rc.getMapHeight()));
                if(d < dist) {
                    dist = d;
                    closest = new MapLocation(rc.getMapWidth(), rc.getMapHeight());
                }
                d = rc.getLocation().distanceSquaredTo(new MapLocation(rc.getMapWidth(), 0));
                if (d < dist) {
                    dist = d;
                    closest = new MapLocation(rc.getMapHeight(), 0);
                }
                d = rc.getLocation().distanceSquaredTo(new MapLocation(0, rc.getMapHeight()));
                if (d < dist) {
                    dist = d;
                    closest = new MapLocation(0, rc.getMapHeight());
                }

                Direction dir = rc.getLocation().directionTo(closest);
                if(rc.canTurn()) {
                    rc.turn(dir);
                }

                if(rc.canMove(dir)) {
                    rc.move(dir);
                }

                RobotInfo[] robots = rc.senseNearbyRobots();

                for(RobotInfo robot : robots) {
                    if(robot.getType().isCatType()) {
                        Direction direction = robot.getLocation().directionTo(rc.getLocation());

                        if(rc.canPlaceCatTrap(robot.getLocation().add(direction))) {
                            rc.placeCatTrap(robot.getLocation().add(direction));
                        }
                        if(rc.canPlaceCatTrap(robot.getLocation().add(direction).add(direction))) {
                            rc.placeCatTrap(robot.getLocation().add(direction).add(direction));
                        }
                    }
                }


            } catch (GameActionException e) {
                // Oh no! It looks like we did something illegal in the Battlecode world. You should
                // handle GameActionExceptions judiciously, in case unexpected events occur in the game
                // world. Remember, uncaught exceptions cause your robot to explode!
                System.out.println("GameActionException");
                e.printStackTrace();
            } catch (Exception e) {
                // Oh no! It looks like our code tried to do something bad. This isn't a
                // GameActionException, so it's more likely to be a bug in our code.
                System.out.println("Exception");
                e.printStackTrace();
            } finally {
                // Signify we've done everything we want to do, thereby ending our turn.
                // This will make our code wait until the next turn, and then perform this loop again.
                Clock.yield();
            }
            // End of loop: go back to the top. Clock.yield() has ended, so it's time for another turn!
        }

        // Your code should never reach here (unless it's intentional)! Self-destruction imminent...
    }
}
