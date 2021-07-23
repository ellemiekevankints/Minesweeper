package cs1302.genlist;

import cs1302.genlistadt.GenList;
import java.util.function.Predicate;
import java.util.function.IntFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.Iterator;
import java.util.Comparator;

/**
 * A class to test the methods {@code map}, {@code reduce}, {@code filter},
 * {@code min}, and {@code allMatch} from the {@code GenList<T>} interface.
*/
public class LinkedGenListTest {

    /**
     * A method to demo the {@code map} method using a
     * parameterized {@code GenList<String>} list.
     */
    public static void demoMap1() {

        // creates a new String list
        GenList<String> list = new LinkedGenList<String>();
        list.add("cinderella");
        list.add("ariel");
        list.add("snow white");
        list.add("belle");
        list.add("jasmine");

        System.out.println("MAP DEMO 1:");
        System.out.println("~~~~~~~~~~~~~~~~~~~~");
        System.out.println("The original String list...");
        System.out.println(list.makeString(", "));
        System.out.println();

        Function<String, Integer> numVowels = (String f) -> {
            int count = 0;
            for (int i = 0; i < f.length(); i++) {
                // if the String contains a vowel
                if (f.charAt(i) == 'a' || f.charAt(i) == 'e' || f.charAt(i) == 'i'
                    || f.charAt(i) == 'o' || f.charAt(i) == 'u') {
                    count++;
                }
            }
            // returns the number of vowels in a String
            return count;
        };
        GenList<Integer> list2 = list.map(numVowels);

        System.out.println("The number of vowels in each String element...");
        System.out.println("(AFTER the demoMap method is applied to the list)");
        System.out.println(list2.makeString(", "));
        System.out.println();

    } // demoMap1

    /**
     * A second method to demo the {@code map} method using a
     * parameterized {@code GenList<Double>} list.
     */
    public static void demoMap2() {

        // creates a new Double list
        GenList<Double> list = new LinkedGenList<Double>();
        list.add(2.446);
        list.add(100.1);
        list.add(843.2395891);
        list.add(9999.0);
        list.add(0.2468);

        System.out.println("MAP DEMO 2:");
        System.out.println("~~~~~~~~~~~~~~~~~~~~");
        System.out.println("The original Double list...");
        System.out.println(list.makeString(", "));


        Function<Double, Integer> decimalDigits = (Double d) -> {
            // calculates the number of digits after the decimal in a non-negative Double
            String str = d.toString();
            int index = str.indexOf('.');
            String substr = str.substring(index + 1); // get the number after the decimal
            int len = substr.length(); // get the total elements (digits) in that number
            return len;
        };
        GenList<Integer> list2 = list.map(decimalDigits);

        System.out.println("The number of digits after the decimal for each Double element...");
        System.out.println("(AFTER the demoMap method is applied to the list)");
        System.out.println(list2.makeString(", "));
        System.out.println();

    } // demoMap2

    /**
     * A method to demo the {@code reduce} method using a
     * parameterized {@code GenList<Integer>} list.
     */
    public static void demoReduce1() {

        // creates a new Integer list
        GenList<Integer> list = new LinkedGenList<Integer>();
        list.add(1);
        list.add(32);
        list.add(15);
        list.add(28);
        list.add(7);

        System.out.println("REDUCE DEMO 1:");
        System.out.println("~~~~~~~~~~~~~~~~~~~~");
        System.out.println("The original Integer list...");
        System.out.println(list.makeString(", "));
        System.out.println();

        // the implementation of the apply method adds the list elements together
        BinaryOperator<Integer> bi = (o1, o2) -> o1 + o2;
        int r1 = list.reduce(-50, bi);

        System.out.println("The result of adding all the elements together, starting at -50:");
        System.out.println(r1);
        System.out.println();

    } // demoReduce1

    /**
     * A second method to demo the {@code reduce} method using a
     * parameterized {@code GenList<String>} list.
     */
    public static void demoReduce2() {

        // create a new String list
        GenList<String> list = new LinkedGenList<String>();
        list.add("to");
        list.add("get");
        list.add("her");

        System.out.println("REDUCE DEMO 2:");
        System.out.println("~~~~~~~~~~~~~~~~~~~~");
        System.out.println("The original String list...");
        System.out.println(list.makeString(", "));
        System.out.println();

        // the implementation of the apply method concatenates the list elements together
        BinaryOperator<String> bi = (o1, o2) -> o1.concat(o2);
        String str = list.reduce("", bi);

        System.out.println("The result of concatenating all the elements together:");
        System.out.println(str);
        System.out.println();

    } // demoReduce2

    /**
     * A method to demo the {@code filter} method using a
     * parameterized {@code GenList<Integer>} list.
     */
    public static void demoFilter1() {

        // create a new Integer list
        GenList<Integer> list = new LinkedGenList<Integer>();
        list.add(100);
        list.add(0);
        list.add(27);
        list.add(-12);
        list.add(105);
        list.add(49);

        System.out.println("FILTER DEMO 1:");
        System.out.println("~~~~~~~~~~~~~~~~~~~~");
        System.out.println("The original Integer list: ");
        System.out.println(list.makeString(", "));
        System.out.println();

        // checks if the element is between 0 and 100, inclusive
        Predicate<Integer> pred = p -> p >= 0 && p <= 100;
        GenList<Integer> list2 = list.filter(pred);

        System.out.print("The elements which are greater than or equal to 0 AND ");
        System.out.print("less than or equal to 100:" + "\n");
        System.out.println(list2.makeString(", "));
        System.out.println();

    } // demoFilter1

