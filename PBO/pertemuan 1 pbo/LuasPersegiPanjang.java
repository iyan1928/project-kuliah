import java.util.Scanner;

public class LuasPersegiPanjang {
    public static void main(String[] args) {
        // Membuat objek Scanner untuk menerima input dari user
        Scanner input = new Scanner(System.in);

        // Meminta user memasukkan panjang
        System.out.print("Masukkan panjang persegi panjang: ");
        double panjang = input.nextDouble();

        // Meminta user memasukkan lebar
        System.out.print("Masukkan lebar persegi panjang: ");
        double lebar = input.nextDouble();

        // Menghitung luas persegi panjang
        double luas = panjang * lebar;

        // Menampilkan hasil
        System.out.println("Luas persegi panjang adalah: " + luas);

        // Menutup scanner
        input.close();
    }
}