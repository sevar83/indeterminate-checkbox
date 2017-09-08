# indeterminate-checkbox
[![Tag](https://img.shields.io/github/tag/sevar83/indeterminate-checkbox.svg?label=JitPack)](https://jitpack.io/#sevar83/indeterminate-checkbox) [![License](https://img.shields.io/:license-apache_2.0-green.svg)](https://raw.githubusercontent.com/sevar83/indeterminate-checkbox/master/LICENSE) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-indeterminate--checkbox-green.svg?style=true)](https://android-arsenal.com/details/1/3224)


![How they look like](https://raw.githubusercontent.com/sevar83/indeterminate-checkbox/master/art/screenshot1.png)

![Tags sample](https://raw.githubusercontent.com/sevar83/indeterminate-checkbox/master/art/3-state%20checkboxes.png)

Android CheckBox and RadioButton with additional 3rd 'indeterminate' state.

## Requirements
  - Based on `AppCompat` library
  - Requires `AppCompat` theme
  - API 7+ (but tested only on API 10+)

## Getting Started

1. You need to have this in your project's `build.gradle` file:

    ```Gradle
    allprojects {
        repositories {
            jcenter()
            maven { url "https://jitpack.io" }
        }
    }
    ```
2. You need this in your app's module `build.gradle` file:
    ```Gradle
    dependencies {
        compile 'com.github.sevar83:indeterminate-checkbox:1.0.5@aar'
    }
    ```

3. The Support library's `AppCompat` theme or its variant must be set for your application or your activity in the manifest:
    ```Manifest
    <activity
        android:theme="@style/AppTheme"
        ...
    />
    ```
    
## Usage

##### In XML layout
Add this to your layout:
```XML
<com.buildware.widget.indeterm.IndeterminateCheckBox
    android:id="@+id/indeterm_checkbox"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="My IndeterminateCheckBox"
    app:indeterminate="true"/>  
    <!-- Add the line above if you want it to be initially indeterminate, 
    otherwise it acts as a plain checkbox. -->
```


#### Listening for state changes

Both changes of the 'checked' state and the 'indeterminate' state can be received in one listener. So, the `OnCheckedChangeListener` is not needed at all. The `OnStateChangedListener` is called just after the standard `OnCheckedChangeListener`.
```Java
IndeterminateCheckBox indetermCheck;
...
indetermCheck.setOnStateChangedListener(new OnStateChangedListener() {
    @Override
    public void onStateChangedListener(IndeterminateCheckBox check, @Nullable Boolean state) {
        if (state == null) {
            // The new state is 'indeterminate'
        } else if (state) {
            // The new state is 'checked'
        } else {
            // The new state is 'unchecked'
        }
    }
}
```

#### State
Note that when user clicks an checkbox in 'indeterminate' state it always becomes 'checked' no matter of its current 'checked' state. Its 'checked' state is not getting toggled, it becomes 'checked'. Clicking it again and it's becomes 'unchecked'. Then another click toggles it back to 'checked' state and so on. In other words user does not toggle all three states one after another. With normal operations (clicks, keypresses) it's not possible. This behaviour is normal and follows various UX guidelines. However switching back to 'indeterminate' state is possible programatically if you decide it is appropriate (see next).

#### Controlling the state programmatically

Switching to 'indeterminate' state at any time is possible with:
```Java
indetermCheck.setIndeterminate(true);
// or 
indetermCheck.setState(null);
```

Switching from 'indeterminate' state to 'determinate' state:
```Java
indetermCheck.setChecked(true); // or false
// or
indetermCheck.setState(true);   // or false
```

Checking the state
```Java
indetermCheck.isIndeterminate();
// or 
indetermCheck.getState();
```

#### Known Issues 
- `IndeterminateRadionButton` is not animated. In order to make it animated I had to include about 120 PNG files from the
Android Lollipop framework. Unlike the `CheckBox` widget the `RadioButton` has no vector animations in Lollipop yet. They still use PNGs. When (and if) Google switches to vectors I'm going to import them here too.
- Highlighting on pre-Lollipop seems not working or missing



## Todo

 - `IndeterminateCheckedTextView` - would be valuable for using in dialogs
 - Write some tests

## License
Apache License, Version 2.0
