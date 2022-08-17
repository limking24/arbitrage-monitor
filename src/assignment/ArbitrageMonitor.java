package assignment;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Detects arbitrage situation when bid > ask for a currency pair
 */
public class ArbitrageMonitor {
	
	private static Logger logger = LogManager.getLogger();

    public static void main(String [ ] args) {
        try {
        	long startTime = System.currentTimeMillis();
            FilePriceReader reader = new FilePriceReader("prices.csv");
            ArbitrageEventNotification notification = new ArbitrageEventNotification();
            PriceTracker tracker = new PriceTracker()
            							.addArbitrageEventListener("USD/CAD", notification)
            							.addArbitrageEventListener("USD/JPY", notification);
            
            logger.info("Finish setting up listeners. Time used: " + (System.currentTimeMillis() - startTime) + "ms");
            startTime = System.currentTimeMillis();
            
            // Example of reading in the prices
            FilePriceReader.Price nextPrice;
            while ((nextPrice = reader.getNextPrice()) != null) {
            	System.out.println("Reading in next price > " + nextPrice);
            	tracker.updatePrice(nextPrice.cp, nextPrice.bidAsk, nextPrice.price);
            }
            
            logger.info("Finish reading in the prices. Time used: " + (System.currentTimeMillis() - startTime) + "ms");
        }
        catch (FileNotFoundException fne) {
            System.out.println("File prices.csv not found");
        }
        catch (IOException ioe) {
            System.out.println("Error reading file " + ioe.getMessage());
        }
    }
}
