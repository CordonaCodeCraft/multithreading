package reentrant_lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PriceContainer {
	public final Lock locker = new ReentrantLock();

	private volatile double bitcoin;
	private volatile double ether;
	private volatile double litecoin;
	private volatile double bitcoinCash;
	private volatile double ripple;

	public double getBitcoin() {
		return bitcoin;
	}

	public void setBitcoin(double bitcoin) {
		this.bitcoin = bitcoin;
	}

	public double getEther() {
		return ether;
	}

	public void setEther(double ether) {
		this.ether = ether;
	}

	public double getLitecoin() {
		return litecoin;
	}

	public void setLitecoin(double litecoin) {
		this.litecoin = litecoin;
	}

	public double getBitcoinCash() {
		return bitcoinCash;
	}

	public void setBitcoinCash(double bitcoinCash) {
		this.bitcoinCash = bitcoinCash;
	}

	public double getRipple() {
		return ripple;
	}

	public void setRipple(double ripple) {
		this.ripple = ripple;
	}
}
