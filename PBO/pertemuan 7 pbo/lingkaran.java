public class lingkaran {

    public static double luasLingkaran(double r) {
        return Math.PI * r * r;
    }

    public static void main(String[] args) {

        Runtime runtime = Runtime.getRuntime();
        long memoryBefore, memoryAfter;
        long startTime, endTime;

        memoryBefore = runtime.totalMemory() - runtime.freeMemory();

        startTime = System.nanoTime();

        double r = 7.0;
        double hasil = luasLingkaran(r);
        System.out.println("Hasil luas lingkaran: " + hasil);

        endTime = System.nanoTime();

        memoryAfter = runtime.totalMemory() - runtime.freeMemory();

        double totalWaktuMS = (double) (endTime - startTime) / 1_000_000_.0;

        long memoriTerpakaiBytes = memoryAfter - memoryBefore;

        double memoryBeforeMB = (double) memoryBefore / (1024.0 * 1024.0);
        double memoryAfterMB = (double) memoryAfter / (1024.0 * 1024.0);
        double memoriTerpakaiMB = (double) memoriTerpakaiBytes / (1024.0 * 1024.0);

        System.out.println("---------------------------------");

        System.out.printf("Memori Sebelum Eksekusi: %.2f MB\n", memoryBeforeMB);
        System.out.printf("Memori Sesudah Eksekusi: %.2f MB\n", memoryAfterMB);
        System.out.printf("Total Memori Terpakai: %.2f MB\n", memoriTerpakaiMB);

        System.out.println("Total Waktu Eksekusi: " + totalWaktuMS + " milidetik");
    }
}
