import tkinter as tk
from tkinter import messagebox
from tkinter import ttk
import psycopg2
from datetime import datetime

WARNA_UTAMA  = "#FF6600"  # Oranye Bata (Header)
WARNA_BG     = "#FDF2E9"  # Krem Muda (Background)
WARNA_TOMBOL = "#FF7700"  # Oranye Cerah
WARNA_TEKS   = "#FFFFFF"  # Putih
WARNA_EXIT   = "#FF1900"  # Merah

# --- KONFIGURASI DATABASE ---
DB_CONFIG = {
    "host": "localhost",
    "database": "perbankan",
    "user": "postgres",
    "password": "oplbois1928"  # PASSWORD PGadmin
}

def get_db_connection():
    try:
        conn = psycopg2.connect(**DB_CONFIG)
        return conn
    except Exception as e:
        messagebox.showerror("Error Koneksi", f"Gagal terhubung ke database:\n{e}")
        return None

# TAMPILAN MENU DATA NASABAH
def buka_data_nasabah():
    window = tk.Toplevel()
    window.title("Kelola Data Nasabah")
    window.geometry("1000x600")
    window.configure(bg=WARNA_BG)

    var_id = tk.StringVar()
    var_nama = tk.StringVar()
    var_alamat = tk.StringVar()
    var_hp = tk.StringVar()
    var_gender = tk.StringVar()

    def refresh_tabel():
        for item in tabel.get_children():
            tabel.delete(item)
        conn = get_db_connection()
        if conn:
            cur = conn.cursor()
            cur.execute("SELECT * FROM nasabah ORDER BY id_nasabah ASC")
            for row in cur.fetchall():
                tabel.insert("", "end", values=row)
            conn.close()

    def pilih_data(event):
        sel = tabel.selection()
        if sel:
            row = tabel.item(sel)['values']
            var_id.set(row[0])
            var_nama.set(row[1])
            var_alamat.set(row[2])
            var_hp.set(row[3])
            var_gender.set(row[4])

    def simpan():
        conn = get_db_connection()
        if conn:
            try:
                cur = conn.cursor()
                cur.execute("INSERT INTO nasabah (nama, alamat, no_hp, jenis_kelamin) VALUES (%s, %s, %s, %s)",
                            (var_nama.get(), var_alamat.get(), var_hp.get(), var_gender.get()))
                conn.commit()
                messagebox.showinfo("Sukses", "Nasabah berhasil disimpan!")
                refresh_tabel()
                conn.close()
            except Exception as e:
                messagebox.showerror("Error", str(e))

    def update():
        conn = get_db_connection()
        if conn:
            try:
                cur = conn.cursor()
                cur.execute("UPDATE nasabah SET nama=%s, alamat=%s, no_hp=%s, jenis_kelamin=%s WHERE id_nasabah=%s",
                            (var_nama.get(), var_alamat.get(), var_hp.get(), var_gender.get(), var_id.get()))
                conn.commit()
                messagebox.showinfo("Sukses", "Data berhasil diupdate!")
                refresh_tabel()
                conn.close()
            except Exception as e:
                messagebox.showerror("Error", str(e))

    def hapus():
        if messagebox.askyesno("Hapus", "Yakin hapus data ini?"):
            conn = get_db_connection()
            if conn:
                try:
                    cur = conn.cursor()
                    cur.execute("DELETE FROM nasabah WHERE id_nasabah=%s", (var_id.get(),))
                    conn.commit()
                    refresh_tabel()
                    conn.close()
                except Exception as e:
                    messagebox.showerror("Error", str(e))

    # UI Layout
    tk.Label(window, text="MENU DATA NASABAH", font=("Segoe UI", 16, "bold"), bg=WARNA_UTAMA, fg="white").pack(fill=tk.X, ipady=10)
    
    frame_kiri = tk.Frame(window, bg=WARNA_BG)
    frame_kiri.pack(side=tk.LEFT, fill=tk.Y, padx=10, pady=10)
    
    tk.Label(frame_kiri, text="Nama:", bg=WARNA_BG).pack(anchor="w")
    tk.Entry(frame_kiri, textvariable=var_nama).pack(fill=tk.X)
    tk.Label(frame_kiri, text="Alamat:", bg=WARNA_BG).pack(anchor="w")
    tk.Entry(frame_kiri, textvariable=var_alamat).pack(fill=tk.X)
    tk.Label(frame_kiri, text="No HP:", bg=WARNA_BG).pack(anchor="w")
    tk.Entry(frame_kiri, textvariable=var_hp).pack(fill=tk.X)
    tk.Label(frame_kiri, text="Gender (L/P):", bg=WARNA_BG).pack(anchor="w")
    ttk.Combobox(frame_kiri, textvariable=var_gender, values=["L", "P"]).pack(fill=tk.X)

    tk.Button(frame_kiri, text="SIMPAN", bg=WARNA_TOMBOL, fg="white", command=simpan).pack(fill=tk.X, pady=5)
    tk.Button(frame_kiri, text="UPDATE", bg="#2980B9", fg="white", command=update).pack(fill=tk.X, pady=5)
    tk.Button(frame_kiri, text="HAPUS", bg=WARNA_EXIT, fg="white", command=hapus).pack(fill=tk.X, pady=5)
    tk.Button(frame_kiri, text="KEMBALI KE MENU", bg="#7F8C8D", fg="white", command=window.destroy).pack(fill=tk.X, pady=20)

    tabel = ttk.Treeview(window, columns=("ID", "Nama", "Alamat", "HP", "Gen"), show="headings")
    tabel.heading("ID", text="ID"); tabel.column("ID", width=30)
    tabel.heading("Nama", text="Nama"); tabel.heading("Alamat", text="Alamat")
    tabel.heading("HP", text="No HP"); tabel.heading("Gen", text="L/P")
    tabel.pack(side=tk.RIGHT, fill=tk.BOTH, expand=True, padx=10, pady=10)
    tabel.bind("<ButtonRelease-1>", pilih_data)
    refresh_tabel()

