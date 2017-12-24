package com.example.natthamondumrongkaviroyaphan.mytransferpatient;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class FareSettingActivity extends AppCompatActivity {

    private LinearLayout notiLayout;
    private Spinner spNumNoti;
    private String[] notiItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fare_setting);
        notiLayout = (LinearLayout) findViewById(R.id.noti_layout);
        Resources res = getResources(); //assuming in an activity for example, otherwise you can provide a context.
        notiItem = res.getStringArray(R.array.noti_arrays);
        spNumNoti = (Spinner) findViewById(R.id.sp_num_noti);
        spNumNoti.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int num = Integer.parseInt(notiItem[position]);
                for (int i=0;i<num;i++) {
                /*    LinearLayout LL = new LinearLayout(this);
                    LL.setOrientation(LinearLayout.HORIZONTAL);

                    LayoutParams LLParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

                    LL.setWeightSum(6f);
                    LL.setLayoutParams(LLParams);


                    ImageView ladder = new ImageView(this);
                    ladder.setImageResource(R.drawable.ic_launcher);

                    FrameLayout.LayoutParams ladderParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM);
                    ladder.setLayoutParams(ladderParams);

                    FrameLayout ladderFL = new FrameLayout(this);
                    LinearLayout.LayoutParams ladderFLParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0);
                    ladderFLParams.weight = 5f;
                    ladderFL.setLayoutParams(ladderFLParams);
                    ladderFL.setBackgroundColor(Color.GREEN);
                    View dummyView = new View(this);

                    LinearLayout.LayoutParams dummyParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
                    dummyParams.weight = 1f;
                    dummyView.setLayoutParams(dummyParams);
                    dummyView.setBackgroundColor(Color.RED);


                    ladderFL.addView(ladder);
                    LL.addView(ladderFL);
                    LL.addView(dummyView);
                    notiLayout.addView(LL);
*/
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
