package com.example.mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class tvshowpoll extends AppCompatActivity {
    Button b;
    RadioButton tvpoll;
    FirebaseDatabase database;
    DatabaseReference reference;
    int i = 0;
    tvshowpollj pollj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshowpoll);
        b =findViewById(R.id.tvpollsubmit);
        tvpoll= findViewById(R.id.radioButton);

        reference= database.getInstance().getReference().child("tvshowpoll");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    i = (int)dataSnapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(tvshowpoll.this, "Please add some data.", Toast.LENGTH_SHORT).show();
            }
        });
        b.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String m1 = tvpoll.getText().toString();
                if (tvpoll.isChecked()){
                    pollj.setTvpoll(m1);
                    reference.child(String.valueOf(i+1)).setValue(pollj);
                }
                else {
                    Toast.makeText(tvshowpoll.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}