package es.geeksusma.ordering.application;

import com.example.ordering.domain.Order;

import java.util.UUID;

public class OrderCommands {

    public Order createOrder(UUID orderId) {
        return new Order(orderId);
    }

    public void placeOrder(Order order) {
        order.place();
    }

    public void authorizePayment(Order order) {
        order.authorizePayment();
    }

    public void acceptOrder(Order order) {
        order.accept();
    }

    public void prepareOrder(Order order) {
        order.prepare();
    }

    public void deliverOrder(Order order) {
        order.deliver();
    }
}
