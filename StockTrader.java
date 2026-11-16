import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/* Part B Question: Someone who bought Microsoft stock in 2000 may have lost 
					money because the stock price decreased over time. 
					If they sold their stock at a lower price, they would 
					have lost money.
*/

public class StockTrader{

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("sbux_2007_2016.txt"));
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
		return buyAndHold(history, holding, boughtAt);	
		//return dipBuyer(history, holding, boughtAt);	
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
