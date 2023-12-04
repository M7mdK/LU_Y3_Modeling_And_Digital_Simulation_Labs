import java.lang.Math;
class GaussSeidel {
    double M[][] , X[] , Y[] , E=0;
    int n;
    public GaussSeidel(double M[][],double Y[]) {
        this.M=M;
        this.Y=Y;
        n=M.length;
        X =new double[n];
    }
    public void GS_Elimination() {
        double Q=0 , S=0;
        do {
            double s=0;
            for(int j=0;j<n;j++){
                s+=X[j]*X[j];
            }
            S=Math.sqrt(s);

            for(int i=0;i<n;i++) {
                X[i]=M[i][n];
                for(int j=0;j<n;j++) {
                    if(j!=i)
                        X[i]-=M[i][j]*X[j];
                }
                X[i]/=M[i][i];
            }
            s=0;
            for(int j=0;j<n;j++)
                s+=X[j]*X[j];
            Q=Math.sqrt(s);
            E=((Q-S)/Q)*100;
            E=Math.abs(E);
        }while(E>5);
    }
}
