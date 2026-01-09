package v1.BabyRat;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import v1.RobotPlayer;
import v1.Utility.Mapping;
import v1.Utility.Pathfinding;

public class Controller extends RobotPlayer {
    public static void init(RobotController rc) throws GameActionException {

    }

    public static void run(RobotController rc) throws GameActionException {
        simpleRush(rc);
    }

    public static void simpleRush(RobotController rc) throws GameActionException {

        MapLocation target;
        if(Mapping.enemyRatKings != null && Mapping.enemyRatKings.length > 1) {
            //rc.setIndicatorString("Rat King");
            target = Mapping.enemyRatKings[0];
            if(rc.canAttack(rc.getLocation().add(rc.getLocation().directionTo(target)))) {
                rc.attack(rc.getLocation().add(rc.getLocation().directionTo(target)));
            }
        } else {
            target = getOppositeSymmetry(rc, spawnLocation);
        }
        //rc.setIndicatorLine(rc.getLocation(),target,0,0,0);
        Pathfinding.moveTo(rc, target);

    }


}
