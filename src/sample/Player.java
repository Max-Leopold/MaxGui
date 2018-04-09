package sample;


public class Player{

    int xPos;
    int yPos;
    double points = 0.0;
    public String name;

    public Player(int xPos, int yPos, String name){
        this.xPos = xPos;
        this.yPos = yPos;
        this.name = name;
    }

    public boolean moveUp(Player otherPlayer){
        if(yPos <= 0 || (yPos - 1 == otherPlayer.yPos && xPos == otherPlayer.xPos)){
            return false;
        }
        else{
            yPos -= 1;
            return true;
        }

    }

    public boolean moveDown(Player otherPlayer){
        if(yPos >= 7 || (yPos + 1 == otherPlayer.yPos && xPos == otherPlayer.xPos)){
            return false;
        }
        else{
            yPos += 1;
            return true;
        }

    }

    public boolean moveRight(Player otherPlayer){
        if(xPos >= 7 || (xPos + 1 == otherPlayer.xPos && yPos == otherPlayer.yPos)){
            return false;
        }
        else {

            xPos += 1;
            return true;
        }

    }

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
