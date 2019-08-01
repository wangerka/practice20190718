package com.example.slideconflict;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageFragment extends Fragment {
    public static int[] imagesId = {R.drawable.one,
            R.drawable.two,
            R.drawable.three,
            R.drawable.four,
            R.drawable.five};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_fragment, null);
        ImageView iv = view.findViewById(R.id.image);
        iv.setBackgroundResource(imagesId[getArguments().getInt("index")]);
        return view;
    }


    public static ImageFragment newInstance(int imageIdIndex){
        ImageFragment fragment = new ImageFragment();
        Bundle b = new Bundle();
        b.putInt("index",imageIdIndex);
        fragment.setArguments(b);
        return fragment;
    }
}
