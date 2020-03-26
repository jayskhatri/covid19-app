package com.jayskhatri.covid19_track.ui.corona_awareness;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.jayskhatri.covid19_track.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.Objects;

public class CovidAwarenessFragment extends Fragment {

//    private CarouselView carouselView;
//    private int[] sampleImages = {R.drawable.awareness1, R.drawable.awareness2, R.drawable.awareness3};

//    private ImageListener imageListener = (position, imageView) -> imageView.setImageResource(sampleImages[position]);

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_corona_awareness, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        carouselView = (CarouselView) Objects.requireNonNull(getActivity()).findViewById(R.id.carouselView);
//        carouselView.setImageListener(imageListener);
//        carouselView.setPageCount(sampleImages.length);

    }
}
