package com.example.kavit.pelicula1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by kavit on 30-03-2018.
 */

public class thirdFragment extends Fragment {
    View v;
    ImageView i;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        Button bc= (Button) view.findViewById(R.id.bc);
        bc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }


}
