package com.example.kennykao;


import android.os.Parcel;
import android.os.Parcelable;

public class CoaAlbum implements Parcelable{
    private String mUrl;
    private String mTitle;

    public CoaAlbum(String url, String title) {
        mUrl = url;
        mTitle = title;
    }

    protected CoaAlbum(Parcel in) {
        mUrl = in.readString();
        mTitle = in.readString();
    }

    public static final Creator<CoaAlbum> CREATOR = new Creator<CoaAlbum>() {
        @Override
        public CoaAlbum createFromParcel(Parcel in) {
            return new CoaAlbum(in);
        }

        @Override
        public CoaAlbum[] newArray(int size) {
            return new CoaAlbum[size];
        }
    };

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public static  CoaAlbum[] getCoaAlbums() {

        return new CoaAlbum[]{
                new CoaAlbum("https://goo.gl/m9Eckm", "Coach K"),
                new CoaAlbum("https://goo.gl/srKcuC", "安西教練"),
                new CoaAlbum("https://goo.gl/BGdnPo", "誰才是教練"),
                new CoaAlbum("https://goo.gl/xArPLe", "流氓教練"),

        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mUrl);
        parcel.writeString(mTitle);
    }
}
