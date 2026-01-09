package v1.Utility;

import battlecode.common.*;
import v1.*;
import v1.FastMath.FastLocSet;

import java.awt.*;

public class Mapping {

    public static MapInfo[] mapInfos;
    public static RobotInfo[] robotInfos;

    public static MapLocation[] alliedRatKings;
    public static MapLocation[] enemyRatKings;

    public static RobotInfo[] alliedRats;
    public static RobotInfo[] enemyRats;

    public static MapLocation[] alliedRatLocations;
    public static MapLocation[] enemyRatLocations;

    public static lastSeenRobot[] lastSeenAlly;
    public static lastSeenRobot[] lastSeenEnemy;

    public static RobotInfo[] cats;


    public static void init(RobotController rc) {

        mapInfos = rc.senseNearbyMapInfos();
        robotInfos = rc.senseNearbyRobots();

        updateNearbyRats(rc);
        updateNearbyCats(rc);

    }

    public static void updateNearbyCats(RobotController rc) {
        int numCats = 0;

        for(RobotInfo robotInfo : robotInfos) {
            if(robotInfo.getType().isCatType()) {
                numCats += 1;
            }
        }

        cats = new RobotInfo[numCats];

        int c = 0;
        for (RobotInfo robotInfo : robotInfos) {
            if (robotInfo.getType().isCatType()) {
                cats[c] = robotInfo;
                c += 1;
            }
        }
    }

    public static void updateNearbyRats(RobotController rc) {

        //count # of enemy and allied robots
        int enemies = 0;
        int allies = 0;
        int enemyKings = 0;
        for(RobotInfo robot : robotInfos) {
            if (robot.getType().isBabyRatType()) {
                if(robot.getTeam() == rc.getTeam()) {
                    allies += 1;
                } else {
                    enemies += 1;
                }
            } else if (robot.getType().isRatKingType() && robot.getTeam() != rc.getTeam()) {
                enemyKings += 1;
            }
            //rc.setIndicatorString(robot.toString() + "." + robot.getType().toString());
        }


        //define size of allied and enemy rats arrays
        alliedRats = new RobotInfo[allies];
        enemyRats = new RobotInfo[enemies];
        alliedRatLocations = new MapLocation[allies];
        enemyRatLocations = new MapLocation[enemies];
        enemyRatKings = new MapLocation[enemyKings];

        //add enemy and allied rats to arrays
        int a = 0;
        int e = 0;
        int eKings = 0;
        for (RobotInfo robotInfo : robotInfos) {

            if (robotInfo.getType().isBabyRatType()) {
                if (robotInfo.getTeam() == rc.getTeam()) {
                    alliedRats[a] = robotInfo;
                    alliedRatLocations[a] = robotInfo.getLocation();
                    a += 1;
                } else {
                    enemyRats[e] = robotInfo;
                    enemyRatLocations[e] = robotInfo.getLocation();
                    e += 1;
                }
            } else if (robotInfo.getType().isRatKingType() && robotInfo.getTeam() != rc.getTeam()) {
                enemyRatKings[eKings] = robotInfo.getLocation();
                eKings += 1;
            }
        }
    }

}



class lastSeenRobot {
    public MapLocation location;
    public int roundsSinceLastSeen;


    public lastSeenRobot(MapLocation location, int roundsSinceLastSeen) {
        this.location = location;
        this.roundsSinceLastSeen = roundsSinceLastSeen;
    }
}
