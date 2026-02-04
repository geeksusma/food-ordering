
package es.geeksusma.ordering.domain.events;

import java.util.UUID;

public record OrderPrepared(UUID orderId) {}