# TAMPILAN MENU DATA REKENING
def buka_data_rekening():
    window = tk.Toplevel()
    window.title("Kelola Data Rekening")
    window.geometry("900x500")
    window.configure(bg=WARNA_BG)

    var_norek = tk.StringVar()
    var_idnas = tk.StringVar()
    var_saldo = tk.DoubleVar()
    var_jenis = tk.StringVar()

    def refresh_tabel():
        for item in tabel.get_children(): tabel.delete(item)
        conn = get_db_connection()
        if conn:
            cur = conn.cursor()
            query = """
                SELECT r.no_rekening, n.nama, r.saldo, r.jenis_rekening 
                FROM rekening r 
                JOIN nasabah n ON r.id_nasabah = n.id_nasabah
            """
            cur.execute(query)
            for row in cur.fetchall(): tabel.insert("", "end", values=row)
            conn.close()

    def simpan():
        conn = get_db_connection()
        if conn:
            try:
                cur = conn.cursor()
                cur.execute("INSERT INTO rekening (no_rekening, id_nasabah, saldo, jenis_rekening) VALUES (%s, %s, %s, %s)",
                            (var_norek.get(), var_idnas.get(), var_saldo.get(), var_jenis.get()))
                conn.commit()
                messagebox.showinfo("Sukses", "Rekening berhasil dibuat!")
                refresh_tabel()
                conn.close()
            except Exception as e:
                messagebox.showerror("Error", str(e))

    def hapus():
        if messagebox.askyesno("Hapus", "Yakin hapus rekening ini?"):
            conn = get_db_connection()
            if conn:
                try:
                    cur = conn.cursor()
                    cur.execute("DELETE FROM rekening WHERE no_rekening=%s", (var_norek.get(),))
                    conn.commit()
                    refresh_tabel()
                    conn.close()
                except Exception as e: messagebox.showerror("Error", str(e))

    tk.Label(window, text="MENU DATA REKENING", font=("Segoe UI", 16, "bold"), bg=WARNA_UTAMA, fg="white").pack(fill=tk.X, ipady=10)
    
    frame_kiri = tk.Frame(window, bg=WARNA_BG)
    frame_kiri.pack(side=tk.LEFT, fill=tk.Y, padx=10, pady=10)

    tk.Label(frame_kiri, text="No Rekening Baru:", bg=WARNA_BG).pack(anchor="w")
    tk.Entry(frame_kiri, textvariable=var_norek).pack(fill=tk.X)
    
    tk.Label(frame_kiri, text="ID Nasabah Pemilik:", bg=WARNA_BG).pack(anchor="w")
    tk.Entry(frame_kiri, textvariable=var_idnas).pack(fill=tk.X)
    tk.Label(frame_kiri, text="*Lihat ID di Menu Nasabah", font=("Arial", 8), fg="gray", bg=WARNA_BG).pack(anchor="w")

    tk.Label(frame_kiri, text="Saldo Awal (Rp):", bg=WARNA_BG).pack(anchor="w")
    tk.Entry(frame_kiri, textvariable=var_saldo).pack(fill=tk.X)

    tk.Label(frame_kiri, text="Jenis Rekening:", bg=WARNA_BG).pack(anchor="w")
    ttk.Combobox(frame_kiri, textvariable=var_jenis, values=["Gold", "Silver", "Platinum"]).pack(fill=tk.X)

    tk.Button(frame_kiri, text="BUAT REKENING", bg=WARNA_TOMBOL, fg="white", command=simpan).pack(fill=tk.X, pady=10)
    tk.Button(frame_kiri, text="HAPUS REKENING", bg=WARNA_EXIT, fg="white", command=hapus).pack(fill=tk.X, pady=5)
    tk.Button(frame_kiri, text="KEMBALI KE MENU", bg="#7F8C8D", fg="white", command=window.destroy).pack(fill=tk.X, pady=20)

    tabel = ttk.Treeview(window, columns=("NoRek", "Pemilik", "Saldo", "Jenis"), show="headings")
    tabel.heading("NoRek", text="No Rekening"); tabel.heading("Pemilik", text="Nama Nasabah")
    tabel.heading("Saldo", text="Saldo (Rp)"); tabel.heading("Jenis", text="Jenis")
    tabel.pack(side=tk.RIGHT, fill=tk.BOTH, expand=True, padx=10, pady=10)
    refresh_tabel()

