package com.example.awakego.Controlers.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.app.FragmentManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.awakego.Controlers.Fragments.Alarm_List;
import com.example.awakego.Controlers.Fragments.EvaluateFragment;
import com.example.awakego.Controlers.Fragments.HelpFragment;
import com.example.awakego.Controlers.Fragments.InfosFragment;
import com.example.awakego.Controlers.Fragments.SettingsFragment;
import com.example.awakego.Controlers.Fragments.ShareFragment;
import com.example.awakego.R;
import com.google.android.material.navigation.NavigationView;



public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
   private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      // mChannels = (Channels)getApplicationContext();
      /*  Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);*/
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Toolbar m_toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(m_toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mDrawerLayout = findViewById(R.id.drawer_layout);
         ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,m_toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerToggle.setDrawerIndicatorEnabled(false);
        drawerToggle.setToolbarNavigationClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        drawerToggle.setHomeAsUpIndicator(R.drawable.ic_icons8_menu);

       if (savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Alarm_List()).commit();
        }
    }

    @Override
    public void onBackPressed() {

         if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
         {
             mDrawerLayout.closeDrawer(GravityCompat.START);
         }
         else
         {
             super.onBackPressed();
         }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        getSupportFragmentManager().popBackStackImmediate(0, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        switch (menuItem.getItemId())
        {
            case R.id.nav_infos:
                if(getSupportFragmentManager().findFragmentByTag("infos") == null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new InfosFragment(),"infos").addToBackStack(null).commit();
                }
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SettingsFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_help:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HelpFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_share:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ShareFragment()).addToBackStack(null).commit();
                break;
            case R.id.nav_evaluate:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new EvaluateFragment()).addToBackStack(null).commit();
                break;

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
       return true;
    }
}
