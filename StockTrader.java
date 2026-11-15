import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class StockTrader{

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("sp500_1990_2025.txt"));
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
	
}
