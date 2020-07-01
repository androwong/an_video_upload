package com.ayusma.upload_video;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.ayusma.upload_video.Fragment.BanglaFragment;
import com.ayusma.upload_video.Fragment.EnglishFragment;
import com.ayusma.upload_video.Fragment.EntertainmentFragment;
import com.ayusma.upload_video.Fragment.MovieFragment;
import com.ayusma.upload_video.Fragment.PoliticalFragment;
import com.ayusma.upload_video.Fragment.RussianFragment;
import com.ayusma.upload_video.Fragment.SportFragment;
import com.ayusma.upload_video.Fragment.TechFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

public class HomeActivity extends AppCompatActivity {

    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  setSupportActionBar(toolbar);



        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), HomeActivity.this);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

        BottomNavigationView bottomNavigationItemView = findViewById(R.id.navigationView);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.getTabView(i));
        }

        TextView upload_button = findViewById(R.id.upload_button);
        upload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, UploadActivity.class);
                startActivity(intent);

            }
        });

        String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();







        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.color.primary_light)
                .addProfiles(
                        new ProfileDrawerItem().withName("User User").withEmail("user@gmail.com").withIcon(getResources().getDrawable(R.drawable.common_google_signin_btn_icon_dark))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        /*Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                        startActivity(intent);*/

                        return false;
                    }
                })
                .build();


        new DrawerBuilder().withActivity(this).build();

        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Home");
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName("History");
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName("Settings");

        //create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1, item2,item3
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (position) {
                            case 1:
                                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                                startActivity(intent);
                                break;
                            case 2:
                                Intent history_intent = new Intent(getApplicationContext(),HistoryActivity.class);
                                startActivity(history_intent);
                                break;
                            case 3:
                                Intent settings_intent = new Intent(getApplicationContext(),SettingsActivity.class);
                                startActivity(settings_intent);
                                break;

                        }

                        return true;
                    }
                })
                .build();

    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_subscribe:
                toolbar.setTitle("Home");
                return true;
            case R.id.navigation_home:
                Toast.makeText(getApplicationContext(),"Matches",Toast.LENGTH_LONG).show();
                toolbar.setTitle("Matches");
                return true;
            case R.id.navigation_profile:
                /*Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(intent);*/
                Toast.makeText(getApplicationContext(),"asdf",Toast.LENGTH_LONG).show();
                toolbar.setTitle("Me");
                return true;
            case R.id.navigation_Subscription:
                Toast.makeText(getApplicationContext(),"awef",Toast.LENGTH_LONG).show();
                toolbar.setTitle("Me");
                return true;
            case R.id.navigation_contact:
                Toast.makeText(getApplicationContext(),"dsaf",Toast.LENGTH_LONG).show();
                toolbar.setTitle("Me");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //TODO Step 11
    public class PagerAdapter extends FragmentPagerAdapter {
        String tabTitles[] = new String[]{"Sport", "Entertainment", "Movie","Political","Bangla","Russian","English","Tech"};
        Context context;

        public PagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new SportFragment();
                case 1:
                    return new EntertainmentFragment();
                case 2:
                    return new MovieFragment();
                case 3:
                    return new PoliticalFragment();
                case 4:
                    return new BanglaFragment();
                case 5:
                    return new RussianFragment();
                case 6:
                    return  new EnglishFragment();
                case 7:
                    return new TechFragment();
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        public View getTabView(int position) {
            View tab = LayoutInflater.from(HomeActivity.this).inflate(R.layout.custom_tab, null);
            TextView tv = (TextView) tab.findViewById(R.id.custom_text);
            tv.setText(tabTitles[position]);
            return tab;
        }


    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_subscribe:

                    toolbar.setTitle("Home");
                    return true;
                case R.id.navigation_home:

                    toolbar.setTitle("Matches");
                    return true;
                case R.id.navigation_profile:
                    Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                    startActivity(intent);
                    toolbar.setTitle("Me");
                    return true;
                case R.id.navigation_Subscription:

                    toolbar.setTitle("Home");
                    return true;
                case R.id.navigation_contact:

                    toolbar.setTitle("Matches");
                    return true;
            }
            return false;
        }
    };
}




