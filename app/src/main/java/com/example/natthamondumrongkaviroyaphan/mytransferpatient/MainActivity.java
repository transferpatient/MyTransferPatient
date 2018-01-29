package com.example.natthamondumrongkaviroyaphan.mytransferpatient;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private DrawerLayout mDrawerLayout;
    private LinearLayout leftMenu;
    private ListView listGroup;
    ArrayList<String> groupNameList = new ArrayList<String>();
    ArrayList<String> groupIdList = new ArrayList<String>();
    private Toolbar toolbar;

  //  private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    private Button btnCreateGroup,btnJoinGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    //    mTitle = mDrawerTitle = getTitle();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        leftMenu = (LinearLayout) findViewById(R.id.left_drawer);
        listGroup = (ListView) findViewById(R.id.list_group);
        btnCreateGroup = (Button) findViewById(R.id.btn_create_group);
        btnCreateGroup.setOnClickListener(this);
        btnJoinGroup = (Button) findViewById(R.id.btn_join_group);
        btnJoinGroup.setOnClickListener(this);
        setupToolbar();

        getGroupsName();
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.list_view_item_row, groupNameList);
        listGroup.setAdapter(adapter);
        listGroup.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setupDrawerToggle();
        selectItem(0, groupNameList.get(0));
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnCreateGroup)) {
            selectItem(-1,"Create Group");
        } else if (v.equals(btnJoinGroup)){
            selectItem(-2,"Join Group");
        }
    }

    private void getGroupsName(){
        groupIdList.add("");
        groupNameList.add("");
        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        mRootRef.child("groups").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                groupIdList.clear();
                groupNameList.clear();
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    String key = postSnapshot.getKey();
                    String groupName = postSnapshot.child("groupName").getValue(String.class);
                    groupIdList.add(key);
                    groupNameList.add(groupName);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: "+ databaseError);
            }
        });
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position, groupNameList.get(position));
        }
    }

    private void selectItem(int position,String name) {
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        switch (position) {
            case -1:
                fragment = new CreateCircleFragment();
                break;
            case -2:
                fragment = new JoinCircleFragment();
                break;
            default:
                Log.d(TAG, "groupIdList: "+ groupIdList);
                String groupId = groupIdList.get(position);
                Log.d(TAG, "groupId: "+ groupId);
                bundle.putString("groupId", groupId);
                fragment = new ContentMainFragment();
                fragment.setArguments(bundle);
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

         //   mDrawerList.setItemChecked(position, true);
         //   mDrawerList.setSelection(position);
            setTitle(name);
            mDrawerLayout.closeDrawer(leftMenu);

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    void setupToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    void setupDrawerToggle(){
        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.app_name, R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        mDrawerToggle.syncState();
    }
}