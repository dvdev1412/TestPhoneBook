package org.example.service;
import org.example.ContactInitializer;
import org.example.model.Contact;
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
        if (isValidPhoneNumber(phoneName) && isValidEmail(email)) {
            Contact newContact = new Contact();
            newContact.setFullName(fullName);
            newContact.setPhoneName(phoneName);
            newContact.setEmail(email);
            contacts.add(newContact);
            System.out.println("Contact created");
        }

            System.out.println("Error: Email address or number entered is incorrect");


    }
    public void deleteContact(String phoneName) {

        Iterator<Contact> iterator = contacts.iterator();
        while (iterator.hasNext()) {
            Contact contact = iterator.next();
            if (phoneName.equals(contact.getPhoneName())) {
                iterator.remove();
                System.out.println("Contact is delete");
                return;
            }
        }
             System.out.println("Contact not found");

    }

    public void saveContactsToFile() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName));

        try {
    for (Contact contact : contacts) {
        String line = contact.getFullName() + ";" + contact.getPhoneName() + ";" + contact.getEmail();
        writer.write(line);
        writer.newLine();
    }
    System.out.println("Contacts save to file: " + outputFileName);
}
     catch (IOException e) {
            System.out.println("Contacts save is error");
}
        finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    public void initializeContactsIfRequired() throws IOException {
        List<Contact> initializedContacts = initializer.initializeContactsFromFile();
        contacts.addAll(initializedContacts);
        }
    }


