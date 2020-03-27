package com.jayskhatri.covid19_track.ui.home;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jayskhatri.covid19_track.R;
import com.jayskhatri.covid19_track.api.HttpHandler;
import com.jayskhatri.covid19_track.object.StatewiseEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class NationalFragment extends Fragment {

    private String TAG = NationalFragment.class.getSimpleName();
    private TextView totalConfirmedCount;
    private TextView totalRecoveredCount;
    private TextView totalActiveCount;
    private TextView totalDeathCount;

    public NationalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_national, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }



}
