package com.example.kavit.pelicula1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by kavit on 30-03-2018.
 */

public class secondFragment extends Fragment {
    ImageView i;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

  /*  i = (ImageView) findViewById(R.id.assess);
        i.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent myIntent = new Intent(secondFragment.this, sort.class);
           /* myIntent.putExtra("key", value);*/ //Optional parameters
            //startActivity(myIntent);
}
