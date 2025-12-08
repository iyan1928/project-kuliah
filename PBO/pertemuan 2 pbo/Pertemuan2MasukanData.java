import java.util.Scanner; // Mengimpor kelas Scanner untuk membaca input dari user

public class Pertemuan2MasukanData {
    public static void main(String[] args) {
        // Deklarasi variabel 'usia' untuk menyimpan input
        int usia;
        
        // Membuat objek Scanner baru untuk membaca input dari keyboard
        Scanner kbd = new Scanner(System.in);
        
        // Meminta user untuk memasukkan usia
        System.out.print("Masukkan usia anda: ");
        // Membaca input integer dari user dan menyimpannya ke variabel 'usia'
        usia = kbd.nextInt();
        
        // Menampilkan usia user untuk tahun depan dengan menambahkan 1
        System.out.println("Terima kasih. Usia anda tahun depan: " + (usia + 1));
        
        // Menutup objek scanner untuk melepaskan sumber daya
        kbd.close();
    }
}