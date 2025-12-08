import java.util.Scanner;
class TiketKereta {
    private String tujuan;
    private String namaKelas;
    private int jumlahPenumpang;
    private int hargaSatuan;

    public TiketKereta(String inputTujuan, int inputPilihanKelas, int inputJumlah) {
        this.tujuan = inputTujuan;
        this.jumlahPenumpang = inputJumlah;

        if (inputPilihanKelas == 1) {
            this.hargaSatuan = 75000;
            this.namaKelas = "Ekonomi";
        } else if (inputPilihanKelas == 2) {
            this.hargaSatuan = 150000;
            this.namaKelas = "Bisnis";
        } else if (inputPilihanKelas == 3) {
            this.hargaSatuan = 300000;
            this.namaKelas = "Eksekutif";
        } else {
            this.hargaSatuan = 0;
            this.namaKelas = "Tidak Diketahui";
        }
    }

    public int hitungTotalBayar() {
        return this.hargaSatuan * this.jumlahPenumpang;
    }

    public String getTujuan() {
        return this.tujuan;
    }

    public String getNamaKelas() {
        return this.namaKelas;
    }

    public int getJumlahPenumpang() {
        return this.jumlahPenumpang;
    }

    public int getHargaSatuan() {
        return this.hargaSatuan;
    }
}

public class PembayaranTiketKereta {
    public static void main(String[] args) {
    Scanner input = new Scanner(System.in);

        System.out.println("--- PEMESANAN TIKET KERETA ---");
        System.out.print("Masukkan Tujuan: ");
        String tujuanInput = input.nextLine();

        System.out.println("Pilih Kelas Kereta:");
        System.out.println("1. Ekonomi");
        System.out.println("2. Bisnis");
        System.out.println("3. Eksekutif");
        System.out.print("Masukkan pilihan (1-3): ");
        int pilihanInput = input.nextInt();

        System.out.print("Masukkan Jumlah Penumpang: ");
        int jumlahInput = input.nextInt();

    PembayaranTiketKereta pesanan = new PembayaranTiketKereta(tujuanInput, pilihanInput, jumlahInput);

        if (pesanan.getHargaSatuan() == 0) {
            System.out.println("Pilihan kelas tidak valid! Transaksi dibatalkan.");
        } else {
            System.out.println("\n=== Struk Pembayaran ===");
            System.out.println("Tujuan            : " + pesanan.getTujuan());
            System.out.println("Kelas             : " + pesanan.getNamaKelas());
            System.out.println("Jumlah Penumpang  : " + pesanan.getJumlahPenumpang());
            System.out.println("Harga Satu tiket  : Rp." + pesanan.getHargaSatuan());
            System.out.println("=================================");
            System.out.println("Total Bayar       : Rp." + pesanan.hitungTotalBayar());
        }
    }
}