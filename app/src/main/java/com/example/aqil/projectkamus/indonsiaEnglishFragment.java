package com.example.aqil.projectkamus;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.aqil.projectkamus.Adapter.KamusAdapter;
import com.example.aqil.projectkamus.Database.KamusHelperIE;
import com.example.aqil.projectkamus.Model.KamusItem;
import com.example.aqil.projectkamus.Model.KamusModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class indonsiaEnglishFragment extends Fragment {
    @BindView(R.id.searc_ie)
    SearchView searchView;
    @BindView(R.id.Rv)
    RecyclerView recyclerView;
    public ArrayList<KamusItem> list = new ArrayList<>();
    KamusAdapter adapter;
    String query = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_indonsia_english, container, false);
        adapter = new KamusAdapter(getContext());
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                query = searchView.getQuery().toString();
                new LoadAdapterData().execute();
                return true;
            }
        });
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public class LoadAdapterData extends AsyncTask<Void, Integer, Void> {

        KamusModel kamusModel = new KamusModel();

        @Override
        protected Void doInBackground(Void... voids) {
            KamusHelperIE kamusHelperIE = new KamusHelperIE(getContext());
            kamusHelperIE.open();
            list = kamusHelperIE.getDataByName(query);
            kamusHelperIE.close();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.setKamusItems(list);
            Log.d("TAG", "onPostExecute: " + adapter.getItemCount());
            Log.d("TAG", "doInBackground: " + adapter.getItemCount());
            adapter.notifyDataSetChanged();


        }

    }
}
