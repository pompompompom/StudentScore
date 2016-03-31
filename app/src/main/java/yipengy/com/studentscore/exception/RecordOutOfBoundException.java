package yipengy.com.studentscore.exception;

/**
 * This exception is thrown when the number of input in the student data file
 * exceeds the maximum allowable number of input.
 * 
 * @author yipengy
 *
 */
public class RecordOutOfBoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message = "";

	public RecordOutOfBoundException(String message) {
		super(message);
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return message;
	}
}
