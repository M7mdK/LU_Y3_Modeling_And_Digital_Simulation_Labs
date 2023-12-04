public class DerivationMethods {

    public static void Euler(float a, float x0, float b, int n) {// x(a)=x0 , Domain=[a,b] , number of steps=n
        float h = (b - a) / n;  //Step size
        float x[] = new float[n + 1];
        float t[] = new float[n + 1];
        t[0] = a;
        x[0] = x0;
        System.out.println("Euler:");
        for (int k = 0; k < n; k++) {
            float Xorder1 = t[k]*t[k] + x[k]*x[k]*x[k] - x[k];  //Xorder1 is x'  (1st derivative of x)
            t[k + 1] = t[k] + h;
            x[k + 1] = x[k] + h * Xorder1;
            System.out.println("(t" + (k+1) + " , x" + (k+1) + ") = [ " + t[k + 1] + "  ,  " + x[k + 1] + " ] ");
        }
    }
    public static void QuadraticTaylor(float a, float x0, float b, int n){
        float h = (b-a)/n;
        float x[] = new float[n + 1];
        float t[] = new float[n + 1];
        t[0] = a;
        x[0] = x0;
        System.out.println("Taylor:");
        for(int k=0 ; k<n ; k++){
            float Xorder1 = t[k]*t[k] + x[k]*x[k]*x[k] - x[k];
            float Xorder2 = 2*t[k] + 3*Xorder1*x[k]*x[k] - Xorder1;  //Xorder2 is x''  (2nd derivative of x)
            t[k + 1] = t[k] + h;
            x[k+1] = x[k] + h*Xorder1 + (h*h/2)*Xorder2;
            System.out.println("(t" + (k+1) + " , x" + (k+1) + ") = [ " + t[k + 1] + "  ,  " + x[k + 1] + " ] ");
        }

    }
    public static void EulerMidpoint(float a, float x0, float b, int n){
        float h = (b-a)/n;
        float x[] = new float[n + 1];
        float t[] = new float[n + 1];
        t[0] = a;
        x[0] = x0;
        System.out.println("Euler Midpoint:");
        for(int k=0 ; k<n ; k++) {

            float Xorder1 = t[k]*t[k] + x[k]*x[k]*x[k] - x[k];
            float t_half = t[k] + h/2;                          //t_half is t_k+1/2
            float x_half = x[k] + (h/2)*Xorder1;                //x_half is x_k+1/2

            float X_half_order1 = t_half * t_half + x_half * x_half * x_half - x_half;
            t[k + 1] = t[k] + h;
            x[k+1] = x[k] + h*X_half_order1;
            System.out.println("(t" + (k+1) + " , x" + (k+1) + ") = [ " + t[k + 1] + "  ,  " + x[k + 1] + " ] ");
        }
    }
    public static void FourthOrderRungeKutta(float a, float x0, float b, int n){
        float k0,k1,k2,k3;  //The 4 parameters
        float h = (b-a)/n;
        float x[] = new float[n + 1];
        float t[] = new float[n + 1];
        t[0] = a;
        x[0] = x0;
        System.out.println("Runge-Kutta:");
        for(int k=0 ; k<n ; k++){

            k0 = t[k]*t[k] + x[k]*x[k]*x[k] - x[k];
            k1 = (float) ( Math.pow(t[k]+h/2 , 2) + Math.pow(x[k]+(h/2)*k0 , 3) - (x[k]+(h/2)*k0) );
            k2 = (float) ( Math.pow(t[k]+h/2 , 2) + Math.pow(x[k]+(h/2)*k1 , 3) - (x[k]+(h/2)*k1) );
            k3 = (float) ( Math.pow(t[k]+h , 2) + Math.pow(x[k]+h*k2 , 3) - (x[k]+h*k2) );

            t[k+1] = t[k] + h;
            x[k+1] = x[k] + (h/6)*(k0 + 2*k1 + 2*k2 + k3);
            System.out.println("(t" + (k+1) + " , x" + (k+1) + ") = [ " + t[k + 1] + "  ,  " + x[k + 1] + " ] ");
        }
    }


    public static void main(String[] args) {

        float a=0, x0=1 ,b=1;   //We can get any x by changing the values of the parameters, not only x(1)

        Euler(a,x0,b,2);  //n=2, To compare with my answers (solved on sheets)
        //Euler(a,x0,b,10);
        //Euler(a,x0,b,100);
        //Euler(a,x0,b,1000);
        //Euler(a,x0,b,10000);
        //Final Output Using Euler: (t10000 , x10000) = [ 1  ,  1.9089824 ]

        QuadraticTaylor(a,x0,b,2);
        //QuadraticTaylor(a,x0,b,10);
        //QuadraticTaylor(a,x0,b,100);
        //QuadraticTaylor(a,x0,b,1000);
        //QuadraticTaylor(a,x0,b,10000);
        //Final Output Using Taylor: (t10000 , x10000) = [ 1  ,  1.9089824 ]


        EulerMidpoint(a,x0,b,2);
        //EulerMidpoint(a,x0,b,10);
        //EulerMidpoint(a,x0,b,100);
        //EulerMidpoint(a,x0,b,1000);
        //EulerMidpoint(a,x0,b,10000);
        //Final Output Using EulerMidpoint: (t10000 , x10000) = [ 1  ,  1.9095483 ]

        FourthOrderRungeKutta(a,x0,b,2);
        //FourthOrderRungeKutta(a,x0,b,10);
        //FourthOrderRungeKutta(a,x0,b,100);
        //FourthOrderRungeKutta(a,x0,b,1000);
        //FourthOrderRungeKutta(a,x0,b,10000);
        //Final Output Using RungeKutta: (t10000 , x10000) = [ 1  ,  1.909548 ]
    }
}
