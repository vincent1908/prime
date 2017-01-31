/*
 * Creation : 30 déc. 2016
 */
package extra;

import demo.DoPr;
import demo.Filiale;

public class CompResults extends DoPr {
    public static void main(String[] args) throws Exception {
        Filiale f = new Filiale();
        DoPr d = new DoPr();
        // int[] even = { 2, 4, 6 };
        // int[] meEvenToo = { 2, 4, 6 };
        // int[] odd = { 3, 5, 7 };
        // boolean result = Arrays.equals(even, meEvenToo);
        // System.out.printf("Comparing two int arrays %s and %s, are they Equal? %s %n ", Arrays.toString(even), Arrays.toString(meEvenToo), result);
        //
        // result = Arrays.equals(even, odd);
        // System.out.printf("Comparing even and odd int arrays %s and %s, are they Equal? %s %n", Arrays.toString(even), Arrays.toString(odd),
        // result);

        boolean result = f.equals(d);
        System.out.println(result);
    }

}
