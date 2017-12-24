package com.example.natthamondumrongkaviroyaphan.mytransferpatient;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CreateCircleFragment extends Fragment {

    public CreateCircleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.create_circle, container, false);

        return rootView;
    }

}