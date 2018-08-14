package com.example.aqil.projectkamus;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.aqil.projectkamus.Database.KamusHelperEI;
import com.example.aqil.projectkamus.Database.KamusHelperIE;
import com.example.aqil.projectkamus.Model.KamusItem;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        new LoadData().execute();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (id) {

            case R.id.IE:
                getSupportActionBar().setTitle("Indonesia-English");
                transaction.replace(R.id.main_container, new indonsia_english());
                break;
            case R.id.EI:
                getSupportActionBar().setTitle("English-Indonesia");
                transaction.replace(R.id.main_container, new englishindonesiaFragment());
                break;

        }
        transaction.addToBackStack(null);
        Log.d("TAG", "onNavigationItemSelected: ");
        transaction.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class LoadData extends AsyncTask<Void, Integer, Void> {
        KamusHelperIE kamusHelperIE;
        KamusHelperEI kamusHelperEI;

        @Override
        protected void onPreExecute() {
            kamusHelperIE = new KamusHelperIE(MainActivity.this);
            kamusHelperEI = new KamusHelperEI(MainActivity.this);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ArrayList<KamusItem> kamusItemsIE = preLoadRaw(1);
            ArrayList<KamusItem> kamusItemsEI = preLoadRaw(2);
            kamusHelperIE.open();
            kamusHelperIE.beginTransaction();

            try {
                for (KamusItem kata : kamusItemsIE) {
                    kamusHelperIE.insertTransaction(kata);

                }

                kamusHelperIE.setTransactionSuccess();

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("TAG", "doInBackground: " + "eror");
            }
            kamusHelperIE.endTransaction();
            kamusHelperIE.close();


            kamusHelperEI.open();
            kamusHelperEI.beginTransaction();
            try {

                for (KamusItem kata : kamusItemsEI) {
                    kamusHelperEI.insertTransaction(kata);

                }

                kamusHelperEI.setTransactionSuccess();
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("TAG", "doInBackground: " + "eror");
            }

            kamusHelperEI.endTransaction();
            kamusHelperEI.close();
            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        public ArrayList<KamusItem> preLoadRaw(int id) {
            Log.d("TAG", "preLoadRaw: ");
            ArrayList<KamusItem> kamusItems = new ArrayList<>();
            String line = null;
            BufferedReader bufferedReader;

            try {
                InputStream raw_dict = null;
                if (id == 1) {
                    raw_dict = getResources().openRawResource(R.raw.indonesia_english);
                } else if (id == 2) {
                    raw_dict = getResources().openRawResource(R.raw.english_indonesia);
                }
                bufferedReader = new BufferedReader(new InputStreamReader(raw_dict));
                int count = 0;
                do {
                    line = bufferedReader.readLine();
                    String[] splitstr = line.split("\t");
                    KamusItem kata;
                    kata = new KamusItem(splitstr[0], splitstr[1]);
                    Log.d("TAG", "preLoadRaw: " + kata.getTitle());
                    kamusItems.add(kata);
                    count++;

                } while (line != null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return kamusItems;
        }
    }
}
