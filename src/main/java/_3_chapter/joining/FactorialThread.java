package _3_chapter.joining;

import java.math.BigInteger;

public class FactorialThread extends Thread {
	private final long inputNumber;
	private BigInteger result;
	private boolean isFinished = false;

	public FactorialThread(final long inputNumber) {
		this.inputNumber = inputNumber;
	}

	@Override
	public void run() {
		result = factorial(inputNumber);
		isFinished = true;
	}

	private BigInteger factorial(final long n) {
		BigInteger result = BigInteger.ONE;
		for (long i = n; i > 0; i--) {
			result = result.multiply(BigInteger.valueOf(n));
		}
		return result;
	}

	public boolean isFinished() {
		return isFinished;
	}

	public BigInteger getResult() {
		return result;
	}
}
