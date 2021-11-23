package _2_chapter;

import static java.lang.Thread.*;

public class _1_Example {

	public static void main(String[] args) {
		Thread thread = new Thread(() -> {
			System.out.printf("Executing code in thread %s%n", currentThread().getName());
			System.out.printf("Thread priority is %s%n", currentThread().getPriority());
			throw new RuntimeException("Intentional Exception");
		});

		thread.setName("New thread");
		thread.setPriority(MAX_PRIORITY);
		thread.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.out.printf("A critical error happened in thread: %s. The error is %s", t.getName(), e.getMessage());
			}
		});

		System.out.printf("We are in thread: %s before starting a new thread%n", currentThread().getId());
		thread.start();
		System.out.printf("We are in thread: %s after starting a new thread%n", currentThread().getId());
	}
}
