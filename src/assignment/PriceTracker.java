package assignment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PriceTracker {

	/**
	 * Key = Currency Pair <br/>
	 * Value = Bid & Ask Prices
	 */
	private Map<String, MarketPrices> pricesMap;
	
	/**
	 * Key = Currency Pair <br/>
	 * Value = Arbitrage Event Listeners
	 */
	private Map<String, List<ArbitrageEventListener>> arbitrageListeners;
	
	public PriceTracker() {
		pricesMap = new HashMap<>();
		arbitrageListeners = new HashMap<>();
	}
	
	/**
	 * Update the current ASK/BID price. Subscribed ArbitrageEventListeners 
	 * will be invoked when the BID price is higher than the ASK price.
	 * 
	 * @param cp currency pair
	 * @param bidAsk BID/ASK price to be updated
	 * @param price the current price
	 */
	public void updatePrice(String cp, String bidAsk, Double price) {
		MarketPrices prices = pricesMap.computeIfAbsent(cp, key -> new MarketPrices());
		
		if (bidAsk.equalsIgnoreCase("bid")) {
			prices.setBid(price);
		} else {
			prices.setAsk(price);
		}
		
		if (prices.getBid() != -1 && prices.getAsk() != -1) {
			if (prices.getBid() > prices.getAsk()) {
				if (arbitrageListeners.containsKey(cp)) {
					arbitrageListeners
						.get(cp)
						.forEach(listener -> listener.onArbitrageDetected(cp, prices));
				}
			}
		}
	}
	
	/**
	 * Add a new ArbitrageEventListener
	 * 
	 * @param cp currency pair
	 * @param listener the ArbitrageEventListener
	 * @return the PriceTracker itself
	 */
	public PriceTracker addArbitrageEventListener(String cp, ArbitrageEventListener listener) {
		arbitrageListeners
			.computeIfAbsent(cp, key -> new ArrayList<>())
			.add(listener);
		return this;
	}
	
}
