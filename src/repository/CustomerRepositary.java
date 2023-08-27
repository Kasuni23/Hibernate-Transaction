package repository;

import org.hibernate.Session;

import entity.CustomerEntity;

public class CustomerRepositary {

    public CustomerEntity gCustomerEntity(String id, Session session) {
        CustomerEntity customerEntity = session.get(CustomerEntity.class, id);
        return customerEntity;
    }

}
