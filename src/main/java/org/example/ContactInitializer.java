package org.example;
import org.example.model.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ContactInitializer {


    private final String contactsFilePath;

    @Autowired
    public ContactInitializer(@Value("${contacts.file-path}") String contactsFilePath) throws FileNotFoundException {
        this.contactsFilePath = contactsFilePath;
    }

    public List<Contact> initializeContactsFromFile() {
        Logger logger = LoggerFactory.getLogger(ContactInitializer.class);
        List<Contact> contacts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(contactsFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] contactInfo = line.split(";");
                if (contactInfo.length == 3) {
                    Contact contact = new Contact();
                    contact.setFullName(contactInfo[0]);
                    contact.setPhoneName(contactInfo[1]);
                    contact.setEmail(contactInfo[2]);
                    contacts.add(contact);
                }
                else {
                    logger.warn("Invalid contact line: {}", line);
                }
            }
            logger.info("Initialized {} contacts from file: {}", contacts.size(), contactsFilePath);
        } catch (IOException e) {
           logger.error("Error initializing contact: ", contactsFilePath, e.getMessage());
            return Collections.emptyList();
        }

        return contacts;
    }
}





