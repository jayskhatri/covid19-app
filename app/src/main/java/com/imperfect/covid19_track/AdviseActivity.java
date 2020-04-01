package com.imperfect.covid19_track;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private AdView mAdView;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String showadd;

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


        //ads
        mAdView = findViewById(R.id.adView_advise);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("ads");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                showadd = value;
                if(showadd !=null)
                    if(showadd.equals("yes")){
                        MobileAds.initialize(getApplicationContext(), initializationStatus -> {
                        });
                        mAdView.setVisibility(View.VISIBLE);
                        AdRequest adRequest = new AdRequest.Builder().build();
                        mAdView.loadAd(adRequest);
                    }else{
                        mAdView.setVisibility(View.GONE);
                    }

//                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
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
