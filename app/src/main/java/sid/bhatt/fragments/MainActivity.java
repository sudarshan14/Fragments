package sid.bhatt.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    Button addFragment;
    TextView fragmentBackstackCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addFragment = findViewById(R.id.btnAddFragment);
        addFragment.setOnClickListener(this);
        fragmentBackstackCount = findViewById(R.id.textViewBackStackEntry);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

                fragmentBackstackCount.setText("Back stack count" + fragmentManager.getBackStackEntryCount());
            }
        });


    }


    @Override
    public void onBackPressed() {

        Fragment fragment = fragmentManager.findFragmentById(R.id.frameLayoutContainer);
        if (fragment != null) {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View view) {

      //  addWithBackStackEntry();

        addWithoutBackStackEntry();


    }

    private void addWithoutBackStackEntry() {

//        Fragment fragment = fragmentManager.findFragmentById(R.id.frameLayoutContainer);
        Fragment fragment = null;
        switch (fragmentManager.getBackStackEntryCount()) {

            case 0:
                fragment = new FirstFragment();
                Log.d("sud","0");

        }

        fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.add(R.id.frameLayoutContainer, fragment);
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
