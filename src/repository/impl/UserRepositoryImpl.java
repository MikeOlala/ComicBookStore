package repository.impl;

import database.FakeDatabase;
import model.Customer;
import model.User;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * Class: UserRepositoryImpl
 * ----------------------------------------------------------------------------
 * Triển khai UserRepository.
 * ============================================================================
 */
public class UserRepositoryImpl implements UserRepository {

    /**
     * Sinh ID tự động.
     */
    private static int nextUserId = 1;

    @Override
    public boolean save(Customer customer) {

        customer.setUserId(nextUserId++);

        return FakeDatabase.USERS.add(customer);

    }

    @Override
    public Customer findByEmail(String email) {

        for (User user : FakeDatabase.USERS) {

            if (user instanceof Customer customer) {

                if (customer.getEmail().equalsIgnoreCase(email)) {

                    return customer;

                }

            }

        }

        return null;

    }

    @Override
    public List<Customer> findAll() {

        List<Customer> customers = new ArrayList<>();

        for (User user : FakeDatabase.USERS) {

            if (user instanceof Customer customer) {

                customers.add(customer);

            }

        }

        return customers;

    }

}