package assignment;

public class MarketPrices {

	private Double ask;
	private Double bid;
	
	/**
	 * Create an MarketPrices instance with unknown ask & bid prices.
	 */
	public MarketPrices() {
		ask = -1.0;
		bid = -1.0;
	}

	/**
	 * Get the ask price. -1 if unknown.
	 * 
	 * @return ask price or -1 if unknown.
	 */
	public Double getAsk() {
		return ask;
	}

	/**
	 * Set the ask price.
	 * 
	 * @param ask
	 */
	public void setAsk(Double ask) {
		this.ask = ask;
	}

	/**
	 * Get the bid price, or -1 if unknown.
	 * 
	 * @return bid price or -1 if unknown.
	 */
	public Double getBid() {
		return bid;
	}

	/**
	 * Set the bid price.
	 * 
	 * @param bid the bid price
	 */
	public void setBid(Double bid) {
		this.bid = bid;
	}
	
}
