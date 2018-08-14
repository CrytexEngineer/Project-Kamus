package com.example.aqil.projectkamus;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.aqil.projectkamus.Database.KamusHelperEI;
import com.example.aqil.projectkamus.Database.KamusHelperIE;
import com.example.aqil.projectkamus.Model.KamusItem;
import com.example.aqil.projectkamus.Pref.AppPreference;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoaderActivity extends AppCompatActivity {
@BindView(R.id.progress_bar)ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader_);
        ButterKnife.bind(LoaderActivity.this);
        new LoadData().execute();
    }


    private class LoadData extends AsyncTask<Void, Integer, Void> {
        KamusHelperIE kamusHelperIE;
        KamusHelperEI kamusHelperEI;
        AppPreference appPreference;
        double progress;
        double maxprogress = 100;

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
                progress = 30;
                publishProgress((int) progress);
                Double progressMaxInsert = 80.0;
                Double progressDiff = (progressMaxInsert - progress) / (kamusItemsEI.size()+kamusItemsIE.size());

                kamusHelperIE.beginTransaction();

                try {
                    for (KamusItem kata : kamusItemsIE) {
                        kamusHelperIE.insertTransaction(kata);
                        progress += progressDiff;
                        publishProgress((int) progress);
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
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }

                    kamusHelperEI.setTransactionSuccess();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("TAG", "doInBackground: " + "eror");
                }

                kamusHelperEI.endTransaction();
                kamusHelperEI.close();
                appPreference.setFirstRun(false);
                publishProgress((int) maxprogress);

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
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
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
