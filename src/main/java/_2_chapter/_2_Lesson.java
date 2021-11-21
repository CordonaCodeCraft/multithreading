package _2_chapter;

/*
* This lesson demonstrates a new way of creating a thread with a private class. The benefit of this approach is, that
* we can call thread related methods by this.method() declaration instead of relying on static methods.
* */

public class _2_Lesson {

	public static void main(String[] args) {
		final Thread newThread = new NewThread();
		newThread.start();
	}

	private static class NewThread extends Thread {
		@Override
		public void run() {
			this.setName("New thread");
			this.setPriority(MAX_PRIORITY);
			System.out.printf("Executing code in thread: %s", this.getName());
		}

	}
}
