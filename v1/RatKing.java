package v1;

import battlecode.common.*;
import v1.Utility.Mapping;
import v1.Utility.Sensing;

import java.util.Map;

public class RatKing extends  RobotPlayer{
    public static void init(RobotController rc) {}

    public static void run(RobotController rc) throws GameActionException {
        //TODO: improve this code and organize them into methods

        //run from cats

        if(Sensing.cats != null && Sensing.cats.length >= 1) {
            Direction catDirection = rc.getLocation().directionTo(Sensing.cats[0].getLocation());
            if(rc.canMove(getOppositeDirection(catDirection))) {
                rc.move(getOppositeDirection(catDirection));
            }

        }

        //spawn rats

        Direction enemyKing = rc.getLocation().directionTo(getOppositeSymmetry(rc, rc.getLocation()));

        if(rc.getAllCheese() > 500) {
            if(rc.canBuildRat(rc.getLocation().add(enemyKing).add(enemyKing))) {
                rc.buildRat(rc.getLocation().add(enemyKing).add(enemyKing));
            }
        }


    }
}
