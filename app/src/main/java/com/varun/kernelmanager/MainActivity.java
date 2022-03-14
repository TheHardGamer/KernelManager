package com.varun.kernelmanager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BottomNavigationView bottomNavigationView;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomnav);
        getkernelver();
        getcurrcpugov();
    }

    protected void getkernelver() {
        TextView kernelver = findViewById(R.id.kernelversion);
        try {
            Process root = Runtime.getRuntime().exec("su");
        } catch (IOException e) {
            System.out.println("Failed");
            e.printStackTrace();
        }
        String CAT_KERNEL = "/system/bin/cat /proc/version";
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(CAT_KERNEL);
        } catch (IOException e) {
            System.out.println("Failed to cat proc");
            e.printStackTrace();
        }
        assert process != null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        char[] buffer = new char[4096];
        int read = 0;
        StringBuffer output = new StringBuffer();
        while (true) {
            try {
                if (!((read = reader.read(buffer)) > 0)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            output.append(buffer, 0, read);
        }
        kernelver.setText(output);
    }

    @SuppressLint("SetTextI18n")
    public void getcurrcpugov() {
        TextView cpu0 = findViewById(R.id.cpu0gov);
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("/system/bin/cat /sys/devices/system/cpu/cpu0/cpufreq/scaling_governor");
        } catch (IOException e) {
            System.out.println("Failed to cat proc");
            e.printStackTrace();
        }
        assert process != null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        char[] buffer = new char[4096];
        int read = 0;
        StringBuffer output = new StringBuffer();
        while (true) {
            try {
                if (!((read = reader.read(buffer)) > 0)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            output.append(buffer, 0, read);
        }
        cpu0.setText(new StringBuilder().append("CPU0 Governor: ").append(output).toString());
    }
}
