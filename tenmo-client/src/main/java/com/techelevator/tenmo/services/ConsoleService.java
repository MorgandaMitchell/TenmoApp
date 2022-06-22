package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;

import java.math.BigDecimal;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);
    private Account account;

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printBalance(BigDecimal balance) {
        System.out.println("Your current balance is: $" + balance.toString());
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    public void printMoneyMenu(User[] users) {
        System.out.println("-------------------------------------------");
        System.out.println("Users");
        System.out.println("ID        Name");
        System.out.println("-------------------------------------------");
        for (User user : users) {
            System.out.println(user.getId() + "        " + user.getUsername());
        }
        System.out.println("-------------------------------------------\n");
        System.out.println("Enter ID of user you want to request from. (0 to cancel):\n");
    }

   public void printTransferHistoryMenu(Transfer[] transfers) {
        System.out.println("-------------------------------------------");
        System.out.println("Transfers");
        System.out.println("ID            From/To        Amount");
        System.out.println("-------------------------------------------");
        for (Transfer transfer :
                transfers) {
            System.out.println(transfer.getTransferId() + "         From: " + transfer.getTransferStatus() + "        $ " + transfer.getTransferAmount());
        }
        System.out.println("-------------------------------------------");
        System.out.println("Enter ID for transfer details. (0 to cancel):\n");
   }


    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }


    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }

}
