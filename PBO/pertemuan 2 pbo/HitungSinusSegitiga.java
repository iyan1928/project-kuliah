import java.text.DecimalFormat;
import java.util.Scanner; // Impor kelas DecimalFormat

public class HitungSinusSegitiga {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Memasukkan sudut dalam derajat
        System.out.print("Masukkan sudut segitiga dalam derajat: ");
        double sudutDerajat = input.nextDouble();

        // Konversi sudut ke radian
        double sudutRadian = Math.toRadians(sudutDerajat);

        // Menghitung nilai sinus dari sudut tersebut
        double nilaiSinus = Math.sin(sudutRadian);

        // Membuat objek untuk memformat angka menjadi 2 desimal
        DecimalFormat df = new DecimalFormat("#.##");
        String hasilBulat = df.format(nilaiSinus);

        // Menampilkan hasil yang sudah dibulatkan
        System.out.println("Nilai sinus dari sudut " + sudutDerajat + " derajat adalah: " + hasilBulat);

        input.close();
    }
}