package com.varun.kernelmanager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;

public class MainActivity extends AppCompatActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getkernelver();
        getcurrcpugov();
    }

    protected void getkernelver() {
        TextView kernelver = findViewById(R.id.kernelversion);
        TextView numcpu = findViewById(R.id.cpunum);
        int num = Runtime.getRuntime().availableProcessors();
        numcpu.setText("Number of CPU Cores: " + String.valueOf(num));
        int flag = 0;
        try {
            Process root = Runtime.getRuntime().exec("su");
        } catch (IOException e) {
            flag = 1;
            System.out.println("Failed");
            e.printStackTrace();
        }
        String CAT_KERNEL = "/system/bin/cat /proc/version";
        Process process = null;
        if(flag == 1) {
            try {
                process = Runtime.getRuntime().exec("/system/bin/uname -rv");
            } catch (IOException e) {
                System.out.println("Failed to uname");
                e.printStackTrace();
            }
        }
        else {
            try {
                process = Runtime.getRuntime().exec(CAT_KERNEL);
            } catch (IOException e) {
                System.out.println("Failed to cat proc");
                e.printStackTrace();
            }
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
        System.out.println(Runtime.getRuntime().availableProcessors());
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
        cpu0.setText(new StringBuilder().append("CPU0 Cluster: ").append(output).toString());
    }
}
