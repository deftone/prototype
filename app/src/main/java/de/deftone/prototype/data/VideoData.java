package de.deftone.prototype.data;

import java.util.ArrayList;
import java.util.List;

import de.deftone.prototype.R;

import static de.deftone.prototype.data.Exercise.BULLET;
import static de.deftone.prototype.data.ExerciseTypes.VIDEO;

public class VideoData {
    public static List<Exercise> getAllVideos() {
        List<Exercise> exercises = new ArrayList<>();

        exercises.add(new Exercise(0, VIDEO, "flute", getIcons(),
                getDescFlute(), R.drawable.flute, 0, new int[]{2}));

        exercises.add(new Exercise(1, VIDEO, "saxophone", getIcons(),
                getDescSax(), R.drawable.sax, 0, new int[]{2}));

        return exercises;
    }

    private static String[] getIcons() {
        return new String[]{BULLET, BULLET,  BULLET};
    }

    private static String[] getDescFlute() {
        return new String[]{"emanuelle",
                "playing the flute with a professional singer",
                "bad sound and video quality "};
    }

    private static String[] getDescSax() {
        return new String[]{"the pink panter",
                "playing the saxphone with other students",
                "quality ok "};
    }

}
