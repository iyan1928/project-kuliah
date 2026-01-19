package projekpbo;

public class TiketEkonomi extends TiketKereta {
    public TiketEkonomi(String tujuan, int jumlah) {
        super(tujuan, jumlah);
    }

    @Override
    public int getHargaSatuan() {
        return 75000;
    }

    @Override
    public String getNamaKelas() {
        return "Ekonomi";
    }
}