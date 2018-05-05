package com.example.kavit.pelicula1;

import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DiseasesActivity extends AppCompatActivity {

    private ListView mDiseaseListView;
    private ArrayList<Disease> mDiseaseList;
    private ArrayList<Symptom> mSymptomList;
    private DiseaseAdapter mDiseaseAdapter;
    private FirebaseFirestore db;
    private CollectionReference mDiseaseCollectionReference;
    private DocumentReference mDiseaseDocumentReference;
    private Bundle prevExtras;
    private String tempID = "abc";
    private Disease tempDisease;
    private Boolean flag = false;
    private Button analyze;
    private final String TAG = "DiseaseActivity";
    private TextView dText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diseases);
        mDiseaseListView = (ListView) findViewById(R.id.disList);
        prevExtras = getIntent().getExtras();
        if(prevExtras == null) {  return;  }
        mSymptomList =  (ArrayList<Symptom>) getIntent().getSerializableExtra("SelectedSymptoms");

        setup();

        mDiseaseCollectionReference = db.collection("disease");
        mDiseaseList = new ArrayList<>();

        try {
            getDiseasesID(mSymptomList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //getAllDiseases();

        mDiseaseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                TextView textView=((view).findViewById(R.id.diseaseTextView));
                String item = textView.getText().toString();

                Intent myIntent = new Intent(DiseasesActivity.this, DiseaseInfoActivity.class);

                //search in diseasse list
                for(int i = 0 ; i < mDiseaseList.size(); i++){
                    if(mDiseaseList.get(i).getName() == item){
                        myIntent.putExtra("desc", mDiseaseList.get(i).getDesc());
                        myIntent.putExtra("specialist", mDiseaseList.get(i).getSpecialists().toString());
                        myIntent.putExtra("kind",mDiseaseList.get(i).getSpecialists().get(0));
                        Log.e("in click", mDiseaseList.get(i).getSpecialists().toString());
                     //   myIntent.putExtra("desc", mDiseaseList.get(i).getDesc());
                    }
                }

                Log.e("in click", item);

                //intent daal bro
               // Intent myIntent = new Intent(DiseasesActivity.this, DiseaseInfoActivity.class);
                //Optional parameters
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

    public void getDiseasesID(ArrayList<Symptom> symptomArrayList) throws InterruptedException {

        tempDisease = new Disease();

        for (int i = 0; i < symptomArrayList.size(); i++) {
            for (int j = 0; j < symptomArrayList.get(i).getD_id().size(); j++) {

                tempID = symptomArrayList.get(i).getD_id().get(j);
                db.collection("disease").document(tempID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            tempDisease = documentSnapshot.toObject(Disease.class);
                            Log.e("tempDidease", tempDisease.getName());

                            tempDisease.setID(documentSnapshot.getId());

                            if (mDiseaseList.isEmpty()) {
                                mDiseaseList.add(tempDisease);
                            }
                            if (!mDiseaseList.isEmpty()) {

                                for (int k = 0; k < mDiseaseList.size(); k++) {
                                    if (tempDisease.getID() == mDiseaseList.get(k).getID()) {
                                        Log.e("tempD.ID", tempDisease.getID());
                                        Log.e("mDL.ID", mDiseaseList.get(k).getID());
                                        mDiseaseList.get(k).setCount(mDiseaseList.get(k).getCount() + 1);
                                        flag = true;
                                        break;
                                    }
                                }

                                if (flag == false) {
                                    mDiseaseList.add(tempDisease);
                                }

                            }

                            Log.e("list", String.valueOf(mDiseaseList.size()) + "  " + mDiseaseList.get(0).getName());

                            flag = false;

                            getDiseases();

                        } else {
                            Log.w("Symptoms", "Error getting documents.", task.getException());
                        }
                    }
                    // [END get_all_users]
                });

                Log.e("outsideOnComplete", mDiseaseList.toString());


            }
        }

        Log.e(TAG, "outsideFOR");
        Log.e(TAG, mDiseaseList.toString());
        Log.e(TAG, "afterPrinting diseaselist");
        //getDiseases();
        //  mDiseaseAdapter = new DiseaseAdapter(DiseasesActivity.this , R.layout.activity_item_disease, mDiseaseList);
        //mDiseaseListView.setAdapter(mDiseaseAdapter);

    }

    public void getDiseases(){

          mDiseaseAdapter = new DiseaseAdapter(DiseasesActivity.this , R.layout.activity_item_disease, mDiseaseList);
        mDiseaseListView.setAdapter(mDiseaseAdapter);
    }

    public void getAllDiseases() {
        db.collection("disease")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d("Diseases", document.getId() + " => " + document.getData().get("name"));
                                mDiseaseList.add(document.toObject(Disease.class));
                            }
                            mDiseaseAdapter = new DiseaseAdapter(DiseasesActivity.this , R.layout.activity_item_disease, mDiseaseList);
                            mDiseaseListView.setAdapter(mDiseaseAdapter);

                        } else {
                            Log.w("Diseases", "Error getting documents.", task.getException());
                        }
                    }
                    // [END get_all_users]
                });
    }



    @Override
    public void onBackPressed() {
        Intent myIntent = new Intent(DiseasesActivity.this, MainActivity1.class);
        //Optional parameters
        startActivity(myIntent);
    }
}