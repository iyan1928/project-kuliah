package projekpbo;

public class TiketBisnis extends TiketKereta {
    public TiketBisnis(String tujuan, int jumlah) {
        super(tujuan, jumlah);
    }

    @Override
    public int getHargaSatuan() {
        return 150000;
    }

    @Override
    public String getNamaKelas() {
        return "Bisnis";
    }
}