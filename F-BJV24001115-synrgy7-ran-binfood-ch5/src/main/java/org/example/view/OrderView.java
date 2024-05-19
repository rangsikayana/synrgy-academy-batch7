package org.example.view;

import org.example.model.Order;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class OrderView {
    public static void displayOrders(List<Order> orderList, FileWriter writer) throws IOException {
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