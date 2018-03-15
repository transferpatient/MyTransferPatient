package com.example.natthamondumrongkaviroyaphan.mytransferpatient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FareActivity extends AppCompatActivity {
    TextView txFareName,txFareDate,txFareBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fare);
        String groupId = getIntent().getStringExtra("groupId");

        txFareName = (TextView) findViewById(R.id.tx_fare_name);
        txFareDate = (TextView) findViewById(R.id.tx_fare_date);
        txFareBy = (TextView) findViewById(R.id.tx_fare_by);

        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        mRootRef.child("groups").child(groupId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                txFareName.setText(dataSnapshot.child("fareName").getValue(String.class));
                txFareDate.setText(dataSnapshot.child("startDate").getValue(String.class));
                txFareBy.setText(dataSnapshot.child("fareBy").getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        TextView editTxt = (TextView) findViewById(R.id.edit_txt);
        editTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FareActivity.this,CreateFareActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
