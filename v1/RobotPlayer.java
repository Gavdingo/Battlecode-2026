package v1;
import battlecode.common.RobotController;
import v1.*;
import v1.BabyRat.Attack;
import v1.BabyRat.Collect;
import v1.BabyRat.Controller;
import v1.BabyRat.Explore;
import v1.Utility.Mapping;

public class RobotPlayer {

    public static void run (RobotController rc) {
        //Run Initialization Methods
        initialize(rc);

    }

    public static void initialize (RobotController rc) {
        //BabyRat
        Attack.init(rc);
        Collect.init(rc);
        Controller.init(rc);
        Explore.init(rc);
        //Utility
        Mapping.init(rc);
        //RatKing
        RatKing.init(rc);
    }
}
