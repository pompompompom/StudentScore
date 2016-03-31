package yipengy.com.studentscore.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;

import yipengy.com.studentscore.R;
import yipengy.com.studentscore.model.Statistics;
import yipengy.com.studentscore.model.Student;
import yipengy.com.studentscore.util.DatabaseConnector;

/**
 * Home Fragment to display student score and stats
 */
public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ViewGroup scores = (ViewGroup) v.findViewById(R.id.scores);

        DatabaseConnector dbConnector = new DatabaseConnector(getContext());
        dbConnector.insertToDb(R.raw.input_more40);

        Student[] students = dbConnector.getAllStudents();
        for (Student s : students) {
            View scoreView = inflater.inflate(R.layout.score_row, scores, false);

            populateScoreView(scoreView, s.getSID() + "", s.getScoreOfQuiz(0) + "", s.getScoreOfQuiz(1)
                    + "", s.getScoreOfQuiz(2) + "", s.getScoreOfQuiz(3) + "", s.getScoreOfQuiz(4) + "");
            scores.addView(scoreView);
        }

        Statistics stats = new Statistics();
        int[] highScores = stats.findhigh(students);
        Log.d(TAG, "highScores: " + Arrays.toString(highScores));
        int[] lowScores = stats.findlow(students);
        Log.d(TAG, "highScores: " + Arrays.toString(lowScores));
        float[] avgScores = stats.findavg(students);
        Log.d(TAG, "highScores: " + Arrays.toString(avgScores));

        View highScoreView = v.findViewById(R.id.highscore);
        View lowScoreView = v.findViewById(R.id.lowscore);
        View avgScoreView = v.findViewById(R.id.avgscore);

        populateScoreView(highScoreView, "High Score", highScores[0] + "", highScores[1] + "",
                highScores[2] + "", highScores[3] + "", highScores[4] + "");

        populateScoreView(lowScoreView, "Low Score", lowScores[0] + "", lowScores[1] + "",
                lowScores[2] + "", lowScores[3] + "", lowScores[4] + "");

        populateScoreView(avgScoreView, "Average", avgScores[0] + "", avgScores[1] + "",
                avgScores[2] + "", avgScores[3] + "", avgScores[4] + "");
        return v;
    }

    private void populateScoreView(View scoreView, String s0, String s1, String s2, String s3, String s4, String s5) {
        TextView id = (TextView) scoreView.findViewById(R.id.student_id);
        TextView q1 = (TextView) scoreView.findViewById(R.id.q1);
        TextView q2 = (TextView) scoreView.findViewById(R.id.q2);
        TextView q3 = (TextView) scoreView.findViewById(R.id.q3);
        TextView q4 = (TextView) scoreView.findViewById(R.id.q4);
        TextView q5 = (TextView) scoreView.findViewById(R.id.q5);

        id.setText(s0);
        q1.setText(s1);
        q2.setText(s2);
        q3.setText(s3);
        q4.setText(s4);
        q5.setText(s5);
    }
}
