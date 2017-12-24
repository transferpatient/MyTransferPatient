package com.example.natthamondumrongkaviroyaphan.mytransferpatient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class FareSettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fare_setting);
        ImageView view = (ImageView) findViewById(R.id.locate_ic);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FareSettingActivity.this,TestSearchActivity.class);
                startActivity(intent);
            }
        });
    }

}
