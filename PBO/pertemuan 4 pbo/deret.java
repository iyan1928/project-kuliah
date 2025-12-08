import java.util.Scanner;

public class deret {
    static int jumlahderet(int n) {
        if(n == 1){
            System.out.print("1");
            return 1;
        }else {
            System.out.print(n + "+");
            return n + jumlahderet(n - 1);
        }

    }
    public static void main(String[]args){
        Scanner in = new Scanner(System.in);
        System.out.print("masukan n:");
        int n =in.nextInt();
        System.out.print("Deret ");
        int hasil = jumlahderet(n);
        System.out.print("= " + hasil);
    }
}