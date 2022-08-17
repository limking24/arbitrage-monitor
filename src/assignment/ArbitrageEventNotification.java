package assignment;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArbitrageEventNotification implements ArbitrageEventListener {
	
	private static Logger logger = LogManager.getLogger();
	
	@Override
	public void onArbitrageDetected(String cp, MarketPrices prices) {
		logger.info("Arbitrage event detected - " + cp + ", ASK: " + prices.getAsk() + ", BID: " + prices.getBid());
	}

}
