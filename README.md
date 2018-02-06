# MultiAppPicker

[![](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
<a target="_blank" href="https://developer.android.com/reference/android/os/Build.VERSION_CODES.html#JELLY_BEAN"><img src="https://img.shields.io/badge/API-16%2B-blue.svg?style=flat" alt="API" /></a>

A simple, material design multi-app picker using RecyclerView and Alphabetical Fast Scrolling (based on [MultiContactPicker](https://github.com/broakenmedia/MultiContactPicker) )! The picker will read the device applications and allow you to select 1-1 or 1-many and return them in a convenient list.

**Note:** In the code you can see some comments relative to the icon of the application selected;
          if you need the icon of the selected application you can uncomments the code (recommended for single choise mode);
          it's not recommended for multi choise mode because the icons (bitmaps) are also serialised into an internal bundle.
          Unfortunately this bundle has a very small size limit.



Screenshot
:-------------------------
![](https://i.imgur.com/Fehq3TW.jpg?1) ![](https://i.imgur.com/Lh46Yfk.jpg?2)

## Install

Add this to your project build.gradle
``` gradle
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

Add this to your module build.gradle

``` gradle
    dependencies {
        compile 'com.github.biagiopietro:MultiAppPicker:1.0'
    }

```
### Usage

Open the picker in your activity/fragment:

```java
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
```

Then listen for results:

```java
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
```

### Themeing
MultiAppPicker has a default theme called Azure, however you can use a custom theme if you wish by using both the builder calls above and using styles:

```xml
    <style name="MyCustomPickerTheme" parent="MultiAppPicker.Azure">
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="mapToolbarTheme">@style/MyCustomToolbarTheme</item>
        <item name="mapListPopupWindowStyleTheme">@style/MyCustomToolbarTheme</item>
    </style>

    <style name="MyCustomToolbarTheme" parent="ThemeOverlay.AppCompat.Light">
        <item name="android:textColorPrimary">@color/colorAccent</item>
        <item name="android:textColorSecondary">@color/colorAccent</item>
    </style>
```

This can then be set in the builder above using **.theme(int)**

### Language
- English;
- Italian.

### Thanks
MultiAppPicker utilises code from these great libraries:

- [MultiContactPicker](https://github.com/broakenmedia/MultiContactPicker)

- [FastScroll](https://github.com/L4Digital/FastScroll)

- [RoundedLetterView](https://github.com/pavlospt/RoundedLetterView)

- [RxAndroid](https://github.com/ReactiveX/RxAndroid)

- [MaterialSearchView](https://github.com/MiguelCatalan/MaterialSearchView)


## License

```
Copyright 2018 biagiopietro

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
