
/**
 * Controller Klasse
 * Alle Logik des Programms ist in dieser Klasse
 * @version 1.2
 */

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;


public class Controller implements Serializable {

    //FXML:

    //Hinzufügen der im Scene Builder erstellten Elemente
    @FXML
    Label label;

    @FXML
    GridPane boardLayout; //GridPane zum darstellen der Buttons in einem Gitter

    @FXML
    Label infoLabel;

    @FXML
    Label pointsPlayerOne;

    @FXML
    Label pointsPlayerTwo;

    @FXML
    Button newGame;

    @FXML
    AnchorPane buttonPane;

    @FXML
    FlowPane flowPane;

    @FXML
    Button saveGame;


    @FXML
    public void initialize(){

        for (int j = 0; j < 8; j++) {
            for (int k = 0; k < 8; k++) {

                //Fraction Array
                board[j][k] = new Fraction();

                //Button
                Button button = new Button();
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                button.setText(board[j][k].toString());
                //Buton Array
                buttons[j][k] = button;

            }
        }

        //Setzen der Hintergrundfarbe des "new Game Buttons"
        newGame.setStyle("-fx-background-color: #303030");

        //Ersetzen der Brüche an den Startpositionen der Spieler mit "0/0"
        board[4][3].nullFrac();
        board[3][4].nullFrac();

        //Initialisierung
        getHighestFraction();
        setBoard();




    }

    //Instanzvariablen:

    private Player playerOne = new Player(3,4, "Player One"); //Spieler 1
    private Player playerTwo = new Player(4,3, "Player Two"); //Spieler 2

    private int turn = 0;

    //Arrays für Fractions und Brüche
    private Fraction[][] board = new Fraction[8][8];
    private Button[][] buttons = new Button[8][8];

    //Variblen für die Position des höchsten Bruchs, sowie dessen Wert
    private int highX = 0;
    private int highY = 0;
    private double high = 0.0;

    //Abbruchvariable des Spiels
    private boolean finished = false;

    int savegames = 1;

