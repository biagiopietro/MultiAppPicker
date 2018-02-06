package com.mcsoft.multiapppicker;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.LongSparseArray;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


public class RxApplication {


    private ContentResolver mResolver;

    public static Observable<Application> fetch (@NonNull final Context context, final Activity activity) {
        return Observable.create(new ObservableOnSubscribe<Application>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Application> e) throws Exception {
                new RxApplication(context).fetch(e, activity);
            }
        });
    }

    private RxApplication(@NonNull Context context) {
        mResolver = context.getContentResolver();
    }

    private void fetch (ObservableEmitter emitter, Activity activity) {
        LongSparseArray<Application> applications = new LongSparseArray<>();
        List pkgAppsList;
        final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        pkgAppsList = activity.getPackageManager().queryIntentActivities( mainIntent, 0);
        Collections.sort(pkgAppsList,new ResolveInfo.DisplayNameComparator(activity.getPackageManager()));
        int id = 0;
        for (Object object : pkgAppsList)
        {
            ResolveInfo info = (ResolveInfo) object;
            Drawable icon    = activity.getBaseContext().getPackageManager().getApplicationIcon(info.activityInfo.applicationInfo);
            String strAppName  	= info.activityInfo.applicationInfo.publicSourceDir.toString();
            String strPackageName  = info.activityInfo.applicationInfo.packageName.toString();
            final String title 	= (String)((info != null) ? activity.getBaseContext().getPackageManager().getApplicationLabel(info.activityInfo.applicationInfo) : "???");
            applications.put(id, new Application(icon, strPackageName, title, id));
            id++;
        }
        for (int i = 0; i < applications.size(); i++) {
            emitter.onNext(applications.valueAt(i));
        }
        emitter.onComplete();
    }


}