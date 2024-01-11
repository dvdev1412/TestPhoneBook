package org.example.service;
import org.example.ContactInitializer;
import org.example.model.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ContactService {

    String outputFileName = "contacts.txt";
    private List<Contact> contacts;
    private final ContactInitializer initializer;

    public ContactService(ContactInitializer initializer) {
        this.initializer = initializer;
        this.contacts = new ArrayList<>();
    }

    private boolean isValidPhoneNumber(String phone) {
        // Пример проверки для формата +XXXXXXXXXXXX или XXXXXXXXXX
        String phoneRegex = "^\\+?\\d{11}$|^\\d{10}$";
        return phone.matches(phoneRegex);
    }

    private boolean isValidEmail(String email) {
        // Пример проверки адреса электронной почты
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }

    public void allContacts() {
        for (Contact contact : contacts) {
            System.out.println("All contacts: " + contact);
        }
    }

    public void addContact(String fullName, String phoneName, String email) {
        Logger logger = LoggerFactory.getLogger(ContactService.class);
        if (isValidPhoneNumber(phoneName) && isValidEmail(email)) {
            Contact newContact = new Contact();
            newContact.setFullName(fullName);
            newContact.setPhoneName(phoneName);
            newContact.setEmail(email);
            contacts.add(newContact);
           logger.info("Contact created");
        }
        else {
            logger.error("Error: Email address or number entered is incorrect");
        }


    }
    public void deleteContact(String email) {
        Logger logger = LoggerFactory.getLogger(ContactService.class);
        Iterator<Contact> iterator = contacts.iterator();
        while (iterator.hasNext()) {
            Contact contact = iterator.next();
            if (email.equals(contact.getEmail())) {
                iterator.remove();
                logger.info("Contact with email {} deleted successfully", email);
                return;
            }
            else {
                logger.warn("Contact with email {} not found", email);
            }
        }
    }

    public void saveContactsToFile() throws IOException {
        Logger logger = LoggerFactory.getLogger(ContactService.class);
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            for (Contact contact : contacts) {
                String line = String.format("%s;%s;%s\n", contact.getFullName(),contact.getPhoneName(),contact.getEmail());
                writer.write(line);
                writer.newLine();
            }
            logger.info("Contacts saved to file: {}", outputFileName);
        } catch (IOException e) {
            logger.error("Error saving contacts to file: {}", outputFileName, e);
            throw e;
        }
    }

    public void initializeContactsIfRequired() throws IOException {
        List<Contact> initializedContacts = initializer.initializeContactsFromFile();
        contacts.addAll(initializedContacts);
        }
    }