    /**
     * A second method to demo the {@code filter} method using a
     * parameterized {@code GenList<Double>} list.
     */
    public static void demoFilter2() {

        // creates a new Double list
        GenList<Double> list = new LinkedGenList<Double>();
        list.add(0.0);
        list.add(-0.01);
        list.add(-25.5);
        list.add(25.5);
        list.add(-1000.099);
        list.add(1000.099);

        System.out.println("FILTER DEMO 2:");
        System.out.println("~~~~~~~~~~~~~~~~~~~~");
        System.out.println("The original Double list...");
        System.out.println(list.makeString(", "));
        System.out.println();

        // check if the element is negative or greater than 1000.0
        Predicate<Double> pred = p -> p < 0.0 || p > 1000.0;
        GenList<Double> list2 = list.filter(pred);

        System.out.println("The elements which are less than 0.0 OR greater than 1000.0:");
        System.out.println(list2.makeString(", "));
        System.out.println();

    } // demoFilter2

    /**
     * A method to demo the {@code min} method using a
     * parameterized {@code GenList<String>} list.
     */
    public static void demoMin1() {

        // create a new String list
        GenList<String> list = new LinkedGenList<String>();
        list.add("Mercury");
        list.add("Venus");
        list.add("Earth");
        list.add("Mars");
        list.add("Jupiter");

        System.out.println("MIN DEMO 1:");
        System.out.println("~~~~~~~~~~~~~~~~~~~~");
        System.out.println("The original String list...");
        System.out.println(list.makeString(", "));
        System.out.println();

        Comparator<String> cmp = (o1, o2) -> {
            Integer num1 = new Integer(o1.length());
            Integer num2 = new Integer(o2.length());
            return num1.compareTo(num2);
        };
        String minimum = list.min(cmp);

        System.out.println("The element from this list with the shortest length: ");
        System.out.println(minimum);
        System.out.println();

    } // demoMin1

    /**
     * A second method to demo the {@code min} method using a
     * parameterized {@code GenList<Double>} list.
     */
    public static void demoMin2() {

        //creates a new Double list
        GenList<Double> list = new LinkedGenList<Double>();
        list.add(0.123456);
        list.add(100.11);
        list.add(9999.999);
        list.add(2.2);
        list.add(54.4444);

        System.out.println("MIN DEMO 2:");
        System.out.println("~~~~~~~~~~~~~~~~~~~~");
        System.out.println("The original Double list...");
        System.out.println(list.makeString(", "));
        System.out.println();

        Comparator<Double> cmp = (o1, o2) -> {
            // convert Double to String
            String str1 = o1.toString();
            String str2 = o2.toString();

            // find the index of the decimal point
            int index1 = str1.indexOf('.');
            int index2 = str2.indexOf('.');

            // store the decimal part of the number in a String
            String num1 = str1.substring(index1 + 1);
            String num2 = str2.substring(index2 + 1);

            // convert to int
            Integer int1 = new Integer(Integer.parseInt(num1));
            Integer int2 = new Integer(Integer.parseInt(num2));

            // compare the fractional part of non-negative numbers o1 and o2
            return int1.compareTo(int2);
        };
        double minimum = list.min(cmp);

        System.out.println("The element with the smallest fractional part: ");
        System.out.println(minimum);
        System.out.println();
    } // demoMin2

    /**
     * A method to demo the {@code allMatch} method using a
     * parameterized {@code GenList<String>} list.
     */
    public static void demoAllMatch1() {

        // create a new String list
        GenList<String> list = new LinkedGenList<String>();
        list.add("strawberry");
        list.add("mango");
        list.add("pineapple");
        list.add("raspberry");

        System.out.println("ALLMATCH DEMO 1:");
        System.out.println("~~~~~~~~~~~~~~~~~~~~");
        System.out.println("The original String list...");
        System.out.println(list.makeString(", "));
        System.out.println();

        // if the String contains either 'p' OR 'y'
        Predicate<String> pred = p -> p.contains("p") || p.contains("y");
        boolean b = list.allMatch(pred);

        System.out.println("Every element in this list contains either 'p' OR 'y':");
        System.out.println(b);
        System.out.println();
    } // demoAllMatch1

    /**
     * A second method to demo the {@code allMatch} method using a
     * parameterized {@code GenList<Integer>} list.
     */
    public static void demoAllMatch2() {

        // create a new Integer list
        GenList<Integer> list = new LinkedGenList<Integer>();
        list.add(10);
        list.add(1001);
        list.add(110);
        list.add(1110111);

        System.out.println("ALLMATCH DEMO 2:");
        System.out.println("~~~~~~~~~~~~~~~~~~~~");
        System.out.println("The original Integer list...");
        System.out.println(list.makeString(", "));
        System.out.println();

        Predicate<Integer> pred = p -> {
            String str = p.toString();
            if (str.contains("1") && str.contains("0")) {
                return true;
            }
            return false;
        };
        boolean b = list.allMatch(pred);

        System.out.println("Every element in this list contains both 0 AND 1:");
        System.out.println(b);
        System.out.println();
    } // demoAllMatch2

    public static void main(String[] args) {

        LinkedGenListTest.demoMap1();
        LinkedGenListTest.demoMap2();
        LinkedGenListTest.demoReduce1();
        LinkedGenListTest.demoReduce2();
        LinkedGenListTest.demoFilter1();
        LinkedGenListTest.demoFilter2();
        LinkedGenListTest.demoMin1();
        LinkedGenListTest.demoMin2();
        LinkedGenListTest.demoAllMatch1();
        LinkedGenListTest.demoAllMatch2();

    } // main

} // LinkedGenListTest
