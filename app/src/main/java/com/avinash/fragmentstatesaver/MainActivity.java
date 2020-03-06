package com.avinash.fragmentstatesaver;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.avinash.library.FragmentStateSaver;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    FragmentStateSaver fragmentStateSaver;
    FrameLayout container;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = findViewById(R.id.container);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

         fragmentStateSaver = new FragmentStateSaver(container, getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new HomeFragment();
                    case 1:
                        return new SearchFragment();
                    case 2:
                        return new ProfileFragment();
                    default:
                        return new MessageFragment();
                }
            }
        };
         fragmentStateSaver.changeFragment(0);

         bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                 switch (item.getItemId()) {
                     case R.id.home:
                         fragmentStateSaver.changeFragment(0);
                         return true;
                     case R.id.search:
                         fragmentStateSaver.changeFragment(1);
                         return true;
                     case R.id.profile:
                         fragmentStateSaver.changeFragment(2);
                         return true;
                     default:
                         fragmentStateSaver.changeFragment(3);
                         return true;
                 }
             }
         });


    }
}
