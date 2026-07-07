import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

class Expense {
    // Creating expense attributes
    private String title;
    private int amount;
    private String desc;
    private String category;
    private String date;

    public Expense(String title, int amount, String desc, String category, String date) {
        this.title = title;
        this.amount = amount;
        this.desc = desc;
        this.category = category;
        this.date = date;
    }

    // Method to get title of expense
    public String getTitle() {
        return this.title;
    }

    // Method to get amount of expense
    public int getAmount() {
        return this.amount;
    }

    // Method to get desc of expense
    public String getDesc() {
        return this.desc;
    }

    // Method to get category of expense
    public String getCategory() {
        return this.category;
    }

    // Method to get date of expense
    public String getDate() {
        return this.date;
    }

    // Method to set title of expense
    public void setTitle(String title) {
        this.title = title;
    }

    //Method to set amount of expense
    public void setAmount(int amount) {
        this.amount = amount;
    }

    //Method to set desc of expense
    public void setDesc(String desc) {
        this.desc = desc;
    }

    // Method to set category of expense
    public void setCategory(String category) {
        this.category = category;
    }

    // Method to set date of expense
    public void setDate(String date) {
        this.date = date;
    }

    //Method to convert expense object to string
    @Override
    public String toString() {
        return title + ", " + amount + ", " + desc + ", " + category + ", " + date;
    }
}

class ExpenseManager {
    private ArrayList<Expense> expenses;
    private File file = new File("expenses.txt");

    public ExpenseManager() {
        // Creating an empty expenses list object
        expenses = new ArrayList<>();

        try {
            if(file.createNewFile()) {
                System.out.println("File created successfully.");
            }
        } catch(IOException e) {
            System.out.println("Unable to create this file");
            e.printStackTrace();
        }

        loadExpenses();
    }

    // Method to add expenses
    public void addExpense(Expense expense) {
        // Writing expenses to the file
        expenses.add(expense);
        saveExpenses();
        System.out.println("Expense added successfully!");
    }

