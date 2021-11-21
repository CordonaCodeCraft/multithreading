package _2_chapter.chalange;

import java.util.List;

public class MultiExecutor {
	private final List<Thread> tasks;

	public MultiExecutor(final List<Thread> tasks) {
		this.tasks = tasks;

	}

	public void executeAll() {
		for (Thread task : tasks) {
			task.start();
		}
	}
}
