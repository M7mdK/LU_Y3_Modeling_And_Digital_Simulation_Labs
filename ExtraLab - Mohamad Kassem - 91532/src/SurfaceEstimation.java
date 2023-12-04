import java.lang.*;
import java.io.FileWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
public class SurfaceEstimation {
    public static int N=4;
    public static double T[];
    // A function that determines f(x), T[] contains the polynomial coefficients
    public static double Determine(double x)
    {
        double s=0;
        for(int i=0;i<T.length;i++)
            s = s + T[i]*Math.pow(x,i);
        return s;
    }
    // Calculate the maximum value of f within [a,b]:
    public static double Max(double a,double b,double step) {
        double m=Determine(a);
        for(double i=a ; i<b ; i= i + step){
            if(m < Determine(i)){
                m=Determine(i);
            }
        }
        return m;
    }

    public static void main(String[] args) {
        int n=1000;
        double x,y;
        T= new double[]{0, 4, 0, -1};
        try {
            FileWriter writer = new FileWriter("Int.txt");
            FileWriter wit = new FileWriter("Ext.txt");
            for(int i=0;i<n;i++) {
                x=Math.random()+1;
                y=Math.random()*Max(1,2,0.001);
                if(y>Determine(x))
                {
                    wit.write(x+" ");
                    wit.write(y+"");
                    wit.write("\r\n");
                }
                else
                {
                    writer.write(x+" ");
                    writer.write(y+"");
                    writer.write("\r\n");
                }
            }
            writer.close();
            wit.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LineNumberReader reader = null;
        int N_inside=0;
        try {
            reader = new LineNumberReader(new FileReader(new File("Int.txt")));
            while ((reader.readLine()) != null);
            N_inside=reader.getLineNumber();
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //testing on 1000 random points:
        double A=Max(1,2,0.001);
        double I = (Double.valueOf(N_inside)/Double.valueOf(n))*A;
        System.out.println("By Taking n=1000 the surface is equal to: " + I);

        //While increasing n to 1000, the precision increases.
        //Each time we obtain a different value (depending on the randomness)
    }

}

