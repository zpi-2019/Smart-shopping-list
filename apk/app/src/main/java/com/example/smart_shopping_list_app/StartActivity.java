package com.example.smart_shopping_list_app;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import java.util.Objects;
import java.util.concurrent.ExecutionException;


public class StartActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static int MODEL_VERSION;
    private AppViewModel appViewModel;
    static int currentUserID = 1;
    static int currentListID = 0;
    private FirebaseAuth mAuth;
    private static final int MEMORY_ACCESS_KEY = 4;

    public enum Unit {
        Kg, G, Litr, Sztuka
    }

    public enum Status {
        bought, lack, toBuy
    }

    public enum GroupColors {
        Red, Blue, Pink, Green, Black
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!(ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(StartActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MEMORY_ACCESS_KEY);
        }
        appViewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        updateModel();
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.start_activity);
        startDrawerLayoutAndMenu();
        startFragmentStart();
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        currentListID = sharedPref.getInt("ListID", 0);
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("ListID", MODEL_VERSION);
        editor.apply();
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
            fragmentClass = StartFragment.class;
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
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.getIdToken(true)
                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()) {
                                String idToken = task.getResult().getToken();
                                Log.d("Token", idToken);
                            }
                        }
                    });
            currentUser.getIdToken(true);
            String name = currentUser.getDisplayName();
            String email = currentUser.getEmail();
            Uri photoUrl = currentUser.getPhotoUrl();
            ImageView imageView = navigationView.getHeaderView(0).findViewById(R.id.user_imageView);
            imageView.setImageURI(photoUrl);
            TextView tvName = navigationView.getHeaderView(0).findViewById(R.id.user_name);
            tvName.setText(name);
            TextView tvEmail = navigationView.getHeaderView(0).findViewById(R.id.user_email);
            tvEmail.setText(email);
        }
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void updateModel(){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        MODEL_VERSION = sharedPref.getInt("Model version", 0);
        int version = -1;
        try {
            version = new API.CheckModelVerison().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(MODEL_VERSION != version){
            Log.d("Model", "Update");
            try {
                JSONOperations.Helper helper = new API.UpdateDistances().execute().get();
                appViewModel.deleteAllDistances();
                for(int i = 0; i < helper.keysList.size(); i++){
                    int index = appViewModel.selectProductID(helper.keysList.get(i));
                    if(index == 0){
                        appViewModel.insertNewProduct(new Product(helper.keysList.get(i)));
                        Log.d("loop", helper.keysList.get(i));
                    }
                }
                for(int i = 0; i < helper.distances.size(); i++){
                    int id1 = appViewModel.selectProductID(helper.keysList.get(i));
                    for(int j = 0; j < helper.distances.get(i).size(); j++){
                        int id2 = appViewModel.selectProductID(helper.keysList.get(i + j + 1));
                        appViewModel.insertNewDistance(new Distance(id1, id2, helper.distances.get(i).get(j)));
                        Log.d("distances", String.valueOf(id1) + " " + id2 + " " + helper.distances.get(i).get(j));
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            MODEL_VERSION = version;
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt("Model version", MODEL_VERSION);
            editor.apply();
        }
        else{
            Log.d("Model", "No update");
        }
    }
}
