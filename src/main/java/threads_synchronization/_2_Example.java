package threads_synchronization;

import java.util.Random;

public class _2_Example {

	public static void main(String[] args) {
		final Metrics metrics = new Metrics();
		final BusinessLogic thread1 = new BusinessLogic(metrics);
		final BusinessLogic thread2 = new BusinessLogic(metrics);
		final MetricsPrinter printer = new MetricsPrinter(metrics);

		thread1.start();
		thread2.start();
		printer.start();
	}

	public static class MetricsPrinter extends Thread {
		private final Metrics metrics;

		public MetricsPrinter(final Metrics metrics) {
			this.metrics = metrics;
		}

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				}

				final double currentAverage = metrics.getAverage();
				System.out.printf("Current average is: %f%n", currentAverage);
			}
		}
	}


	public static class BusinessLogic extends Thread {
		final private Metrics metrics;
		final Random random = new Random();

		public BusinessLogic(final Metrics metrics) {
			this.metrics = metrics;
		}

		@Override
		public void run() {
			while (true) {
				final long start = System.currentTimeMillis();

				try {
					Thread.sleep(random.nextInt(10));
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				}

				final long end = System.currentTimeMillis();

				metrics.addSample(end - start);
			}
		}

	}

	public static class Metrics {
		private long count = 0;
		private volatile double average = 0.0;

		public synchronized void addSample(final long sample) {
			final double currentSum = average * count;
			count++;
			average = (currentSum + sample) / count;
		}

		public double getAverage() {
			return average;
		}
	}
}
