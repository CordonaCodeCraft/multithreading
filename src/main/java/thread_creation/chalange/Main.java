package thread_creation.chalange;

import java.util.List;

public class Main {
	public static void main(String[] args) {
		List<Thread> threads = List.of(
				new CustomThread("First thread"),
				new CustomThread("Second thread"),
				new CustomThread("Third thread"));

		final MultiExecutor executor = new MultiExecutor(threads);
		executor.executeAll();
	}

	private static class CustomThread extends Thread {
		public CustomThread(final String name) {
			this.setName(name);
			this.setPriority(MAX_PRIORITY);
		}

		@Override
		public void start() {
			System.out.printf("Starting thread %s%n", this.getName());
			super.start();
		}

		@Override
		public void run() {
			System.out.printf("Executing code for thread: %s%n", this.getName());
			super.run();
		}
	}
}
