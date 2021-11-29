package atomic_reference.lock_free_stack;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

public class LockFreeStack<T> {
	private AtomicReference<StackNode<T>> head = new AtomicReference<>();
	private AtomicInteger counter = new AtomicInteger(0);

	public void push(T value) {
		StackNode<T> newHead = new StackNode<>(value);

		while (true) {
			StackNode<T> currentHead = head.get();
			newHead.next = currentHead;
			if (head.compareAndSet(currentHead, newHead)) {
				break;
			} else {
				LockSupport.parkNanos(1);
			}
		}
		counter.incrementAndGet();
	}

	public T pop() {
		StackNode<T> currentHead = head.get();
		StackNode<T> newHead;
		while (currentHead != null) {
			newHead = currentHead.next;
			if (head.compareAndSet(currentHead, newHead)) {
				break;
			} else {
				LockSupport.parkNanos(1);
				currentHead = head.get();
			}
		}
		counter.incrementAndGet();
		return currentHead != null ? currentHead.value : null;
	}

	public int getCounter() {
		return counter.get();
	}
}
