package de.deftone.prototype.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.List;

import de.deftone.prototype.R;
import de.deftone.prototype.data.AudioData;
import de.deftone.prototype.data.Exercise;
import de.deftone.prototype.data.LegExerciseData;
import de.deftone.prototype.data.PDFData;
import de.deftone.prototype.data.VideoData;
import de.deftone.prototype.fragments.ExerciseFragment;
import de.deftone.prototype.fragments.TopFragment;

import static de.deftone.prototype.activities.ExerciseDetailActivity.EXTRA_VIEWPAGER;


public class MainActivity extends AppCompatActivity {

    public static final List<Exercise> ALL_PICTURES = LegExerciseData.getAllLegExercises();
    public static final List<Exercise> ALL_VIDEOS = VideoData.getAllVideos();
    public static final List<Exercise> ALL_AUDIOS = AudioData.getAllAudios();
    public static final List<Exercise> ALL_PDFS = PDFData.getAllPDFs();

    public final static String TYPE = "type";
    public final static String TYPE_PICTURE = "picture tab";
    public final static String TYPE_VIDEO = "video tab";
    public final static String TYPE_AUDIO = "audio tab";
    public final static String TYPE_PDF = "pdf tab";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpViewPager();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        tabLayout.getTabAt(3).setText("Audio");
        tabLayout.getTabAt(4).setText("PDF");

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
                case TYPE_AUDIO:
                    viewPagerItem = 3;
                    break;
                case TYPE_PDF:
                    viewPagerItem = 4;
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
                case 3:
                    ExerciseFragment audioFragment = new ExerciseFragment();
                    bundle.putString(TYPE, TYPE_AUDIO);
                    audioFragment.setArguments(bundle);
                    return audioFragment;
                case 4:
                    ExerciseFragment pdfFragment = new ExerciseFragment();
                    bundle.putString(TYPE, TYPE_PDF);
                    pdfFragment.setArguments(bundle);
                    return pdfFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_main_activity_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }

}
