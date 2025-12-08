import java.util.Scanner;

public class BilanganTerbesar {
    public static void main(String [] args){
        System.out.println("menentukan bilangan terbesar");
        
        int x,y;
        
        Scanner ctk = new Scanner(System.in);
        
        System.out.print("masukan bilangan pertama = ");
        x = ctk.nextInt();
        
        System.out.print("masukan bilangan kedua = ");
        y = ctk.nextInt();
        
        if (x > y) {
            System.out.println( x + " adalah Bilangan terbesar");
        }
        else {
          System.out.println( x + " adalah Bilangan terkecil");
        }
    }
}