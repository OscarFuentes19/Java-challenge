
	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStreamReader;
	import java.net.HttpURLConnection;
	import java.net.URL;
	import java.util.Scanner;

	import com.google.gson.JsonObject;
	import com.google.gson.JsonParser;

	public class Conversor {

	    private static final String API_KEY = "vxEzEP0YFtrBxy3eE2hxID6nlF8yxN7C";
	    private static final String API_URL = "http://data.fixer.io/api/latest?access_key=" + API_KEY;

	    public static void main(String[] args) {
	        Scanner scanner = new Scanner(System.in);
	        System.out.print("Ingrese la cantidad a convertir: ");
	        double amount = scanner.nextDouble();

	        System.out.print("Ingrese la divisa original: ");
	        String baseCurrency = scanner.next();

	        System.out.print("Ingrese la divisa a convertir: ");
	        String targetCurrency = scanner.next();

	        double convertedAmount = convertCurrency(amount, baseCurrency, targetCurrency);

	        System.out.println(amount + " " + baseCurrency + " equivale a " + convertedAmount + " " + targetCurrency);
	    }

	    public static double convertCurrency(double amount, String baseCurrency, String targetCurrency) {
	        try {
	            URL url = new URL(API_URL + "&base=" + baseCurrency + "&symbols=" + targetCurrency);
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.setRequestMethod("GET");

	            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	            String line;
	            StringBuilder response = new StringBuilder();
	            while ((line = reader.readLine()) != null) {
	                response.append(line);
	            }
	            reader.close();

	            JsonParser parser = new JsonParser();
	            JsonObject jsonObject = parser.parse(response.toString()).getAsJsonObject();

	            double conversionRate = jsonObject.getAsJsonObject("rates").get(targetCurrency).getAsDouble();

	            return amount * conversionRate;

	        } catch (IOException e) {
	            e.printStackTrace();
	            return 0;
	        }
	    }
	}

}
