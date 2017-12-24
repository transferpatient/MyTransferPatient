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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private LinearLayout leftMenu;
  //  private ListView mDrawerList;
    Toolbar toolbar;
  //  private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    private Button btnGroup,btnCreateGroup,btnJoinGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    //    mTitle = mDrawerTitle = getTitle();
        mNavigationDrawerItemTitles= getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        leftMenu = (LinearLayout) findViewById(R.id.left_drawer);
        //mDrawerList = (ListView) findViewById(R.id.left_drawer);
        btnGroup = (Button) findViewById(R.id.btn_group);
        btnGroup.setOnClickListener(this);
        btnCreateGroup = (Button) findViewById(R.id.btn_create_group);
        btnCreateGroup.setOnClickListener(this);
        btnJoinGroup = (Button) findViewById(R.id.btn_join_group);
        btnJoinGroup.setOnClickListener(this);
        setupToolbar();

      //  DataModel[] drawerItem = new DataModel[3];

      //  drawerItem[0] = new DataModel(R.drawable.ic_profile, "Connect");
      //  drawerItem[1] = new DataModel(R.drawable.ic_chat, "Fixtures");
      //  drawerItem[2] = new DataModel(R.drawable.ic_fare, "Table");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        //DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.list_view_item_row, drawerItem);
        //mDrawerList.setAdapter(adapter);
//        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setupDrawerToggle();
        selectItem(0);

    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnGroup)) {
            selectItem(0);
        } else if (v.equals(btnCreateGroup)) {
            selectItem(1);
        } else if (v.equals(btnJoinGroup)){
            selectItem(2);
        }
    }

/*
    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

    }
*/

    private void selectItem(int position) {
        Log.i("Boom","selectItem");
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new ContentMainFragment();
                break;
            case 1:
                fragment = new CreateCircleFragment();
                break;
            case 2:
                fragment = new JoinCircleFragment();
                break;
            default:
                fragment = new ContentMainFragment();
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

         //   mDrawerList.setItemChecked(position, true);
         //   mDrawerList.setSelection(position);
            setTitle(mNavigationDrawerItemTitles[position]);
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