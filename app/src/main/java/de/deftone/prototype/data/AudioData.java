package de.deftone.prototype.data;

import java.util.ArrayList;
import java.util.List;

import de.deftone.prototype.R;

import static de.deftone.prototype.data.Exercise.BULLET;
import static de.deftone.prototype.data.ExerciseTypes.AUDIO;

public class AudioData {

    public static List<Exercise> getAllAudios() {
        List<Exercise> exercises = new ArrayList<>();

        exercises.add(new Exercise(0, AUDIO, "flute_audio", getIcons(),
                getDescFlute(), R.drawable.audio_image, 0, new int[]{2}));

        exercises.add(new Exercise(0, AUDIO, "saxophone_audio", getIcons(),
                getDescSax(), R.drawable.audio_image, 0, new int[]{2}));

        exercises.add(new Exercise(0, AUDIO, "children_audio", getIcons(),
                getDescChildren(), R.drawable.audio_image, 0, new int[]{2}));
        return exercises;
    }

    private static String[] getIcons() {
        return new String[]{BULLET, BULLET,  BULLET};
    }

    private static String[] getDescFlute() {
        return new String[]{"emanuelle",
                "playing the flute with a professional singer",
                "better sound quality"};
    }

    private static String[] getDescSax() {
        return new String[]{"in the mood",
                "playing the saxophone by myself",
                "glenn miller swings ;)"};
    }

    private static String[] getDescChildren() {
        return new String[]{"fireman sam",
                "sung by kira and justus",
                "until thty fight about who sings what..."};
    }

}
