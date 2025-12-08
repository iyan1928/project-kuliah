import java.util.Scanner;

public class LuasLingkaran {
    public static void main(String[] args) {
        // Membuat objek Scanner untuk menerima input dari user
        Scanner input = new Scanner(System.in);

        // Meminta user memasukkan jari-jari
        System.out.print("Masukkan jari-jari lingkaran (cm): ");
        double jarijari = input.nextDouble();

        // Menghitung luas lingkaran menggunakan konstanta PI dari 
        double luas = Math.PI * jarijari * jarijari;

        // Menampilkan hasil
        System.out.println("Luas lingkaran adalah " + luas + " cm");

        // Menutup scanner
        input.close();
    }
}