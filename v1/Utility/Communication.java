package v1.Utility;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.Message;
import battlecode.common.RobotController;
import v1.RatKing;

import java.util.Map;

public class Communication {

    static int RAT_KING_FLAG =          0x80000000; //byte 31
    static int IDENTIFIER_FLAG =        0x7F000000; //byte 30 - 24
    static int X2_FLAG =                0x00FC0000; //bits 23 - 18
    static int Y2_FLAG =                0x0003F000; //bits 17 - 12
    static int X1_FlAG =                0x00000FC0; //bits 11 - 6
    static int Y1_FLAG =                0x0000003F; //bits 5 - 0




    public static void WriteArr(RobotController rc, int idx, int message) throws GameActionException {
        rc.writeSharedArray(idx, message);
    }

    public static void ReadArr(RobotController rc, int idx) throws GameActionException {
        int res = rc.readSharedArray(idx);
        if (idx == 0) {

        }
    }

    //SAMPLE FUNCTION TO MAKE SQUEAK:
    // new Communication.Squeak(rc).kidnapped(new MapLocation(3,5)).EncodeandSqueak(rc);
    public static class Squeak {
        public boolean forRatKing = false;
        public int identifier = 0x7F;
        public int x1;
        public int y1;
        public int x2 = 0;
        public int y2 = 0;

        public Squeak(RobotController rc) {
            this.x1 = rc.getLocation().x;
            this.y1 = rc.getLocation().y;
        }

        //ADD YOUR PERSONAL SQUEAK FUNCTIONS HERE
        //REMEMBER TO ADD YOUR DECODE COMPONENT IN COMMINFO.java!!!!
        public Squeak kidnapped(MapLocation kidnapped_Location) {
            this.identifier = 0;
            this.x2 = kidnapped_Location.x;
            this.y2 = kidnapped_Location.y;
            return this;
        }

        public Squeak cheeseMines(MapLocation coord1) {
            this.forRatKing = true;
            this.identifier = 1;
            this.x1 = coord1.x;
            this.y1 = coord1.y;
            return this;
        }

        public Squeak cheeseMines(MapLocation coord1, MapLocation coord2) {
            this.forRatKing = true;
            this.identifier = 2;
            this.x1 = coord1.x;
            this.y1 = coord1.y;
            this.x2 = coord2.x;
            this.y2 = coord2.y;
            return this;
        }

        public Squeak enemyRatKing(MapLocation coord1) {
            this.forRatKing = true;
            this.identifier = 3;
            this.x1 = coord1.x;
            this.y1 = coord1.y;
            return this;
        }

        public Squeak CatFound(MapLocation coord1) {
            this.forRatKing = true;
            this.identifier = 4;
            this.x1 = coord1.x;
            this.y1 = coord1.y;
            return this;
        }

        public Squeak CatFound(MapLocation coord1, MapLocation coord2) {
            this.forRatKing = true;
            this.identifier = 5;
            this.x1 = coord1.x;
            this.y1 = coord1.y;
            this.x2 = coord2.x;
            this.y2 = coord2.y;
            return this;
        }

        public Squeak RatFound(MapLocation coord1) {
            this.forRatKing = true;
            this.identifier = 6;
            this.x1 = coord1.x;
            this.y1 = coord1.y;
            return this;
        }

        public Squeak RatFound(MapLocation coord1, MapLocation coord2) {
            this.forRatKing = true;
            this.identifier = 7;
            this.x1 = coord1.x;
            this.y1 = coord1.y;
            this.x2 = coord2.x;
            this.y2 = coord2.y;
            return this;
        }

        int Encode() {
            if (this.forRatKing) {
                return 1 << 31 + this.identifier << 24 +
                        this.x1 << 18 + this.y1 << 12 +
                        this.y1 << 6 + this.y2;
            }
            else {
                return this.identifier << 24 +
                this.x1 << 18 + this.y1 << 12 +
                this.y1 << 6 + this.y2;
            }

        }

        public void EncodeandSqueak(RobotController rc) {
            rc.squeak(Encode());
        }

        /*This will set the debug indicator line to be the unencoded Squeak */
        public void EncodeandSqueakDEBUG(RobotController rc) {
            //Rat King Bool Followed by identifier followed by the 2 coords in x, y
            rc.squeak(Encode());
            String print = "Rat King: " +String.valueOf(this.forRatKing) + " ID: " + String.valueOf(this.identifier) +
                    " (" + String.valueOf(this.x1) + ", " + String.valueOf(this.y1) + ") " +
                    " (" + String.valueOf(this.x2) + ", " + String.valueOf(this.y2) + ") ";
            rc.setIndicatorString(print);
        }

    }


    public class Listen {

        CommInfo[] messages = {};

        CommInfo[] Decode(Message[] input) {
            for (message : input) {

            }
            return new CommInfo(message);
        }

        public Listen(RobotController rc) {
            rc.readSqueaks(-1);
        }

        public Listen(RobotController rc, int numRounds) {
            rc.readSqueaks(numRounds);
        }


    }



}

