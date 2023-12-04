public class FEM {
    int a,b;    //interval [a,b]
    int n;      //number of sub-intervals
    double h;   //step size

    public FEM(int a , int b , int n){
        this.a =a;
        this.b = b;
        this.n = n;
        h =(double) (b-a)/n;
    }

    public double f(double x){
        return Math.exp(x);
    }
    public double FE_6Methods(String Method){ //All the 6 Methods in 1 function :)
        double F;  //F is the integral of f over [a,b]
        double Sum=0;
        double x[] = new double[n+1];
        x[0] = a;                         //x[i-1]=a
        for(int i=1 ; i<=n ; i++){
            x[i] =x[i-1] + h;
            if(Method.equals("Lowest")){
                Sum += f(x[i-1]);
            }
            else if(Method.equals("Highest")){
                Sum += f(x[i]);
            }
            else if(Method.equals("Minimum")){
                Sum += Math.min(f(x[i-1]) , f(x[i])) ;
            }
            else if(Method.equals("Maximum")){
                Sum += Math.max(f(x[i-1]) , f(x[i])) ;
            }
            else if(Method.equals("Midpoint")){
                Sum += f((x[i-1]+x[i])/2 );
            }
            else if(Method.equals("Trapezoidal")){
                Sum += ( f(x[i-1]) + f(x[i]) )/2;
            }
            else{
                System.out.println("You need to choose a valid Method name among the 6 methods:\n" +
                        "Lowest\nHighest\nMinimum\nMaximum\nMidpoint\nTrapezoidal");
                return -1;
            }

        }
        F = h*Sum;
        return F;
    }

    public double Error(double approx){
        double e;
        e = Math.abs(1.718281828 - approx);
        return e;
    }
}
