package task2;
import java.util.*;
import java.time.LocalDateTime;
class Stock {
    String symbol;
    String name;
    double price;
    public Stock(String symbol, String name, double price) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
    }
    @Override
    public String toString() {
        return symbol + " (" + name + ") - ₹" + price;
    }
}
class Transaction {
    String stockSymbol;
    int quantity;
    double price;
    String type;
    LocalDateTime timestamp;
    public Transaction(String stockSymbol, int quantity, double price, String type) {
        this.stockSymbol = stockSymbol;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
        this.timestamp = LocalDateTime.now();
    }
    @Override
    public String toString() {
        return timestamp + " | " + type + " | " + stockSymbol + " | Qty: " + quantity + " @ ₹" + price;
    }
}
class User {
    String name;
    double balance;
    Map<String, Integer> portfolio = new HashMap<>();
    List<Transaction> transactions = new ArrayList<>();

    public User(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }
    public void buyStock(Stock stock, int quantity) {
        double totalCost = stock.price * quantity;
        if (balance >= totalCost) {
            balance -= totalCost;
            portfolio.put(stock.symbol, portfolio.getOrDefault(stock.symbol, 0) + quantity);
            transactions.add(new Transaction(stock.symbol, quantity, stock.price, "BUY"));
            System.out.println("✅ Bought " + quantity + " shares of " + stock.symbol);
        } else {
            System.out.println("❌ Not enough balance!");
        }
    }
    public void sellStock(Stock stock, int quantity) {
        int owned = portfolio.getOrDefault(stock.symbol, 0);
        if (owned >= quantity) {
            balance += stock.price * quantity;
            portfolio.put(stock.symbol, owned - quantity);
            transactions.add(new Transaction(stock.symbol, quantity, stock.price, "SELL"));
            System.out.println("✅ Sold " + quantity + " shares of " + stock.symbol);
        } else {
            System.out.println("❌ You don't own enough shares to sell.");
        }
    }
    public void viewPortfolio() {
        System.out.println("\n📊 Portfolio for " + name + ":");
        for (Map.Entry<String, Integer> entry : portfolio.entrySet()) {
            System.out.println("Stock: " + entry.getKey() + " | Quantity: " + entry.getValue());
        }
       System.out.printf("💰 Available Balance: ₹%.2f\n", balance);
    }

    public void viewTransactions() {
        System.out.println("\n📄 Transaction History:");
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }
}
public class Task2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Sample stock market data
        List<Stock> market = Arrays.asList(
            new Stock("TCS", "Tata Consultancy Services", 3800.00),
            new Stock("INFY", "Infosys", 1400.00),
            new Stock("HDFCBANK", "HDFC Bank", 1600.00),
            new Stock("RELIANCE", "Reliance Industries", 2400.00)
        );

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        User user = new User(name, 10000.00);  // starting balance

        int choice;
        do {
            System.out.println("\n📈 STOCK TRADING MENU");
            System.out.println("1. View Market");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. View Transactions");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("\n📋 Market Stocks:");
                    for (Stock s : market) {
                        System.out.println(s);
                    }
                    break;
                case 2:
                    System.out.print("Enter stock symbol to buy: ");
                    String buySymbol = scanner.next();
                    System.out.print("Enter quantity: ");
                    int buyQty = scanner.nextInt();
                    Stock buyStock = getStockBySymbol(market, buySymbol);
                    if (buyStock != null) {
                        user.buyStock(buyStock, buyQty);
                    } else {
                        System.out.println("❌ Invalid stock symbol!");
                    }
                    break;
                case 3:
                    System.out.print("Enter stock symbol to sell: ");
                    String sellSymbol = scanner.next();
                    System.out.print("Enter quantity: ");
                    int sellQty = scanner.nextInt();
                    Stock sellStock = getStockBySymbol(market, sellSymbol);
                    if (sellStock != null) {
                        user.sellStock(sellStock, sellQty);
                    } else {
                        System.out.println("❌ Invalid stock symbol!");
                    }
                    break;
                case 4:
                    user.viewPortfolio();
                    break;
                case 5:
                    user.viewTransactions();
                    break;
                case 0:
                    System.out.println("👋 Exiting... Thank you for trading!");
                    break;
                default:
                    System.out.println("❌ Invalid choice. Try again.");
            }
        } while (choice != 0);

        scanner.close();
    }
    public static Stock getStockBySymbol(List<Stock> market, String symbol) {
        for (Stock s : market) {
            if (s.symbol.equalsIgnoreCase(symbol)) {
                return s;
            }
        }
        return null;
    }
}
