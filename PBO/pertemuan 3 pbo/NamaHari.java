public import java.util.Scanner;

public class NamaHari {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Masukkan kode hari (0-6): ");
        int kodeHari = input.nextInt();

        String namaHari;

        switch (kodeHari) {
            case 0:
                namaHari = "Minggu";
                break;
            case 1:
                namaHari = "Senin";
                break;
            case 2:
                namaHari = "Selasa";
                break;
            case 3:
                namaHari = "Rabu";
                break;
            case 4:
                namaHari = "Kamis";
                break;
            case 5:
                namaHari = "Jumat";
                break;
            case 6:
                namaHari = "Sabtu";
                break;
            default:
                namaHari = "Kode yang Anda masukkan tidak valid.";
                break;
        }

        System.out.println("Hasil: " + namaHari);
    }
} 
    
