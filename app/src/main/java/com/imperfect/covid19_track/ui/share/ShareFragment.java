package com.imperfect.covid19_track.ui.share;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.imperfect.covid19_track.BuildConfig;
import com.imperfect.covid19_track.R;

import java.util.Objects;

public class ShareFragment extends Fragment {

    private Button whatsappShare;
    private TextView otherOptions;
    private String shareMessage = "Hey I have been using this app for getting updates regarding covid19 in India, Download the application now.\n\n" + "bit.ly/covid19updatesindia";//"https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().findViewById(R.id.fab).setVisibility(View.INVISIBLE);
        return inflater.inflate(R.layout.fragment_share, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        whatsappShare = (Button) getActivity().findViewById(R.id.btn_share_on_whatsapp);
        otherOptions = (TextView) getActivity().findViewById(R.id.text_view_other_options);

        whatsappShare.setOnClickListener(this::onClickWhatsApp);
        otherOptions.setOnClickListener(this::shareApp);
    }

    private void onClickWhatsApp(View view) {

        PackageManager pm = Objects.requireNonNull(getActivity()).getPackageManager();
        try {

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");

            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp");

            waIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(waIntent, "Share with"));

        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(getActivity(), "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }

    }

    private void shareApp(View v){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,shareMessage);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}
