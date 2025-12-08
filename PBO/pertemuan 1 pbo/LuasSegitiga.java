import java.util.Scanner;

public class LuasSegitiga {
    public static void main(String[] args) {
        // Membuat objek Scanner untuk menerima input dari user
        Scanner input = new Scanner(System.in);

        // Meminta user memasukkan alas
        System.out.print("Masukkan alas: ");
        double alas = input.nextDouble();

        // Meminta user memasukkan tinggi
        System.out.print("Masukkan tinggi: ");
        double tinggi = input.nextDouble();

        // Menghitung luas segitiga
        double luas = 0.5 * alas * tinggi;

        // Menampilkan hasil
        System.out.println("Luas segitiga adalah " + luas + " cm");

        // Menutup scanner
        input.close();
    }
}
