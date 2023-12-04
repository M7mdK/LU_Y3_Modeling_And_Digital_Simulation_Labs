import java.util.concurrent.ThreadLocalRandom;

public class MonteCarlo {

    int a,b;    //interval [a,b]
    int n;      //number of sub-intervals
    double h;   //step size

    public MonteCarlo(int a , int b , int n){
        this.a =a;
        this.b = b;
        this.n = n;
        h =(double) (b-a)/n;
    }

    public double f(double x){
        return Math.exp(x);
    }

    public double MonteCarlo(){
        int i = 0;
        double I = 0, N_inside = 0, N_total = 0, A , x , y;
        x = ThreadLocalRandom.current().nextDouble(a, b);
        y = ThreadLocalRandom.current().nextDouble(a, Ymax());
        A = (b - a)*Ymax(); //Length*Width
        while(i < n){
            if(y <= f(x)){
                N_inside++;
            }
            N_total++;
            I += (N_inside/N_total)*A;
            i++;
            x = ThreadLocalRandom.current().nextDouble(a, b);
            y = ThreadLocalRandom.current().nextDouble(a, Ymax());
        }
        return I;
    }

    public double EnhancedMC(){
        int i = 0;
        double I = 0;
        double t = ThreadLocalRandom.current().nextDouble(a, b);
        while(i < n){
            I += f(t)*(h);
            i++;
            t = ThreadLocalRandom.current().nextDouble(a, b);
        }
        return I;
    }

    public double Ymax(){ //Returns Max y
        double max = 0, t = 0;
        for(double i = 0; i < n ; i++){
            if(f(t) > max)
                max = f(i);
            t += h;
        }
        return max;
    }

    public double Error(double approx){
        double e;
        e = Math.abs(1.718281828 - approx);
        return e;
    }
}
