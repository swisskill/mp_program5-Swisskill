package com.example.wargame;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
    ImageView turnDisplay;
    Paint myPaint;
    ImageView grid;
    Bitmap bitmap;
    TextView textView;
    Chip reset;
    int xoro = 1; //where x is 1 and o is -1
    int gameOver = 0; //counting turns means we don't have to check for ties with nested loops
    int[][] gridMatrix = new int[3][3];
    //----------------------------------------------------------------------------------------------
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_wargame, container, false);
        Objects.requireNonNull(((MainActivity) requireActivity()).getSupportActionBar()).hide();//get rid of toolbar
        //-----------------------------Nasty declarations-------------------------------------------
        turnDisplay= myView.findViewById(R.id.imageView1);
        textView = myView.findViewById(R.id.textView);
        reset=myView.findViewById(R.id.chip4);
        grid = myView.findViewById(R.id.imageView);
        //----------------canvas stuff. who knows if it works?
        myPaint = new Paint();
//        myPaint.setStyle(Paint.Style.STROKE);
        bitmap = Bitmap.createBitmap(4500, 4800, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Bitmap bgrid = BitmapFactory.decodeResource(getResources(), R.drawable.ttt);
        canvas.drawBitmap(bgrid, 0, 0, myPaint);
        grid.setImageBitmap(bitmap);
        //------------------------------------------------------------------------------------------
        grid.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view,  MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_UP) {
                    view.performClick();
                    float getX = motionEvent.getX();
                    float getY = motionEvent.getY();
                    boolean check;
                    placeSymbol(getX, getY);
                    check = checkWin();
                    gameOver++;
                    if (check) {
                        if (xoro == 1) {
                            textView.setText(R.string.x_wins);
                        } else {
                            textView.setText(R.string.o_wins);
                        }
                        turnDisplay.setImageResource(0);
                        gameOver = 9; //WHY? it just signals game over
                    } else if ((!check) && (gameOver == 9)) { //can i simplify?
                        textView.setText(R.string.draw);
                        turnDisplay.setImageResource(0);
                    } else{turner();}

                }
                return false;
                }
            });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Reset", "");
                xoro = 1;
                gameOver = 0;
                turnDisplay.setImageResource(R.drawable.x);
                textView.setText("");
                gridMatrix = newInstance().gridMatrix;
            }
        });

        return myView;
    }


    public void placeSymbol(float getX, float getY){
        //TODO:do something in place symbol to make sure we don't overwrite anything. we could very well
        //not change it and subtract the game counter by 1 to pretend like nothing happened.
        int col;
        int row;
        col = matrixFind(getX);
        row = matrixFind(getY);
        if(xoro == 1){
            //draw x in col and row
        } else {
            //draw o in col and row
        }
        gridMatrix[row][col] = xoro;
        Log.wtf("row and column", "" + row + " "+col);
        Log.wtf("wb", ""+gridMatrix[row][0]+","+gridMatrix[row][1]+","+gridMatrix[row][2]);
        //Log.wtf("grid[][] ", String.valueOf(gridMatrix[row][col]));
    }
    public int matrixFind(float getX){
        int cor;
        if(getX <=250){cor=0;} //who knows, might be bugged
        else if(getX<=500){cor=1;}
        else{cor=2;}
        return cor;
    }
    public boolean checkWin(){ //this function scares me for the chance of bugs
        boolean win = false;
        int winner;
        winner = gridMatrix[0][0] + gridMatrix[1][1]+gridMatrix[2][2];
        win = checkCount(winner);
        if(!win){
            winner = gridMatrix[0][2] + gridMatrix[1][1]+gridMatrix[2][0];
            win = checkCount(winner);
        }
        //two individual loops for row and col are faster than a nested loop. Big O issue
        if(!win){
        for(int i = 0; i <= 2; i++){ //rows
            winner = gridMatrix[i][0] + gridMatrix[i][1] + gridMatrix[i][2];
            win = checkCount(winner);
            if(win){break;}
        }}
        if(!win){
        for(int j = 0; j <= 2; j++){//columns
            winner = gridMatrix[0][j] + gridMatrix[1][j] + gridMatrix[2][j];
            Log.d("", ""+winner);
            win = checkCount(winner);
            if(win){break;}
        }}
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