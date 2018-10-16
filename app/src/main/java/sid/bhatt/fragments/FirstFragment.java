package sid.bhatt.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import sid.bhatt.fragments.services.FirstService;

public class FirstFragment extends Fragment {

    public static final String PERMISSON = "permission";
    Button button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.first_fragment, container, false);
        button = v.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getActivity().startService(new Intent(getActivity(), FirstService.class));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    getActivity().startForegroundService(new Intent(getActivity(), FirstService.class));
                }
            }

        });

        return v;
    }

    @Override
    public String toString() {
        return FirstFragment.class.getSimpleName();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(PERMISSON, "onStop");
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.d(PERMISSON, "onPause");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(PERMISSON, "onDestroyView");
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        Log.d(PERMISSON, "onDestroy");
        getActivity().stopService(new Intent(getActivity(), FirstService.class));
    }
}
