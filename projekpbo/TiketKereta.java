package projekpbo;

public class TiketKereta {
    protected String tujuan;
    protected int jumlahPenumpang;

    public TiketKereta(String tujuan, int jumlah) {
        this.tujuan = tujuan;
        this.jumlahPenumpang = jumlah;
    }

    public int getHargaSatuan() {
        return 0;
    }

    public String getNamaKelas() {
        return "Umum";
    }

    public void tampilkanStruk() {
        System.out.println("Tujuan           : " + this.tujuan);
        System.out.println("Kelas            : " + getNamaKelas());
        System.out.println("Jumlah Penumpang : " + this.jumlahPenumpang);
        System.out.println("Harga Satuan     : Rp." + getHargaSatuan());
        System.out.println("Total Bayar      : Rp." + (getHargaSatuan() * jumlahPenumpang));
    }
}