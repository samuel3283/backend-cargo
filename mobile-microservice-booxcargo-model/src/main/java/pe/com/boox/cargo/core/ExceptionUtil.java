package pe.com.boox.cargo.core;

public class ExceptionUtil {
	/**
	 * Walk down the causal chain to find first exception
	 * 
	 * @param throwable
	 *            Exception to start with
	 * @return the root cause exception
	 */
	public static Throwable fetchRootCause(Throwable throwable) {
		Throwable follower = null;

		// Walk down the causal linked list
		while (throwable != null) {
			follower = throwable;
			throwable = throwable.getCause();
		}

		return follower;
	}

	public static String toString(Throwable aThrowable) {
		final StringBuilder tStringBuilder = new StringBuilder();
		printStackTrace(aThrowable, "\n", tStringBuilder);
		return tStringBuilder.toString();
	}

	private static void printStackTrace(Throwable aThrowable, String aNewLine,
			StringBuilder aStringBuilder) {
		aStringBuilder.append(aThrowable.getClass().getName());
		aStringBuilder.append(": ");
		aStringBuilder.append(aThrowable.getMessage());
		aStringBuilder.append(aNewLine);

		for (StackTraceElement tElement : aThrowable.getStackTrace()) {
			aStringBuilder.append("\tat ");
			aStringBuilder.append(tElement.getClassName());
			aStringBuilder.append(".");
			aStringBuilder.append(tElement.getMethodName());
			aStringBuilder.append("(");
			aStringBuilder.append(tElement.getFileName());
			aStringBuilder.append(":");
			aStringBuilder.append(tElement.getLineNumber());
			aStringBuilder.append(")");
			aStringBuilder.append(aNewLine);
		}

		Throwable tCause = aThrowable.getCause();
		if (tCause != null) {
			aStringBuilder.append("Caused by: ");
			printStackTrace(tCause, aNewLine, aStringBuilder);
		}
	}
}