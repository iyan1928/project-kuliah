import java.util.Scanner;

public class Diskon {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        double hargaAwal, persentaseDiskon;

        System.out.print("Masukkan harga asli barang: Rp.");
        hargaAwal = input.nextDouble();

        System.out.print("Masukkan persen diskon: ");
        persentaseDiskon = input.nextDouble();
        System.out.println("Diskon: " + persentaseDiskon + "%");

        tampilkanHasilDiskon(hargaAwal, persentaseDiskon);
        
    }
     
    public static void tampilkanHasilDiskon(double harga, double persen) {
        double potonganHarga = harga * (persen / 100.0);
        double hargaAkhir = harga - potonganHarga;

        System.out.printf("Harga Asli       : Rp.%.2f\n", harga);
        System.out.printf("Potongan Harga   : Rp.%.2f\n", potonganHarga);
        System.out.printf("Harga Akhir      : Rp.%.2f\n", hargaAkhir);
    }
}