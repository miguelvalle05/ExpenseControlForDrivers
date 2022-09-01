package com.example.traz.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.traz.R;

public class HomeFragment extends Fragment {
    Animation anim1;

    ImageView imagen;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        imagen = root.findViewById(R.id.homeimagen);

        anim1 = AnimationUtils.loadAnimation(getContext(), R.anim.alpha);

        imagen.setAnimation(anim1);


        return root;
    }
}