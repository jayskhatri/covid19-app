package com.jayskhatri.covid19_track;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class AdviseActivity extends AppCompatActivity {

    private int totalScore;
    private TextView advice;
    private Button callHelp, goBack;
    private ImageView testResult;
    private int[] adviseImageList = new int[]{
        R.drawable.cover_pic,
        R.drawable.stress,
        R.drawable.hydrate,
        R.drawable.consultdr,
        R.drawable.urgent_call
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advise);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Advise");

        advice = findViewById(R.id.text_view_advise);
        goBack = findViewById(R.id.btn_go_back);
        callHelp = findViewById(R.id.btn_call_helpline);

        testResult = findViewById(R.id.image_view_ad_test);
        testResult.setImageResource(R.drawable.cover_pic);
        goBack.setOnClickListener(view -> onBackPressed());
        totalScore = getIntent().getIntExtra("TOTAL_SCORE", 24);
        init(totalScore);
    }

    private void init(int total){
        if (total ==0){
            testResult.setImageResource(adviseImageList[0]);
            advice.setText(R.string.advice_zero);
        }
        else if(total>=1 && total <=2){
            testResult.setImageResource(adviseImageList[1]);
            advice.setText(R.string.advice_two);
        }
        else if(total>=3 && total<=5){
            testResult.setImageResource(adviseImageList[2]);
            advice.setText(R.string.advice_five);
        }
        else if(total>=6 && total <=12){
            testResult.setImageResource(adviseImageList[3]);
            advice.setText(R.string.advice_twelve);
        }
        else if(total>=13 && total<=24){
            testResult.setImageResource(adviseImageList[4]);
            advice.setText(R.string.advice_twenty_four);
            callHelp.setVisibility(View.VISIBLE);
            callHelp.setOnClickListener(view -> callHelp("104"));
        }
    }
    private void callHelp(String num){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+num));
        startActivity(intent);
    }
}
