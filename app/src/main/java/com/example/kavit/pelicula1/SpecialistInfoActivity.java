package com.example.kavit.pelicula1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SpecialistInfoActivity extends AppCompatActivity {

    private Bundle prevExtras;
    private String name, email, loc, kind;
    private TextView sname, semail, sloc, skind;
;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialist_info);

        prevExtras = getIntent().getExtras();
        if(prevExtras == null) {  return;  }
        name = prevExtras.getString("name");
        sname= (TextView)findViewById(R.id.sName);
        sname.setText(name);

        email = prevExtras.getString("email");
        semail = (TextView)findViewById(R.id.sEmail);
        semail.setText(email);

        loc = prevExtras.getString("loc");
        sloc = (TextView)findViewById(R.id.sLoc);
        sloc.setText(loc);

        kind = prevExtras.getString("design");
        skind = (TextView)findViewById(R.id.sKind);
        skind.setText(kind);

    }
}
