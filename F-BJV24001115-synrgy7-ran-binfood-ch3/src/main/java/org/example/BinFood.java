import org.example.controller.OrderController;
import org.example.model.Order;
import org.example.util.OrderUtilException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.*;
import java.util.stream.Collectors;

public class BinFood {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Order> orderList = new ArrayList<>();
    private static final String MENU_SEPARATOR = "==========================";
    private static final String THANK_YOU_MESSAGE = "Terima kasih telah";
    private static final String USING_BINFOOD_MESSAGE = "menggunakan BinFood!";
    private static final String NEW_LINE = System.lineSeparator();
    private static final String MENU_SEPARATOR_FORMAT = "%s%s%s";
    private static final String MENU_SEPARATOR_NEWLINE_FORMAT = "%s%s%s\n";

    public static void main(String[] args) {
        displayMainMenu();
    }

    private static void displayMainMenu() {
        System.out.printf(MENU_SEPARATOR_FORMAT, NEW_LINE, MENU_SEPARATOR, NEW_LINE);
        System.out.println("Selamat Datang di BinFood");
        System.out.printf(MENU_SEPARATOR_NEWLINE_FORMAT, MENU_SEPARATOR, "", "");
        System.out.println("Silahkan pilih makanan:");
        System.out.println("1. Nasi Goreng   | 15.000");
        System.out.println("2. Mie Goreng    | 13.000");
        System.out.println("3. Nasi + Ayam   | 18.000");
        System.out.println("4. Es Teh Manis  | 3.000");
        System.out.println("5. Es Jeruk      | 5.000");
        System.out.println("99. Pesan dan Bayar");
        System.out.println("0. Keluar Aplikasi");
        System.out.print("Pesan => ");

        int option = scanner.nextInt();
        switch (option) {
            case 1, 2, 3, 4, 5:
                placeOrder(option);
                break;
            case 99:
                checkout();
                break;
            case 0:
                System.out.printf(MENU_SEPARATOR_FORMAT, NEW_LINE, THANK_YOU_MESSAGE, NEW_LINE);
                System.out.println(USING_BINFOOD_MESSAGE);
                System.exit(0);
                break;
            default:
                System.out.println("\nPilihan tidak valid!");
                displayMainMenu();
                break;
        }
    }

    private static void placeOrder(int item) {
        System.out.printf(MENU_SEPARATOR_FORMAT, NEW_LINE, MENU_SEPARATOR, NEW_LINE);
        System.out.println("Berapa pesanan Anda?");
        System.out.printf(MENU_SEPARATOR_NEWLINE_FORMAT, MENU_SEPARATOR, "", "");
        String itemName = getItemName(item);
        int itemPrice = getItemPrice(item);
        System.out.println(itemName + "\t| " + itemPrice);
        System.out.println("Pilih 0 untuk kembali.");
        System.out.print("Jumlah => ");
        int quantity = scanner.nextInt();

        if (quantity == 0) {
            displayMainMenu();
            return;
        }
        try {
            validateQuantity(quantity);
            int totalPrice = itemPrice * quantity;
            Order order = new Order(itemName, quantity, totalPrice);
            orderList.add(order);
            displayMainMenu();
        } catch (OrderUtilException e) {
            System.out.printf(MENU_SEPARATOR_FORMAT, NEW_LINE, MENU_SEPARATOR, NEW_LINE);
            System.out.println("Minimal 1 jumlah pesanan!");
            System.out.printf(MENU_SEPARATOR_NEWLINE_FORMAT, MENU_SEPARATOR, "", "");
            System.out.println("Mohon masukkan input");
            System.out.println("pilihan Anda:");
            System.out.println("(y) untuk lanjut");
            System.out.println("(n) untuk keluar");
            System.out.print("Pilih => ");
            char choice = scanner.next().charAt(0);
            if (choice == 'y') {
                placeOrder(item);
            } else if (choice == 'n') {
                System.out.printf(MENU_SEPARATOR_FORMAT, NEW_LINE, THANK_YOU_MESSAGE, NEW_LINE);
                System.out.println(USING_BINFOOD_MESSAGE);
                System.exit(0);
            } else {
                System.out.println("Pilihan tidak valid!");
                placeOrder(item);
            }
        }
    }

    private static void validateQuantity(int quantity) throws OrderUtilException {
        if (quantity <= 0) {
            throw new OrderUtilException("Minimal 1 jumlah pesanan!");
        }
    }

