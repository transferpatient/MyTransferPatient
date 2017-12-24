package com.example.natthamondumrongkaviroyaphan.mytransferpatient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class FareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fare);
        TextView editTxt = (TextView) findViewById(R.id.edit_txt);
        editTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FareActivity.this,FareSettingActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
