package de.deftone.prototype.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.List;

import de.deftone.prototype.R;
import de.deftone.prototype.data.Exercise;
import de.deftone.prototype.data.LegExerciseData;
import de.deftone.prototype.data.VideoData;
import de.deftone.prototype.fragments.ExerciseFragment;
import de.deftone.prototype.fragments.TopFragment;

import static de.deftone.prototype.activities.ExerciseDetailActivity.EXTRA_VIEWPAGER;
import static de.deftone.prototype.helper.ExerciseDetailAddPoints.PREFS_DATES;
import static de.deftone.prototype.helper.ExerciseDetailAddPoints.PREFS_POINTS;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final List<Exercise> ALL_PICTURES = LegExerciseData.getAllLegExercises();
    public static final List<Exercise> ALL_VIDEOS = VideoData.getAllVideos();

    public final static String TYPE = "type";
    public final static String TYPE_PICTURE = "picture tab";
    public final static String TYPE_VIDEO = "video tab";

    private ShareActionProvider shareActionProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpViewPager();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //add burger icon for drawer to toolbar
        DrawerLayout drawer = findViewById(R.id.drawer_main_activity_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.nav_open_drawer,
                R.string.nav_close_drawer);
        //add drawer toggle to drawer layout
        drawer.addDrawerListener(toggle);
        //sync state of burger icon on toolbar with state of the drawer
        toggle.syncState();

        //set listener to drawer
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setUpViewPager() {
        //Attach the SectionsPagerAdapter to the Viewpager
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(sectionsPagerAdapter);

        //Attach the ViewPager to the TabLayout
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("Home");
        tabLayout.getTabAt(1).setText("Picture");
        tabLayout.getTabAt(2).setText("Video");

        //das hier sollte gar nicht sein... denn das letzte fragment aufrufen, keinen neuen intent starten!!
        //set correct tab - when mainActivity is called from ExerciseDetailActivity (upArrow)
        if (getIntent().getExtras() != null && getIntent().getExtras().getString(EXTRA_VIEWPAGER) != null) {
            String viewPagerTitle = getIntent().getExtras().getString(EXTRA_VIEWPAGER);
            int viewPagerItem;
            switch (viewPagerTitle) {
                case TYPE_PICTURE:
                    viewPagerItem = 1;
                    break;
                case TYPE_VIDEO:
                    viewPagerItem = 2;
                    break;
                default:
                    viewPagerItem = 0;
            }
            viewPager.setCurrentItem(viewPagerItem);
        }
    }

    //viewpager
    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            switch (position) {
                case 0:
                    return new TopFragment();
                case 1:
                    ExerciseFragment pictureFragment = new ExerciseFragment();
                    bundle.putString(TYPE, TYPE_PICTURE);
                    pictureFragment.setArguments(bundle);
                    return pictureFragment;
                case 2:
                    ExerciseFragment videoFragment = new ExerciseFragment();
                    bundle.putString(TYPE, TYPE_VIDEO);
                    videoFragment.setArguments(bundle);
                    return videoFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    //navigation drawer
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        //warum muss ich diese methode ueberschreiben? was macht die? war bei catChat nicht noetig...
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Intent intent = null;
        String title;
        switch (id) {
//            //hier jedesmal die CREATED_EXERCISES_LIST neu befuellen
//            case R.id.nav_random_mix_long:
//                CreatedExercise.generateRandomExercises(4, 5,
//                        5, 7, 5);
//                intent = new Intent(this, CreateWorkoutActivity.class);
//                title = getString(R.string.random_mix_long);
//                intent.putExtra(EXTRA_TITLE, title);
//                break;
//            case R.id.nav_random_mix_medium:
//                CreatedExercise.generateRandomExercises(3, 4,
//                        3, 5, 3);
//                title = getString(R.string.random_mix_medium);
//                intent = new Intent(this, CreateWorkoutActivity.class);
//                intent.putExtra(EXTRA_TITLE, title);
//                break;
//            case R.id.nav_random_mix_short:
//                CreatedExercise.generateRandomExercises(2, 2,
//                        2, 2, 2);
//                title = getString(R.string.random_mix_short);
//                intent = new Intent(this, CreateWorkoutActivity.class);
//                intent.putExtra(EXTRA_TITLE, title);
//                break;
//            case R.id.nav_radom_legs:
//                CreatedExercise.generateRandomExercises(6, 0,
//                        0, 0, 0);
//                title = getString(R.string.random_legs);
//                intent = new Intent(this, CreateWorkoutActivity.class);
//                intent.putExtra(EXTRA_TITLE, title);
//                break;
//            case R.id.nav_radom_belly:
//                CreatedExercise.generateRandomExercises(0, 6,
//                        0, 0, 0);
//                title = getString(R.string.random_belly);
//                intent = new Intent(this, CreateWorkoutActivity.class);
//                intent.putExtra(EXTRA_TITLE, title);
//                break;
//            case R.id.nav_radom_back:
//                CreatedExercise.generateRandomExercises(0, 0,
//                        6, 0, 0);
//                title = getString(R.string.random_back);
//                intent = new Intent(this, CreateWorkoutActivity.class);
//                intent.putExtra(EXTRA_TITLE, title);
//                break;
//            case R.id.nav_radom_combi:
//                CreatedExercise.generateRandomExercises(0, 0,
//                        0, 6, 0);
//                title = getString(R.string.random_combi);
//                intent = new Intent(this, CreateWorkoutActivity.class);
//                intent.putExtra(EXTRA_TITLE, title);
//                break;
//            case R.id.statistic_last10:
//                intent = new Intent(this, StatisticActivity.class);
//                intent.putExtra(EXTRA, LAST_TEN);
//                break;
//            case R.id.statistic_4weeks:
//                intent = new Intent(this, StatisticActivity.class);
//                intent.putExtra(EXTRA, FOUR_WEEKS);
//                break;
//            case R.id.statistic_12weeks:
//                intent = new Intent(this, StatisticActivity.class);
//                intent.putExtra(EXTRA, TWELVE_WEEKS);
//                break;
//            case R.id.statistic_all:
//                intent = new Intent(this, StatisticActivity.class);
//                intent.putExtra(EXTRA, ALL);
//                break;
//            case R.id.delete_all_points:
//                deletePoints();
//                break;
        }
        if (intent != null)
            startActivity(intent);

        DrawerLayout drawer = findViewById(R.id.drawer_main_activity_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_main_activity_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }

    private void deletePoints() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle(R.string.delete_points_title)
                .setMessage(R.string.delete_points_message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        resetPreferences();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void resetPreferences() {
        SharedPreferences sharedPreferencesDates = getSharedPreferences(PREFS_DATES, 0);
        SharedPreferences.Editor sharedPreferencesDatesEditor = sharedPreferencesDates.edit();
        sharedPreferencesDatesEditor.clear().apply();

        SharedPreferences sharedPreferencesPoints = getSharedPreferences(PREFS_POINTS, 0);
        SharedPreferences.Editor sharedPreferencesPointsEditor = sharedPreferencesPoints.edit();
        sharedPreferencesPointsEditor.clear().apply();
    }
}
