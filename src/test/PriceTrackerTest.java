package test;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import assignment.ArbitrageEventListener;
import assignment.PriceTracker;

@ExtendWith(MockitoExtension.class)
public class PriceTrackerTest {
	
	private static String USD_JPY = "USD/JPY";
	
	private static String USD_CAD = "USD/CAD";

	@Mock
	private ArbitrageEventListener usdJpyListener;

	@Mock
	private ArbitrageEventListener usdCadListener;

	private PriceTracker tracker;
	
	@BeforeEach
	public void before() {
		tracker = new PriceTracker()
						.addArbitrageEventListener(USD_JPY, usdJpyListener)
						.addArbitrageEventListener(USD_CAD, usdCadListener);
	}

	@Test
	public void usd_jpy_arbitrage_one_time() {
		tracker.updatePrice(USD_JPY, "BID", 1.0);
		tracker.updatePrice(USD_JPY, "ASK", 1.2);
		tracker.updatePrice(USD_JPY, "BID", 1.5);
		verify(usdJpyListener, times(1)).onArbitrageDetected(eq(USD_JPY), notNull());
		verify(usdCadListener, times(0)).onArbitrageDetected(eq(USD_CAD), notNull());
	}
	
	@Test
	public void usd_cad_arbitrage_two_times() {
		tracker.updatePrice(USD_JPY, "BID", 1.0);
		tracker.updatePrice(USD_JPY, "ASK", 1.2);
		tracker.updatePrice(USD_JPY, "ASK", 1.6);
		tracker.updatePrice(USD_JPY, "BID", 1.5);
		tracker.updatePrice(USD_JPY, "BID", 1.0);
		tracker.updatePrice(USD_CAD, "BID", 1.0);
		tracker.updatePrice(USD_CAD, "ASK", 1.0);
		tracker.updatePrice(USD_CAD, "BID", 1.2);
		tracker.updatePrice(USD_CAD, "ASK", 1.7);
		tracker.updatePrice(USD_CAD, "BID", 1.6);
		tracker.updatePrice(USD_CAD, "ASK", 1.2);
		tracker.updatePrice(USD_CAD, "BID", 1.2);
		verify(usdJpyListener, times(0)).onArbitrageDetected(eq(USD_JPY), notNull());
		verify(usdCadListener, times(2)).onArbitrageDetected(eq(USD_CAD), notNull());
	}

}
