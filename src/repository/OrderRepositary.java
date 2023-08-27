package repository;

import org.hibernate.Session;

import entity.OrderEntity;

public class OrderRepositary {

    public String save(OrderEntity entity, Session session) {
        String id = (String) session.save(entity);
        return id;
    }

}
