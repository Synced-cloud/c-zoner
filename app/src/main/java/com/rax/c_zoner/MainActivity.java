package com.rax.c_zoner;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.rax.c_zoner.databinding.ActivityMainBinding;
import com.rax.c_zoner.databinding.DialogIntroBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private DialogIntroBinding dialogBinding;
    private Context mContext;
    private Activity activity = this;
    private AlertDialog introDialog;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.account_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (introDialog != null) {
            introDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mContext = this;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        dialogBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.dialog_intro, null, false);
        MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(mContext);
        dialogBinding.btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.navView.getMenu().clear();
                binding.navView.inflateMenu(R.menu.bottom_nav_menu_user);
                BottomNavigationView navView = findViewById(R.id.nav_view);
                AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                        R.id.news_feed, R.id.c19Map, R.id.epass_user)
                        .build();
                NavController navController = Navigation.findNavController(activity, R.id.nav_host_fragment);
                NavigationUI.setupActionBarWithNavController((AppCompatActivity) activity, navController, appBarConfiguration);
                NavigationUI.setupWithNavController(navView, navController);
                binding.navView.setSelectedItemId(R.id.news_feed);
                introDialog.dismiss();
            }
        });
        dialogBinding.btnPWD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.navView.getMenu().clear();
                binding.navView.inflateMenu(R.menu.bottom_nav_menu_pwd);
                BottomNavigationView navView = findViewById(R.id.nav_view);
                AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                        R.id.tracking, R.id.add_fence, R.id.epass_pwd)
                        .build();
                NavController navController = Navigation.findNavController(activity, R.id.nav_host_fragment);
                NavigationUI.setupActionBarWithNavController((AppCompatActivity) activity, navController, appBarConfiguration);
                NavigationUI.setupWithNavController(navView, navController);
                binding.navView.setSelectedItemId(R.id.tracking);
                introDialog.dismiss();
            }
        });
        materialAlertDialogBuilder.setView(dialogBinding.getRoot()).setCancelable(false);
        introDialog = materialAlertDialogBuilder.create();
        introDialog.show();
    }

}
