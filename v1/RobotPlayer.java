package v1;
import battlecode.common.*;
import v1.*;
import v1.BabyRat.Attack;
import v1.BabyRat.Collect;
import v1.BabyRat.Controller;
import v1.BabyRat.Explore;
import v1.Utility.Mapping;

import java.util.Random;

public class RobotPlayer {

    public static int turnCount = 0;

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

    public static MapLocation spawnLocation;
    public static Random rand = new Random();

    public static void run(RobotController rc) throws GameActionException {

        spawnLocation = rc.getLocation();

        //Run Initialization Methods
        initialize(rc);

        while (true) {

            turnCount++;

            try {

                //run Utility functions first
                Mapping.run(rc);

                if (rc.getType().isBabyRatType()) {
                    Controller.run(rc);
                }

                if (rc.getType().isRatKingType()) {
                    RatKing.run(rc);
                }



            } finally {
                Clock.yield();
            }

        }
    }

    public static void initialize(RobotController rc) throws GameActionException {
        //BabyRat
        Attack.init(rc);
        Collect.init(rc);
        Controller.init(rc);
        Explore.init(rc);
        //Utility
        Mapping.init(rc);
        //RatKing
        RatKing.init(rc);
    }

    public static MapLocation getOppositeSymmetry(RobotController rc, MapLocation loc) {
        int centerX = rc.getMapWidth() / 2;
        int centerY = rc.getMapHeight() / 2;

        int distFromCenterX = centerX - loc.x;
        int distFromCenterY = centerY - loc.y;

        return new MapLocation(centerX + distFromCenterX - 1, centerY + distFromCenterY - 1);


    }

    public static Direction getOppositeDirection(Direction dir) {
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
}
