package reentrant_read_write_lock;

import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*	Explanation:
	If we use the ReentrantLock lock = new ReentrantLock() the execution time will be approximately 3 seconds.
	But when using ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock() = the execution time is
	approximately 1.5 seconds
*/
public class InventoryDatabase {
	private final TreeMap<Integer, Integer> priceToCountMap = new TreeMap<>();

	private final ReentrantLock lock = new ReentrantLock();

	private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private final Lock readLock = readWriteLock.readLock();
	private final Lock writeLock = readWriteLock.writeLock();

	public int getNumberOfItemsInPriceRange(final int lowerBound, final int upperBound) {
		readLock.lock();
		try {
			final var fromKey = priceToCountMap.ceilingKey(lowerBound);
			final var toKey = priceToCountMap.floorKey(upperBound);

			if (fromKey == null || toKey == null) {
				return 0;
			}

			final var rangeOfPrices =
					priceToCountMap.subMap(fromKey, true, toKey, true);

			int sum = 0;

			for (Integer numberOfItemsForPrice : rangeOfPrices.values()) {
				sum += numberOfItemsForPrice;
			}

			return sum;
		} finally {
			readLock.unlock();
		}
	}

	public void addItem(final int price) {
		writeLock.lock();
		try	{
			priceToCountMap.merge(price, 1, Integer::sum);
		} finally {
			writeLock.unlock();
		}
	}

	public void removeItem(final int price) {
		writeLock.lock();
		try {
			final var numberOfItemsForPrice = priceToCountMap.get(price);
			if (numberOfItemsForPrice == null || numberOfItemsForPrice == 1) {
				priceToCountMap.remove(price);
			} else {
				priceToCountMap.put(price, numberOfItemsForPrice - 1);
			}
		} finally {
			writeLock.unlock();
		}
	}
}
