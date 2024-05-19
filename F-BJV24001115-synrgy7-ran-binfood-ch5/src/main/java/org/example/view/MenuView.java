package org.example.view;

import org.example.model.Order;

import java.util.List;
import java.util.Scanner;

public class MenuView {
    private static Scanner scanner = new Scanner(System.in);

    public static void displayMainMenu() {
        System.out.println("\n==========================");
        System.out.println("Selamat Datang di BinFood");
        System.out.println("==========================");
        System.out.println("Silahkan pilih makanan:");
        System.out.println("1. Nasi Goreng   | 15.000");
        System.out.println("2. Mie Goreng    | 13.000");
        System.out.println("3. Nasi + Ayam   | 18.000");
        System.out.println("4. Es Teh Manis  | 3.000");
        System.out.println("5. Es Jeruk      | 5.000");
        System.out.println("99. Pesan dan Bayar");
        System.out.println("0. Keluar Aplikasi");
        System.out.print("Pesan => ");
    }

    public static int getMenuOption() {
        return scanner.nextInt();
    }

    public static int getQuantity() {
        System.out.print("Jumlah => ");
        return scanner.nextInt();
    }

    public static void displayCheckoutMenu(int totalPayment, List<Order> orderList) {
        System.out.println("\n==========================");
        System.out.println("Konfirmasi & Pembayaran");
        System.out.println("==========================");
        System.out.println("Pesanan Anda:");

        for (Order order : orderList) {
            System.out.printf("%-16s %d %d\n", order.getItemName(), order.getQuantity(), order.getTotalPrice());
        }

        System.out.println("=========================+");
        System.out.printf("Total\t\t\t   %d\n", totalPayment);
        System.out.println("1. Konfirmasi dan Bayar");
        System.out.println("2. Kembali ke Menu Utama");
        System.out.println("0. Keluar Aplikasi");
        System.out.print("Pilih => ");
    }
}