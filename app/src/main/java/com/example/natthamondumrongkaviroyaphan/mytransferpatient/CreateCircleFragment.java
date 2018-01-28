package com.example.natthamondumrongkaviroyaphan.mytransferpatient;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CreateCircleFragment extends Fragment implements View.OnClickListener {
    private Button btn;
    private EditText groupName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.create_circle, container, false);
        btn = (Button) rootView.findViewById(R.id.done_button);
        btn.setOnClickListener(this);
        groupName = rootView.findViewById(R.id.group_name);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.done_button) {
            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            if(currentFirebaseUser != null) {
                String userId = currentFirebaseUser.getUid();
                createGroup(userId, groupName.getText().toString());
            }
        }
    }

    private void createGroup(String userId, String groupName) {
        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference groups = mRootRef.child("groups");
        String key = groups.push().getKey();

        HashMap<String, Object> postValues = new HashMap<>();
        postValues.put("createdById", userId);
        postValues.put("groupName", groupName);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(key, postValues);
        groups.updateChildren(childUpdates);
    }
}