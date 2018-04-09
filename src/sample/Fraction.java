package sample;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
//Kartoffel
public class Fraction {

    Random r = new Random();

    int nenner;
    int zaehler;

    public Fraction(){
        nenner = r.nextInt(99 - 1) + 1;
        zaehler = r.nextInt(99 - 1) + 1;

    }

    public double getValue(){
        return zaehler == 0 ? 0 : round(((double) nenner / (double) zaehler), 4);
    }

    @Override
    public String toString() {
        return " " + nenner + "\n --\n " + zaehler;
    }

    public void nullFrac(){
        nenner = 0;
        zaehler = 0;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
