package com.simplisafe.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[4][4];

    private boolean player1Turn = true;

    private int roundCount;

    private int player1Points;
    private int player2Points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = findViewById(R.id.tv_play1);
        textViewPlayer2 = findViewById(R.id.tv_play2);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.b_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBoard();
                player1Points = 0;
                player2Points = 0;
                updatePointsText();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (player1Turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }

        roundCount++;

        if (checkForWin(4)) {
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 16) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }

    }

    //algorithm goes here
    private boolean checkForWin(int count) {
        String[][] field = new String[count][count];
        boolean isWon = false;
        int cValue = count-1;

        //Add the values
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        //check for horizontal
        for (int i = 0; i < count; i++) {
            if(!field[i][0].equals("")){
                for(int j= 1; j<count ; j++){
                    isWon = field[i][0].equals(field[i][j]);
                    if(!isWon){
                        break;
                    }
                }
                if(isWon){
                    return true;
                }

            }
        }

        //check for vertical
        for (int i = 0; i < count; i++) {
            if (!field[0][i].equals("")) {
                for(int j= 1; j<count ; j++){
                    isWon = field[0][i].equals(field[j][i]);
                    if(!isWon){
                        break;
                    }
                }
                if(isWon){
                    return true;
                }
            }
        }

        //check for diagonal
        if (!field[0][0].equals("")) {
            for(int j= 1; j<count ; j++){
                isWon = field[0][0].equals(field[j][j]);
                if(!isWon){
                    break;
                }
            }
            if(isWon){
                return true;
            }
        }

        //check for diagonal
        if (!field[0][cValue].equals("")) {
            for(int j= 1; j<count ; j++){
                isWon = field[0][cValue].equals(field[j][cValue- j]);
                if(!isWon){
                    break;
                }
            }
            if(isWon){
                return true;
            }
        }

        return false;
    }

    private void player1Wins() {
        player1Points++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void player2Wins() {
        player2Points++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText() {
        textViewPlayer1.setText("Player 1: " + player1Points);
        textViewPlayer2.setText("Player 2: " + player2Points);
    }

    private void resetBoard() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        player1Turn = true;
    }
}
