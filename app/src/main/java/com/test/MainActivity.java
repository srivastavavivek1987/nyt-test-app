package com.test;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.test.R;
import com.test.adapter.DrawerListAdapter;
import com.test.fragment.HomeFragment;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ListView listView;
    private ActionBarDrawerToggle drawerToggle;

    private String[] menu;
    private String[] menuIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listView = (ListView) findViewById(R.id.left_drawer);

        menu = getResources().getStringArray(R.array.menu);
        menuIds = getResources().getStringArray(R.array.menu_id);

        listView.setAdapter(new DrawerListAdapter(this, menu));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                addFragment(position);
                drawerLayout.closeDrawer(listView);
            }
        });


        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        addFragment(0);
        drawerToggle.syncState();
    }

    private void addFragment(int position) {
        getSupportFragmentManager().beginTransaction().replace(R.id.content,
                HomeFragment.newInstance(menuIds[position], false),
                HomeFragment.TAG).commit();
        getSupportActionBar().setTitle(menu[position]);
    }

    private void addFragment(String query) {
        getSupportFragmentManager().beginTransaction().replace(R.id.content,
                HomeFragment.newInstance(query, true),
                HomeFragment.TAG).commit();
        getSupportActionBar().setTitle(query);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.actionSearch).getActionView();
        searchView.setQueryHint(getString(R.string.search_article));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                addFragment(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: {
                if (drawerLayout.isDrawerOpen(listView)) {
                    drawerLayout.closeDrawer(listView);
                } else {
                    drawerLayout.openDrawer(listView);
                }
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