# TAMPILAN MENU TRANSAKSI (SETOR/TARIK/TRANSFER)
def buka_transaksi():
    window = tk.Toplevel()
    window.title("Kasir / Transaksi")
    window.geometry("800x600")
    window.configure(bg=WARNA_BG)

    var_rek_sumber = tk.StringVar()
    var_rek_tujuan = tk.StringVar()
    var_nominal = tk.DoubleVar()
    var_jenis = tk.StringVar()

    def proses_transaksi():
        jenis = var_jenis.get()
        rek_asal = var_rek_sumber.get()
        rek_tujuan = var_rek_tujuan.get()
        nom = var_nominal.get()

        conn = get_db_connection()
        if not conn: return
        
        try:
            cur = conn.cursor()

            cur.execute("SELECT saldo FROM rekening WHERE no_rekening = %s", (rek_asal,))
            res = cur.fetchone()
            if not res:
                messagebox.showerror("Error", "Nomor Rekening Sumber tidak ditemukan!")
                return
            saldo_asal = float(res[0])

            if jenis == "setor":
                cur.execute("UPDATE rekening SET saldo = saldo + %s WHERE no_rekening = %s", (nom, rek_asal))
                cur.execute("INSERT INTO transaksi (no_rekening, nominal, jenis_transaksi) VALUES (%s, %s, 'setor')", (rek_asal, nom))
            
            elif jenis == "tarik":
                if saldo_asal < nom:
                    messagebox.showerror("Gagal", "Saldo tidak cukup!")
                    return
                cur.execute("UPDATE rekening SET saldo = saldo - %s WHERE no_rekening = %s", (nom, rek_asal))
                cur.execute("INSERT INTO transaksi (no_rekening, nominal, jenis_transaksi) VALUES (%s, %s, 'tarik')", (rek_asal, nom))
            
            elif jenis == "transfer":
                if saldo_asal < nom:
                    messagebox.showerror("Gagal", "Saldo tidak cukup untuk transfer!")
                    return
                cur.execute("UPDATE rekening SET saldo = saldo - %s WHERE no_rekening = %s", (nom, rek_asal))
                cur.execute("UPDATE rekening SET saldo = saldo + %s WHERE no_rekening = %s", (nom, rek_tujuan))
                cur.execute("INSERT INTO transaksi (no_rekening, nominal, jenis_transaksi) VALUES (%s, %s, 'transfer')", (rek_asal, nom))
            
            conn.commit()
            messagebox.showinfo("Sukses", f"Transaksi {jenis} berhasil!")
            conn.close()
            
        except Exception as e:
            conn.rollback()
            messagebox.showerror("Error", f"Transaksi Gagal: {e}")

    tk.Label(window, text="TELLER / TRANSAKSI", font=("Segoe UI", 16, "bold"), bg=WARNA_UTAMA, fg="white").pack(fill=tk.X, ipady=10)
    
    frame = tk.Frame(window, bg="white", bd=1, relief="solid")
    frame.pack(pady=30, padx=50, fill=tk.BOTH, expand=True)

    tk.Label(frame, text="Jenis Transaksi:", bg="white").pack(anchor="w", padx=20, pady=5)
    ttk.Combobox(frame, textvariable=var_jenis, values=["setor", "tarik", "transfer"]).pack(fill=tk.X, padx=20)

    tk.Label(frame, text="No Rekening (Sumber):", bg="white").pack(anchor="w", padx=20, pady=5)
    tk.Entry(frame, textvariable=var_rek_sumber, font=("Arial", 14)).pack(fill=tk.X, padx=20)

    tk.Label(frame, text="Nominal (Rp):", bg="white").pack(anchor="w", padx=20, pady=5)
    tk.Entry(frame, textvariable=var_nominal, font=("Arial", 14)).pack(fill=tk.X, padx=20)

    tk.Label(frame, text="No Rekening Tujuan (Khusus Transfer):", bg="white").pack(anchor="w", padx=20, pady=5)
    tk.Entry(frame, textvariable=var_rek_tujuan, bg="#EEE").pack(fill=tk.X, padx=20)

    tk.Button(frame, text="PROSES TRANSAKSI", bg=WARNA_TOMBOL, fg="white", font=("Arial", 12, "bold"), command=proses_transaksi).pack(fill=tk.X, padx=20, pady=20)
    
    tk.Button(window, text="KEMBALI KE MENU", bg="#7F8C8D", fg="white", command=window.destroy).pack(fill=tk.X, padx=50, pady=10)

