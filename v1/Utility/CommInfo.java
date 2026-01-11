package v1.Utility;

import battlecode.common.MapLocation;
import battlecode.common.Message;

//Decoded message information can be stored in this class
public class CommInfo {
    //Messages
    public Message message;
    //FLAGS
    public boolean flag_cat = false;
    public boolean flag_enemyRatKing = false;
    public boolean flag_enemyRat1 = false;
    public boolean flag_enemyRat2 = false;
    public boolean flag_cheeseMine1 = false;
    public boolean flag_cheeseMine2 = false;
    public boolean flag_kidnapped =false;

    boolean RatKing;
    int Ident;
    MapLocation Coord1;
    MapLocation Coord2;


    public CommInfo(Message input) {
        message = input;

        int encoded = message.getBytes();
        RatKing = (encoded & 0x80000000) < 0;
        Ident = (encoded & 0x7F000000) >> 24;
        Coord1 = new MapLocation(encoded & 0x00FC0000 >> 18, encoded & 0x0003F000 >> 12);
        Coord2 = new MapLocation(encoded & 0x00000FC0 >> 6 , encoded & 0x00FC003F);

        //THIS IS THE DECODE FUNCTION
        //PUT YOUR INTERPRETATIONS HERE
        switch (Ident) {
            case 0:
                flag_kidnapped = true;
                break;
            case 1:
                flag_cheeseMine1 = true;
                break;
            case 2:
                flag_cheeseMine1 = true;
                flag_cheeseMine2 = true;
                break;
            case 3:
                flag_enemyRatKing = true;
                break;
            case 4:
            case 5:
                flag_cat = true;
                break;
            case 6:
                flag_enemyRat1 = true;
                break;
            case 7:
                flag_enemyRat1 = true;
                flag_enemyRat2 = true;
                break;
        }
    }
}
