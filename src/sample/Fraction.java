package sample;

/**
 * Fraction Klasse, die den nenner und den zähler eines Bruchs speichert
 * @version 1.0
 */


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class Fraction {

    private Random r = new Random();

    private int nenner;
    private int zaehler;

    Fraction(){

        //Erstellen von Fractions bis sie einen Wert zwischen 1 und 10 haben
        nenner = r.nextInt(99 - 1) + 1;
        zaehler = r.nextInt(99 - 1) + 1;

        while((double) nenner / (double) zaehler > 10 || (double) nenner / (double) zaehler < 1){
            nenner = r.nextInt(99 - 1) + 1;
            zaehler = r.nextInt(99 - 1) + 1;
        }

    }

    /**
     *
     * @return Wert des Bruchs
     */
    double getValue(){
        return zaehler == 0 ? 0 : round(((double) nenner / (double) zaehler), 4);
    }

    /**
     *
     * @return String der als übereinadnergeschriebener Bruch formatiert ist
     */
    @Override
    public String toString() {
        return " " + nenner + "\n --\n " + zaehler;
    }

    /**
     * Setzt nenner und zähler des Objekts gleich null
     */
    void nullFrac(){
        nenner = 0;
        zaehler = 0;
    }

    /**
     * Rundet den übergebenen Wert (value) auf soviele Stellen hinter dem Komma wie angegeben (places)
     * @param value zu rundender Wert
     * @param places Stellen hinter dem Komma
     * @return gerundeter wert
     */
    static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
