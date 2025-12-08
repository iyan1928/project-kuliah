#include <iostream>
#include <vector>
#include <cmath>
#include <string>
#include "matplotlibcpp.h"

namespace plt = matplotlibcpp;

using namespace std;

int main() {
    double x_min, x_max, step;
    
    cout << "=== PROGRAM GRAFIK FUNGSI 2D ===" << endl;
    cout << "Fungsi yang akan di-plot: 2*x*x - 3" << endl;
    cout << "Masukkan batas bawah x: ";
    cin >> x_min;
    cout << "Masukkan batas atas x: ";
    cin >> x_max;
    cout << "Masukkan kenaikan (step): ";
    cin >> step;

    vector<double> x, y;
    vector<double> y_base;

    for (double i = x_min; i <= x_max; i += step) {
        double fx = 2*i*i - 3;

        x.push_back(i);
        y.push_back(fx);
        y_base.push_back(0.0);
    }

    plt::figure_size(800, 600)
    plt::plot(x, y, {{"color", "green"}});
    plt::fill_between(x, y_base, y, {{"color", "skyblue"}, {"alpha", "0.4"}});
    plt::grid(true);
    cout << "Menyimpan grafik ke file 'grafik.png'..." << endl;
    plt::savefig("grafik.png");
    cout << "Grafik berhasil disimpan." << endl;

    return 0;
}