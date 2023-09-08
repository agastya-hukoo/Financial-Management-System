import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FinancialManagementSystem {
    private static final String USER_DATA_FILE = "userdata.txt";
    private static boolean loggedIn = false;
    private static String loggedInUser = "";
    private static List<Transaction> transactions = new ArrayList<>();
    private static Map<String, Double> budgetLimits = new HashMap<>();
    private static Map<String, Double> expensesByCategory = new HashMap<>();
    private static List<Investment> investments = new ArrayList<>();
    private static List<FinancialGoal> goals = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            if (!loggedIn) {
                System.out.println("Welcome to the Financial Management System");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                    registerUser(scanner);
                    break;
                    case 2:
                    loggedIn = loginUser(scanner);
                    break;
                    case 3:
                    System.out.println("Exiting the application. Goodbye!");
                    System.exit(0);
                    default:
                    System.out.println("Invalid choice. Please try again.");
                }
            } else {

                System.out.println("Logged in as: " + loggedInUser);
                System.out.println("1. Dashboard");
                System.out.println("2. Income & Expense Management");
                System.out.println("3. Budgeting");
                System.out.println("4. Investments");
                System.out.println("5. Financial Goals");
                System.out.println("6. Reports & Analysis");
                System.out.println("7. Data Backup & Restore");
                System.out.println("8. Logout");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                    displayDashboard();
                    break;
                    case 2:
                    manageIncomeAndExpenses(scanner);
                    break;
                    case 3:
                    manageBudgets(scanner);
                    break;
                    case 4:
                    manageInvestments(scanner);
                    break;
                    case 5:
                    manageFinancialGoals(scanner);
                    break;
                    case 6:
                    generateReportsAndAnalysis(scanner);
                    break;
                    case 7:

                    break;
                    case 8:
                    loggedIn = false;
                    loggedInUser = "";
                    break;
                    default:
                    System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    private static void generateReportsAndAnalysis(Scanner scanner) {
        while (true) {
            System.out.println("Reports & Analysis");
            System.out.println("1. Generate Monthly Report");
            System.out.println("2. Generate Quarterly Report");
            System.out.println("3. Generate Yearly Report");
            System.out.println("4. Perform Budget vs Actual Analysis");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                generateMonthlyReport();
                break;

                case 2:
                generateQuarterlyReport();
                break;

                case 3:
                generateYearlyReport();
                break;

                case 4:
                performBudgetVsActualAnalysis();
                break;

                case 5:
                return;

                default:
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void generateMonthlyReport() {
        System.out.println("Generating Monthly Report...");
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        double totalIncome = 0.0;
        double totalExpenses = 0.0;

        for (Transaction transaction : transactions) {
            if (isSameMonth(currentDate, transaction.getDate())) {
                if (transaction.getAmount() > 0) {
                    totalIncome += transaction.getAmount();
                } else {
                    totalExpenses -= transaction.getAmount(); 
                }
            }
        }

        System.out.println("Report for " + dateFormat.format(currentDate));
        System.out.println("Total Income: $" + decimalFormat.format(totalIncome));
        System.out.println("Total Expenses: $" + decimalFormat.format(totalExpenses));
        System.out.println("Net Savings: $" + decimalFormat.format(totalIncome - totalExpenses));
    }

    private static void generateQuarterlyReport() {
        System.out.println("Generating Quarterly Report...");
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        int currentQuarter = getQuarter(currentDate);
        double[] quarterlyIncome = new double[4];
        double[] quarterlyExpenses = new double[4];

        for (Transaction transaction : transactions) {
            Date transactionDate = transaction.getDate();
            int quarter = getQuarter(transactionDate);

            if (quarter >= 0 && quarter < 4 && isSameYear(currentDate, transactionDate)) {
                if (transaction.getAmount() > 0) {
                    quarterlyIncome[quarter] += transaction.getAmount();
                } else {
                    quarterlyExpenses[quarter] -= transaction.getAmount();
                }
            }
        }

        System.out.println("Report for Year " + dateFormat.format(currentDate));
        for (int i = 0; i < 4; i++) {
            System.out.println("Quarter " + (i + 1));
            System.out.println("Total Income: $" + decimalFormat.format(quarterlyIncome[i]));
            System.out.println("Total Expenses: $" + decimalFormat.format(quarterlyExpenses[i]));
            System.out.println("Net Savings: $" + decimalFormat.format(quarterlyIncome[i] - quarterlyExpenses[i]));
        }
    }

    private static boolean isSameMonth(Date date1, Date date2) {
        SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
        return monthFormat.format(date1).equals(monthFormat.format(date2));
    }

    private static boolean isSameYear(Date date1, Date date2) {
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        return yearFormat.format(date1).equals(yearFormat.format(date2));
    }

    private static int getQuarter(Date date) {
        int month = Integer.parseInt(new SimpleDateFormat("MM").format(date));
        return (month - 1) / 3;
    }

private static class Transaction {
    private final Date date;
    private final String description;
    private final double amount;
    private final String category;

    public Transaction(Date date, String description, double amount, String category) {
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.category = category;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }
}

    private static void generateYearlyReport() {

        System.out.println("Generating Yearly Report...");
    }

    private static void performBudgetVsActualAnalysis() {

        System.out.println("Performing Budget vs Actual Analysis...");
    }

    private static void manageFinancialGoals(Scanner scanner) {
        while (true) {
            System.out.println("Financial Goals");
            System.out.println("1. Set Financial Goal");
            System.out.println("2. View Financial Goals");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                setFinancialGoal(scanner);
                break;

                case 2:
                viewFinancialGoals();
                break;

                case 3:
                return;

                default:
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void setFinancialGoal(Scanner scanner) {
        System.out.print("Enter goal description: ");
        String description = scanner.nextLine();
        System.out.print("Enter target amount: $");
        double targetAmount = scanner.nextDouble();
        scanner.nextLine(); 
        System.out.print("Enter target date (YYYY-MM-DD): ");
        String targetDate = scanner.nextLine();

        goals.add(new FinancialGoal(description, targetAmount, targetDate));
        System.out.println("Financial goal set successfully.");
    }

    private static void viewFinancialGoals() {
        System.out.println("Financial Goals:");
        for (FinancialGoal goal : goals) {
            System.out.println(goal);
        }
    }

    private static class FinancialGoal {
        private final String description;
        private final double targetAmount;
        private final String targetDate;

        public FinancialGoal(String description, double targetAmount, String targetDate) {
            this.description = description;
            this.targetAmount = targetAmount;
            this.targetDate = targetDate;
        }

        @Override
        public String toString() {
            return "Description: " + description + ", Target Amount: $" + targetAmount + ", Target Date: " + targetDate;
        }
    }
    private static void manageInvestments(Scanner scanner) {
        while (true) {
            System.out.println("Investments");
            System.out.println("1. Record Investment");
            System.out.println("2. View Investments");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                recordInvestment(scanner);
                break;

                case 2:
                viewInvestments();
                break;

                case 3:
                return;

                default:
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void recordInvestment(Scanner scanner) {
        System.out.println("Recording Investment");
        System.out.print("Enter investment name: ");
        String name = scanner.nextLine();
        System.out.print("Enter investment type: ");
        String type = scanner.nextLine();
        System.out.print("Enter initial investment amount: $");
        double initialAmount = scanner.nextDouble();
        scanner.nextLine(); 
        System.out.print("Enter purchase date (YYYY-MM-DD): ");
        String purchaseDate = scanner.nextLine();

        investments.add(new Investment(name, type, initialAmount, purchaseDate));
        System.out.println("Investment recorded successfully.");
    }

    private static void viewInvestments() {
        System.out.println("Investment Portfolio:");
        for (Investment investment : investments) {
            System.out.println(investment);
        }
    }
    private static class Investment {
        private final String name;
        private final String type;
        private final double initialAmount;
        private final String purchaseDate;

        public Investment(String name, String type, double initialAmount, String purchaseDate) {
            this.name = name;
            this.type = type;
            this.initialAmount = initialAmount;
            this.purchaseDate = purchaseDate;
        }

        @Override
        public String toString() {
            return "Name: " + name + ", Type: " + type + ", Initial Amount: $" + initialAmount + ", Purchase Date: " + purchaseDate;
        }
    }
    private static void manageBudgets(Scanner scanner) {
        while (true) {
            System.out.println("Budgeting");
            System.out.println("1. Set Monthly Budget");
            System.out.println("2. View Budgets");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                setMonthlyBudget(scanner);
                break;

                case 2:
                viewBudgets();
                break;

                case 3:
                return;

                default:
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void setMonthlyBudget(Scanner scanner) {
        System.out.print("Enter expense category: ");
        String category = scanner.nextLine();
        System.out.print("Enter monthly budget limit: $");
        double limit = scanner.nextDouble();
        scanner.nextLine(); 

        budgetLimits.put(category, limit);
        System.out.println("Monthly budget set successfully.");
    }

    private static void viewBudgets() {
        System.out.println("Monthly Budgets:");
        for (Map.Entry<String, Double> entry : budgetLimits.entrySet()) {
            String category = entry.getKey();
            double limit = entry.getValue();
            System.out.println(category + ": $" + limit);
        }
    }

    private static void manageIncomeAndExpenses(Scanner scanner) {
        while (true) {
            System.out.println("Income & Expense Management");
            System.out.println("1. Record Income");
            System.out.println("2. Record Expense");
            System.out.println("3. List Recent Transactions");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                recordIncome(scanner);
                break;

                case 2:
                recordExpense(scanner);
                break;

                case 3:
                listRecentTransactions();
                break;

                case 4:
                return;

                default:
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void recordIncome(Scanner scanner) {
        System.out.println("Recording Income");
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter amount: $");
        double amount = scanner.nextDouble();
        scanner.nextLine(); 

   transactions.add(new Transaction(new Date(), description, amount, "Income"));
        System.out.println("Income recorded successfully.");
    }

    private static void recordExpense(Scanner scanner) {
        System.out.println("Recording Expense");
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter amount: $");
        double amount = scanner.nextDouble();
        scanner.nextLine(); 
        System.out.print("Enter category: ");
        String category = scanner.nextLine();

        transactions.add(new Transaction(new Date(),description, amount, category));
        System.out.println("Expense recorded successfully.");
    }

    private static void listRecentTransactions() {
        System.out.println("Recent Transactions:");
        int count = 0;
        for (Transaction transaction : transactions) {
            if (count < 5) {
                System.out.println(transaction);
                count++;
            } else {
                break;
            }
        }
    }

    private static void displayDashboard() {
        System.out.println("Dashboard for " + loggedInUser);

        double monthlyIncome = calculateMonthlyIncome();
        double monthlyExpenses = calculateMonthlyExpenses();
        double savings = monthlyIncome - monthlyExpenses;

        System.out.println("Monthly Income: $" + monthlyIncome);
        System.out.println("Monthly Expenses: $" + monthlyExpenses);
        System.out.println("Savings: $" + savings);

        System.out.println("Recent Transactions:");
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    private static double calculateMonthlyIncome() {

        return 3000.0; 
    }

    private static double calculateMonthlyExpenses() {

        return 2000.0; 
    }

    private static void registerUser(Scanner scanner) {
        try {
            FileWriter fileWriter = new FileWriter(USER_DATA_FILE, true); 
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);

            System.out.println("User Registration:");
            System.out.print("Enter a username: ");
            String username = scanner.nextLine();

            if (checkUsernameExists(username)) {
                System.out.println("Username already exists. Registration failed.");
                return;
            }

            System.out.print("Enter a password: ");
            String password = scanner.nextLine();

            printWriter.println(username + "," + password);

            printWriter.close();
            System.out.println("Registration successful!");
        } catch (IOException e) {
            System.err.println("Error occurred during registration: " + e.getMessage());
        }
    }

    private static boolean checkUsernameExists(String username) {
        try {
            File file = new File(USER_DATA_FILE);
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] userData = line.split(",");
                if (userData.length > 0 && userData[0].equals(username)) {
                    fileScanner.close();
                    return true; 
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {

        }
        return false; 
    }

    private static boolean loginUser(Scanner scanner) {
        try {
            System.out.println("User Login:");
            System.out.print("Enter your username: ");
            String enteredUsername = scanner.nextLine();
            System.out.print("Enter your password: ");
            String enteredPassword = scanner.nextLine();

            File file = new File(USER_DATA_FILE);
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] userData = line.split(",");

                if (userData.length == 2) {
                    String username = userData[0];
                    String password = userData[1];

                    if (username.equals(enteredUsername) && password.equals(enteredPassword)) {
                        fileScanner.close();
                        System.out.println("Login successful!");
                        return true;
                    }
                }
            }
            fileScanner.close();
            System.out.println("Login failed. Please check your username and password.");
        } catch (FileNotFoundException e) {
            System.err.println("User data file not found.");
        }
        return false; 
    }
}