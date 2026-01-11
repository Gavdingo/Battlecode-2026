package v1.Utility;

//Decoded message information can be stored in this class
public class CommInfo {
    //FLAGS
    boolean flag_cat = false;
    boolean flag_enemyRatKing = false;
    boolean flag_enemyRat1 = false;
    boolean flag_enemyRat2 = false;
    boolean flag_cheeseMine1 = false;
    boolean flag_cheeseMine2 = false;

    int Ident;
    int x1;
    int x2;
    int y1;
    int y2;


    public CommInfo(int message) {
        
    }
}
