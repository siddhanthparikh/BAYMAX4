package com.example.kavit.pelicula1;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SpecialistAdapter extends ArrayAdapter<Specialist> {
   public ArrayList<Specialist> selectedSpecialists;


    public SpecialistAdapter(Context context, int resource, ArrayList<Specialist> Specialists) {
        super(context, resource, Specialists);

        selectedSpecialists = new ArrayList<>();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.activity_item_specialist, parent, false);
        }

        TextView nameTextView = (TextView) convertView.findViewById(R.id.specialistTextView);


        final Specialist symptom = getItem(position);

        nameTextView.setText(symptom.getName());


        nameTextView.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //sent intent to the Specialist info send the name, desc, symptom, specialist

                //  Intent myIntent = new Intent(SpecialistsActivity, SpecialistInfoActivity.class);

                //myIntent.putExtra("SelectedSymptoms", mSelectedSymptoms);
                //startActivity(myIntent);
            }
        });


        return convertView;
    }
}
