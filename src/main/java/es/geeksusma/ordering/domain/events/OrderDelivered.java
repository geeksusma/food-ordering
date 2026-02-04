
package es.geeksusma.ordering.domain.events;

import java.util.UUID;

public record OrderDelivered(UUID orderId) {}
