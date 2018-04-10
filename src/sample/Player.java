package sample;

/**
 * Die Player Klasse, die die x und y Position eines Spielers speichert,
 * sowie diese verschiebt
 * @version 1.0
 */

public class Player{

    int xPos;
    int yPos;
    double points = 0.0;
    public String name;

    public Player(int xPos, int yPos, String name){
        this.xPos = xPos; //Startpunkt x Koordinate...
        this.yPos = yPos; //und y Koordinate
        this.name = name;
    }

    /**
     * Bewegt die y Position des Spielers, wenn möglich (Spielfeldrand erreicht oder anderer Spieler
     * im Weg) um eine Stelle nach oben -> y - 1
     * @param otherPlayer
     * @return boolean -> gibt an ob der Spieler bewegt wurde oder ob die Bewegung nicht möglich
     * ist, da der andere Spieler im Weg steht bzw. der Spielfeldrand erreicht wurde
     */
    public boolean moveUp(Player otherPlayer){
        if(yPos <= 0 || (yPos - 1 == otherPlayer.yPos && xPos == otherPlayer.xPos)){
            return false;
        }
        else{
            yPos -= 1;
            return true;
        }

    }

    /**
     * Bewegt die y Position des Spielers, wenn möglich (Spielfeldrand erreicht oder anderer Spieler
     * im Weg) um eine Stelle nach unten -> y + 1
     * @param otherPlayer
     * @return boolean -> gibt an ob der Spieler bewegt wurde oder ob die Bewegung nicht möglich
     * ist, da der andere Spieler im Weg steht bzw. der Spielfeldrand erreicht wurde
     */
    public boolean moveDown(Player otherPlayer){
        if(yPos >= 7 || (yPos + 1 == otherPlayer.yPos && xPos == otherPlayer.xPos)){
            return false;
        }
        else{
            yPos += 1;
            return true;
        }

    }

    /**
     * Bewegt die x Position des Spielers, wenn möglich (Spielfeldrand erreicht oder anderer Spieler
     * im Weg) um eine Stelle nach rechts -> x + 1
     * @param otherPlayer
     * @return boolean -> gibt an ob der Spieler bewegt wurde oder ob die Bewegung nicht möglich
     * ist, da der andere Spieler im Weg steht bzw. der Spielfeldrand erreicht wurde
     */
    public boolean moveRight(Player otherPlayer){
        if(xPos >= 7 || (xPos + 1 == otherPlayer.xPos && yPos == otherPlayer.yPos)){
            return false;
        }
        else {

            xPos += 1;
            return true;
        }

    }

    /**
     * Bewegt die x Position des Spielers, wenn möglich (Spielfeldrand erreicht oder anderer Spieler
     * im Weg) um eine Stelle nach links -> x - 1
     * @param otherPlayer
     * @return boolean -> gibt an ob der Spieler bewegt wurde oder ob die Bewegung nicht möglich
     * ist, da der andere Spieler im Weg steht bzw. der Spielfeldrand erreicht wurde
     */
    public boolean moveLeft(Player otherPlayer){
        if(xPos <= 0 || (xPos - 1 == otherPlayer.xPos && yPos == otherPlayer.yPos)){
            return false;
        }
        else {

            xPos -= 1;
            return true;
        }

    }

}
