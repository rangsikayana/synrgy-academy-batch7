package org.example.controller;

import org.example.model.Order;
import org.example.service.OrderService;
import org.example.service.OrderServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderController {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Order> orderList = new ArrayList<>();
    private static OrderService orderService = new OrderServiceImpl(orderList);

    public static int calculateTotalPrice(int item, int quantity) {
        return switch (item) {
            case 1 -> 15000 * quantity;
            case 2 -> 13000 * quantity;
            case 3 -> 18000 * quantity;
            case 4 -> 3000 * quantity;
            case 5 -> 5000 * quantity;
            default -> 0;
        };
    }
}