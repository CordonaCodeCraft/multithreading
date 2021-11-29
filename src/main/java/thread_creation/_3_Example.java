package thread_creation;

import java.util.List;
import java.util.Random;

public class _3_Example {
	public static final int MAX_PASSWORD = 9999;

	public static void main(String[] args) {
		final Random random = new Random();
		final Vault vault = new Vault(random.nextInt(MAX_PASSWORD));
		final List<Thread> threads = List.of(
				new AscendingHackerThread(vault),
				new DescendingHackerThread(vault),
				new PoliceThread());

		for (Thread thread : threads) {
			thread.start();
		}
	}

	private static class Vault {
		private final int password;

		public Vault(final int password) {
			this.password = password;
		}

		public boolean isCorrectPassword(final int guess) {
			try {
				Thread.sleep(5);
			} catch (InterruptedException exception) {
				System.out.println(exception.getMessage());
			}
			return this.password == guess;
		}
	}

	private static abstract class HackerThread extends Thread {
		protected final Vault vault;

		public HackerThread(final Vault vault) {
			this.vault = vault;
			this.setName(this.getClass().getSimpleName());
			this.setPriority(MAX_PRIORITY);
		}

		@Override
		public void start() {
			System.out.printf("Starting thread %s%n", this.getName());
			super.start();
		}
	}

	private static class AscendingHackerThread extends HackerThread {

		public AscendingHackerThread(final Vault vault) {
			super(vault);
		}

		@Override
		public void run() {
			for (int guess = 0; guess < MAX_PASSWORD; guess++) {
				if (vault.isCorrectPassword(guess)) {
					System.out.printf("%s guessed the password: %d%n", this.getName(), guess);
					System.exit(0);
				}
			}
		}
	}

	private static class DescendingHackerThread extends HackerThread {

		public DescendingHackerThread(Vault vault) {
			super(vault);
		}

		@Override
		public void run() {
			for (int guess = MAX_PASSWORD; guess >= 0; guess--) {
				if (vault.isCorrectPassword(guess)) {
					System.out.printf("%s guessed the password: %d%n", this.getName(), guess);
					System.exit(0);
				}
			}
		}
	}

	private static class PoliceThread extends Thread {

		public PoliceThread() {
			this.setName("Police Thread");
		}

		@Override
		public void run() {
			for (int i = 10; i > 0; i--) {
				System.out.println(i);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException exception) {
					System.out.println(exception.getMessage());
				}
			}
			System.out.println("Game over for you hackers");
			System.exit(0);
		}
	}
}


