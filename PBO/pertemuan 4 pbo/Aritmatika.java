import java.util.Scanner;

public class Aritmatika{
    public static int tambah(int A, int B){
        return A + B;
    }
    public static int kurang(int A, int B){
        return A - B;
    }
    public static int bagi(int A, int B){
        return A / B;
    }
    public static int kali(int A, int B){
        return A * B;
    }

    public static void main(String [] args){

            int menghitung, A, B;

            Scanner input = new Scanner(System.in);

            A = input.nextInt();
            B = input.nextInt();

            System.out.println("pilih penjumlahan aritmatik yang di inginkan");
            System.out.println("(1,2,3,4) 1.tambah / 2.kurang / 3.bagi / 4.kali");

            menghitung = input.nextInt();

            switch(menghitung){
                case 1:
                menghitung = tambah(A,B);
                System.out.println(menghitung);
                break;

                case 2:
                menghitung = kurang(A,B);
                System.out.println(menghitung);
                break;

                case 3:
                menghitung = bagi(A,B);
                System.out.println(menghitung);
                break;

                case 4:
                menghitung = kali(A,B);
                System.out.println(menghitung);
                break;

                default:
                System.out.println("piliahan salah");
            }
    }
}