package de.deftone.prototype.data;

import java.util.ArrayList;
import java.util.List;

import de.deftone.prototype.R;

import static de.deftone.prototype.data.Exercise.ARROW;
import static de.deftone.prototype.data.Exercise.BULLET;
import static de.deftone.prototype.data.Exercise.FINGER_POINT_UP;
import static de.deftone.prototype.data.Exercise.MUSCLE;
import static de.deftone.prototype.data.ExerciseTypes.PICTURE;

public class LegExerciseData {
    public static List<Exercise> getAllLegExercises() {
        List<Exercise> exercises = new ArrayList<>();

        exercises.add(new Exercise(0, PICTURE, "Kniebeuge im Ausfallschritt", getIconsKnieb(),
                getDescKnieb(), R.drawable.split1, 0, new int[]{2}));

        exercises.add(new Exercise(1, PICTURE, "Kniebeugen", getIconsKnieb(),
                getDescKnieb(), R.drawable.split2, 0, new int[]{2}));

        exercises.add(new Exercise(2, PICTURE, "Fersenheben", getIconsKnieb(),
                getDescKnieb(), R.drawable.split3, 0, new int[]{2}));

        exercises.add(new Exercise(3, PICTURE, "Oberes Bein im Liegen heben", getIconsKnieb(),
                getDescKnieb(), R.drawable.split4, 0, new int[]{2}));

        return exercises;
    }

    private static String[] getIconsKnieb() {
        return new String[]{BULLET, BULLET, BULLET, ARROW, FINGER_POINT_UP, FINGER_POINT_UP, MUSCLE};
    }

    private static String[] getDescKnieb() {
        return new String[]{"mit leicht gebeugten Knien stehen",
                "Füsse großen Schrittabstand entfernt ",
                "Oberkörper gerade, vorgestreckte Brust ",
                "dann das vordere Knie beugen, bis Oberschenkel waagerecht und wieder aufrichten ",
                "je größer der Abstand, desto mehr Gesäßmuskel ",
                "je kleiner der Abstand, desto mehr Oberschenkelmuskel ",
                "20 Wiederholungen pro Seite "};
    }


}
