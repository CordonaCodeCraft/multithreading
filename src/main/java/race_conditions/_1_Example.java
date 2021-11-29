package race_conditions;

public class _1_Example {

	public static void main(String[] args) {

		final SharedClass sharedClass = new SharedClass();

		final Thread incrementingThread = new Thread(() -> {
			for (int i = 0; i < Integer.MAX_VALUE; i++) {
				sharedClass.increment();
			}
		});

		final Thread inspectorThread = new Thread(() -> {
			for (int i = 0; i < Integer.MAX_VALUE; i++) {
				sharedClass.checkForDataRace();
			}
		});

		incrementingThread.start();
		inspectorThread.start();

	}

	public static class SharedClass {
		private volatile int x = 0;
		private volatile int y = 0;

		public void increment() {
			x++;
			y++;
		}

		public void checkForDataRace() {
			if (y > x) {
				System.out.println("y > x - Data Race is detected");
			}
		}
	}
}
