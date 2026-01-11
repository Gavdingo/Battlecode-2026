package v1.Utility;


import battlecode.common.*;
import battlecode.common.RobotController;
import v1.RobotPlayer;

public class Micro extends Sensing{

    //heuristic constants:

    public static final int CLOSEST_ENEMY_RAT_WEIGHT = 80;
    
    public static final int ENEMY_RAT_WEIGHT = -50;
    public static final int ALLIED_RAT_WEIGHT = 50;

    public static final int ALLIED_RAT_NEAR_ENEMY_WEIGHT = 0;

    public static final int LAST_KNOWN_ENEMY_RAT_WEIGHT = -30;
    public static final int LAST_KNOWN_ALLIED_RAT_WEIGHT = 0;

    public static final int ALLIED_RAT_KING_WEIGHT = 0;
    public static final int ENEMY_RAT_KING_WEIGHT = 0;

    public static final int HEALTH_DIFFERENCE_WEIGHT = 50;

    public static int[] heuristicValues = new int[8];

    public static void init(RobotController rc) {}

    public static void run(RobotController rc) throws GameActionException {

        heuristicValues = new int[8];

        RobotInfo closestEnemy = getClosestEnemy(rc);
        if(closestEnemy!= null) {
            rc.setIndicatorLine(rc.getLocation(),closestEnemy.location,255,255,255);
        }
        boolean inCombat = false;

        int distToClosestEnemy = chebyshevDistance(rc.getLocation(),closestEnemy.location);

        if(rc.getCarrying() != null) {
            if (distToClosestEnemy <= 2) {
                if(rc.canTurn(rc.getLocation().directionTo(closestEnemy.getLocation()))) {
                    rc.turn(rc.getLocation().directionTo(closestEnemy.getLocation()));
                    if(rc.canThrowRat()){
                        rc.throwRat();
                    }
                }
            }
        } else {

            //engaged in combat (cannot run turn away or will get ratnapped)
            if(distToClosestEnemy <= 1) {
                inCombat = true;

                addHeuristic(rc, rc.getLocation().add(rc.getLocation().directionTo(closestEnemy.getLocation()).opposite()), 100);

                if (closestEnemy.location.x == rc.getLocation().x || rc.getLocation().y == rc.getLocation().y) {
                    if (rc.getActionCooldownTurns() == 0 && rc.getTurningCooldownTurns() == 0 && rc.getMovementCooldownTurns() == 0) {
                        //TODO: step into enemy rats blind spot and ratnap them
                    }
                }

                if(rc.canCarryRat(closestEnemy.getLocation())) {
                    rc.carryRat(closestEnemy.getLocation());
                }
                if (rc.canAttack(closestEnemy.getLocation())) {
                    rc.attack(closestEnemy.getLocation());
                }
            }

            if(distToClosestEnemy == 2) {
                if (rc.getActionCooldownTurns() < 1) {

                }
            }

            addHeuristic(rc, closestEnemy.getLocation(),CLOSEST_ENEMY_RAT_WEIGHT +  getHealthDifferenceWeight(rc,closestEnemy.health));

            for(RobotInfo enemy : enemyRats) {
                if(enemy.getID() != closestEnemy.getID()) {
                    addHeuristic(rc, enemy.getLocation(), ENEMY_RAT_WEIGHT);
                }
            }

            if(lastSeenEnemy != null) {
                for(LastSeenRobot enemy : lastSeenEnemy) {
                    addHeuristic(rc, enemy.location, LAST_KNOWN_ENEMY_RAT_WEIGHT);
                }
            }

            for(RobotInfo allied : alliedRats) {
                addHeuristic(rc, allied.getLocation(), ALLIED_RAT_WEIGHT);
            }

        }

        String h = "";
        for(int i = 0 ; i < heuristicValues.length; i++) {
            h = h + heuristicValues[i] + RobotPlayer.directions[i]+ ",";
        }
        rc.setIndicatorString(h + " " + Boolean.toString(inCombat));


        boolean found = false;
        int x = 0;
        while (!found) {
            x++;
            int highest = 0;
            int highestDir = 0;
            for(int i = 0 ; i < heuristicValues.length ; i++) {
                if(heuristicValues[i] > highest) {
                    highest = heuristicValues[i];
                    highestDir = i;
                }
            }

            if(rc.canMove(RobotPlayer.directions[highestDir])) {
                found = true;
                if(rc.canTurn() && !inCombat) {
                    rc.turn(RobotPlayer.directions[highestDir]);
                }
                rc.move(RobotPlayer.directions[highestDir]);
                if(inCombat) {
                    if(rc.canTurn(rc.getLocation().directionTo(closestEnemy.getLocation()))) {
                        rc.turn(rc.getLocation().directionTo(closestEnemy.getLocation()));
                    }
                }
            } else {
                heuristicValues[highestDir] = -1;
            }
            if(x == 8) {
                found = true;
            }
        }
    }
    public static void addHeuristic(RobotController rc, MapLocation location, int weight) {
        for(Direction dir : RobotPlayer.directions) {
            int direction_modifier = 0;
            if(dir == rc.getLocation().directionTo(location)) {
                if (weight > 0) {
                    direction_modifier = 10;
                } else {
                    direction_modifier = -10;
                }
            }

            if( chebyshevDistance(rc.getLocation().add(dir), location) < chebyshevDistance(rc.getLocation(), location)) {
                heuristicValues[Math.floorMod(dir.getDirectionOrderNum() -3,8 )] += weight + direction_modifier;
            }
        }
    }

    public static RobotInfo getClosestEnemy(RobotController rc) {
        int dist = Integer.MAX_VALUE;
        RobotInfo closestEnemy = null;
        for (RobotInfo enemy : enemyRats) {
            if(rc.getLocation().distanceSquaredTo(enemy.getLocation()) < dist) {
                dist = rc.getLocation().distanceSquaredTo(enemy.getLocation());
                closestEnemy = enemy;
            }
        }
        return closestEnemy;
    }

    public static int getHealthDifferenceWeight(RobotController rc, int health) {
        if(rc.getHealth() < health) {
            return -1 * HEALTH_DIFFERENCE_WEIGHT;
        } else  if (rc.getHealth() > health) {
            return HEALTH_DIFFERENCE_WEIGHT;
        } else {
            return 0;
        }
    }

    public static int chebyshevDistance(MapLocation p1, MapLocation p2) {
        int dx = Math.abs(p1.x - p2.x);
        int dy = Math.abs(p1.y - p2.y);
        return Math.max(dx, dy);
    }
}
