package yipengy.com.studentscore.model;

/**
 * Student class represents each student.
 * @author yipengy
 *
 */
public class Student implements Printable {
	private int SID;
	private int scores[] = new int[5];

	public Student() {
		this(0);
	}

	public Student(int id) {
		this(id, new int[5]);
	}

	public Student(int SID, int[] scores) {
		this.SID = SID;
		this.scores = scores;
	}

	public int getSID() {
		return SID;
	}

	public void setSID(int sID) {
		SID = sID;
	}

	public int[] getScores() {
		return scores;
	}

	public void setScores(int scores[]) {
		this.scores = scores;
	}

	public int getScoreOfQuiz(int quiz) {
		if (quiz < 0 || quiz >= scores.length) {
			System.out.println("Error: quiz out of range");
			return -1;
		}
		return scores[quiz];
	}

	@Override
	public void print() {
		StringBuilder sb = new StringBuilder();
		sb.append(SID);

		for (int i = 0; i < scores.length; i++) {
			sb.append(" ").append(scores[i]);
		}
		sb.append("\n");
		System.out.print(sb.toString());
	}
}
