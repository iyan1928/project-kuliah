package projekpbo;

public class TiketEksekutif extends TiketKereta {
    public TiketEksekutif(String tujuan, int jumlah) {
        super(tujuan, jumlah);
    }

    @Override
    public int getHargaSatuan() {
        return 300000;
    }

    @Override
    public String getNamaKelas() {
        return "Eksekutif";
    }
}