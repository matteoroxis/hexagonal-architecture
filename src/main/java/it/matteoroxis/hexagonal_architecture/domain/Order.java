package it.matteoroxis.hexagonal_architecture.domain;

public class Order {

    private final OrderId id;
    private OrderStatus status;
    private Money totalAmount;

    public Order(OrderId id, OrderStatus status, Money totalAmount) {
        this.id = id;
        this.status = status;
        this.totalAmount = totalAmount;
    }

    public void markAsShipped() {
        if (status != OrderStatus.PAID) {
            throw new IllegalStateException("Only paid orders can be shipped");
        }
        this.status = OrderStatus.SHIPPED;
    }

    public void markAsPaid() {
        if (status != OrderStatus.PENDING) {
            throw new IllegalStateException("Only pending orders can be marked as paid");
        }
        this.status = OrderStatus.PAID;
    }

    public OrderId id() {
        return id;
    }

    public OrderStatus status() {
        return status;
    }

    public Money totalAmount() {
        return totalAmount;
    }
}
