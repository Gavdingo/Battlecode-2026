package v1.Utility;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;

public class Communication {
    public static void Write(RobotController rc, int idx, int message) throws GameActionException {
        rc.writeSharedArray(idx, message);
    }

    public static void Read(RobotController rc, int idx) throws GameActionException {
        rc.readSharedArray(idx);
    }

    public static int Encode(int x, int y) {
        return x << 6 + y;
    }
}
