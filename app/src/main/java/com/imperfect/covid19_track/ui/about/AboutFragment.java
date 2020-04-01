package com.imperfect.covid19_track.ui.about;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.imperfect.covid19_track.R;

public class AboutFragment extends Fragment {

    private Button btnCovid19, btnZoomage, btnMailDev;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().findViewById(R.id.fab).setVisibility(View.INVISIBLE);
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnCovid19 = (Button) getActivity().findViewById(R.id.btn_covid19india);
//        btnMailDev = (Button) getActivity().findViewById(R.id.btn_mail_dev);
        btnZoomage = (Button) getActivity().findViewById(R.id.btn_zoomage);

//        btnMailDev.setOnClickListener(view -> mailDev(""));
        btnCovid19.setOnClickListener(view -> openURL("https://github.com/covid19india"));
        btnZoomage.setOnClickListener(view -> openURL("https://github.com/jsibbold/zoomage"));
    }

    private void mailDev(String email){
        final Intent send = new Intent(Intent.ACTION_SENDTO);
        final String uriText = "mailto:" + Uri.encode(email);
        final Uri uri = Uri.parse(uriText);
        send.setData(uri);
        startActivity(Intent.createChooser(send,"Send Email"));
    }

    private void openURL(String url){
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setPackage("com.android.chrome");
        try {
            startActivity(i);
        } catch (ActivityNotFoundException e) {
            // Chrome is probably not installed
            // Try with the default browser
            i.setPackage(null);
            startActivity(i);
        }
    }
}
