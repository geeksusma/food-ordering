package es.geeksusma.ordering;

import com.example.ordering.application.OrderCommands;
import com.example.ordering.domain.Order;
import com.example.ordering.domain.events.*;

import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderFlowTest {

    @Test
    void order_happy_path_emits_expected_events_in_order() {
        OrderCommands service = new OrderCommands();
        UUID orderId = UUID.randomUUID();

        Order order = service.createOrder(orderId);

        service.placeOrder(order);
        service.authorizePayment(order);
        service.acceptOrder(order);
        service.prepareOrder(order);
        service.deliverOrder(order);

        List<Object> events = order.pullEvents();

        assertThat(events).containsExactly(
                new OrderPlaced(orderId),
                new PaymentAuthorized(orderId),
                new RestaurantAcceptedOrder(orderId),
                new OrderPrepared(orderId),
                new OrderDelivered(orderId)
        );
    }
}
