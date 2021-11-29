package threads_synchronization;

/*	Explanation:
	This example solves the problem with atomicity from the previous section (sharing data between threads) with two
	locking mechanisms (notice, that the both join() methods are called after the two start() methods are being called);

		1)  By using a locking object (monitor).
			The benefit of this approach is that if there is a small section of the code, that must not be executed
			concurrently - we can achieve atomicity only for these lines of code.

		2)	By using the synchronized keyword on the method, containing critical section.
			The problem with this approach is, that we are locking the entire method even when not all the logic must
			be executed atomically.
			The other problem is that if we synchronize two methods - they will be both locked for access for another
			thread even if the first thread is executing only one of these methods.


*/

public class _1_Example {
	public static void main(String[] args) throws InterruptedException {
		final InventoryCounter inventoryCounter = new InventoryCounter();
		final IncrementingThread incrementingThread = new IncrementingThread(inventoryCounter);
		final DecrementingThread decrementingThread = new DecrementingThread(inventoryCounter);

		incrementingThread.start();
		decrementingThread.start();

		incrementingThread.join();
		decrementingThread.join();

		System.out.printf("We currently have %d items", inventoryCounter.getItems());

	}

	public static class IncrementingThread extends Thread {
		private final InventoryCounter inventoryCounter;

		public IncrementingThread(final InventoryCounter inventoryCounter) {
			this.inventoryCounter = inventoryCounter;
		}

		@Override
		public void run() {
			for (int i = 0; i < 1000; i++) {
				inventoryCounter.increment();
			}
		}
	}

	public static class DecrementingThread extends Thread {
		private final InventoryCounter inventoryCounter;

		public DecrementingThread(final InventoryCounter inventoryCounter) {
			this.inventoryCounter = inventoryCounter;
		}

		@Override
		public void run() {
			for (int i = 0; i < 1000; i++) {
				inventoryCounter.decrement();
			}
		}
	}

	private static class InventoryCounter {
		private int items = 0;
		private final Object lock = new Object();

		public void increment() {
			synchronized (lock) {
				items++;
			}
		}

		public synchronized void decrement() {
			items--;
		}

		public synchronized int getItems() {
			synchronized (lock) {
				return items;
			}
		}
	}
}
