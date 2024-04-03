package org.example.service;

import org.example.model.Order;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private List<Order> orderList;

    public OrderServiceImpl(List<Order> orderList) {
        this.orderList = orderList;
    }

    @Override
    public void addOrder(Order order) {
        orderList.add(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderList;
    }

    @Override
    public int calculateTotal() {
        int total = 0;
        for (Order order : orderList) {
            total += order.getTotalPrice();
        }
        return total;
    }

    @Override
    public void displayOrders(List<Order> orderList, FileWriter writer) throws IOException {
        if (orderList.isEmpty()) {
            writer.write("Tidak ada pesanan yang ditampilkan.\n");
        } else {
            writer.write("Pesanan Anda:\n");
            writer.write("----------------------------\n");
            writer.write(String.format("%-16s %-8s %-8s\n", "Item", "Quantity", "Total Price"));
            writer.write("----------------------------\n");
            for (Order order : orderList) {
                writer.write(String.format("%-16s %-8d %-8d\n", order.getItemName(), order.getQuantity(), order.getTotalPrice()));
            }
            writer.write("----------------------------\n");
        }
    }
}