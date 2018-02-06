package com.mcsoft.multiapppickerexample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.mcsoft.multiapppicker.ApplicationResult;
import com.mcsoft.multiapppicker.MultiAppPicker;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int APP_PICKER_REQUEST = 992;
    private boolean singleChoiseMode=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnOpenPicker = (Button) findViewById(R.id.btnOpenPicker);

        btnOpenPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MultiAppPicker.Builder(MainActivity.this) //Activity/fragment context
                        .theme(R.style.MyCustomPickerTheme) //Optional - default: MultiAppPicker.Azure
                        .hideScrollbar(false) //Optional - default: false
                        .showTrack(true) //Optional - default: true
                        .searchIconColor(Color.WHITE) // Optional - default: White
                        .setSelectOnlyOneItem(singleChoiseMode) // Optional -  default: false
                        .showBackButton(true) // Optional - default: true
                        .handleColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary)) //Optional - default: Azure Blue
                        .bubbleColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary)) //Optional - default: Azure Blue
                        .bubbleTextColor(Color.WHITE) //Optional - default: White
                        .showPickerForResult(APP_PICKER_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == APP_PICKER_REQUEST){
            if(resultCode == RESULT_OK) {
                List<ApplicationResult> results = MultiAppPicker.obtainResult(data);
                if (singleChoiseMode == true) {
                    Log.d("MyTag", results.get(results.size() - 1).getApplicationPackageName());
                } else {
                    printSelectedItemsOnConsole(results);
                }
            } else if(resultCode == RESULT_CANCELED){
                System.out.println("User closed the picker without selecting items.");
            }
        }
    }

    private void printSelectedItemsOnConsole(List<ApplicationResult> results) {
        for(ApplicationResult result: results) {
            Log.d("MyTag", result.getApplicationTitle() + " -> " + result.getApplicationPackageName());
        }
    }

}
