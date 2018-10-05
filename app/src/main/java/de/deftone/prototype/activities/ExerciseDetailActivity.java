package de.deftone.prototype.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import de.deftone.prototype.R;
import de.deftone.prototype.data.Exercise;
import de.deftone.prototype.helper.ExerciseDetailAddPoints;
import de.deftone.prototype.helper.ExerciseDetailList;

import static de.deftone.prototype.activities.MainActivity.ALL_PICTURES;
import static de.deftone.prototype.activities.MainActivity.ALL_VIDEOS;
import static de.deftone.prototype.activities.MainActivity.TYPE_PICTURE;
import static de.deftone.prototype.activities.MainActivity.TYPE_VIDEO;
import static de.deftone.prototype.data.Exercise.MUSCLE;

public class ExerciseDetailActivity extends AppCompatActivity {

    public static final String EXTRA_EXERCISE_ID = "exercise_id";
    public static final String EXTRA_EXERCISE_NAME = "exercise_name";
    public static final String EXTRA_EXERCISE_TYPE = "exercise_type";
    public static final String EXTRA_VIEWPAGER = "viewpager";
    ListView listView;
    String title = "";
    int image = 0;
    String[] icon = {};
    String[] desc = {};
    int seconds = 0;
    int[] weight = {};
    Button weightButton;
    Button weightButton2;
    Button weightButton3;
    boolean buttonClicked = false;
    ExerciseDetailAddPoints exerciseDetailAddPoints;
    Context context = this;
    private String exerciseType;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_detail);

        if (exerciseDetailAddPoints == null) {
            exerciseDetailAddPoints = new ExerciseDetailAddPoints(this);
        }

        //get details of the exercise
        id = getIntent().getExtras().getInt(EXTRA_EXERCISE_ID);
        exerciseType = getIntent().getExtras().getString(EXTRA_EXERCISE_TYPE);
        getDescriptionDetails(id, exerciseType);

        //scroll to top and adjust/set elements on activity layout
        NestedScrollView scrollView = findViewById(R.id.detail_scrollview);
        scrollView.getParent().requestChildFocus(scrollView, scrollView);
        setToobar();
        setImageAndDescription();
        setWeightButtons(weight);
        buttonClicked = false;
    }

    private void getDescriptionDetails(int id, String type) {
        TextView buttonsWithPoints = findViewById(R.id.text_points);
        TableLayout tableWitButtons = findViewById(R.id.table_buttons);
        Button buttonPlay = findViewById(R.id.show_Video);
        switch (type) {
            case TYPE_PICTURE:
                buttonsWithPoints.setVisibility(View.VISIBLE);
                tableWitButtons.setVisibility(View.VISIBLE);
                buttonPlay.setVisibility(View.GONE);
                setExerciseData(ALL_PICTURES.get(id));
                break;

            case TYPE_VIDEO:
                buttonsWithPoints.setVisibility(View.GONE);
                tableWitButtons.setVisibility(View.GONE);
                buttonPlay.setVisibility(View.VISIBLE);
                setExerciseData(ALL_VIDEOS.get(id));
                break;
        }
    }

    private void setExerciseData(Exercise exercise) {
        title = exercise.getName();
        image = exercise.getImageResourceId();
        desc = exercise.getDescription();
        icon = exercise.getIcon();
        seconds = exercise.getSeconds();
        weight = exercise.getWeight();
    }

    private void setToobar() {
        Toolbar toolbar = findViewById(R.id.toolbar_detail);
        toolbar.setTitle(title);
        //in case i will ever need this:
//        Now If you want to use CollapsingToolbarLayout and Toolbar together then you have to use setTitle() of CollapsingToolbarLayout
//        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.confirm_order_mail_layout);
//        collapsingToolbarLayout.setTitle("My Title");

        //show up arrow
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        DrawerLayout drawer = findViewById(R.id.drawer_main_activity_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, toolbar,
                R.string.nav_open_drawer,
                R.string.nav_close_drawer);
        //show up arrow (setting it first false and then true is "best practise"...)
        actionBar.setDisplayHomeAsUpEnabled(false);
        toggle.setDrawerIndicatorEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //and implement up arrow functionality: set correct tab of viewpager
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void setImageAndDescription() {
        ImageView imageView = findViewById(R.id.image_detail);
        imageView.setImageDrawable(ContextCompat.getDrawable(this, image));

        ExerciseDetailList adapter = new ExerciseDetailList(ExerciseDetailActivity.this, icon, desc);
        listView = findViewById(R.id.list_detail);
        listView.setAdapter(adapter);

        //adjust the height of the listview
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = getTotalHeightofListView();
        int padding = 65;
        params.height = height + padding;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    private int getTotalHeightofListView() {
        ListAdapter mAdapter = listView.getAdapter();
        int listviewElementsheight = 0;
        for (int i = 0; i < desc.length; i++) {
            View mView = mAdapter.getView(i, null, listView);
            mView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            listviewElementsheight += mView.getMeasuredHeight();
        }
        return listviewElementsheight;
    }

    private void setWeightButtons(int[] weight) {
        weightButton = findViewById(R.id.weight_button);
        weightButton2 = findViewById(R.id.weight_button_2);
        weightButton3 = findViewById(R.id.weight_button_3);
        weightButton.setText("1x" + String.valueOf(weight[0]) + MUSCLE);
        weightButton2.setText("2x" + String.valueOf(weight[0]) + MUSCLE);
        weightButton3.setText("3x" + String.valueOf(weight[0]) + MUSCLE);
    }

    //these methods must be public otherwise onClick won't work
    public void onClickPoints(View view) {
        handleOnClick(weight[0], weightButton);
    }

    public void onClickPoints2(View view) {
        handleOnClick(2 * weight[0], weightButton2);
    }

    public void onClickPoints3(View view) {
        handleOnClick(3 * weight[0], weightButton3);
    }

    private void handleOnClick(int buttonPoints, Button button) {
        if (!buttonClicked) {
            int points = exerciseDetailAddPoints.addExercisePointsToDailySum(buttonPoints);
            changeButtonAndShowToast(button, points);
            buttonClicked = true;
        }
    }

    public void onClickShowVideo(View view){
        //open video activity
        Intent intent = new Intent(this, VideoActivity.class);
        String name = ALL_VIDEOS.get(id).getName();
        intent.putExtra(EXTRA_EXERCISE_NAME, name);
        startActivity(intent);
    }

    private void changeButtonAndShowToast(Button button, int points) {
        String toastText = getString(R.string.points_today_1)
                + points + getString(R.string.points_today_2);
        Toast toast = Toast.makeText(this, toastText, Toast.LENGTH_SHORT);
        toast.show();
    }

}