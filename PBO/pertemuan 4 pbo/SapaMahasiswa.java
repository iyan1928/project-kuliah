import java.util.Scanner;

public class SapaMahasiswa {

    public static void sapa(String nama) {
        System.out.println("Halo " + nama + ", selamat datang di FASILKOM!");
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Masukkan nama: ");
        String nama = input.nextLine();
        sapa(nama);
    }
}