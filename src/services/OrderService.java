package services;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;

import Util.sessionFactoryConfiguration;
import dto.OrderDetailDto;
import dto.OrderDto;
import entity.CustomerEntity;
import entity.ItemEntity;
import entity.OrderDetailEntity;
import entity.OrderEntity;
import repository.CustomerRepositary;

import repository.ItemRepositary;

import repository.OrderDetailRepositary;

import repository.OrderRepositary;

public class OrderService {
    private CustomerRepositary customerRepository = new CustomerRepositary();
    private OrderRepositary orderRepository = new OrderRepositary();
    private ItemRepositary itemRepository = new ItemRepositary();
    private OrderDetailRepositary detailRepository = new OrderDetailRepositary();

    public String placeOrder(OrderDto orderDto) {
        System.out.println(orderDto.toString());

        Session session = sessionFactoryConfiguration.getInstance().getSession();

        Transaction transaction = session.beginTransaction();

        try {
            CustomerEntity customerEntity = customerRepository.gCustomerEntity(orderDto.getCustId(), session);
            if (customerEntity != null) {
                OrderEntity orderEntity = new OrderEntity();
                orderEntity.setCustomerEntity(customerEntity);
                orderEntity.setDate(new Date());
                orderEntity.setId(orderDto.getOrderId());

                String orderId = orderRepository.save(orderEntity, session);
                if (orderId.equals(orderDto.getOrderId())) {
                    boolean isItemAndOrderDetailsUpdate = true;
                    for (OrderDetailDto orderDetailDto : orderDto.getOrderDetailDtos()) {
                        ItemEntity itemEntity = itemRepository.getItem(orderDetailDto.getItemCode(), session);
                        if (itemEntity != null) {
                            OrderDetailEntity detailEntity = new OrderDetailEntity();
                            detailEntity.setDiscount(orderDetailDto.getDiscount());
                            detailEntity.setItemEntity(itemEntity);
                            detailEntity.setOrderEntity(orderEntity);
                            detailEntity.setQty(orderDetailDto.getQty());

                            itemEntity.setQoh(itemEntity.getQoh() - orderDetailDto.getQty());
                            itemRepository.updateItem(itemEntity, session);

                            if (detailRepository.save(detailEntity, session) == null) {
                                isItemAndOrderDetailsUpdate = false;
                            }

                        } else {
                            isItemAndOrderDetailsUpdate = false;
                        }
                    }

                    if (isItemAndOrderDetailsUpdate) {
                        transaction.commit();
                        return "Success";
                    } else {
                        transaction.rollback();
                        return "Order Detail and Item Update Error";
                    }
                } else {
                    transaction.rollback();
                    return "Order Save Error";
                }

            } else {
                transaction.rollback();
                return "No Customer Found";
            }
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
            return e.getMessage();
        }
    }

}
