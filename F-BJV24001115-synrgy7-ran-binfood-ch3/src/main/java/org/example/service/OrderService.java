package org.example.service;

import org.example.model.Order;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public interface OrderService {
    void addOrder(Order order);
    List<Order> getAllOrders();
    int calculateTotal();
    void displayOrders(List<Order> orderList, FileWriter writer) throws IOException;
}