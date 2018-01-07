package com.example.natthamondumrongkaviroyaphan.mytransferpatient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ChatRoomActivity extends AppCompatActivity {
    private static final String TAG = "ChatRoomActivity";
    private DatabaseReference chatGroup;
    private EditText et_message;
    private Button btn_send;
    private String key;
    private String groupId;
    private String uid;
    private String username;
    private ListView listview;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        onBindView();

        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        chatGroup = mRootRef.child("chat").child("1");


        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentFirebaseUser != null) {
            uid = currentFirebaseUser.getUid();
        }
        Log.d(TAG, "uid:" + uid);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            groupId = bundle.getString("groupId");
        }
        Log.d(TAG, "groupId:" + groupId);

        mRootRef.child("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user != null) {
                    username = user.getUsername();
                }
                Log.d(TAG, "username:" + username);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());
            }
        });

        list = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,list);
        listview.setAdapter(adapter);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = chatGroup.push().getKey();
                HashMap<String, Object> postValues = new HashMap<>();
                postValues.put("message", et_message.getText().toString());
                postValues.put("userId", uid);
                postValues.put("username", username);

                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put(key, postValues);
                chatGroup.updateChildren(childUpdates);

                Toast.makeText(ChatRoomActivity.this,"send",Toast.LENGTH_SHORT).show();
                et_message.setText("");
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        chatGroup.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                onGetChild(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                onGetChild(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("databaseError",databaseError.toString());
                Toast.makeText(ChatRoomActivity.this,"onCancelled",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void onBindView() {
        listview = (ListView)findViewById(R.id.list_view);
        et_message = (EditText) findViewById(R.id.message);
        btn_send = (Button) findViewById(R.id.btn_send);
    }

    private void onGetChild(DataSnapshot dataSnapshot){
        String message = "";
        String userId = "";
        String username = "";
        Iterator i = dataSnapshot.getChildren().iterator();
        while(i.hasNext()){
            message = (String) ((DataSnapshot)i.next()).getValue();
            userId = (String) ((DataSnapshot)i.next()).getValue();
            username = (String) ((DataSnapshot)i.next()).getValue();
            list.add(username+" : "+message);
        }
        adapter.notifyDataSetChanged();
    }
}

