package reentrant_read_write_lock;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
	public static final int HIGHEST_PRICE = 1000;

	public static void main(String[] arg) throws InterruptedException {
		final var inventoryDatabase = new InventoryDatabase();
		final var random = new Random();

		for (int i = 0; i < 100000; i++) {
			inventoryDatabase.addItem(random.nextInt(HIGHEST_PRICE));
		}

		final var writer = new Thread(() -> {
			while (true) {
				inventoryDatabase.addItem(random.nextInt(HIGHEST_PRICE));
				inventoryDatabase.removeItem(random.nextInt(HIGHEST_PRICE));

				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				}
			}
		});

		writer.setDaemon(true);
		writer.start();

		final var readersCount = 7;
		final List<Thread> readers = new ArrayList<>();

		for (int readerIndex = 0; readerIndex < readersCount; readerIndex++) {
			final var reader = new Thread(() -> {
				for (int i = 0; i < 100000; i++) {
					final var upperBoundPrice = random.nextInt(HIGHEST_PRICE);
					final var lowerBoundPrice = upperBoundPrice > 0 ? random.nextInt(upperBoundPrice) : 0;
					inventoryDatabase.getNumberOfItemsInPriceRange(lowerBoundPrice, upperBoundPrice);
				}
			});

			reader.setDaemon(true);
			readers.add(reader);
		}

		final var startReadingTime = System.currentTimeMillis();

		for (Thread reader : readers) {
			reader.start();
		}

		for (Thread reader : readers) {
			reader.join();
		}

		final var endReadingTime = System.currentTimeMillis();

		System.out.printf("Reading took %d ms", endReadingTime - startReadingTime);
	}
}
