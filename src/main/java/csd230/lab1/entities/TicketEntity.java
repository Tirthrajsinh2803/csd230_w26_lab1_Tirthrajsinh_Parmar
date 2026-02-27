package csd230.lab1.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("TICKET")
public class TicketEntity extends ProductEntity {

    private String eventName;
    private double price;

    public TicketEntity() {}

    public TicketEntity(String eventName, double price) {
        this.eventName = eventName;
        this.price = price;
    }

    @Override
    public void sellItem() {
        System.out.println("Sold Ticket: " + eventName);
    }

    @Override
    public double getPrice() {
        return price;
    }
}