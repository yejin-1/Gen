import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Main {
    // x + 50 (50 <= x <= 300)

    public static int fx(int x)
    {
        return x + 50;
    }

    public static int[] init()
    {
        Random r = new Random();
        int[] arr = new int[4];
        for(int i=0; i<4; i++)
        {
            arr[i] = r.nextInt(300+1+50)-50;
            System.out.printf("%d ", arr[i]);
        }
        System.out.println();

        return arr;
    }

    public static int[] selection(int[] x)
    {
        int sum = 0;
        int[] f = new int[x.length];
        for(int i=0; i<x.length; i++)
        {
            f[i] = fx(x[i]);
            sum += f[i];
        }

        double[] ratio = new double[x.length];
        for(int i=0; i<x.length; i++)
        {

            if(i==0) ratio[i] = (double)sum - (double)f[i];
            else ratio[i] = ratio[i-1] + (double)sum - (double)f[i];
        }

        int[] sx = new int[x.length];
        Random r = new Random();
        for(int i=0; i<x.length; i++)
        {
            double p = r.nextDouble();

            if(p < ratio[0]) sx[i] = x[0];
            else if(p < ratio[1]) sx[i] = x[1];
            else if(p < ratio[2]) sx[i] = x[2];
            else sx[i] = x[3];
        }

        return sx;
    }


    public static String int2String(String x) {
        return String.format("%8s", x).replace(' ', '0');
    }

    public static String[] crossOver(int[] x) {
        String[] arr = new String[x.length];
        for(int i=0; i<x.length; i+=2) {
            String bit1 = int2String(Integer.toBinaryString(x[i]));
            String bit2 = int2String(Integer.toBinaryString(x[i+1]));

            arr[i] = bit1.substring(0, 2) + bit2.substring(2, 8);
            arr[i+1] = bit2.substring(0, 2) + bit1.substring(2, 8);
        }

        return arr;
    }
    public static int invert(String x)
    {
        Random r = new Random();
        int a = Integer.parseInt(x, 2);
        for(int i=0; i<x.length(); i++) {
            double p = (double)1/ (double)10;
            if(r.nextDouble() < p)
            {
                a = 1 << i ^ a;
            }
        }
        return a;
    }

    public static int[] mutation(String[] x)
    {
        int[] arr = new int[x.length];
        for (int i=0; i<x.length; i++)
        {
            arr[i] = invert(x[i]);
        }
        return arr;
    }

    public static void main(String[] args)
    {
        int[] x = init();
        for(int i=0; i<10000; i++)
        {
            int[] sx = selection(x);
            String[] cx = crossOver(sx);
            int[] mx = mutation(cx);

            int[] f = new int[mx.length];
            int max = 0;
            for(int j = 0; j <mx.length; j++)
            {
                f[j] = fx(mx[j]);
                max = Math.max(max, f[j]);
                System.out.printf("%d ", f[j]);
            }
            System.out.println();

            System.out.println(max);
        }
    }
}