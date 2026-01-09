package v1.Utility;

import battlecode.common.*;
import v1.*;
import v1.FastMath.FastLocSet;

public class Mapping {

    public static MapInfo[] mapInfos;
    public static RobotInfo[] robotInfos;

    public static void init(RobotController rc) {

        mapInfos = rc.senseNearbyMapInfos();
        robotInfos = rc.senseNearbyRobots();



    }

}