    /**
     * Methode die beim drücken des "new Game" Buttons aufgerufen wird
     * @param actionEvent
     * @throws IOException
     */
    public void buttonPressed(ActionEvent actionEvent) throws IOException {

        //Erstellen einer neuen Stage
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Max");
        Scene scene = new Scene(root, 750, 600);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    /**
     * Methode die beim drücken einer Taste aufgerufen wird
     * @param keyEvent
     */
    public void moveKeyPressed(KeyEvent keyEvent) {

        Player tmp;
        Player otherPlayer;

        //Konnte der Spieler bewegt werden?
        boolean succesful = false;

        //Abwechselnde Züge der zwei Spieler
        if (turn % 2 == 0) {
            tmp = playerOne;
            otherPlayer = playerTwo;
        } else {
            tmp = playerTwo;
            otherPlayer = playerOne;
        }

        //Solange niemand die nötige Menge an Punkten aufgesammelt hat:
        if (!finished) {
            KeyCode keyCode = keyEvent.getCode();

            //Abfrage ob bzw. welche Pfeiltaste gedrückt wurde
            switch (keyCode.toString()) {
                case "UP":
                    succesful = tmp.moveUp(otherPlayer);//Pfeiltaste hoch, ...
                    turn++;
                    break;
                case "DOWN":
                    succesful = tmp.moveDown(otherPlayer); //runter, ...
                    turn++;
                    break;
                case "LEFT":
                    succesful = tmp.moveLeft(otherPlayer); //links, ...
                    turn++;
                    break;
                case "RIGHT":
                    succesful = tmp.moveRight(otherPlayer);//rechts, ...
                    turn++;
                    break;
            }

            //Wenn der Spieler nicht bewegt werden konnte wird eine Info ausgegeben
            //und der selbe Spieler ist erneut am Zug
            if (!succesful) {
                turn--;
                infoLabel.setText("Spielfeldrand erreicht \noder anderer Spieler im weg!");
            } else {
                infoLabel.setText("");
            }

            setBoard();
            System.out.println(turn);
            System.out.println(tmp.points);


        }




    }

    /**
     * "Print" Methode für das Spielbrett
     */
    private void setBoard(){

        //Komplettes leerräumen des GridPane
        boardLayout.getChildren().clear();

        testForWin();

        //Updaten des button Arrays ("buttons") bzw. des Fraction Arrays ("board")
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {


                //Setzen des Player One mit einer farbigen markierung
                if(i == playerOne.xPos && j == playerOne.yPos) {
                    buttons[i][j].setStyle("-fx-background-color: goldenrod");
                    playerOne.points = playerOne.points + board[i][j].getValue();
                    pointsPlayerOne.setText("Points: " + Fraction.round(playerOne.points, 4)); //Schreiben der Punkte anzahl in das entsprechende Labeö
                    testForWin();
                    board[i][j].nullFrac(); //Zähler und Nenner an der Stelle des Spielers gleih null setzen
                    getHighestFraction();
                }
                //Setzen des Spieler zwei mit einer farbigen Markierung
                else if(i == playerTwo.xPos && j == playerTwo.yPos){
                    buttons[i][j].setStyle("-fx-background-color: cadetblue");
                    playerTwo.points = playerTwo.points + board[i][j].getValue();
                    pointsPlayerTwo.setText("Points: " + Fraction.round(playerTwo.points, 4));
                    testForWin();
                    board[i][j].nullFrac();
                    getHighestFraction();
                }
                //Markieren des höchsten Bruchs
                else if(i == highX && j == highY){
                        buttons[i][j].setStyle("-fx-background-color: crimson");
                        getHighestFraction();
                }
                //Setzen der Felder um den Spieler der am Zug ist
                else if(        turn % 2 == 0 && (
                        //Alle Felder um Spieler 1
                                (i == playerOne.xPos + 1 && j == playerOne.yPos) ||
                                (i == playerOne.xPos - 1 && j == playerOne.yPos) ||
                                (j == playerOne.yPos - 1 && i == playerOne.xPos) ||
                                (j == playerOne.yPos + 1 && i == playerOne.xPos))){
                    buttons[i][j].setStyle("-fx-background-color: rgba(218,165,32,0.6)");
                }
                else if(        turn % 2 != 0 && (
                        //Alle Felder um Spieler 2
                                (i == playerTwo.xPos + 1 && j == playerTwo.yPos) ||
                                (i == playerTwo.xPos - 1 && j == playerTwo.yPos) ||
                                (j == playerTwo.yPos - 1 && i == playerTwo.xPos) ||
                                (j == playerTwo.yPos + 1 && i == playerTwo.xPos))){
                    buttons[i][j].setStyle("-fx-background-color: rgba(95,158,160,0.57)");
                }
                //Alle anderen Felder
                else{
                    buttons[i][j].setStyle("-fx-background-color: rgb(58, 58, 58)");
                }

                //Wenn einer der Spieler auf dem Feld des höchsten Bruchs steht muss der nächst höhste Bruch ermittlet werden
                if((    playerOne.xPos == highX && playerOne.yPos == highY)||
                        playerTwo.xPos == highX && playerOne.yPos == highY){
                    getHighestFraction();
                }

                //Schriftfarbe der Buttons
                buttons[i][j].setTextFill(Color.WHITE);

                //Sync Arrays
                buttons[i][j].setText(board[i][j].toString());

                //Adden der Buttons auf das GridPane
                boardLayout.add(buttons[i][j], i, j);

            }
        }




    }

    /**
     * Methode zum Heraussuchen des Bruchs mit dem höchsten Wert
     */
    private void getHighestFraction(){

        high = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                if(board[i][j].getValue() > high){
                    high = board[i][j].getValue();
                    highX = i;
                    highY = j;
                }

            }
        }
    }


    /**
     * Testet ob ein Spieler gewonne hat
     */
    private void testForWin(){

        if(playerOne.points > 10){
            infoLabel.setText("Player One wins!");
            finished = true; //Abbruchvariable
        }
        else if(playerTwo.points > 10){
            infoLabel.setText("Player Two wins!");
            finished = true; //Abbruchvariable
        }


    }


    public void saveGame(ActionEvent actionEvent) {

        try{

            Stage stage = new Stage();

            FileChooser fileChooser1 = new FileChooser();
            fileChooser1.setTitle("Save Game");
            File file = fileChooser1.showSaveDialog(stage);
            System.out.println(file);


            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(board);
            oos.writeObject(playerOne);
            oos.writeObject(playerTwo);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadGame(ActionEvent actionEvent) {

        try {

            Stage stage = new Stage();

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load Gaem");
            File file = fileChooser.showOpenDialog(stage);

            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            Fraction[][] board = (Fraction[][]) ois.readObject();
            Player playerOne = (Player) ois.readObject();
            Player playerTwo = (Player) ois.readObject();

            this.board = board;
            this.playerOne = playerOne;
            this.playerTwo = playerTwo;

            getHighestFraction();
            setBoard();

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {

        }
    }
}
