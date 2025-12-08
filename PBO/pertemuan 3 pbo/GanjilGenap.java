import java.util.Scanner;

public class Ganjilgenap {
    public static void main(String [] args){
        System.out.println("menentukan biangan Ganil Genap");
        
        int x;
        
        Scanner ctk = new Scanner(System.in);
        
        System.out.print("masukan bilangan = ");
        x = ctk.nextInt();
        
        if (x % 2 == 0) {
            System.out.println( x + " adalah Bilangan genap");
        }
        else {
          System.out.println( x +" adalah Bilangan ganjil");
        }
    }
}