    // Method to save expenses
    private void saveExpenses() {
        try {
            FileWriter writer = new FileWriter(file);
            for (int i = 0; i < expenses.size(); i++) {
                Expense expense = expenses.get(i);
                writer.write(expense.toString());
                writer.write("\n");
            }
            writer.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    //Method to load expenses
    public void loadExpenses() {
        try {
            Scanner sc = new Scanner(file);

            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split(", ");
                if(parts.length != 5) {
                    System.out.println("Skipping invalid line: " + line);
                    continue;
                }

                Expense expense = new Expense(parts[0], Integer.parseInt(parts[1]), parts[2], parts[3], parts[4]);
                expenses.add(expense);
            }
            sc.close();
        } catch(IOException e) {
            System.out.println("Unable to read in this file");
            e.printStackTrace();
        }
    }

    // Method to delete expenses
    public void deleteExpense(Scanner scan) {
        boolean found = false;

        if(expenses.isEmpty()) {
            System.out.println("No expenses found!");
            return;
        } else {
            System.out.println("Enter the title of expense to search...");
            String searchTitle = scan.nextLine();

            System.out.println("Enter the amount of expense to search...");
            int amount = scan.nextInt();
            scan.nextLine();

            for (int i = 0; i < expenses.size(); i++) {
                Expense expense = expenses.get(i);
                if (expense.getTitle().equalsIgnoreCase(searchTitle) && expense.getAmount() == amount) {
                    found = true;
                    expenses.remove(i);
                    break;
                }
            }

            if(found) {
                saveExpenses();
                System.out.println("Expense deleted successfully\n");
            } else {
                System.out.println("No expense found matching your search");
            }
        }
    }

    // Method to view expenses
    public void viewExpenses() {
       if(expenses.isEmpty()) {
           System.out.println("No expenses added!");
           return;
       } else {
           for(int i = 0; i < expenses.size(); i++) {
                Expense expense = expenses.get(i);

                System.out.println("------------------------------");
                System.out.print("Expense #");
                System.out.println(i + 1);
                System.out.println();

                System.out.println("Title: " + expense.getTitle());
                System.out.println("Amount: " + expense.getAmount());
                System.out.println("Description: " + expense.getDesc());
                System.out.println("Category: " + expense.getCategory());
                System.out.println("Date: " + expense.getDate());
                System.out.println();

                System.out.println("------------------------------");
                System.out.println();
           }
       }
    }

    // Method to edit expenses
    public void editExpenses(Scanner scanner) {
        boolean found = false;
        Expense expenseToEdit = null;

        if(expenses.isEmpty()) {
            System.out.println("No expenses found!");
            return;
        } else {
            System.out.println("Enter the title of expense to search...");
            String searchTitle = scanner.nextLine();

            System.out.println("Enter the amount of expense to search...");
            int amount = scanner.nextInt();
            scanner.nextLine();

            for (int i = 0; i < expenses.size(); i++) {
                Expense expense = expenses.get(i);

                if (expense.getTitle().equalsIgnoreCase(searchTitle) && expense.getAmount() == amount) {
                    found = true;
                    expenseToEdit = expense;
                    break;
                }
            }

            if(found) {
                String newTitle;
                while(true){
                    System.out.println("New title: ");
                    newTitle = scanner.nextLine();
                    if(newTitle.isBlank()) {
                        System.out.println("Title cannot be empty...");
                    } else break;
                }

                int newAmount = 0;
                while(true) {
                    try {
                        System.out.println("New amount:");
                        newAmount = scanner.nextInt();
                        scanner.nextLine();

                        if(newAmount <= 0) {
                            System.out.println("Amount must be greater than 0.");
                            continue;
                        }

                        break;
                    } catch(InputMismatchException e) {
                        System.out.println("Enter a valid number.");
                        scanner.nextLine();
                    }
                }

                String newDesc;
                while(true) {
                    System.out.println("New Desc: ");
                    newDesc = scanner.nextLine();
                    if(newDesc.isBlank()) {
                        System.out.println("Description cannot be empty...");
                    } else break;
                }

                String newCategory;
                while(true) {
                    System.out.println("New Category: ");
                    newCategory = scanner.nextLine();
                    if(newCategory.isBlank()) {
                        System.out.println("Category cannot be empty...");
                    } else break;
                }

                String newDate;
                while(true) {
                    System.out.println("New Date: ");
                    newDate = scanner.nextLine();
                    if(newDate.isBlank()) {
                        System.out.println("Date cannot be empty...");
                    } else break;
                }

                expenseToEdit.setTitle(newTitle);
                expenseToEdit.setCategory(newCategory);
                expenseToEdit.setDesc(newDesc);
                expenseToEdit.setDate(newDate);
                expenseToEdit.setAmount(newAmount);

                saveExpenses();
                System.out.println("Expenses updated successfully!");
            } else {
                System.out.println("No expense found matching your search.");
                return;
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ExpenseManager manager = new ExpenseManager();

       while(true) {
           System.out.println("====== Expense Tracker ======");
           System.out.println("1. Add expense");
           System.out.println("2. View expenses");
           System.out.println("3. Delete expense");
           System.out.println("4. Edit expense");
           System.out.println("5. Exit");

           System.out.println("Choose from the menu:");
           String choice = scan.nextLine();
           System.out.println();


           if(choice.equalsIgnoreCase("Add expense")) {
               // Take input of expense from the user
               String title;
               while(true) {
                   System.out.println("Title: ");
                   title = scan.nextLine();
                   if(title.isBlank()) {
                       System.out.println("Title cannot be empty...");
                   } else break;
               }

               int amount = 0;
               while(true) {
                   try {
                       System.out.println("Amount: ");
                       amount = scan.nextInt();
                       scan.nextLine();
                       if (amount <= 0) {
                           System.out.println("Amount must be greater than 0.");
                           continue;
                       }
                       break;
                   } catch(InputMismatchException e) {
                       System.out.println("Please enter a valid number...");
                       scan.nextLine();
                   }
               }

               String desc;
               while(true) {
                   System.out.println("Description: ");
                   desc = scan.nextLine();
                   if(desc.isBlank()) {
                       System.out.println("Desc cannot be empty...");
                   } else break;
               }

               String category;
               while(true) {
                   System.out.println("Category: ");
                   category = scan.nextLine();
                   if(category.isBlank()) {
                       System.out.println("Category cannot be empty...");
                   } else break;
               }

               String date;
               while(true) {
                   System.out.println("Date: ");
                   date = scan.nextLine();
                   if(date.isBlank()) {
                       System.out.println("Date cannot be empty...");
                   } else break;
               }

               Expense expense = new Expense(title, amount, desc, category, date);
               manager.addExpense(expense);
           } else if(choice.equalsIgnoreCase("View expenses")) {
               manager.viewExpenses();
           } else if(choice.equalsIgnoreCase("Delete expense")) {
               manager.deleteExpense(scan);
           } else if(choice.equalsIgnoreCase("Edit expense")) {
               manager.editExpenses(scan);
           } else if(choice.equalsIgnoreCase("exit")) {
               break;
           }
       }
       scan.close();
    }
}