package com.example.natthamondumrongkaviroyaphan.mytransferpatient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseUser;

public class SetProfileActivity extends AppCompatActivity {

    private DatabaseReference users;
    private Button nextBtn;
    private EditText userName;
    private String key;
    private String uid;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentFirebaseUser != null){
            uid = currentFirebaseUser.getUid();
            email = currentFirebaseUser.getEmail();

            userName = findViewById(R.id.field_username);
            nextBtn = findViewById(R.id.username_next_button);
            nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    writeNewUser(uid+ "", userName.getText().toString(), email);
                    Intent intent = new Intent(SetProfileActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    private void writeNewUser(String userId, String name, String email) {
        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        User user = new User(name, email);
        mRootRef.child("users").child(userId).setValue(user);
    }
}