    private static void checkout() {
        if (!orderList.isEmpty()) {
            System.out.printf(MENU_SEPARATOR_FORMAT, NEW_LINE, MENU_SEPARATOR, NEW_LINE);
            System.out.println("Konfirmasi & Pembayaran");
            System.out.printf(MENU_SEPARATOR_NEWLINE_FORMAT, MENU_SEPARATOR, "", "");
            System.out.println("Pesanan Anda:");

            orderList.forEach(order -> System.out.printf("%-16s %d %d%n", order.getItemName(), order.getQuantity(), order.getTotalPrice()));

            int totalPayment = calculateTotalPayment();
            System.out.println("=========================+");
            System.out.printf("Total\t\t\t   %d%n", totalPayment);
            System.out.println("1. Konfirmasi dan Bayar");
            System.out.println("2. Kembali ke Menu Utama");
            System.out.println("0. Keluar Aplikasi");
            System.out.print("Pilih => ");

            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    saveReceipt(totalPayment);
                    System.out.printf(MENU_SEPARATOR_FORMAT, NEW_LINE, THANK_YOU_MESSAGE, NEW_LINE);
                    System.out.println("berbelanja di BinFood!");
                    System.exit(0);
                    break;
                case 2:
                    displayMainMenu();
                    break;
                case 0:
                    System.out.printf(MENU_SEPARATOR_FORMAT, NEW_LINE, THANK_YOU_MESSAGE, NEW_LINE);
                    System.out.println(USING_BINFOOD_MESSAGE);
                    System.exit(0);
                    break;
                default:
                    System.out.println("Pilihan tidak valid!");
                    checkout();
                    break;
            }
        } else {
            System.out.println("Tidak ada pesanan yang ditempatkan.");
            displayMainMenu();
        }
    }

    private static int calculateTotalPayment() {
        return orderList.stream().mapToInt(Order::getTotalPrice).sum();
    }

    private static String getItemName(int item) {
        Map<Integer, String> itemMap = new HashMap<>();
        itemMap.put(1, "Nasi Goreng");
        itemMap.put(2, "Mie Goreng");
        itemMap.put(3, "Nasi + Ayam");
        itemMap.put(4, "Es Teh Manis");
        itemMap.put(5, "Es Jeruk");

        return Optional.ofNullable(itemMap.get(item)).orElse("Item Tidak Dikenal");
    }

    private static int getItemPrice(int item) {
        Map<Integer, Integer> priceMap = new HashMap<>();
        priceMap.put(1, 15000);
        priceMap.put(2, 13000);
        priceMap.put(3, 18000);
        priceMap.put(4, 3000);
        priceMap.put(5, 5000);

        return Optional.ofNullable(priceMap.get(item)).orElse(0);
    }

    private static void saveReceipt(int totalPayment) {
        try (FileWriter writer = new FileWriter("struk_pembayaran.txt")) {
            writer.write(String.format(MENU_SEPARATOR_FORMAT, "", MENU_SEPARATOR, ""));
            writer.write(NEW_LINE + "BinFood" + NEW_LINE);
            writer.write(String.format(MENU_SEPARATOR_FORMAT, "", MENU_SEPARATOR, ""));
            writer.write(NEW_LINE + "Terima kasih sudah memesan" + NEW_LINE);
            writer.write("di BinFood. Di bawah ini" + NEW_LINE);
            writer.write("adalah pesanan Anda:" + NEW_LINE);

            orderList.forEach(order -> {
                try {
                    writer.write(String.format("%-16s %d %d%n", order.getItemName(), order.getQuantity(), order.getTotalPrice()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            writer.write("=========================+" + NEW_LINE);
            writer.write(String.format("Total\t\t\t   %d%n", totalPayment));
            writer.write("Pembayaran: BinarCash" + NEW_LINE);
            writer.write(String.format(MENU_SEPARATOR_FORMAT, "", MENU_SEPARATOR, ""));
            writer.write(NEW_LINE + "Simpan struk ini sebagai" + NEW_LINE);
            writer.write("bukti pembayaran." + NEW_LINE);
            writer.write(String.format(MENU_SEPARATOR_FORMAT, "", MENU_SEPARATOR, ""));
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat mencoba menyimpan struk pembayaran.");
            e.printStackTrace();
        }
    }
}