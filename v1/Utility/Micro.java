package v1.Utility;


import battlecode.common.RobotController;

public class Micro extends Sensing{

    //heuristic constants:
    
    public static final int ENEMY_RAT_WEIGHT = 0;
    public static final int ALLIED_RAT_WEIGHT = 0;

    public static final int LAST_KNOWN_ENEMY_RAT_WEIGHT = 0;
    public static final int LAST_KNOWN_ALLIED_RAT_WEIGHT = 0;

    public static final int ALLIED_RAT_KING_WEIGHT = 0;
    public static final int ENEMY_RAT_KING_WEIGHT = 0;

    public static final int HEALTH_DIFFERENCE_WEIGHT = 0;


    public static void init(RobotController rc) {}

    public static void run(RobotController rc) {
        if(rc.getCarrying() != null) {

        } else {


        }
    }


}
