package com.example.wargame;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
    public static wargame newInstance() {return new wargame();} //CONSTRUCTOR
    ImageView turnDisplay;
    Paint myPaint;
    ImageView grid;
    Drawable drawable;
    Bitmap bitmap;
    TextView textView;
    int cWit;
    int cHit;
    Chip reset;
    int xoro = 1; //where x is 1 and o is -1
    int gameOver = 0; //turn counter
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
        myPaint = new Paint();
        bitmap = Bitmap.createBitmap(2059, 2371, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Bitmap bgrid = BitmapFactory.decodeResource(getResources(), R.drawable.ttt);
        canvas.drawBitmap(bgrid, 0, 0, myPaint);
        grid.setImageBitmap(bitmap);
        cWit = canvas.getWidth();
        cHit = canvas.getHeight();
        //------------------On touch listener for the grid------------------------------------------
        grid.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view,  MotionEvent motionEvent) {
                if((motionEvent.getAction()==MotionEvent.ACTION_UP)&&(gameOver != 9)) {
                    drawable = turnDisplay.getDrawable();
                    view.performClick();
                    float getX = motionEvent.getX();
                    float getY = motionEvent.getY();
                    boolean check;
                    placeSymbol(getX, getY, canvas);
                    check = checkWin();
                    gameOver++;
                    if (check) {
                        if (xoro == 1) {textView.setText(R.string.x_wins);}
                        else {textView.setText(R.string.o_wins);}
                        turnDisplay.setImageResource(0);
                        gameOver = 9;
                    } else if (gameOver == 9) {
                        textView.setText(R.string.draw);
                        turnDisplay.setImageResource(0);
                    } else{turner();}
                }
                return false;
                }
            });
        //-------------------listener for the reset button------------------------------------------
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Reset", "");
                xoro = 1;
                gameOver = 0;
                gridMatrix = newInstance().gridMatrix;
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                canvas.drawBitmap(bgrid, 0, 0, myPaint);
                turnDisplay.setImageResource(R.drawable.x);
                textView.setText("");
            }
        });

        return myView;
    }
    //-------------------Puts the X or O on the canvas and in the matrix----------------------------
    public void placeSymbol(float getX, float getY, Canvas canvas){
        int col;
        int row;
        col = matrixFind(getX, cWit/9);
        row = matrixFind(getY, cHit/9);
        Log.d("row + col: ", ""+row+","+col);
        if(gridMatrix[row][col] == 0) {
            gridMatrix[row][col] = xoro;
            if (xoro == 1) {
                drawable.setBounds((col*cWit/3),row*cHit/3,((col*cWit/3)+cWit/3),(row*cHit/3)+cHit/3);
                drawable.draw(canvas);
            } else {
                drawable.setBounds((col*cWit/3),row*cHit/3,((col*cWit/3)+cWit/3),(row*cHit/3)+cHit/3);
                drawable.draw(canvas);
            }
        } else {gameOver--;xoro = xoro*-1;}
    }
    //-----------called by placeSymbol. Determines the column and row-------------------------------
    public int matrixFind(float get, int dimension){
        int cor;
        Log.wtf("dim,get", ""+dimension + "," + get);
        if(get <=dimension){cor=0;}
        else if(get<=(dimension+dimension)){cor=1;}
        else{cor=2;}
        return cor;
    }
    //-----------Called in grid listener; checks win conditions on every click----------------------
    public boolean checkWin(){
        boolean win = false;
        int winner;
        winner = gridMatrix[0][0] + gridMatrix[1][1]+gridMatrix[2][2];//diagonal left to right
        win = checkCount(winner);
        if(!win){ //diagonal right to left
            winner = gridMatrix[0][2] + gridMatrix[1][1]+gridMatrix[2][0];
            win = checkCount(winner);
        }
        if(!win){ //two individual loops for row and col are faster than a nested loop. Big O issue
        for(int i = 0; i <= 2; i++){ //rows check
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
    //------Called by checkWin;Determines if winner is X or O---------------------------------------
    public boolean checkCount(int winner){
        boolean win = false;
        if(winner == 3){
            win = true;
        } else if (winner == -3){
            win = true;
        }
        return win;
    }
    //------Called by grid listener. Changes xoro and display---------------------------------------
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
