/*
Part 2: Find the lowest and highest prices

File               Lowest price    When    Highest price   When
tiny.txt               $30.00     Mar 2020    $70.00    Jul 2020
rising.txt             $20.00     Jan 2010    $48.14    Dec 2014
falling.txt            $30.36     Dec 2014   $100.00    Jan 2010
zigzag.txt             $35.00     Jul 2010    $65.00    Mar 2010
aapl_2000_2010.txt      $7.07     Mar 2003   $223.02    Mar 2010
amzn_2000_2010.txt      $5.97     Sep 2001   $135.91    Nov 2009
msft_2000_2010.txt     $15.81     Feb 2009    $43.22    Mar 2000
ibm_2000_2010.txt      $53.01     Sep 2002   $130.32    Dec 2009
goog_2004_2010.txt    $102.37     Aug 2004   $707.00    Oct 2007
sbux_2007_2016.txt      $4.09     Nov 2008    $62.16    Oct 2015
sp500_1925_1945.txt     $4.77     Jun 1932    $31.30    Sep 1929
sp500_1990_2025.txt   $307.12     Oct 1990  $6853.03    Dec 2025


1. For msft_2000_2010.txt, the highest price
   comes before the lowest price. What does
   that mean for someone who bought Microsoft
   stock in 2000?

   Someone who bought Microsoft stock in 2000 may have lost 
   money because the stock price decreased over the following years. 
   If they sold their stock at a lower price than 
   they bought it for, they would have lost money.


2. Look up what happened in the world around
   the lowest month for sbux_2007_2016.txt
   and sp500_1925_1945.txt.
   Does the timing make sense?

   Yes, the timing makes sense. Starbucks reached its lowest 
   price in November 2008 during the global financial crisis. 
   The S&P 500 reached its lowest price in June 1932 during the
   Great Depression. Both events caused economic problems
   and stock prices to fall.

										
										
Part 3 Step E: Compare the strategies

File                 Buy and hold   Dip buyer    Moving average   My strategy
tiny.txt              12,975.00     14,975.00     10,000.00      14,975.00
rising.txt            24,031.86     10,000.00     20,411.92      10,000.00
falling.txt            3,095.64      3,159.28     10,000.00       3,159.28
zigzag.txt            12,101.39     38,071.99      9,848.78      38,071.99
aapl_2000_2010.txt    85,865.80     36,610.49    114,231.31      36,610.49
amzn_2000_2010.txt    19,886.04     14,192.52     38,170.87      14,192.52
msft_2000_2010.txt     7,237.50     49,273.15      7,208.23      48,464.77
ibm_2000_2010.txt     12,467.97     27,998.86      7,549.75      23,566.23
goog_2004_2010.txt    54,398.54     13,655.41     18,706.20      13,655.41
sbux_2007_2016.txt    36,280.60     15,759.50     67,500.23      14,695.67
sp500_1925_1945.txt   16,362.00     11,483.45     37,195.81      10,795.38
sp500_1990_2025.txt  198,868.74     40,836.68    199,113.67      29,584.49


1. Which strategy did best on the most files?
   Was any strategy the best on every file?

   The moving average strategy did the best
   on the most files. It had the highest final value
   on 6 out of 12 files. No strategy was the best
   on every file because different stocks had
   different price patterns.


2. Pick a file where the dip buyer did badly.
   Use the printed trades to explain what went wrong.

   The dip buyer did badly on goog_2004_2010.txt.
   It ended with $13,655.41, while buy and hold
   ended with $54,398.54. It bought 55 shares at
   $180.51 in March 2005 and sold them at $220
   in April. Later, it bought 24 shares at $691.48
   in December 2007 and never sold them.
   It sold too early and bought at a high price,
   so it missed larger profits.


3. Pick a file where the moving average beat buy
   and hold. What happened to the stock that made
   the difference?

   Moving average did better on aapl_2000_2010.txt.
   It ended with $114,231.31, while buy and hold
   ended with $85,865.80. The stock price went
   up and down, so moving average used these
   changes to buy and sell at better times.


4. How did your strategy do against buy and hold?
   If you changed your rules to do better on one
   file, did they still work on the others?

   My strategy did better than buy and hold
   on 3 files, but worse on the other 9 files.
   I changed the selling target from 10% to 8%.
   My strategy did well on falling.txt, zigzag.txt,
   and msft_2000_2010.txt, but it did not work
   well on every file.
   
*/


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class StockTrader{

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("aapl_2000_2010.txt"));
        String symbol = in.next();
        int n = in.nextInt();

        // TODO: create the three arrays, each with n elements
        int[] years = new int[n];
        int[] months = new int[n];
        double[] prices = new double[n];
        
        // TODO: call loadData, then printData
        loadData(in, years, months, prices);
        printData(years, months, prices);
        int low  = indexOfLowest(prices);
        int high = indexOfHighest(prices);
        System.out.printf("%s lowest: $%.2f in %s %d\n", symbol.toUpperCase(), prices[low], monthName(months[low]), years[low]);
		System.out.printf("%s highst: $%.2f in %s %d\n", symbol.toUpperCase(), prices[high], monthName(months[high]), years[high]);
		
		double result = simulate(years, months, prices);

        System.out.printf("Final value: $%,.2f%n", result);
        
    }
    public static void loadData(Scanner in, int[] years, int[] months, double[] prices){
		for(int i = 0; i < years.length; i++){
			years[i] = in.nextInt();
			months[i] = in.nextInt();
			prices[i] = in.nextDouble();
		}
	}
	public static String monthName(int month){
		String[] names = {"Jan", "Feb", "Mar", 
						  "Apr", "May", "Jun",
						  "Jul", "Aug", "Sep", 
						  "Oct", "Nov", "Dec"};
		return names[month-1];
	}
	public static void printData(int[] years, int[] months, double[] prices){
		for(int i = 0; i < years.length; i++){
			System.out.printf("%s %d: $%.2f \n", monthName(months[i]), years[i], prices[i]);
		}
	}
	public static int indexOfHighest(double[] prices){
		int max = 0;
		for(int i = 1; i < prices.length; i++){
			if(prices[i] > prices[max]){
				max = i;
			}
		}
		return max;
	}
	public static int indexOfLowest(double[] prices){
        int min = 0;
		for(int i = 1; i < prices.length; i++){
			if(prices[i] < prices[min]){
				min = i;
			}
		}
		return min;
	}
	
	public static final double START_CASH = 10000.0;
	public static final double FEE = 10.0;

	public static double simulate(int[] years, int[] months, double[] prices) {
		double cash = START_CASH;
		int shares = 0;
		double boughtAt = 0.0;

		for (int i = 0; i < prices.length; i++) {
			double[] history = Arrays.copyOf(prices, i + 1);
			String action = decide(history, shares > 0, boughtAt);

			if (action.equals("BUY") && shares == 0) {
				int canBuy = (int) ((cash - FEE) / prices[i]);
					if (canBuy > 0) {
						shares = canBuy;
						cash = cash - shares * prices[i] - FEE;
						boughtAt = prices[i];
						System.out.printf("%s %d: BUY %d shares at $%.2f%n", monthName(months[i]), years[i], shares, prices[i]);
					}
			} 
			else if (action.equals("SELL") && shares > 0) {
				cash = cash + shares * prices[i] - FEE;
				System.out.printf("%s %d: SELL %d shares at $%.2f%n", monthName(months[i]), years[i], shares, prices[i]);
				shares = 0;
				boughtAt = 0.0;
			}
		}
		return cash + shares * prices[prices.length - 1];
	}
	
	public static String decide(double[] history, boolean holding, double boughtAt){		
		//return buyAndHold(history, holding, boughtAt);	
		return dipBuyer(history, holding, boughtAt);	
		//return movingAverage(history, holding, boughtAt);
		//return myStrategy(history, holding, boughtAt);
	}	
	public static String buyAndHold(double[] history, boolean holding, double boughtAt){
		return "BUY";
    }
    public static String dipBuyer(double[] history, boolean holding, double boughtAt){
		int n = history.length;
		if (!holding && n >= 3) {
			if (history[n - 1] < history[n - 2] && history[n - 2] < history[n - 3]){
				return "BUY";
            }
        }
		if (holding && history[n - 1] >= boughtAt * 1.10){
            return "SELL";
        }
		return "HOLD";
    }
    public static String movingAverage(double[] history, boolean holding, double boughtAt){
		int n = history.length;
		if (n < 12){
			return "HOLD";
        }
        double total = 0;
		for (int i = n - 12; i < n; i++){
            total = total + history[i];
        }
		double average = total / 12;
        double today = history[n - 1];
		if (!holding && today > average){
            return "BUY";
        }
		if (holding && today < average){
            return "SELL";
        }
		return "HOLD";
    }
    
    /*
	* My strategy is based on the Dip Buyer strategy.
	* I kept the same buying rule but changed the selling target from 10% to 8%. 
	* I think selling earlier may help me make a profit before the stock price drops again.
	* Otherwise, I hold and wait.
	*/
     
    public static String myStrategy(double[] history, boolean holding, double boughtAt){
		int n = history.length;
		if (!holding && n >= 3){
			if (history[n - 1] < history[n - 2] && history[n - 2] < history[n - 3]){
				return "BUY";
            }
        }
		if (holding && history[n - 1] >= boughtAt * 1.08){
            return "SELL";
        }
		return "HOLD";
    }

	
}
