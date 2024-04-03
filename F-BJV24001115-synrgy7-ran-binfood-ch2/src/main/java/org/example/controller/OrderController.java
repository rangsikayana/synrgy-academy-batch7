package org.example.controller;

import org.example.model.Order;
import org.example.service.OrderService;
import org.example.service.OrderServiceImpl;
import org.example.view.MenuView;
import org.example.view.OrderView;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderController {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Order> orderList = new ArrayList<>();
    private static OrderService orderService = new OrderServiceImpl(orderList);

    public static void main(String[] args) {
        displayMainMenu();
    }

    public static void displayMainMenu() {
        MenuView.displayMainMenu();
        int choice = MenuView.getMenuOption();
        switch (choice) {
            case 1, 2, 3, 4, 5:
                placeOrder(choice);
                break;
            case 99:
                checkout();
                break;
            case 0:
                System.out.println("\nTerima kasih telah menggunakan BinFood!");
                System.exit(0);
                break;
            default:
                System.out.println("Pilihan tidak valid!");
                displayMainMenu();
                break;
        }
    }

    public static void placeOrder(int item) {
        int quantity = MenuView.getQuantity();
        int totalPrice = calculateTotalPrice(item, quantity);
        Order order = new Order(getItemName(item), quantity, totalPrice);
        orderService.addOrder(order);
        System.out.println("Pesanan berhasil ditambahkan!");
        displayMainMenu();
    }

    public static void checkout() {
        List<Order> orders = orderService.getAllOrders();
        int totalPayment = orderService.calculateTotal();
        MenuView.displayCheckoutMenu(totalPayment, orders);
        int choice = MenuView.getMenuOption();
        switch (choice) {
            case 1:
                saveReceipt(totalPayment);
                System.out.println("\nTerima kasih telah berbelanja di BinFood!");
                System.exit(0);
                break;
            case 2:
                orderList.clear();
                System.out.println("\nPesanan telah dibatalkan.");
                displayMainMenu();
                break;
            case 0:
                System.out.println("\nTerima kasih telah menggunakan BinFood!");
                System.exit(0);
                break;
            default:
                System.out.println("Pilihan tidak valid!");
                checkout();
                break;
        }
    }

    public static int calculateTotalPrice(int item, int quantity) {
        switch (item) {
            case 1:
                return 15000 * quantity;
            case 2:
                return 13000 * quantity;
            case 3:
                return 18000 * quantity;
            case 4:
                return 3000 * quantity;
            case 5:
                return 5000 * quantity;
            default:
                return 0;
        }
    }

    public static String getItemName(int item) {
        switch (item) {
            case 1:
                return "Nasi Goreng";
            case 2:
                return "Mie Goreng";
            case 3:
                return "Nasi + Ayam";
            case 4:
                return "Es Teh Manis";
            case 5:
                return "Es Jeruk";
            default:
                return "Item Tidak Dikenal";
        }
    }

    public static void saveReceipt(int totalPayment) {
        try {
            FileWriter writer = new FileWriter("struk_pembayaran.txt");
            writer.write("==========================\n");
            writer.write("BinFood\n");
            writer.write("==========================\n");
            writer.write("Terima kasih sudah memesan\n");
            writer.write("di BinFood. Di bawah ini\n");
            writer.write("adalah pesanan Anda:\n");

            for (Order order : orderList) {
                writer.write(String.format("%-16s %d %d\n", order.getItemName(), order.getQuantity(), order.getTotalPrice()));
            }

            writer.write("=========================+\n");
            writer.write(String.format("Total\t\t\t   %d\n", totalPayment));
            writer.write("Pembayaran: BinarCash\n");
            writer.write("==========================\n");
            writer.write("Simpan struk ini sebagai\n");
            writer.write("bukti pembayaran.\n");
            writer.write("==========================\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat mencoba menyimpan struk pembayaran.");
            e.printStackTrace();
        }
    }
}