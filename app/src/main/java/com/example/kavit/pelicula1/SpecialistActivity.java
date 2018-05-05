package com.example.kavit.pelicula1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class SpecialistActivity extends AppCompatActivity {

    private ListView mSpecialistListView;
    private ArrayList<Specialist> mSpecialistList;
    private SpecialistAdapter mSpecialistAdapter;
    private FirebaseFirestore db;
    private CollectionReference mSpecialistCollectionReference;
    private DocumentReference mSpecialistDocumentReference;
    private Button analyze;
    private Bundle prevExtras;
    private final String TAG = "SpecialistActivity";
    private String spec; // spec stores the value of the kind of doc

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialist);
        prevExtras = getIntent().getExtras();

        spec = prevExtras.getString("kind");

        mSpecialistListView = (ListView) findViewById(R.id.SpList);
        setup();

        getAllSpecialists();


        mSpecialistListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                TextView textView=((view).findViewById(R.id.specialistTextView));
                String item = textView.getText().toString();

                Intent myIntent = new Intent(SpecialistActivity.this, SpecialistInfoActivity.class);

                //search in diseasse list
                for(int i = 0 ; i < mSpecialistList.size(); i++){
                    if(mSpecialistList.get(i).getName() == item){
                        myIntent.putExtra("design", mSpecialistList.get(i).getKind());
                        myIntent.putExtra("loc", mSpecialistList.get(i).getLoc());
                        Log.e("in click", mSpecialistList.get(i).getLoc());
                        myIntent.putExtra("email", mSpecialistList.get(i).getEmail());
                    }
                }

                Log.e("in click", item);

                //intent
                myIntent.putExtra("name", item);

                startActivity(myIntent);
            }
        });


    }

    public void setup() {
        // [START get_firestore_instance]
        db = FirebaseFirestore.getInstance();
        // [END get_firestore_instance]

        // [START set_firestore_settings]
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);
        // [END set_firestore_settings]
    }

    public void getAllSpecialists() {
        db.collection("specialists").whereEqualTo("kind",spec)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            mSpecialistList = new ArrayList<Specialist>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Specialists", document.getId() + " => " + document.getData().get("name"));
                                mSpecialistList.add(document.toObject(Specialist.class));
                            }

                            mSpecialistAdapter = new SpecialistAdapter(SpecialistActivity.this, R.layout.activity_item_specialist, mSpecialistList);
                            mSpecialistListView.setAdapter(mSpecialistAdapter);

                        } else {
                            Log.w("Specialists", "Error getting documents.", task.getException());
                        }
                    }
                    // [END get_all_users]
                });
    }


    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(SpecialistActivity.this, MainActivity.class);
        //Optional parameters
        startActivity(myIntent);
    }
}


