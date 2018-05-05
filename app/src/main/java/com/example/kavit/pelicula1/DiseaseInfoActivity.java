package com.example.kavit.pelicula1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.ArrayList;

public class DiseaseInfoActivity extends AppCompatActivity {

    private Bundle prevExtras;
    private String name, desc, symptoms, specialist,kind;
    private TextView dname, ddesc, dsym, dspec;

    private FirebaseFirestore db;
    private CollectionReference mDiseaseCollectionReference;
    private DocumentReference mDiseaseDocumentReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disease_info);


        prevExtras = getIntent().getExtras();
        if(prevExtras == null) {  return;  }
        name = prevExtras.getString("name");
        dname= (TextView)findViewById(R.id.dName);
        dname.setText(name);

        desc = prevExtras.getString("desc");
        ddesc= (TextView)findViewById(R.id.dInfo);
        ddesc.setText(desc);

        specialist= prevExtras.getString("specialist");
        specialist = specialist.replace('[', ' ');

        specialist = specialist.replace(']', ' ');
        //specialist = specialist.replace(']', ',');
        dspec= (TextView)findViewById(R.id.dSpec);
        dspec.setText(specialist);

        kind = prevExtras.getString("kind");


        dspec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(DiseaseInfoActivity.this, SpecialistActivity.class);

                myIntent.putExtra("kind", kind);
                startActivity(myIntent);
            }
        });

    }

}
