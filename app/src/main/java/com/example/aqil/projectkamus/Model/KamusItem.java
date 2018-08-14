package com.example.aqil.projectkamus.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class KamusItem implements Parcelable {
    private int id;
    private String title;
    private String translation;

    public KamusItem() {
    }


    public KamusItem(int id, String title, String translation) {
        this.id = id;
        this.title = title;
        this.translation = translation;
    }

    public KamusItem(String title, String translation) {
        this.title = title;
        this.translation = translation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.translation);
    }

    protected KamusItem(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.translation = in.readString();
    }

    public static final Creator<KamusItem> CREATOR = new Creator<KamusItem>() {
        @Override
        public KamusItem createFromParcel(Parcel source) {
            return new KamusItem(source);
        }

        @Override
        public KamusItem[] newArray(int size) {
            return new KamusItem[size];
        }
    };
}
