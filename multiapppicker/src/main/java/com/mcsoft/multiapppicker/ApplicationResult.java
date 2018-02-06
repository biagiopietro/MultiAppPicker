package com.mcsoft.multiapppicker;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;


public class ApplicationResult implements Parcelable {

    private Drawable applicationImage;
    private String applicationPackageName;
    private String applicationTitle;


    public ApplicationResult(Application application){
       // this.applicationImage = application.getApplicationImage();
        this.applicationPackageName = application.getApplicationPackageName();
        this.applicationTitle = application.getApplicationTitle();
    }

    public Drawable getApplicationImage() {
        return applicationImage;
    }

    public void setApplicationImage(Drawable applicationImage) {
        this.applicationImage = applicationImage;
    }

    public String getApplicationPackageName() {
        return applicationPackageName;
    }

    public void setApplicationPackageName(String applicationPackageName) {
        this.applicationPackageName = applicationPackageName;
    }

    public String getApplicationTitle() {
        return applicationTitle;
    }

    public void setApplicationTitle(String applicationTitle) {
        this.applicationTitle = applicationTitle;
    }

    protected ApplicationResult(Parcel in) {
        // The order is important
        applicationTitle = in.readString();
        applicationPackageName = in.readString();
        //Bitmap bitmap = in.readParcelable(Bitmap.class.getClassLoader());
       // applicationImage = new BitmapDrawable(bitmap);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // The order is important
        dest.writeString(applicationTitle);
        dest.writeString(applicationPackageName);
        //dest.writeParcelable(((BitmapDrawable) applicationImage).getBitmap() ,flags);
       // dest.writeParcelable(getBitmapFromDrawable(applicationImage), flags);
    }

//    @NonNull
//    private Bitmap getBitmapFromDrawable(@NonNull Drawable drawable) {
//        final Bitmap bmp = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
//        final Canvas canvas = new Canvas(bmp);
//        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
//        drawable.draw(canvas);
//        return bmp;
//    }

    @SuppressWarnings("unused")
    public static final Creator<ApplicationResult> CREATOR = new Creator<ApplicationResult>() {
        @Override
        public ApplicationResult createFromParcel(Parcel in) {
            return new ApplicationResult(in);
        }

        @Override
        public ApplicationResult[] newArray(int size) {
            return new ApplicationResult[size];
        }
    };
}
