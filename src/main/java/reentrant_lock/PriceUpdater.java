package reentrant_lock;

import java.util.Random;

public class PriceUpdater extends Thread {
	private final PriceContainer priceContainer;
	private final Random random = new Random();

	public PriceUpdater(final PriceContainer priceContainer) {
		this.priceContainer = priceContainer;
	}

	@Override
	public void run() {
		while (true) {
			priceContainer.locker.lock();

			try {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				}
				priceContainer.setBitcoin(random.nextInt(20000));
				priceContainer.setEther(random.nextInt(2000));
				priceContainer.setLitecoin(random.nextInt(500));
				priceContainer.setBitcoinCash(random.nextInt(5000));
				priceContainer.setRipple(random.nextDouble());
			} finally {
				priceContainer.locker.unlock();
			}

			try	{
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
