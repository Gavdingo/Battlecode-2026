package v1;

import battlecode.common.*;
import v1.Utility.Mapping;
import v1.Utility.Sensing;

import java.util.Map;

public class RatKing extends  RobotPlayer{
    private static final int minCheese = 500;
    private static MapLocation ratSpawnLocation;

    public static void init(RobotController rc) {
        Direction enemyKingDir = rc.getLocation().directionTo(getOppositeSymmetry(rc, rc.getLocation()));
        ratSpawnLocation = rc.getLocation().add(enemyKingDir).add(enemyKingDir);
    }

    public static void run(RobotController rc) throws GameActionException {
        if (Sensing.cats != null && Sensing.cats.length > 0) {
            runFromCats(rc);
        }

        if (rc.getAllCheese() > minCheese) {
            buildRat(rc, ratSpawnLocation);
        }
    }

    private static void runFromCats(RobotController rc)
            throws GameActionException {
        Direction catDirection = rc.getLocation().directionTo(Sensing.cats[0].getLocation());
        if (rc.canMove(catDirection.opposite())) {
            rc.move(catDirection.opposite());
        }
    }

    private static void buildRat(RobotController rc, MapLocation targetLocation)
            throws GameActionException {
        if (rc.canBuildRat(targetLocation)) {
            rc.buildRat(targetLocation);
        }
    }
}
