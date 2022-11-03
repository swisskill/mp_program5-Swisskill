package com.example.wargame;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class wargame extends Fragment {
    //---------------------------Declarations-------------------------------------------------------
    public static wargame newInstance() {
        return new wargame();
    } //CONSTRUCTOR
    ImageView xoro; //aka X or O; pronounced "zoro"
    ImageView empty;
    Chip reset;
    boolean turn = true; //where x is true and o is false; always start on x
    int[][] gridMatrix = new int[3][3];
    //----------------------------------------------------------------------------------------------
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_wargame, container, false);
        Objects.requireNonNull(((MainActivity) requireActivity()).getSupportActionBar()).hide();//get rid of toolbar
        xoro= myView.findViewById(R.id.imageView1);
        reset=myView.findViewById(R.id.chip4);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //reset the canvas and the matrix
            }
        });
        //upon click:
            //draw image
            //turner();

        return myView;
    }
    public void turner(){
        if (turn) {  //if x
            turn = false;
            xoro.setImageResource(R.drawable.o);
        } else {
            turn = true;
            xoro.setImageResource(R.drawable.x);

        }
    }
}


/*
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
 */