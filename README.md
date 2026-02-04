
# EventStorming Example — Online Food Ordering

This project demonstrates how **EventStorming** and **Domain Events** can be translated into executable code using **Java**. The source of inspiration for this repo is this [article](https://emmanuelvalverderamos.substack.com/p/introduction-to-event-storming) written by [Emmanuel Valverde](https://www.linkedin.com/in/emmanuel-valverde-ramos/), so Kudos for him <3

The goal is not technical sophistication, but **clarity**:  
the code mirrors the business language and the EventStorming model one‑to‑one.

---

## Domain Overview

The domain represents a simplified **Online Food Ordering** process.

### Core Business Flow

```
OrderPlaced
PaymentAuthorized
RestaurantAcceptedOrder
OrderPrepared
OrderDelivered
```

Each step is represented as a **Domain Event** that already happened.

---

## EventStorming Model

### Timeline (Big Picture)

```
Customer
  |
PlaceOrder
  |
(OrderPlaced)
  |
AuthorizePayment
  |
(PaymentAuthorized)
  |
AcceptOrder
  |
(RestaurantAcceptedOrder)
  |
PrepareOrder
  |
(OrderPrepared)
  |
DeliverOrder
  |
(OrderDelivered)
```

---

## Building Blocks

### Domain Events
- OrderPlaced
- PaymentAuthorized
- RestaurantAcceptedOrder
- OrderPrepared
- OrderDelivered

### Aggregate
- Order

The `Order` aggregate enforces business rules and emits domain events.

- Can an order be placed?

- Can it be accepted?

- Can it be prepared twice? ❌

```
[AcceptOrder]
      |
      v
🟨 Order
      |
      v
(RestaurantAcceptedOrder)

```

### Commands
- placeOrder()
- authorizePayment()
- acceptOrder()
- prepareOrder()
- deliverOrder()

### Policies
Policies are modeled implicitly through application services reacting to events.
A policy is a business rule that It reacts to something that happened

- When OrderPlaced → AuthorizePayment

- When PaymentAuthorized → NotifyRestaurant

- When RestaurantAcceptedOrder → PrepareOrder

```
(OrderPlaced)
      |
      v
[Policy: OnOrderPlaced]
      |
      v
[AuthorizePayment]

```

### Read Models

Read Models represent the information that different actors need to make decisions.

- They are projections of the domain events, not part of the domain itself.

- They help visualize and query the current state without affecting business rules.

Examples in the Online Food Ordering domain:

- OrderStatus → tracks the current status of an order (Placed, Paid, Accepted, etc.)

- OrderSummary → contains details for customer or restaurant dashboards

- RestaurantQueue → shows the list of orders waiting to be prepared

Why it matters:

- Read models decouple querying from business logic

- They make information available for UI, notifications, dashboards without touching the aggregates

### Hotspots

Hotspots are areas of uncertainty, risk, or business decisions that require clarification.

Examples in the Online Food Ordering domain:

- What happens if payment fails?

- Can the restaurant reject an order after it was placed?

- How do we handle late deliveries or unavailable items?

Purpose of identifying hotspots:

- They highlight gaps in understanding the process

- Help the team prioritize questions for the business

Guide future design decisions (e.g., adding compensating actions or error handling)

Tip:

- Mark hotspots with a red post-it or visual marker in your EventStorming session.

- They often lead to new policies, events, or aggregates in the model.

---



## How to Run the Examples

### Requirements
- Java 17+
- Maven

### Run Tests

```
mvn test
```

The tests execute the full business flow and assert emitted events.

No infrastructure is required.
Everything runs in memory.

---

## Why This Example

- Uses business language, not technical jargon
- Events are first‑class citizens
- Easy to extend and refactor
- Suitable for learning and workshops

This project is intentionally small so the **model remains visible**.
