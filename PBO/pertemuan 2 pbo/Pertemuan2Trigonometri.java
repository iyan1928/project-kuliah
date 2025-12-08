import java.util.Scanner; // Mengimpor kelas Scanner
import java.lang.Math; // Mengimpor kelas Math untuk operasi matematika

public class Pertemuan2Trigonometri {
    public static void main(String[] args) {
        // Membuat objek Scanner untuk input
        Scanner kbd = new Scanner(System.in);
        
        // Deklarasi variabel untuk jarak (x) dan sudut (derajat)
        double x, derajat;
        
        // Meminta input untuk jarak horizontal (x)
        System.out.print("x = ");
        x = kbd.nextDouble();
        
        // Meminta input untuk sudut dalam derajat
        System.out.print("Sudut (dalam derajat) = ");
        derajat = kbd.nextDouble();
        
        // Mengonversi sudut dari derajat ke radian karena Math.tan() menggunakan radian
        double radian = Math.toRadians(derajat);
        
        // Menghitung tinggi (y) menggunakan rumus y = x * tan(radian)
        double y = x * Math.tan(radian);
        
        // Menampilkan hasil tinggi menara dengan format 2 angka di belakang koma
        System.out.printf("Tinggi menara = %.2f\n", y);
        
        // Menutup scanner
        kbd.close();
    }
}