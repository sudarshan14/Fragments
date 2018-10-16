package sid.bhatt.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String PERMISSON = "permission";
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    Button addFragment;
    Button popFragment;
    TextView fragmentBackstackCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(PERMISSON, "on create");
        addFragment = findViewById(R.id.btnAddFragment);
        addFragment.setOnClickListener(this);

        popFragment = findViewById(R.id.btnPopFragment);
        popFragment.setOnClickListener(this);
        fragmentBackstackCount = findViewById(R.id.textViewBackStackEntry);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

                fragmentBackstackCount.setText("Back stack count" + fragmentManager.getBackStackEntryCount());

                StringBuilder builder = new StringBuilder("BackStack entry status" + fragmentManager.getBackStackEntryCount() + "\n");
                for (int i = (fragmentManager.getBackStackEntryCount() - 1); i >= 0; i--) {
                    builder.append(fragmentManager.getBackStackEntryAt(i).getName().toString() + "\n");

                }
                Log.d(PERMISSON, builder.toString());
            }
        });

        // 0 -> granted
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        Log.d(PERMISSON, "permission" + permission);
        if (permission != 0) {
            Log.d(PERMISSON, "permission asking");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        }
        Log.d(PERMISSON, "onCreate" + Thread.currentThread().getId());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Log.d(PERMISSON, "onConfigurationChanged" + newConfig.orientation);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(PERMISSON, "onResume");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(PERMISSON, "granted" + grantResults.length + "requestCode" + requestCode + "grantResults" + grantResults[0]);
    }

    @Override
    public void onBackPressed() {

        Fragment fragment = fragmentManager.findFragmentById(R.id.frameLayoutContainer);

        fragmentBackstackCount.setText("removing" + fragmentManager.getBackStackEntryCount());
        if (fragment != null)
            Log.d(PERMISSON, "\n" + fragment.toString());
        if (fragment != null) {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragment);
          //  fragmentTransaction.addToBackStack("Remove" + fragment.toString());
            fragmentTransaction.commit();
        } else {
            Log.d(PERMISSON, "onBackPressed");
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {

        //  addWithBackStackEntry();
        switch (view.getId()) {

            case R.id.btnAddFragment:
                addWithoutBackStackEntry();
                break;

            case R.id.btnPopFragment:
                popFragments();
                break;
        }

    }

    private void popFragments() {
        fragmentManager.popBackStack();
    }

    private void addWithoutBackStackEntry() {

        Fragment fragment = fragmentManager.findFragmentById(R.id.frameLayoutContainer);
//        Fragment fragment = null;
        if (fragment instanceof FirstFragment) {
            fragment = new SecondFragment();
        } else if (fragment instanceof SecondFragment) {
            fragment = new ThirdFragment();
        } else {
            fragment = new FirstFragment();
        }


        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack("Added " + fragment.toString());
        fragmentTransaction.replace(R.id.frameLayoutContainer, fragment);
        fragmentTransaction.commit();


    }


    private void addWithBackStackEntry() {
        Fragment fragment;

        fragment = new FirstFragment();

        switch (fragmentManager.getBackStackEntryCount()) {

            case 0:
                fragment = new FirstFragment();
                break;
            case 1:
                fragment = new SecondFragment();
                break;
            case 2:
                fragment = new ThirdFragment();
                break;
        }

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.add(R.id.frameLayoutContainer, fragment);
        fragmentTransaction.commit();
    }
}
