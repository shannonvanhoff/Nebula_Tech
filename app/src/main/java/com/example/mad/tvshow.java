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

public class tvshow extends AppCompatActivity {

    private  EditText tvshowreview;
    private Button confirm;
    RecyclerView Rreview;

    FirebaseDatabase firebaseDatabase,firebaseDatabase2;
    DatabaseReference databaseReference, databaseReferenceR;

    ArrayList<tvshowreviews> list;
    Tvshowadapter tvshowadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow);

        tvshowreview = findViewById(R.id.addreviewtv);
        confirm = findViewById(R.id.reviewconfirmtv);
        Rreview = findViewById(R.id.tvshowR);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReferenceR = firebaseDatabase.getReference("TVShow").child("-MkMiBSz-svCP2tp2rx2");
        Rreview.setHasFixedSize(true);
        Rreview.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        tvshowadapter = new Tvshowadapter(this, list);
        Rreview.setAdapter(tvshowadapter);

        databaseReference = firebaseDatabase.getReference("TVShow").child("-MkMiBSz-svCP2tp2rx2").child("reviews");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    tvshowreviews user = dataSnapshot1.getValue(tvshowreviews.class);
                    list.add(user);
                }
                tvshowadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Review = tvshowreview.getText().toString();

                if (TextUtils.isEmpty(Review) ){
                    Toast.makeText(tvshow.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                } else {
                    addDatatoFirebase(Review);
                }

            }
        });


    }
    private void addDatatoFirebase ( String Review) {
        String id= "id";

        String randomkey = id+""+new Random().nextInt(1000);

        HashMap cmnt = new HashMap();
        cmnt.put("review", Review);

        databaseReference.child(randomkey).updateChildren(cmnt).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful())
                    Toast.makeText(tvshow.this, "data added", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(tvshow.this, "data  not added", Toast.LENGTH_SHORT).show();
            }
        });

    }
}