package com.example.mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class movie extends AppCompatActivity {
    private EditText moviewreview;
    private Button confirm;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    movieclass Movieclass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        moviewreview = findViewById(R.id.addreview);
        confirm = findViewById(R.id.reviewconfirm);

        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("Movie");
         Movieclass = new movieclass();

         confirm.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String Review = moviewreview.getText().toString();

                 if (TextUtils.isEmpty(Review) ){
                     Toast.makeText(movie.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                 } else {
                     addDatatoFirebase(Review);
                 }

             }
         });


    }
    private void addDatatoFirebase ( String Review){
        Movieclass.setMoviewreview(Review);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                databaseReference.child("-MkIhcvghnveftcABZMp").setValue(Review);

                Toast.makeText(movie.this, "data added", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(movie.this, "Fail to add data "  , Toast.LENGTH_SHORT).show();

            }
        });

    }


}