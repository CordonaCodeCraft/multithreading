package sharing_data_between_threads;

/*	Explanation:
	items++ and items-- are not atomic operations.
	An atomic operation is an operation which is performed as a single unit of work without the possibility to be
	interfered by another operation.
	Each thread is working with the InventoryCounter object, which is stored in the heap and have access to the members
	of this class.
	If we do not synchronize the incrementing and decrementing thread - we will have unpredictable results, because the
	responsibility for scheduling the execution of these threads is out of our control and belongs to the OS.
	Therefore - in the bellow example we use the .join() method.
	The calling thread (the main thread) will sleep until the joined thread returns a result.
	Given the implemented logic - the desired result is 0.
	But if we call the join() method after we start the both threads - each time we execute main, we will have different
	result.
*/

public class _1_Example {
	public static void main(String[] args) throws InterruptedException {
		 final InventoryCounter inventoryCounter = new InventoryCounter();
		 final IncrementingThread incrementingThread = new IncrementingThread(inventoryCounter);
		 final DecrementingThread decrementingThread = new DecrementingThread(inventoryCounter);

		incrementingThread.start();
		incrementingThread.join();
		decrementingThread.start();
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

		public void increment() {
			items++;
		}

		public void decrement() {
			items--;
		}

		public int getItems() {
			return items;
		}
	}
}
