package de.deftone.prototype.data;

import java.util.ArrayList;
import java.util.List;

import de.deftone.prototype.R;

import static de.deftone.prototype.data.Exercise.BULLET;
import static de.deftone.prototype.data.ExerciseTypes.PDF;

public class PDFData {
    public static List<Exercise> getAllPDFs() {
        List<Exercise> exercises = new ArrayList<>();

        exercises.add(new Exercise(R.raw.presentation, PDF, "testing", getIcons(),
                getDescTesting(), R.drawable.testing_pdf, 0, new int[]{2}));

        return exercises;
    }

    private static String[] getIcons() {
        return new String[]{BULLET, BULLET, BULLET};
    }

    private static String[] getDescTesting() {
        return new String[]{"how to test android apps",
                "bla bla bla",
                "and some more "};
    }


}
