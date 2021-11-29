package atomic_reference.lock_free_stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

	public static void main(String[] args) throws InterruptedException {
//		final StandardStack<Integer> stack = new StandardStack<>();
		final LockFreeStack<Integer> stack = new LockFreeStack<>();
		final Random random = new Random();

		for (int i = 0; i < 100000; i++) {
			stack.push(random.nextInt());
		}

		List<Thread> threads = new ArrayList<>();

		final int pushingThreads = 2;
		final int poppingThreads = 2;

		for (int i = 0; i < pushingThreads; i++) {
			final Thread thread = new Thread(() -> {
				while (true) {
					stack.push(random.nextInt());
				}
			});
			thread.setDaemon(true);
			threads.add(thread);
		}

		for (int i = 0; i < poppingThreads; i++) {
			final Thread thread = new Thread(() -> {
				while (true) {
					stack.pop();
				}
			});
			thread.setDaemon(true);
			threads.add(thread);
		}

		threads.forEach(Thread::start);
		Thread.sleep(10000);

		System.out.printf("%d operations were performed in 10 seconds", stack.getCounter());
	}
}
