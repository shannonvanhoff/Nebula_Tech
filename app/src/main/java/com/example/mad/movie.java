package com.example.mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class movie extends AppCompatActivity {
    private EditText moviewreview;
    private Button confirm;
    RecyclerView Rreview;

    FirebaseDatabase firebaseDatabase,firebaseDatabase2;
    DatabaseReference databaseReference, databaseReferenceR;

    movieclass Movieclass;
    ArrayList <userreviews> list;
    movieadapter Movieadapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        moviewreview = findViewById(R.id.addreview);
        confirm = findViewById(R.id.reviewconfirm);
        Rreview = findViewById(R.id.commentview);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceR= firebaseDatabase.getReference("Book").child("-MkLSICLNkE3e7GEUhkf");
        Rreview.setHasFixedSize(true);
        Rreview.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        Movieadapter = new movieadapter(this, list);
        Rreview.setAdapter(Movieadapter);




        databaseReference = firebaseDatabase.getReference("Book").child("-MkLSICLNkE3e7GEUhkf").child("reviews");
         Movieclass = new movieclass();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    userreviews user = dataSnapshot1.getValue(userreviews.class);
                    list.add(user);
                }
                Movieadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
    private void addDatatoFirebase ( String Review) {
        String id= "id";
        Movieclass.setMoviewreview(Review);
        String randomkey = id+""+new Random().nextInt(1000);

        HashMap cmnt = new HashMap();
        cmnt.put("review", Review);

        databaseReference.child(randomkey).updateChildren(cmnt).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful())
                    Toast.makeText(movie.this, "data added", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(movie.this, "data  not added", Toast.LENGTH_SHORT).show();
            }
        });

       /* databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> updates= new HashMap<String, Object>();
                updates.put("reviews", Movieclass);


                databaseReference.updateChildren(updates);
                //databaseReference.child("-MkIhcvghnveftcABZMp").setValue(Review);

                Toast.makeText(movie.this, "data added", Toast.LENGTH_SHORT).show();
            }*/


           /* @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(movie.this, "Fail to add data "  , Toast.LENGTH_SHORT).show();

            }*/
    }

    }


