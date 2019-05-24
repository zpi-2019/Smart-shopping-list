package com.example.smart_shopping_list_app;

import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import java.util.Objects;


public class StartActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private AppViewModel appViewModel;
    static int currentUserID = 1;
    static int currentListID = 1;

    public enum Unit {
        Kg, G, Litr, Sztuka
    }

    public enum Status {
        Kupione, Brak, DoKupienia
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        setContentView(R.layout.start_activity);
        startDrawerLayoutAndMenu();
        startFragmentStart();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        Class fragmentClass = null;
        int id = item.getItemId();

        if (id == R.id.nav_all_lists) {
            fragmentClass = ListOfListsFragment.class;
        } else if (id == R.id.nav_groups) {
            fragmentClass = ListOfGroupsFragment.class;
        } else if (id == R.id.nav_new_list) {
            fragmentClass = SingleListFragment.class;
        } else if (id == R.id.nav_settings) {
            fragmentClass = null;
        } else if(id == R.id.nav_last_list) {
            fragmentClass = SingleListFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame1, fragment)
                .commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void startFragmentStart() {
        StartFragment nextFrag = StartFragment.newInstance();
        Objects.requireNonNull(this).getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame1, nextFrag, "findThisFragment")
                .addToBackStack(null)
                .commit();
    }

    void startDrawerLayoutAndMenu() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
}
