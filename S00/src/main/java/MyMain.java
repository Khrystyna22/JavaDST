import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.*;
import java.text.*;

public class MyMain {
    public static void main(String[] args) throws ParseException {
        System.out.println("HELLo World!");
        //---------------------------------
        Scanner sc = new Scanner(System.in);
        System.out.print("Please, enter your name: ");
        String name = sc.nextLine();
        System.out.println("Hello, " + name + "!");
        //---------------------------------
        int fact = 1;
        for(int i = 1; i<=name.length(); i++){
            fact*=i;
        }
        System.out.println("Your name has " + name.length()+ " letters. " + name.length()+"! = " +fact);
        //---------------------------------
        Scanner sca = new Scanner(System.in);
        System.out.print("Please, enter your birth date in format (DD.MM.YYYY): ");
        String dt = sca.next();
        SimpleDateFormat sdf = new SimpleDateFormat("DD.MM.YYYY");
        Date now = sdf.parse(dt);
        long ms = System.currentTimeMillis()-now.getTime();
        long age = (long)((long)ms/(1000.0*60*60*24*365));
        long days = (long)((long)ms/(1000.0*60*60*24));
        LocalDate localDate = LocalDate.now();
        System.out.println("Today is " + DateTimeFormatter.ofPattern("yyy/MM/dd").format(localDate) + " you are " + age + " year ("+ days +" days) old.");
    }
}