import org.example.model.Order;
import org.example.util.OrderUtilException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BinFood {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Order> orderList = new ArrayList<>();

    public static void main(String[] args) {
        displayMainMenu();
    }

    private static void displayMainMenu() {
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
        int option = scanner.nextInt();
        switch (option) {
            case 1, 2, 3, 4, 5:
                placeOrder(option);
                break;
            case 99:
                checkout();
                break;
            case 0:
                System.out.println("\nTerima kasih telah");
                System.out.println("menggunakan BinFood!");
                System.exit(0);
                break;
            default:
                System.out.println("\nPilihan tidak valid!");
                displayMainMenu();
                break;
        }
    }

    private static void placeOrder(int item) {
        System.out.println("\n==========================");
        System.out.println("Berapa pesanan Anda?");
        System.out.println("==========================");
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
            System.out.println("\nPesanan berhasil ditambahkan!");
            displayMainMenu();
        } catch (OrderUtilException e) {
            System.out.println("\n==========================");
            System.out.println("Minimal 1 jumlah pesanan!");
            System.out.println("==========================");
            System.out.println("Mohon masukkan input pilihan Anda:");
            System.out.println("(y) untuk lanjut");
            System.out.println("(n) untuk keluar");
            System.out.print("Pilih => ");
            char choice = scanner.next().charAt(0);
            if (choice == 'y') {
                placeOrder(item);
            } else if (choice == 'n') {
                System.out.println("\nTerima kasih telah");
                System.out.println("menggunakan BinFood!");
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
            System.out.println("\n==========================");
            System.out.println("Konfirmasi & Pembayaran");
            System.out.println("==========================");
            System.out.println("Pesanan Anda:");

            for (Order order : orderList) {
                System.out.printf("%-16s %d %d\n", order.getItemName(), order.getQuantity(), order.getTotalPrice());
            }

            int totalPayment = calculateTotalPayment();
            System.out.println("=========================+");
            System.out.printf("Total\t\t\t   %d\n", totalPayment);
            System.out.println("1. Konfirmasi dan Bayar");
            System.out.println("2. Kembali ke Menu Utama");
            System.out.println("0. Keluar Aplikasi");
            System.out.print("Pilih => ");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    saveReceipt(totalPayment);
                    System.out.println("\nTerima kasih telah");
                    System.out.println("berbelanja di BinFood!");
                    System.exit(0);
                    break;
                case 2:
                    displayMainMenu();
                    break;
                case 0:
                    System.out.println("\nTerima kasih telah");
                    System.out.println("menggunakan BinFood!");
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
        int total = 0;
        for (Order order : orderList) {
            total += order.getTotalPrice();
        }
        return total;
    }

    private static String getItemName(int item) {
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

    private static int getItemPrice(int item) {
        switch (item) {
            case 1:
                return 15000;
            case 2:
                return 13000;
            case 3:
                return 18000;
            case 4:
                return 3000;
            case 5:
                return 5000;
            default:
                return 0;
        }
    }

    private static void saveReceipt(int totalPayment) {
        try (FileWriter writer = new FileWriter("struk_pembayaran.txt")) {
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
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat mencoba menyimpan struk pembayaran.");
            e.printStackTrace();
        }
    }
}