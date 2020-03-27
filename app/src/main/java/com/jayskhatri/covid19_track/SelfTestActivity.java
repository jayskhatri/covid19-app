package com.jayskhatri.covid19_track;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SelfTestActivity extends AppCompatActivity {

    private String TAG = SelfTestActivity.class.getSimpleName();
    private int[] queImageList = new int[]{
            R.drawable.cough1,
            R.drawable.cold2,
            R.drawable.diarrhea3,
            R.drawable.sorethroat4,
            R.drawable.aches5,
            R.drawable.headache6,
            R.drawable.fever7,
            R.drawable.difficultybreathing8,
            R.drawable.fatigue9,
            R.drawable.aeroplane10,
            R.drawable.covid11,
            R.drawable.direct12
    };
    private int[] queList = new int[]{
            R.string.q1,
            R.string.q2,
            R.string.q3,
            R.string.q4,
            R.string.q5,
            R.string.q6,
            R.string.q7,
            R.string.q8,
            R.string.q9,
            R.string.q10,
            R.string.q11,
            R.string.q12,
    };
    private int[] scorelist = {1,1,1,1,1,1,1,2,2,3,3,3};
    private  int currentQue = 0;
    private ImageView testQueImage;
    private TextView question, ans;
    private Button btnYes, btnNo;
    private int totalScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_test);

        getSupportActionBar().setTitle("Self Test");
        testQueImage = findViewById(R.id.image_view_test);
        question = findViewById(R.id.text_view_question);
        ans = findViewById(R.id.text_view_ans);
        btnYes = findViewById(R.id.btn_yes);
        btnNo = findViewById(R.id.btn_no);
        question.setText(queList[currentQue]);
        testQueImage.setImageResource(queImageList[currentQue]);
        btnYes.setOnClickListener(view -> addScore(currentQue));
        btnNo.setOnClickListener(view -> nextQuestion(currentQue));
    }

    private void addScore(int currentPos){
        try {
            if (currentPos < 12) {
                Log.e(TAG, "addScore: " + currentPos);
                totalScore += scorelist[currentPos];
                nextQuestion(currentPos);
            } else if (currentPos == 12) {
                Intent i = new Intent(SelfTestActivity.this, AdviceActivity.class);
                i.putExtra("TOTAL_SCORE", totalScore);
                startActivity(i);
                finish();
            }
        } catch (Exception e){
            Log.e(TAG, "addScore: "+e.getMessage());
        }
    }

    public void nextQuestion(int currentPos){
        try {
            if (currentPos < 12) {
                Log.e(TAG, "nextQuestion: currentPos: " + currentPos);
                currentQue = currentPos + 1;
                testQueImage.setImageResource(queImageList[currentQue]);
                question.setText(queList[currentQue]);
                Log.e(TAG, "total Score: " + totalScore);
            } else if (currentPos == 12) {
                Intent i = new Intent(SelfTestActivity.this, AdviceActivity.class);
                i.putExtra("TOTAL_SCORE", totalScore);
                startActivity(i);
                finish();
            }
        } catch (Exception e){
            Log.e(TAG, "nextQuestion: " + e.getMessage());
        }

    }
}
