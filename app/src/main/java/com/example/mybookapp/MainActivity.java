package com.example.mybookapp;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.TextPaint;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.mybookapp.home.HomeFragment;
import com.example.mybookapp.searchtab.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private Fragment homeFragment = new HomeFragment();
    private Fragment searchFragment = new SearchFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView textViewTitle = findViewById(R.id.mainTextViewTitle);
        if (textViewTitle != null) {
            TextPaint paint = textViewTitle.getPaint();
            Shader textShader = new LinearGradient(
                    0, 0, 0, textViewTitle.getTextSize(),
                    new int[]{Color.parseColor("#FF6F61"), Color.parseColor("#333333")}, // Gradient colors
                    null, Shader.TileMode.CLAMP
            );
            paint.setShader(textShader);
        }

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        if (savedInstanceState == null) {
            loadFragment(homeFragment);
        }

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment =
                    item.getItemId() == R.id.nav_home ? homeFragment : searchFragment;
            loadFragment(selectedFragment);
            return true;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}
