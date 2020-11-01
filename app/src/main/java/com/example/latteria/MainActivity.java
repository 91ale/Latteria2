package com.example.latteria;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.concurrent.ExecutionException;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    Fragment spesaFragment = new Fragment();
    Fragment insertFragment = new Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {

            @Override
            public void onBackStackChanged() {
                Fragment f = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
                if (f != null) {
                    Log.d("HEREHERE","");
                    String fragClassName = spesaFragment.getClass().getName();
                    if (fragClassName.equals(SpesaFragment.class.getName())) {
                        setTitle("Home");
                    }
                }
            }
        });

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_spesa, R.id.navigation_insert, R.id.navigation_prodotti)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        //Fragment navHostFragment = getSupportFragmentManager().getPrimaryNavigationFragment();
        //spesaFragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Fragment navHostFragment = getSupportFragmentManager().getPrimaryNavigationFragment();
        Fragment fragment = navHostFragment.getChildFragmentManager().getFragments().get(0);

        if(fragment instanceof SpesaFragment) {
            try {
                ((SpesaFragment) fragment).onMyKeyDown(keyCode, event);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (fragment instanceof InsertFragment) {
                try {
                    ((InsertFragment) fragment).onMyKeyDown(keyCode, event);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }

        return super.onKeyDown(keyCode, event);
    }

}