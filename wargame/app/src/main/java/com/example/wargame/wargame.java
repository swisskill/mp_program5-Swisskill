package com.example.wargame;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class wargame extends Fragment {
    //---------------------------Declarations-------------------------------------------------------
    public static wargame newInstance() {
        return new wargame();
    } //CONSTRUCTOR
    ImageView turnDisplay; //aka X or O; pronounced "zoro"
    TextView textView;
    Chip reset;
    int xoro = 1; //where x is 1 and o is -1
    int turnCounter = 0; //counting turns means we don't have to check for ties with nested loops
    int[][] gridMatrix = new int[3][3];
    //----------------------------------------------------------------------------------------------
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_wargame, container, false);
        Objects.requireNonNull(((MainActivity) requireActivity()).getSupportActionBar()).hide();//get rid of toolbar
        turnDisplay= myView.findViewById(R.id.imageView1);
        textView = myView.findViewById(R.id.textView);
        reset=myView.findViewById(R.id.chip4);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnDisplay.setImageResource(R.drawable.x);
                textView.setText("");
                gridMatrix = newInstance().gridMatrix;
                        
            }
        });
        //upon click:
//            boolean check;
//            //placeSymbol();
//            check = checkWin();
//            turnCounter++;
//            if(check){
//              textView.setText(R.string.x_wins);
//              turnDisplay.setImageResource(R.drawable.x);
//              //display game over and allow no more touches or something
//            }else if (!check && (turnCounter == 9))
//            turner();

        return myView;
    }


    public void placeSymbol(){
        if(xoro == 1){
            //draw x where it should be
        } else {
            //draw o where it should be
        }
        //TODO: Implement matrix tracker
        int col;
        int row;
        /*
        whatever row was clicked, set row = rowClicked (int from 0-2)
        whatever col was clicked, set col = colClicked (int from 0-2)
        gridMatrix[row][col] = xoro //i believe this is the layout of the matrix

        switch (placeClicked){ //use this if necessary
            case 0<rowClicked<1:
                row = 1;
        }
         */
    }
    public boolean checkWin(){ //this function scares me for the chance of bugs
        boolean win = false;
        //two individual loops for row and col are faster than a nested loop. Big O issue
        for(int i = 0; i < 2; i++){
            int winner = gridMatrix[i][0] + gridMatrix[i][1] + gridMatrix[i][2];
            win = checkCount(winner);
            if(win){break;}
        }
        for(int j = 0; j < 2; j++){
            int winner = gridMatrix[0][j] + gridMatrix[1][j] + gridMatrix[2][j];
            win = checkCount(winner);
            if(win){break;}
        }
        return win;
    }
    public boolean checkCount(int winner){
        boolean win = false;
        if(winner == 3){
            win = true;
        } else if (winner == -3){
            win = true;
        }
        return win;
    }
    public void turner(){
        if (xoro == 1) {  //if x
            turnDisplay.setImageResource(R.drawable.o);
            xoro = -1;
        } else {
            turnDisplay.setImageResource(R.drawable.x);
            xoro = 1;
        }
    }
}


/*
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
 */