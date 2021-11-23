package _3_chapter.joining;

import java.util.ArrayList;
import java.util.List;

/*	Explanation:
*	In this example we are observing race condition between the main thread and the factorial threads.
* 	We have no control over the execution of the threads, the execution of which is being prioritized by the OS.
*
* 	Three scenarios might occur;
* 		1) All the threads might complete and produce result;
* 		2) The threads might run concurrently (one after another);
* 		3) The threads might run in parallel.
*
* 	In particular - the race condition is happening between the main thread when calling the thread.isFinished() method
* 	and the calculation, performed in each factorial thread.
*
* 	1) 	When calling the thread.join() method - this will guarantee, that the main thread will exit the program only and
* 		after each thread completes its work.
* 		If we comment out the thread.join() method call - we will observe how the threads "randomly" return a result.
*
* 	2)	The first input number is really large and computing the result will take massive time to be calculated.
* 		The program will not exit and will keep waiting for the particular thread (at threads(0)) to return a result.
* 		Such behaviour must be managed by:
* 			a)  Passing an argument in the thread.join() method - how much time we can tolerate for each thread to return
* 				a result.
* 				Passing this argument will not suffice, because the program is not instructed how to handle the case
* 				where the threshold is being reached
* 			b)	We must either interrupt the thread, ot set the factorial threads to be daemons (even if a particular
* 				thread did not return a result - this will not prevent the program from exiting)
*
* 	Summary:
* 	1) Do not rely on the order of execution of the threads
* 	2) Always use thread coordination
* 	3) Design code for worst case scenario
* 	4) Threads may take unreasonably long time
* 	5) Always use Thread.join() with a time limit
* 	6) Stop the thread if it is not done in time
* */
public class Main {

	public static void main(String[] args) throws InterruptedException {
		final List<Long> inputNumbers = List.of(1000000000L, 0L, 3435L, 35435L, 2324L, 4655L, 23L, 2335L, 5566L);

		final List<FactorialThread> threads = new ArrayList<>();

		inputNumbers.forEach(n -> threads.add(new FactorialThread(n)));

		for (FactorialThread thread : threads) {
			thread.setDaemon(true);
			thread.start();
		}

		for (FactorialThread thread : threads) {
				thread.join(2000);
		}

		for (int i = 0; i < inputNumbers.size(); i++) {
			final long number = inputNumbers.get(i);
			final FactorialThread thread = threads.get(i);
			if (thread.isFinished()) {
				System.out.printf("Factorial of %d is %s%n", number, thread.getResult());
			} else {
				System.out.printf("The calculation for %d is still in progress%n", number);
			}
		}
	}

}