# TAMPILAN MENU LAPORAN (VIEW ONLY)
def buka_laporan():
    window = tk.Toplevel()
    window.title("Laporan & Monitoring")
    window.geometry("1000x600")
    window.configure(bg=WARNA_BG)

    def load_data(jenis):
        for item in tabel.get_children(): tabel.delete(item)

        tabel["columns"] = ()
        
        conn = get_db_connection()
        cur = conn.cursor()
        
        if jenis == "nasabah":
            tabel["columns"] = ("ID", "Nama", "Alamat", "NoHP", "Gender")
            for col in tabel["columns"]: tabel.heading(col, text=col)
            cur.execute("SELECT * FROM nasabah")
            
        elif jenis == "saldo":
            tabel["columns"] = ("NoRek", "Pemilik", "Saldo", "Jenis")
            for col in tabel["columns"]: tabel.heading(col, text=col)
            cur.execute("SELECT r.no_rekening, n.nama, r.saldo, r.jenis_rekening FROM rekening r JOIN nasabah n ON r.id_nasabah = n.id_nasabah")
            
        elif jenis == "transaksi":
            tabel["columns"] = ("ID", "NoRek", "Waktu", "Nominal", "Jenis")
            for col in tabel["columns"]: tabel.heading(col, text=col)
            cur.execute("SELECT * FROM transaksi ORDER BY tanggal DESC")

        for row in cur.fetchall():
            tabel.insert("", "end", values=row)
        conn.close()

    tk.Label(window, text="PUSAT LAPORAN", font=("Segoe UI", 16, "bold"), bg=WARNA_UTAMA, fg="white").pack(fill=tk.X, ipady=10)
    
    frame_tombol = tk.Frame(window, bg=WARNA_BG)
    frame_tombol.pack(pady=10)

    tk.Button(frame_tombol, text="LAPORAN DATA NASABAH", bg="#2980B9", fg="white", command=lambda: load_data("nasabah")).pack(side=tk.LEFT, padx=5)
    tk.Button(frame_tombol, text="LAPORAN SALDO REKENING", bg="#8E44AD", fg="white", command=lambda: load_data("saldo")).pack(side=tk.LEFT, padx=5)
    tk.Button(frame_tombol, text="RIWAYAT TRANSAKSI", bg="#27AE60", fg="white", command=lambda: load_data("transaksi")).pack(side=tk.LEFT, padx=5)

    tabel = ttk.Treeview(window, show="headings")
    tabel.pack(fill=tk.BOTH, expand=True, padx=20, pady=10)

    tk.Button(window, text="KEMBALI KE MENU UTAMA", bg=WARNA_EXIT, fg="white", command=window.destroy).pack(fill=tk.X, padx=20, pady=10)

