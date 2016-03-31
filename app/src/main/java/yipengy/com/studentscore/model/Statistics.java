package yipengy.com.studentscore.model;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Statistics for the quiz scores.
 * @author yipengy
 *
 */
public class Statistics implements Printable {
	int[] lowscores = new int[5];
	int[] highscores = new int[5];
	float[] avgscores = new float[5];

	/*
	 * This method will find the lowest score and store it in an array names
	 * lowscores.
	 */
	public int[] findlow(Student[] a) {
		// Initialize all low scores to Integer.MAX_VALUE
		for (int i = 0; i < lowscores.length; i++) {
			lowscores[i] = Integer.MAX_VALUE;
		}

		// Loop through all students, replace if found lower score
		for (int i = 0; i < a.length; i++) {
			if (a[i] == null) {
				break;
			}
			for (int j = 0; j < lowscores.length; j++) {
				if (a[i].getScoreOfQuiz(j) < lowscores[j]) {
					lowscores[j] = a[i].getScoreOfQuiz(j);
				}
			}
		}

		return lowscores;
	}

	/*
	 * This method will find the highest score and store it in an array names
	 * highscores.
	 */
	public int[] findhigh(Student[] a) {
		// Initialize all low scores to Integer.MIN_VALUE
		for (int i = 0; i < highscores.length; i++) {
			highscores[i] = Integer.MIN_VALUE;
		}

		// Loop through all students, replace if found lower score
		for (int i = 0; i < a.length; i++) {
			if (a[i] == null) {
				break;
			}
			for (int j = 0; j < highscores.length; j++) {
				if (a[i].getScoreOfQuiz(j) > highscores[j]) {
					highscores[j] = a[i].getScoreOfQuiz(j);
				}
			}
		}

		return highscores;
	}

	/*
	 * This method will find avg score for each quiz and store it in an array
	 * names avgscores.
	 */
	public float[] findavg(Student[] a) {
		int totalStudents = 0;

		// Initialize avgscores to all 0
		for (int i = 0; i < avgscores.length; i++) {
			avgscores[i] = 0;
		}

		// Add each score to its corresponding avgscore
		for (int i = 0; i < a.length; i++) {
			if (a[i] == null) {
				break;
			}
			for (int j = 0; j < avgscores.length; j++) {
				avgscores[j] += a[i].getScoreOfQuiz(j);
			}
			totalStudents++;
		}

		// Divide sum by total number of students to get the average
		for (int i = 0; i < avgscores.length; i++) {
			avgscores[i] = avgscores[i] / totalStudents;
		}

		return avgscores;
	}

	@Override
	public void print() {
		StringBuilder sb = new StringBuilder();
		// high score
		sb.append("High Score");
		for (int score : highscores) {
			sb.append(" ").append(score);
		}
		sb.append("\n");

		// low score
		sb.append("Low Score");
		for (int score : lowscores) {
			sb.append(" ").append(score);
		}
		sb.append("\n");

		// average score
		NumberFormat formatter = new DecimalFormat("#0.0");
		sb.append("Average");
		for (float score : avgscores) {
			sb.append(" ").append(formatter.format(score));
		}
		sb.append("\n");

		System.out.print(sb.toString());
	}
}
