package com.wisielec;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    ListPicker ListPicker = new ListPicker();
    private boolean Playable;
    private int Lives = 7;
    private String filepath = ListPicker.filepath;
    private String MainTextString;
    private int ScoreCount = 0;
    private int HiScore = 0;
    String ScoreCountString = String.valueOf(ScoreCount);


    @FXML
    private Text AlfabetText;
    @FXML
    private Text MainText;
    @FXML
    private Text Score;
    @FXML
    private Text CurrentScore;
    @FXML
    private Text EndText;
    @FXML
    private Button NewGame;
    @FXML
    private Text TheWordWas;
    @FXML
    private Button choosewordlist;
    @FXML
    private Button HintButton;
    @FXML
    private AnchorPane Background;



    public List<Character> playerGuesses = new ArrayList<>();
    private String AlfabetPrzed = "";



    @FXML
    private Circle head;

    @FXML
    private Line torso;

    @FXML
    private Line arm1 = new Line();

    @FXML
    private Line arm2;

    @FXML
    private Line leg1;

    @FXML
    private Line leg2;
    @FXML
    private Line rope;

    @FXML
    private ChoiceBox<String> DifficultyChoiceBox;
    private String[] difficulties = {"Easy", "Normal", "Hard"};
    Random rand = new Random();
    char randomletter;




    @FXML
    protected void onHintButtonClick(){

        boolean isItDone = false;

        do {
            randomletter = MainTextString.charAt(rand.nextInt(MainTextString.length()));

            if (!playerGuesses.contains(randomletter)) {

                playerGuesses.add(randomletter);
                changeMainText();
                HintButton.setDisable(true);
                Background.requestFocus();
                isItDone = true;

            }

        }
        while (isItDone == false);

    }

    @FXML
    protected void onNewGameButtonClick(){
        try {
            MainTextString = ListPicker.returnword(filepath).toUpperCase();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (DifficultyChoiceBox.getValue() == "Hard"){
            HintButton.setDisable(true);
        } else
            HintButton.setDisable(false);

        MainText.setText(MainTextString);
        playerGuesses.clear();
        AlfabetText.setText("A B C D E F G H I J K L M N O P Q R S T U V W X Y Z");
        Lives = 7;
        SetScoreText();
        EndText.setText("");
        NewGame.setDisable(true);
        TheWordWas.setText("");
        changeMainText();
        SetHangmanInvisible();
        choosewordlist.setDisable(true);
        DifficultyChoiceBox.setDisable(true);
        Background.requestFocus();
        System.out.println(MainTextString);




        Playable = true;
    }





    @FXML
    protected void onTyped(KeyEvent event) {

        if(Playable == true){


            char pressed = event.getText().toUpperCase().charAt(0);

            if (pressed >= 'A' || pressed <= 'Z') {

                if (playerGuesses.contains(pressed)) {

                return;

            }
            else {

                playerGuesses.add(pressed);

            }



        }

        if (MainTextString.indexOf(pressed) == -1) {

            RemoveLife();

        }

        changeAlfabetText();
        changeMainText();

        }
        else return;


    }

    private void changeAlfabetText() {
        for (int i = 0; i < AlfabetText.getText().length(); i++) {
            if (playerGuesses.contains(AlfabetText.getText().charAt(i))) {

                AlfabetPrzed = AlfabetPrzed + "_";

            } else {
                AlfabetPrzed = AlfabetPrzed + AlfabetText.getText().charAt(i);


            }

        }

        AlfabetText.setText(AlfabetPrzed);
        AlfabetPrzed = "";
    }

    private String TextPrzed = "";
    private void changeMainText() {

        for (int i = 0; i < MainText.getText().length(); i++) {
            if (playerGuesses.contains(MainTextString.charAt(i))) {

                TextPrzed = TextPrzed + MainTextString.charAt(i);

            }
            else TextPrzed = TextPrzed + "-";

        }
        MainText.setText(TextPrzed);
        if (TextPrzed.equals(MainTextString)){
            GameWon();
        }
        if (Lives == 0){
            GameLost(DifficultyChoiceBox.getValue());

        }
        TextPrzed = "";


    }


    public void RemoveLife() {

        Lives = Lives - 1;
        if (Lives == 6)
            rope.setVisible(true);
        else if (Lives == 5)
            head.setVisible(true);
        else if (Lives == 4)
            torso.setVisible(true);
        else if (Lives == 3)
            arm1.setVisible(true);
        else if (Lives == 2)
            arm2.setVisible(true);
        else if (Lives == 1)
            leg1.setVisible(true);
        else if (Lives == 0)
            leg2.setVisible(true);

    }


    protected void SetScoreText() {

        String HiScoreString = String.valueOf(HiScore);
        Score.setText(HiScoreString);

    }

        protected void GameWon() {

        EndText.setText("You won!");
        EndText.setFill(Color.LIGHTBLUE);
        Playable = false;
        NewGame.setText("Next word");
        NewGame.setDisable(false);
        ScoreCount++;
        if (ScoreCount > HiScore){
            HiScore = ScoreCount;
        }

        SetScoreText();
        ScoreCountString = String.valueOf(ScoreCount);
        CurrentScore.setText(ScoreCountString);
        NewGame.requestFocus();


    }

    protected void GameLost(String difficulty){

        if (difficulty == "Easy"){

        if(ScoreCount < 2) {

            EndText.setText("You lost!");
            EndText.setStyle("-fx-font-size: 54");
        EndText.setFill(Color.RED);
        Playable = false;
        NewGame.setDisable(false);
        NewGame.setText("New Game");
        NewGame.requestFocus();
        MainText.setText(MainTextString);
        TheWordWas.setText("The word was:");
        ScoreCount = 0;
        choosewordlist.setDisable(false);
        DifficultyChoiceBox.setDisable(false);
        ScoreCountString = String.valueOf(ScoreCount);
        CurrentScore.setText(ScoreCountString);
        }
        else {

            EndText.setStyle("-fx-font-size: 30");
            EndText.setText("You lost a point!");
            EndText.setFill(Color.PINK);
            Playable = false;
            NewGame.setDisable(false);
            NewGame.setText("Next word");
            NewGame.requestFocus();
            MainText.setText(MainTextString);
            TheWordWas.setText("The word was:");
            ScoreCount--;
            choosewordlist.setDisable(true);
            DifficultyChoiceBox.setDisable(true);
            ScoreCountString = String.valueOf(ScoreCount);
            CurrentScore.setText(ScoreCountString);
        }
        } else if (difficulty == "Normal") {

            EndText.setText("You lost!");
            EndText.setStyle("-fx-font-size: 54");
            EndText.setFill(Color.RED);
            Playable = false;
            NewGame.setDisable(false);
            NewGame.setText("New Game");
            NewGame.requestFocus();
            MainText.setText(MainTextString);
            TheWordWas.setText("The word was:");
            ScoreCount = 0;
            choosewordlist.setDisable(false);
            DifficultyChoiceBox.setDisable(false);
            ScoreCountString = String.valueOf(ScoreCount);
            CurrentScore.setText(ScoreCountString);

        } else {

            EndText.setText("You lost!");
            EndText.setStyle("-fx-font-size: 54");
            EndText.setFill(Color.RED);
            Playable = false;
            NewGame.setDisable(false);
            NewGame.setText("New Game");
            NewGame.requestFocus();
            MainText.setText(MainTextString);
            TheWordWas.setText("The word was:");
            ScoreCount = 0;
            choosewordlist.setDisable(false);
            DifficultyChoiceBox.setDisable(false);
            ScoreCountString = String.valueOf(ScoreCount);
            CurrentScore.setText(ScoreCountString);



        }


    }



    FileChooser fileChooser = new FileChooser();
    @FXML
    void getList(MouseEvent event){
        File selectedFile = fileChooser.showOpenDialog(null);
        filepath = selectedFile.getPath();

    }
    protected void SetHangmanInvisible(){
        rope.setVisible(false);
        head.setVisible(false);
        torso.setVisible(false);
        arm1.setVisible(false);
        arm2.setVisible(false);
        leg1.setVisible(false);
        leg2.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fileChooser.setInitialDirectory(new File("src\\main\\resources\\WordLists"));

        SetHangmanInvisible();
        DifficultyChoiceBox.getItems().addAll(difficulties);
        DifficultyChoiceBox.setValue("Normal");

    }
}