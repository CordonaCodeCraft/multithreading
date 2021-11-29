package threads_coordination;

public class _1_Example {
	public static void main(String[] args) {
		final Thread thread = new Thread(new BlockingTask());
		thread.start();
		thread.interrupt();
	}

	private static class BlockingTask implements Runnable {
		@Override
		public void run() {
			try {
				Thread.sleep(500000);
			} catch (InterruptedException e) {
				System.out.println("Exiting blocking thread");
			}
		}
	}
}
