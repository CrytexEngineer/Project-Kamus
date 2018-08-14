package com.example.aqil.projectkamus.Model;

import java.util.ArrayList;

public class KamusModel {
    ArrayList<KamusItem> kamusItemArrayList = new ArrayList<>();

    public KamusModel() {
    }

    public ArrayList<KamusItem> getKamusItemArrayList() {
        return kamusItemArrayList;
    }

    public void setKamusItemArrayList(ArrayList<KamusItem> kamusItemArrayList) {
        this.kamusItemArrayList = kamusItemArrayList;
    }

    public KamusModel(ArrayList<KamusItem> kamusItemArrayList) {

        this.kamusItemArrayList = kamusItemArrayList;
    }
}
