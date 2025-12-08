public class Pertemuan2RapatKiri {
    public static void main(String[] args) {
        // Deklarasi dan inisialisasi variabel nama barang dan harga
        String namaBarang = "Buku Pemrograman Berorientasi Objek";
        int harga = 102000;
        
        // Mencetak dengan cara biasa menggunakan penggabungan string (+)
        System.out.println("|" + namaBarang + "| " + harga + "|");
        
        // Mencetak dengan format:
        // %30s -> string dengan lebar 30 karakter, rata kanan (default)
        // %10d -> integer dengan lebar 10 karakter, rata kanan (default)
        System.out.printf("|%30s| %10d|\n", namaBarang, harga);
        
        // Mencetak dengan format:
        // %-30s -> string dengan lebar 30 karakter, rata kiri (karena ada tanda "-")
        // %10d -> integer dengan lebar 10 karakter, rata kanan
        System.out.printf("|%-30s| %10d|\n", namaBarang, harga);
    }
}