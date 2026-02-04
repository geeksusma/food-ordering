package es.geeksusma.ordering.domain;

import com.example.ordering.domain.events.OrderDelivered;
import com.example.ordering.domain.events.OrderPlaced;
import com.example.ordering.domain.events.OrderPrepared;
import com.example.ordering.domain.events.PaymentAuthorized;
import com.example.ordering.domain.events.RestaurantAcceptedOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Order {

    private final UUID id;
    private Status status;
    private final List<Object> domainEvents = new ArrayList<>();

    public Order(UUID id) {
        this.id = id;
        this.status = Status.NEW;
    }

    public void place() {
        ensure(Status.NEW);
        status = Status.PLACED;
        domainEvents.add(new OrderPlaced(id));
    }

    public void authorizePayment() {
        ensure(Status.PLACED);
        status = Status.PAID;
        domainEvents.add(new PaymentAuthorized(id));
    }

    public void accept() {
        ensure(Status.PAID);
        status = Status.ACCEPTED;
        domainEvents.add(new RestaurantAcceptedOrder(id));
    }

    public void prepare() {
        ensure(Status.ACCEPTED);
        status = Status.PREPARED;
        domainEvents.add(new OrderPrepared(id));
    }

    public void deliver() {
        ensure(Status.PREPARED);
        status = Status.DELIVERED;
        domainEvents.add(new OrderDelivered(id));
    }

    public List<Object> pullEvents() {
        var events = List.copyOf(domainEvents);
        domainEvents.clear();
        return events;
    }

    private void ensure(Status expected) {
        if (status != expected) {
            throw new IllegalStateException(
                    "Order is in state " + status + " but expected " + expected
            );
        }
    }

    private enum Status {
        NEW,
        PLACED,
        PAID,
        ACCEPTED,
        PREPARED,
        DELIVERED
    }
}
