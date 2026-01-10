package v1.BabyRat;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import v1.RobotPlayer;
import v1.Utility.Mapping;
import v1.Utility.Pathfinding;

import java.util.Map;

public class Controller extends RobotPlayer {

    public static MapLocation target;

    public static void init(RobotController rc) throws GameActionException {
        target = getOppositeSymmetry(rc, spawnLocation);
    }

    public static void run(RobotController rc) throws GameActionException {
        simpleRush(rc);
    }

    public static void simpleRush(RobotController rc) throws GameActionException {

        if(Mapping.enemyRatKings != null && Mapping.enemyRatKings.length >= 1) {
            //rc.setIndicatorString("Rat King");
            target = Mapping.enemyRatKings[0];
            if(rc.canAttack(rc.getLocation().add(rc.getLocation().directionTo(target)))) {
                rc.attack(rc.getLocation().add(rc.getLocation().directionTo(target)));
            }
        } else if(rc.getLocation().equals(target)) {
            target = new MapLocation(rand.nextInt(rc.getMapWidth()), rand.nextInt(rc.getMapHeight()));
        }
        rc.setIndicatorLine(rc.getLocation(),target,255,255,255);
        Pathfinding.moveTo(rc, target);

    }


}
