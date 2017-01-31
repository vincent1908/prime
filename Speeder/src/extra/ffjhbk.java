/*
 * Creation : 2 janv. 2017
 */
package extra;

public class ffjhbk {

    public static void main(String[] args) {
        String[] a = { "1,103,623", "917,899" };
        String[] b = { "2,3984,874", "45,64564" };

        String[] c = new String[a.length];
        for (int i = 0; i < a.length; ++i) {
            a[i].replaceAll(",", " ");
            b[i].replace(",", "");
            c[i] = a[i] + b[i];
            System.out.println(a[i]);
            System.out.println(c[i]);
        }

    }

}
