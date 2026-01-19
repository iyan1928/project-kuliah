package projekpbo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.text.NumberFormat;
import java.util.ArrayList;

public class PembayaranTiketKereta extends Application {

    private VBox panelStruk;
    private VBox panelMemory;
    private NumberFormat fmt = NumberFormat.getInstance();
    private ArrayList<byte[]> penampungDataBerat = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        Label lblJudul = new Label("PEMESANAN TIKET KERETA");
        lblJudul.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        TextField tfTujuan = new TextField();
        tfTujuan.setPromptText("Tujuan");

        ComboBox<String> cbKelas = new ComboBox<>();
        cbKelas.getItems().addAll("Ekonomi", "Bisnis", "Eksekutif");
        cbKelas.getSelectionModel().selectFirst();
        cbKelas.setMaxWidth(Double.MAX_VALUE);

        TextField tfJumlah = new TextField();
        tfJumlah.setPromptText("Jumlah");
        
        tfJumlah.textProperty().addListener((obs, old, newVal) -> {
            if (!newVal.matches("\\d*")) tfJumlah.setText(newVal.replaceAll("[^\\d]", ""));
        });

        Button btnProses = new Button("PROSES TIKET");
        btnProses.setMaxWidth(Double.MAX_VALUE);
        btnProses.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");

        VBox layoutInput = new VBox(10, lblJudul, new Separator(), 
                new Label("Tujuan:"), tfTujuan, new Label("Kelas:"), cbKelas, new Label("Jumlah:"), tfJumlah, btnProses);
        layoutInput.setPadding(new Insets(15));
        layoutInput.setStyle("-fx-background-color: white; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 1);");

        ToggleButton btnStruk = new ToggleButton("📄 Struk");
        ToggleButton btnMemory = new ToggleButton("📊 Memory");
        ToggleGroup group = new ToggleGroup();
        btnStruk.setToggleGroup(group); btnMemory.setToggleGroup(group);
        btnStruk.setSelected(true); 
        btnStruk.setMaxWidth(Double.MAX_VALUE); btnMemory.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(btnStruk, Priority.ALWAYS); HBox.setHgrow(btnMemory, Priority.ALWAYS);
        HBox layoutTombol = new HBox(5, btnStruk, btnMemory);

        panelStruk = new VBox(10);
        panelStruk.setAlignment(Pos.CENTER);
        panelStruk.setPadding(new Insets(15));
        panelStruk.setStyle("-fx-background-color: #fff; -fx-border-color: #bdc3c7; -fx-border-style: dashed; -fx-border-width: 2;");
        panelStruk.getChildren().add(new Label("Belum ada data..."));

        panelMemory = new VBox(10);
        panelMemory.setPadding(new Insets(15));
        panelMemory.setStyle("-fx-background-color: #2c3e50;");
        Label lblInfo = new Label("Klik PROSES untuk melihat log.");
        lblInfo.setTextFill(Color.WHITE);
        panelMemory.getChildren().add(lblInfo);

        StackPane outputStack = new StackPane(panelMemory, panelStruk);
        panelStruk.visibleProperty().bind(btnStruk.selectedProperty());
        panelMemory.visibleProperty().bind(btnMemory.selectedProperty());

        btnProses.setOnAction(e -> {
            try {
                String tujuan = tfTujuan.getText();
                String inputJumlah = tfJumlah.getText();
                String kelas = cbKelas.getValue();

                if (tujuan.isEmpty() || inputJumlah.isEmpty()) { 
                    showAlert("Data Kosong!"); 
                    return; 
                }
                int jumlah = Integer.parseInt(inputJumlah);
                
                System.gc();
                try { 
                    Thread.sleep(50); 
                } catch (Exception ex) {} 
                Runtime rt = Runtime.getRuntime();
                
                long totalBefore = rt.totalMemory() / 1024;
                long freeBefore = rt.freeMemory() / 1024;
                long usedBefore = totalBefore - freeBefore;

                TiketKereta tiket;
                if (kelas.equals("Ekonomi")) tiket = new TiketEkonomi(tujuan, jumlah);
                else if (kelas.equals("Bisnis")) tiket = new TiketBisnis(tujuan, jumlah);
                else tiket = new TiketEksekutif(tujuan, jumlah);

                byte[] beban = new byte[1024 * 1024];
                penampungDataBerat.add(beban);

                long totalAfter = rt.totalMemory() / 1024;
                long freeAfter = rt.freeMemory() / 1024;
                long usedAfter = totalAfter - freeAfter;
                
                long selisih = usedAfter - usedBefore;

                updateStruk(tiket);
                updateMemory(totalBefore, freeBefore, usedBefore, 
                             totalAfter, freeAfter, usedAfter, selisih);
                
                btnStruk.setSelected(true);

            } catch (Exception ex) {
                showAlert("Error: " + ex.getMessage());
            }
        });

