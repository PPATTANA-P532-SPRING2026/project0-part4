package edu.iu.ppattana.ducksservice.repository;

import edu.iu.ppattana.ducksservice.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class CustomerFileRepository {

    private static final Logger LOG = LoggerFactory.getLogger(CustomerFileRepository.class);
    private static final String DATABASE_NAME = "ducks/customers.txt";

    public CustomerFileRepository() {
        File file = new File(DATABASE_NAME);
        file.getParentFile().mkdirs();
        try {
            file.createNewFile();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }

    public void save(Customer customer) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATABASE_NAME, true))) {
            writer.write(customer.getUsername() + "," + customer.getPassword() + "," + customer.getEmail());
            writer.newLine();
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }

    public Customer findByUsername(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATABASE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username)) {
                    return new Customer(parts[0], parts[1], parts[2]);
                }
            }
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
        return null;
    }
}