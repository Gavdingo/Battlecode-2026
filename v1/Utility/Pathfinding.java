package v1.Utility;

import battlecode.common.*;
import v1.RobotPlayer;

public class Pathfinding {

    public static void moveTo(RobotController rc, MapLocation target) throws GameActionException {
        //TODO: add actual pathfinding logic

        Direction dir = rc.getLocation().directionTo(target);
        if(rc.canTurn()) {
            rc.turn(dir);
        }

        if(rc.canRemoveDirt(rc.getLocation().add(dir))) {
            rc.removeDirt(rc.getLocation().add(dir));
        }

        if (rc.canMove(dir)) {
            rc.move(dir);
        } else {
            dir = RobotPlayer.directions[RobotPlayer.rand.nextInt(8)];
            if (rc.canMove(dir)) {
                rc.move(dir);
            }
        }

    }
}
