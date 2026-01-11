package v1.Utility;

import battlecode.common.*;
import v1.*;
import v1.FastMath.FastLocSet;

import java.awt.*;

public class Sensing {

    public static int LAST_SEEN_TIMEOUT = 3;

    public static MapInfo[] mapInfos;
    public static RobotInfo[] robotInfos;

    public static MapLocation[] alliedRatKings;
    public static MapLocation[] enemyRatKings;

    public static RobotInfo[] alliedRats;
    public static RobotInfo[] enemyRats;

    public static MapLocation[] alliedRatLocations;
    public static MapLocation[] enemyRatLocations;

    public static LastSeenRobot[] lastSeenAlly;
    public static LastSeenRobot[] lastSeenEnemy;

    public static RobotInfo[] cats;


    public static void init(RobotController rc) {}

    public static void run(RobotController rc) throws GameActionException {
        mapInfos = rc.senseNearbyMapInfos();
        robotInfos = rc.senseNearbyRobots();

        if(RobotPlayer.turnCount > 1) {
            updateLastSeen(rc);
        }
        updateNearbyRats(rc);
        updateNearbyCats(rc);

        /*
        for(RobotInfo ri : enemyRats) {
            rc.setIndicatorLine(rc.getLocation(),ri.getLocation(),255,255,255);
        }
        if(lastSeenEnemy != null) {
            for (LastSeenRobot lr : lastSeenEnemy) {
                rc.setIndicatorLine(rc.getLocation(), lr.location, 0, 255, 255);
            }
        }
        */
    }

    public static void updateLastSeen(RobotController rc) {

        for(RobotInfo robot : robotInfos) {
            if(rc.getType().isBabyRatType()) {
                if(lastSeenAlly != null) {
                    for (LastSeenRobot lr : lastSeenAlly) {
                        if (lr.id == robot.ID) {
                            lr.roundsSinceLastSeen = LAST_SEEN_TIMEOUT + 1;
                        }
                    }
                }
                if(lastSeenEnemy != null) {
                    for (LastSeenRobot lr : lastSeenEnemy) {
                        if (lr.id == robot.ID) {
                            lr.roundsSinceLastSeen = LAST_SEEN_TIMEOUT + 1;
                        }
                    }
                }
            }
        }
        int alliedCount = 0;
        int enemyCount = 0;

        if (lastSeenAlly != null) {
            alliedCount = lastSeenAlly.length;
        }
        if (lastSeenEnemy != null) {
            enemyCount = lastSeenEnemy.length;
        }

        for(RobotInfo robot : alliedRats) {
            if(!rc.canSenseRobot(robot.getID())) {
                alliedCount++;
            }
        }
        for(RobotInfo robot : enemyRats) {
            if(!rc.canSenseRobot(robot.getID())) {
                enemyCount++;
            }
        }

        if(lastSeenAlly != null) {
            for (LastSeenRobot robot : lastSeenAlly) {
                if (robot.roundsSinceLastSeen > LAST_SEEN_TIMEOUT) {
                    alliedCount--;
                }
            }
        }
        if(lastSeenEnemy != null) {
            for (LastSeenRobot robot : lastSeenEnemy) {
                if (robot.roundsSinceLastSeen > LAST_SEEN_TIMEOUT) {
                    enemyCount--;
                }
            }
        }
        LastSeenRobot[] lastSeenAllyCopy = new LastSeenRobot[0];
        LastSeenRobot[] lastSeenEnemyCopy = new LastSeenRobot[0];
        if(lastSeenAlly != null) {
            lastSeenAllyCopy = lastSeenAlly.clone();
        }
        if(lastSeenEnemy != null) {
            lastSeenEnemyCopy = lastSeenEnemy.clone();
        }

        lastSeenAlly = new LastSeenRobot[alliedCount];
        lastSeenEnemy = new LastSeenRobot[enemyCount];

        int a = 0;
        int e = 0;

        for (LastSeenRobot robot : lastSeenAllyCopy) {
            if (robot.roundsSinceLastSeen <= LAST_SEEN_TIMEOUT) {
                robot.roundsSinceLastSeen += 1;
                lastSeenAlly[a] = robot;
                a++;
            }
        }


        for(LastSeenRobot robot : lastSeenEnemyCopy) {
            if(robot.roundsSinceLastSeen <= LAST_SEEN_TIMEOUT) {
                robot.roundsSinceLastSeen += 1;
                lastSeenEnemy[e] = robot;
                e++;
            }
        }

        for(RobotInfo robot : alliedRats) {
            if(!rc.canSenseRobot(robot.getID())) {
                LastSeenRobot lastSeen = new LastSeenRobot(robot.getLocation(),robot.team,1,robot.getID());
                lastSeenAlly[a] = lastSeen;
                a++;
            }
        }
        for(RobotInfo robot : enemyRats) {
            if(!rc.canSenseRobot(robot.getID())) {
                LastSeenRobot lastSeen = new LastSeenRobot(robot.getLocation(),robot.team,1,robot.getID());
                lastSeenEnemy[e] = lastSeen;
                e++;
            }
        }
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
                //rc.setIndicatorString(Integer.toString(enemyKings));
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
                //rc.setIndicatorString(enemyRatKings[0].toString());
            }
        }
    }

}



class LastSeenRobot {
    public MapLocation location;
    public int roundsSinceLastSeen;
    public int id;
    public Team team;


    public LastSeenRobot(MapLocation location, Team team, int roundsSinceLastSeen, int id) {
        this.location = location;
        this.team = team;
        this.roundsSinceLastSeen = roundsSinceLastSeen;
        this.id = id;
    }
}
