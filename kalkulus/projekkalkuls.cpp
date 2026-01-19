#include <iostream>
#include <cstdio>
#include <string>

using namespace std;

void kirimKeGnuplot(FILE *gp, string judul, string rumusPlot) {
    fprintf(gp, "set title '%s'\n", judul.c_str());
    fprintf(gp, "set xlabel 'Sumbu X'\n");
    fprintf(gp, "set ylabel 'Sumbu Y'\n");
    fprintf(gp, "set zlabel 'Sumbu Z'\n");
    fprintf(gp, "set grid\n");
    fprintf(gp, "set xzeroaxis lt -1 lw 2\n");
    fprintf(gp, "set yzeroaxis lt -1 lw 2\n");
    fprintf(gp, "set samples 1000\n");
    fprintf(gp, "set isosamples 50\n");
    fprintf(gp, "set hidden3d\n");
    fprintf(gp, "set pm3d depthorder\n");
    fprintf(gp, "%s\n", rumusPlot.c_str());
}
// BAGIAN 2D
void plot2D() {
    int pilihan;
    double a, b, c;
    char buffer[200]; 

    cout << "\n=== PILIH JENIS KURVA 2D ===\n";
    cout << "1. Kurva S (Kubik: y = ax^3 + bx)\n";
    cout << "2. Parabola (Kuadrat: y = ax^2 + bx + c)\n";
    cout << "3. Gelombang (Sinus: y = a * sin(bx))\n";
    cout << "Pilihanmu (1-3): ";
    if (!(cin >> pilihan)) {
        cin.clear(); cin.ignore(1000, '\n'); return;
    }

    FILE *gp = _popen("gnuplot -persistent", "w");
    if (!gp) { cout << "Error: Gnuplot tidak jalan.\n"; return; }

    if (pilihan == 1) {
        cout << "\n>> Mode Kubik (Kurva S).\n";
        cout << "Tips: Coba a = 0.1, b = -5\n";
        cout << "Masukkan a: "; cin >> a;
        cout << "Masukkan b: "; cin >> b;
        sprintf(buffer, "plot [-10:10] %f*x**3 + %f*x lw 3 lc rgb 'red' title 'Kurva S'", a, b);
        kirimKeGnuplot(gp, "Grafik Pangkat 3", string(buffer));

    } else if (pilihan == 2) {
        cout << "\n>> Mode Parabola.\n";
        cout << "Masukkan a, b, c (contoh: 1 0 -5): ";
        cin >> a >> b >> c;
        sprintf(buffer, "plot [-10:10] %f*x**2 + %f*x + %f lw 3 lc rgb 'blue' title 'Parabola'", a, b, c);
        kirimKeGnuplot(gp, "Grafik Parabola", string(buffer));

    } else if (pilihan == 3) {
        cout << "\n>> Mode Trigonometri.\n";
        cout << "Masukkan Tinggi & Kerapatan (contoh: 3 1): ";
        cin >> a >> b;
        sprintf(buffer, "plot [-10:10] %f * sin(%f * x) lw 3 lc rgb 'green' title 'Sinus'", a, b);
        kirimKeGnuplot(gp, "Grafik Sinus", string(buffer));
    }
    _pclose(gp);
}
// BAGIAN 3D 
void plot3D() {
    int pilihan;
    char buffer[200];
    
    cout << "\n=== PILIH BENTUK 3D (KURVA MULUS) ===\n";
    cout << "1. Mangkok (Paraboloid) -> Melengkung ke atas\n";
    cout << "2. Pelana Kuda (Saddle) -> Melengkung berlawanan\n";
    cout << "3. Corong (Cone) -> Melengkung tajam ke bawah\n";
    cout << "Pilihanmu (1-3): ";
    if (!(cin >> pilihan)) {
        cin.clear(); cin.ignore(1000, '\n'); return;
    }

    FILE *gp = _popen("gnuplot -persistent", "w");
    if (!gp) return;

    fprintf(gp, "set view 60, 30\n"); 

    if (pilihan == 1) {
        double a; 
        cout << "\n>> Mode Mangkok terpilih.\n";
        cout << "Masukkan kelebaran mangkok (misal 0.1 lebar, 1 sempit): "; cin >> a;
        sprintf(buffer, "splot [-10:10] [-10:10] %f*(x**2 + y**2) with lines title 'Mangkok'", a);
        kirimKeGnuplot(gp, "Grafik 3D Paraboloid", string(buffer));
        
    } else if (pilihan == 2) {
        cout << "\n>> Mode Pelana Kuda terpilih.\n";
        cout << "Langsung tekan Enter untuk melihat bentuk pelana...";
        cin.ignore(); cin.get();
        
        sprintf(buffer, "splot [-10:10] [-10:10] (x**2 - y**2)/4 with lines title 'Pelana'");
        kirimKeGnuplot(gp, "Grafik 3D Saddle", string(buffer));

    } else if (pilihan == 3) {
        cout << "\n>> Mode Corong terpilih.\n";
        cout << "Langsung tekan Enter...";
        cin.ignore(); cin.get();

        sprintf(buffer, "splot [-10:10] [-10:10] -sqrt(x**2 + y**2) with lines title 'Corong'");
        kirimKeGnuplot(gp, "Grafik 3D Cone", string(buffer));
    }

    cout << ">> Grafik 3D dibuat. Gunakan MOUSE untuk memutar gambar!\n";
    _pclose(gp);
}
// MENU UTAMA
int main() {
    int menu;
    do {
        system("cls");
        cout << "========================================\n";
        cout << "   APLIKASI VISUALISASI MATEMATIKA      \n";
        cout << "========================================\n";
        cout << "1. Grafik 2D (Kurva S, Parabola, Sinus)\n";
        cout << "2. Grafik 3D (Mangkok, Pelana, Corong)\n";
        cout << "0. Keluar\n";
        cout << "========================================\n";
        cout << "Pilihan: ";
        if (!(cin >> menu)) {
            cin.clear(); cin.ignore(1000, '\n'); menu = -1;
        }

        if (menu == 1) plot2D();
        else if (menu == 2) plot3D();
        
        if (menu != 0) {
            cout << "\nTekan Enter untuk kembali ke menu...";
            cin.ignore(); cin.get();
        }
    } while (menu != 0);

    return 0;
}
