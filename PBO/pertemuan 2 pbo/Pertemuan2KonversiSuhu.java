import java.util.Scanner; // Mengimpor kelas Scanner

public class Pertemuan2KonversiSuhu {
    public static void main(String[] args) {
        // Deklarasi variabel celcius dan reamur
        float celcius;
        double reamur;
        
        // Membuat objek Scanner
        Scanner kbd = new Scanner(System.in);
        
        // Meminta input suhu dalam Celcius
        System.out.print("Suhu dalam celcius: ");
        celcius = kbd.nextFloat();
        
        // Menghitung konversi ke Reamur menggunakan rumus
        reamur = 0.8 * celcius;
        
        // Menampilkan hasil konversi
        System.out.println("Reamur = " + reamur);
        
        // Menutup scanner
        kbd.close();
    }
}