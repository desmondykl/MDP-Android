package com.example.mdp_android_grp17.ui.main;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.mdp_android_grp17.MainActivity;
import com.example.mdp_android_grp17.R;

import static android.content.Context.SENSOR_SERVICE;
import static com.example.mdp_android_grp17.MainActivity.getGridMap;
import static com.example.mdp_android_grp17.MainActivity.refreshMessageReceived;

public class SettingFragment extends Fragment {
    // Init
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String TAG = "SettingFragment";
    private PageViewModel pageViewModel;
    private static SharedPreferences.Editor editor;
    static Button f1, f2;
    Button reconfigure;
    // Declaration Variable
    // Shared Preferences
    SharedPreferences sharedPreferences;
    ReconfigureFragment reconfigureFragment = new ReconfigureFragment();
    GridMap gridMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);



    }

    public static Button getF1() { return f1; }

    public static Button getF2() { return f2; }
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // inflate
        View root = inflater.inflate(R.layout.activity_setting, container, false);
        // get shared preferences
        sharedPreferences = getActivity().getSharedPreferences("Shared Preferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        f1 = root.findViewById(R.id.f1ActionButton2);
        f2 = root.findViewById(R.id.f2ActionButton2);
        reconfigure = root.findViewById(R.id.configureButton2);

        if (sharedPreferences.contains("F1")) {
            f1.setContentDescription(sharedPreferences.getString("F1", ""));
            showLog("setText for f1Btn: " + f1.getContentDescription().toString());
        }
        if (sharedPreferences.contains("F2")) {
            f2.setContentDescription(sharedPreferences.getString("F2", ""));
            showLog("setText for f2Btn: " + f2.getContentDescription().toString());
        }

        f1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLog("Clicked f1Btn");
                if (!f1.getContentDescription().toString().equals("empty"))
                    MainActivity.printMessage(f1.getContentDescription().toString());
                showLog("f1Btn value: " + f1.getContentDescription().toString());
                showLog("Exiting f1Btn");
            }
        });

        f2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLog("Clicked f2Btn");
                if (!f2.getContentDescription().toString().equals("empty"))
                    MainActivity.printMessage(f2.getContentDescription().toString());
                showLog("f2Btn value: " + f2.getContentDescription().toString());
                showLog("Exiting f2Btn");
            }
        });

        reconfigure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLog("Clicked reconfigureBtn");
                reconfigureFragment.show(getActivity().getFragmentManager(), "Reconfigure Fragment");
                showLog("Exiting reconfigureBtn");
            }
        });

        Button printMDFStringButton = (Button) root.findViewById(R.id.printMDFString2);
        printMDFStringButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cm = "";
                String message = "Explored : " + GridMap.getPublicMDFExploration();
                editor = sharedPreferences.edit();
                editor.putString("message", CommsFragment.getMessageReceivedTextView().getText() + "\n" + message);
                editor.commit();
                cm = message;
                refreshMessageReceived();
                message = "Obstacle : " + GridMap.getPublicMDFObstacle() + "0";
                editor.putString("message", CommsFragment.getMessageReceivedTextView().getText() + "\n" + message);
                editor.commit();
                refreshMessageReceived();
                cm = cm +"\n" +message;
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("mdf",cm);
                clipboard.setPrimaryClip(clip);
            }
        });

        // Toolbar
        Button bluetoothButton = (Button) root.findViewById(R.id.bluetoothButton);
        bluetoothButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent popup = new Intent(getActivity(), BluetoothPopUp.class);
                startActivity(popup);
            }
        });

        gridMap = getGridMap();

        Button mapInformationButton = (Button) root.findViewById(R.id.mapInfoButton);
        mapInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("mapJsonObject", String.valueOf(gridMap.getCreateJsonObject()));
                editor.commit();
                Intent popup = new Intent(getActivity(), MapInformation.class);
                startActivity(popup);
            }
        });


        return root;
    }

    private static void showLog(String message) {
        Log.d(TAG, message);
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
