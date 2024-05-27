package test;
public class MoneyRounding {

    public static void main(String[] args) {
        double result1 = 12600.0;
        double result2 = 12800.0;

        System.out.println("Original: " + result1 + " -> Rounded: " + roundToNearest500(result1));
        System.out.println("Original: " + result2 + " -> Rounded: " + roundToNearest500(result2));
    }

    public static double roundToNearest500(double value) {
        double lower = Math.floor(value / 500) * 500;
        double upper = Math.ceil(value / 500) * 500;
        
        if (value - lower < upper - value) {
            return lower;
        } else {
            return upper;
        }
    }
}