# DASHBOARD UTAMA
def buka_dashboard():
    dashboard = tk.Tk()
    dashboard.title("Dashboard - Bank Hasbul Unggul")
    dashboard.geometry("900x600")
    dashboard.configure(bg=WARNA_BG)

    tk.Label(dashboard, text="SISTEM INFORMASI BANK HASBUL UNGGUL", font=("Segoe UI", 22, "bold"), bg=WARNA_UTAMA, fg="white").pack(fill=tk.X, ipady=20)
    
    frame_menu = tk.Frame(dashboard, bg="white", bd=2, relief="ridge")
    frame_menu.place(relx=0.5, rely=0.5, anchor=tk.CENTER, width=600, height=350)

    tk.Label(frame_menu, text="MENU UTAMA", font=("Segoe UI", 14, "bold"), bg="white", fg=WARNA_UTAMA).pack(pady=20)

    btn_frame = tk.Frame(frame_menu, bg="white")
    btn_frame.pack()

    def tombol(txt, cmd):
        return tk.Button(btn_frame, text=txt, font=("Segoe UI", 11, "bold"), bg=WARNA_TOMBOL, fg="white", width=20, height=2, command=cmd)

    tombol("DATA NASABAH", buka_data_nasabah).grid(row=0, column=0, padx=10, pady=10)
    tombol("DATA REKENING", buka_data_rekening).grid(row=0, column=1, padx=10, pady=10)
    tombol("TRANSAKSI", buka_transaksi).grid(row=1, column=0, padx=10, pady=10)
    tombol("LAPORAN", buka_laporan).grid(row=1, column=1, padx=10, pady=10)

    tk.Button(dashboard, text="LOGOUT", bg=WARNA_EXIT, fg="white", command=dashboard.destroy).pack(side=tk.BOTTOM, pady=30)
    dashboard.mainloop()

# TAMPILAN LOGIN
def login():
    user = entry_user.get()
    pwd = entry_pass.get()
    
    conn = get_db_connection()
    if conn:
        cur = conn.cursor()
        cur.execute("SELECT * FROM login WHERE username=%s AND password=%s", (user, pwd))
        if cur.fetchone():
            messagebox.showinfo("Sukses", "Login Berhasil!")
            root.destroy()
            buka_dashboard()
        else:
            messagebox.showerror("Gagal", "Username/Password Salah!")
        conn.close()

root = tk.Tk()
root.title("Login System")
root.geometry("400x500")
root.configure(bg=WARNA_BG)

tk.Label(root, text="BANK HASBUL\nUNGGUL", font=("Segoe UI", 20, "bold"), bg=WARNA_UTAMA, fg="white").pack(fill=tk.X, ipady=20)

frame_login = tk.Frame(root, bg="white", bd=0)
frame_login.pack(pady=40, padx=40, fill=tk.BOTH, expand=True)

tk.Label(frame_login, text="Username:", bg="white").pack(anchor="w", padx=20, pady=(20,5))
entry_user = tk.Entry(frame_login, font=("Arial", 12), bg="#FDEBD0"); entry_user.pack(fill=tk.X, padx=20)

tk.Label(frame_login, text="Password:", bg="white").pack(anchor="w", padx=20, pady=(10,5))
entry_pass = tk.Entry(frame_login, show="•", font=("Arial", 12), bg="#FDEBD0"); entry_pass.pack(fill=tk.X, padx=20)

tk.Button(frame_login, text="MASUK", bg=WARNA_TOMBOL, fg="white", font=("Arial", 12, "bold"), command=login).pack(fill=tk.X, padx=20, pady=30)

root.mainloop()