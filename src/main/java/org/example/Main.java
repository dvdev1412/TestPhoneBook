package org.example;
import org.example.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Logger logger = LoggerFactory.getLogger(Main.class);

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        ContactService contactService = context.getBean(ContactService.class);
        try {
            contactService.initializeContactsIfRequired();
        }
        catch (IOException e) {
            logger.error("Error initializing contacts from file: {}", e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the contact Manager system!");
        String option = "";

        while (!option.equalsIgnoreCase("4")) {
            MenuHandler.printMenu();
            option = scanner.nextLine();
            switch (option.toLowerCase()) {
                case "1" -> {
                    handleAddContact(contactService, scanner);
                }
                case "2" -> contactService.allContacts();

                case "3" ->
                    handleDeleteContact(contactService, scanner);

                case "4"->
                    System.out.println("Goodbye!");

                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public static void handleAddContact(ContactService service, Scanner scanner) {
        Logger logger = LoggerFactory.getLogger(Main.class);
        System.out.println("Enter full name: ");
        String fullName = scanner.nextLine();
        System.out.println("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.println("Enter email: ");
        String email = scanner.nextLine();

        try {
            service.addContact(fullName,phoneNumber,email);
            logger.info("Contact added successfully");
        }  catch (IllegalArgumentException e) {
            logger.error("Invalid contact data: {}", e.getMessage());
            System.out.println("Invalid contact data. Please check your input and try again.");
        }
        try {
            service.saveContactsToFile();
            System.out.println("Contacts saved to the file");
        } catch (IOException e) {
            logger.error("Error saving contacts to file");
        }
    }
    public static void handleDeleteContact(ContactService service, Scanner scanner) {
        System.out.println("Enter phone number to delete: ");
        String deletePhone = scanner.nextLine();
        try {
            service.deleteContact(deletePhone);
            System.out.println("Contact deleted successfully!");
        }
        catch (Exception e) {
            System.out.println("Contact not deleted");
        }
    }
    public class MenuHandler {
        public static void printMenu() {
            System.out.println("\nChoose an option:");
            System.out.println("Add contact: Enter command 1 ");
            System.out.println("View All contacts: Enter command 2");
            System.out.println("Delete Contact: Enter command 3");
            System.out.println("Exit: Enter command 4");
        }
    }

}