        VBox root = new VBox(15, layoutInput, layoutTombol, outputStack);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #ecf0f1;");

        Scene scene = new Scene(root, 360, 700);
        primaryStage.setTitle("HASBUL HADI ADZKIYAN");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateMemory(long t1, long f1, long u1, long t2, long f2, long u2, long diff) {
        panelMemory.getChildren().clear();
        Label title = new Label("LOG MONITOR MEMORI");
        title.setTextFill(Color.YELLOW); title.setFont(Font.font("Consolas", FontWeight.BOLD, 14));

        panelMemory.getChildren().addAll(
            title, new Separator(),
            
            header("SEBELUM PROSES"),
            rowMem("Total Memory", fmt.format(t1) + " KB"),
            rowMem("Free Memory", fmt.format(f1) + " KB"),
            rowMem("Used Memory", fmt.format(u1) + " KB"),
            
            new Label(" "),
            
            header("SESUDAH PROSES"),
            rowMem("Total Memory", fmt.format(t2) + " KB"),
            rowMem("Free Memory", fmt.format(f2) + " KB"),
            rowMem("Used Memory", fmt.format(u2) + " KB"),
            
            new Separator(),
            rowMem("KENAIKAN USED", "+" + fmt.format(diff) + " KB")
        );
    }

    private void updateStruk(TiketKereta t) {
        panelStruk.getChildren().clear();
        Label head = new Label("=== STRUK PEMESANAN ===");
        head.setFont(Font.font("Courier New", FontWeight.BOLD, 16));
        panelStruk.getChildren().addAll(
                head,
            rowStruk("Tujuan", t.tujuan.toUpperCase()),
            rowStruk("Kelas", t.getNamaKelas()),
            rowStruk("Penumpang", t.jumlahPenumpang + " Orang"),
            rowStruk("Harga", "Rp " + fmt.format(t.getHargaSatuan())),
            rowStruk("TOTAL", "Rp " + fmt.format(t.getHargaSatuan()*t.jumlahPenumpang))
        );
    }

    private Label header(String s) { 
        Label l=new Label(s); 
    l.setTextFill(Color.LIGHTGREEN); 
    l.setFont(Font.font("Consolas",12)); 
    return l; 
    }
    
    private HBox rowMem(String k, String v) {
        Label l1=new Label(String.format("%-14s", k)); 
        l1.setTextFill(Color.WHITE); 
        l1.setFont(Font.font("Consolas",12));
        Label l2=new Label(": " + v); 
        l2.setTextFill(Color.WHITE); 
        l2.setFont(Font.font("Consolas",12));
        return new HBox(l1, l2);
    }
    
    private HBox rowStruk(String k, String v) {
        Label l1=new Label(k); 
        l1.setFont(Font.font("Courier New",12));
        Label l2=new Label(v); 
        l2.setFont(Font.font("Courier New",FontWeight.BOLD,12));
        Region sp=new Region(); 
        HBox.setHgrow(sp, Priority.ALWAYS);
        return new HBox(l1, sp, l2);
    }
    
    private void showAlert(String m) { 
        new Alert(Alert.AlertType.WARNING, m).show(); 
    }

    public static void main(String[] args) { 
        launch(args); 
    }
}