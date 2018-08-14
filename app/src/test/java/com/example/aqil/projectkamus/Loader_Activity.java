package com.example.aqil.projectkamus;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.aqil.projectkamus.Database.KamusHelperEI;
import com.example.aqil.projectkamus.Database.KamusHelperIE;
import com.example.aqil.projectkamus.Model.KamusItem;
import com.example.aqil.projectkamus.Pref.AppPreference;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Loader_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader_);
        new LoadData().execute();
    }


    private class LoadData extends AsyncTask<Void, Integer, Void> {
        KamusHelperIE kamusHelperIE;
        KamusHelperEI kamusHelperEI;
        AppPreference appPreference;

        @Override
        protected void onPreExecute() {
            appPreference = new AppPreference(LoaderActivity.this);
            kamusHelperIE = new KamusHelperIE(LoaderActivity.this);
            kamusHelperEI = new KamusHelperEI(LoaderActivity.this);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Boolean firstRun = appPreference.getFirstRun();
            if (firstRun) {
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
                appPreference.setFirstRun(false);
            } else {
                try {
                    synchronized (this) {
                        this.wait(2000);

                        // publishProgress(50);

                        this.wait(2000);

                    }
                } catch (Exception e) {
                }
            }


            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = new Intent(LoaderActivity.this, MainActivity.class);
            startActivity(intent);
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
