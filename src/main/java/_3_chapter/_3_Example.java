package _3_chapter;

import java.math.BigInteger;

public class _3_Example {

	public static void main(String[] args) {
		final Thread thread = new Thread(new LongComputationTask(BigInteger.valueOf(2132322323), BigInteger.valueOf(341443143)));
		thread.setDaemon(true);
		thread.start();
		thread.interrupt();

	}

	private static class LongComputationTask implements Runnable {
		private final BigInteger base;
		private final BigInteger power;

		public LongComputationTask(final BigInteger base, final BigInteger power) {
			this.base = base;
			this.power = power;
		}

		@Override
		public void run() {
			final BigInteger product = pow(base, power);
			System.out.printf("%s^%s = %s", base, power, product);
		}

		private BigInteger pow(final BigInteger base, final BigInteger power) {
			BigInteger result = BigInteger.ONE;

			for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
				result = result.multiply(base);

			}
			return result;
		}
	}
}

