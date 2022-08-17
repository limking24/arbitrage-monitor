package assignment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Reads prices from a file which has the format [Currency Pair, BID|ASK, price]. Where Currency Pair = base/terms (eg. USD/CAD)
 */
public class FilePriceReader {

    public class Price {
        String cp;
        String bidAsk;
        Double price;

        Price(String cp, String bidAsk, Double price) {
            this.cp = cp;
            this.bidAsk = bidAsk;
            this.price = price;
        }

        public String toString() {
            return cp + " " + bidAsk + " " + price;
        }
    }

    private final BufferedReader in;

    public FilePriceReader(String fileName) throws FileNotFoundException {
        in = new BufferedReader(new FileReader(fileName));
    }

    public Price getNextPrice() throws IOException {
        String line = in.readLine();
        if (line == null) {
            in.close();
            return null;
        }

        String[] tokens = line.split(",");
        if (tokens.length != 3) { throw new IOException("Invalid line format " + line); }
        if (!(tokens[1].equals("BID") || tokens[1].equals("ASK"))) { throw new IOException("Invalid Bid/Ask identifier " + tokens[1]); }

        String cp = tokens[0];
        String bidAsk = tokens[1];
        Double price = Double.valueOf(tokens[2]);
        return new Price(cp, bidAsk, price);
    }
}
