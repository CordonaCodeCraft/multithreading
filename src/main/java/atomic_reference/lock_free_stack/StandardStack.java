package atomic_reference.lock_free_stack;

public class StandardStack<T> {
	private StackNode<T> head;
	private int counter = 0;

	public synchronized void push(T value) {
		StackNode<T> newHead = new StackNode<>(value);
		newHead.next = head;
		head = newHead;
		counter++;
	}

	public synchronized T pop() {
		if (head == null) {
			counter++;
			return null;
		}

		T value = head.value;
		head = head.next;
		counter++;
		return value;
	}

	public int getCounter() {
		return counter;
	}


}
