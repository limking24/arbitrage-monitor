package assignment;

public interface ArbitrageEventListener {
	
	void onArbitrageDetected(String cp, MarketPrices prices);
	
}
