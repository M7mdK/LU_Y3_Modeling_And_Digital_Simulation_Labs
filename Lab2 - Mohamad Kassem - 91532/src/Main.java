import org.ejml.simple.SimpleMatrix;
import org.ejml.simple.*;
public class Main {

    public static void main(String args[]) {
        double [] ys= {8.2,8.4,8.5,8.6,8.8,8.7}, yHats = {8.2633,8.3713,8.4793,8.5873,8.6953,8.803};
        System.out.println(Adjacent(ys,yHats,6,6));
        SimpleMatrix X = new SimpleMatrix(4,3,true,new double[] {1,1,2,1,2,3,1,3,2,1,4,3});
        SimpleMatrix Y = new SimpleMatrix(4,1,true,new double[] {5,8,9,12});
        SimpleMatrix B = Beta(X,Y);
        System.out.println(B);
        System.out.println(Prediction(X,B));
    }

    public static double Adjacent ( double [] y,double [] yHats ,int n,int p ) {
        double meany = 0, sse = 0, tss = 0;

        for (int i = 0; i < n; i++) {
            meany += y[i];
        }
        meany /= n;

        for (int i = 0; i < n; i++){
            tss += Math.pow(y[i]-meany,2);
            sse += Math.pow(y[i] - yHats[i], 2);
        }

        return 1 - (sse/tss) * ((n-1)/(n-p-1));
    }

    public static SimpleMatrix Beta ( SimpleMatrix X,SimpleMatrix Y){
        SimpleMatrix B;
        SimpleMatrix Xt = X.transpose();
        SimpleMatrix m = (Xt.mult(X)).invert();
        B = (m.mult(Xt)).mult(Y);

        return B;
    }

    public static SimpleMatrix Prediction ( SimpleMatrix X, SimpleMatrix B){
        return X.mult(B);
    }

}
