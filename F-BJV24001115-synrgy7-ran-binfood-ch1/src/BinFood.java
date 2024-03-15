import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class BinFood {
    static Scanner scanner = new Scanner(System.in);
    static StringBuilder strukPembayaran = new StringBuilder();

    public static void main(String[] args) {
        menuUtama();
    }

    static void menuUtama() {
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
        int pilihan = scanner.nextInt();
        switch (pilihan) {
            case 1, 2, 3, 4, 5:
                menuPesanan(pilihan);
                break;
            case 99:
                menuPembayaran();
                break;
            case 0:
                System.out.println("\nTerima kasih telah");
                System.out.println("menggunakan BinFood!");
                System.exit(0);
                break;
            default:
                System.out.println("Pilihan tidak valid!");
                menuUtama();
                break;
        }
    }

    static void menuPesanan(int item) {
        System.out.println("\n==========================");
        System.out.println("Berapa pesanan Anda?");
        System.out.println("==========================");
        String namaItem = daftarNamaItem(item);
        int hargaItem = daftarHargaItem(item);
        System.out.println(namaItem + "       | " + hargaItem);
        System.out.println("Pilih 0 untuk kembali.");
        System.out.print("Jumlah => ");
        int jumlahPesanan = scanner.nextInt();
        if (jumlahPesanan == 0) {
            menuUtama();
        } else {
            int total = hargaItem * jumlahPesanan;
            strukPembayaran.append(namaItem).append("\t").append(jumlahPesanan).append("\t").append(total).append("\n");
            menuUtama();
        }
    }

    static void menuPembayaran() {
        System.out.println("\n==========================");
        System.out.println("Konfirmasi & Pembayaran");
        System.out.println("==========================");
        System.out.println("Pesanan Anda:");

        StringTokenizer tokenizer = new StringTokenizer(strukPembayaran.toString(), "\n");
        while (tokenizer.hasMoreTokens()) {
            String baris = tokenizer.nextToken();
            String[] bagian = baris.split("\t");
            String namaItem = bagian[0];
            int jumlah = Integer.parseInt(bagian[1]);
            int total = Integer.parseInt(bagian[2]);
            System.out.printf("%-16s %d %d\n", namaItem, jumlah, total);
        }
        System.out.println("=========================+");
        int totalPembayaran = hitungTotal();
        System.out.print("Total\t\t\t   " + totalPembayaran);
        System.out.println("\n1. Konfirmasi dan Bayar");
        System.out.println("2. Kembali ke Menu Utama");
        System.out.println("0. Keluar Aplikasi");
        System.out.print("Pilih => ");
        int pilihan = new Scanner(System.in).nextInt();
        switch (pilihan) {
            case 1:
                simpanStruk(totalPembayaran);
                System.out.println("\nTerima kasih telah");
                System.out.println("berbelanja di BinFood!");
                System.exit(0);
                break;
            case 2:
                menuUtama();
                break;
            case 0:
                System.out.println("\nTerima kasih telah");
                System.out.println("menggunakan BinFood!");
                System.exit(0);
                break;
            default:
                System.out.println("Pilihan tidak valid!");
                menuPembayaran();
                break;
        }
    }

    static int hitungTotal() {
        int total = 0;
        StringTokenizer tokenizer = new StringTokenizer(strukPembayaran.toString(), "\n");
        while (tokenizer.hasMoreTokens()) {
            String baris = tokenizer.nextToken();
            String[] bagian = baris.split("\t");
            total += Integer.parseInt(bagian[2]);
        }
        return total;
    }

    static String daftarNamaItem(int item) {
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

    static int daftarHargaItem(int item) {
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

    static void simpanStruk(int totalPembayaran) {
        try {
            FileWriter writer = new FileWriter("struk_pembayaran.txt");
            writer.write("==========================\n");
            writer.write("BinFood\n");
            writer.write("==========================\n");
            writer.write("Terima kasih sudah memesan\n");
            writer.write("di BinFood. Di bawah ini\n");
            writer.write("adalah pesanan Anda:\n");

            StringTokenizer tokenizer = new StringTokenizer(strukPembayaran.toString(), "\n");
            while (tokenizer.hasMoreTokens()) {
                String baris = tokenizer.nextToken();
                String[] bagian = baris.split("\t");
                String namaItem = bagian[0];
                int jumlah = Integer.parseInt(bagian[1]);
                int total = Integer.parseInt(bagian[2]);
                writer.write(String.format("%-16s %d %d\n", namaItem, jumlah, total));
            }

            writer.write("=========================+\n");
            writer.write(String.format("Total\t\t\t   %d\n", totalPembayaran));
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