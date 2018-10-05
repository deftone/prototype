package de.deftone.prototype.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static de.deftone.prototype.activities.ExerciseDetailActivity.EXTRA_EXERCISE_ID;
import static de.deftone.prototype.activities.ExerciseDetailActivity.EXTRA_EXERCISE_TYPE;
import static de.deftone.prototype.activities.MainActivity.*;

import java.util.ArrayList;
import java.util.List;

import de.deftone.prototype.R;
import de.deftone.prototype.activities.ExerciseDetailActivity;
import de.deftone.prototype.data.Exercise;
import de.deftone.prototype.helper.CaptionedImagesAdapter;

import static de.deftone.prototype.activities.MainActivity.TYPE_PICTURE;

public class ExerciseFragment extends Fragment {

    private static final String BUNDLE_RECYCLER_LAYOUT = "potato";
    private RecyclerView recyclerView;

    public ExerciseFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Bundle bundle = this.getArguments();
        List<String> exerciseNames = new ArrayList<>();
        List<Integer> exerciseImages = new ArrayList<>();

        recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view_exercises, container, false);

        if (bundle != null) {
            switch (bundle.getString(TYPE)) {
                case TYPE_PICTURE:
                    for (Exercise exercise : ALL_PICTURES) {
                        exerciseNames.add(exercise.getName());
                        exerciseImages.add(exercise.getImageResourceId());
                    }
                    break;
                case TYPE_VIDEO:
                    for (Exercise exercise : ALL_VIDEOS) {
                        exerciseNames.add(exercise.getName());
                        exerciseImages.add(exercise.getImageResourceId());
                    }
                    break;
                case TYPE_AUDIO:
                    for (Exercise exercise : ALL_AUDIOS) {
                        exerciseNames.add(exercise.getName());
                        exerciseImages.add(exercise.getImageResourceId());
                    }
                    break;
                case TYPE_PDF:
                    for (Exercise exercise : ALL_PDFS) {
                        exerciseNames.add(exercise.getName());
                        exerciseImages.add(exercise.getImageResourceId());
                    }
                    break;
            }
        }


        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(exerciseNames, exerciseImages);
        recyclerView.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        adapter.setListener(new CaptionedImagesAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), ExerciseDetailActivity.class);
                intent.putExtra(EXTRA_EXERCISE_ID, position);
                intent.putExtra(EXTRA_EXERCISE_TYPE, bundle.getString(TYPE));
                getActivity().startActivity(intent);
            }
        });
        return recyclerView;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            recyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, recyclerView.getLayoutManager().onSaveInstanceState());
    }
}
