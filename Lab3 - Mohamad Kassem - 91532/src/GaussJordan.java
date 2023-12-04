public class GaussJordan {
    private double[][] X;

    public GaussJordan(double[][] X){
        this.X = X;
    }
    public void GJ_Elimination() {
        int firstCol = 0;
        for (int r=0; r<X.length; r++) {
            while (X[r][firstCol]==0.0){
                boolean switched = false;
                int i=r;
                while (!switched && i<X.length) {
                    if(X[i][firstCol]!=0.0){
                        double[] temp = X[i];
                        X[i]=X[r];
                        X[r]=temp;
                        switched = true;
                    }
                    i++;
                }
                if (X[r][firstCol]==0.0) {
                    firstCol++;
                }
            }
            if(X[r][firstCol]!=1.0) {
                double divisor = X[r][firstCol];
                for (int i=firstCol; i<X[r].length; i++) {
                    X[r][i] = X[r][i]/divisor;
                }
            }
            for (int i=0; i<X.length; i++) {
                if (i!=r && X[i][firstCol]!=0) {
                    double multiple = 0-X[i][firstCol];
                    for (int j=firstCol; j<X[r].length; j++){
                        X[i][j] += multiple*X[r][j];
                    }
                }
            }
            firstCol++;
        }
    }
    public String toString() {
        String s = "";
        for (int i=0; i<X.length; i++) {
            for (int j=0; j<X[i].length; j++) {
                if(j==(X[i].length-2)) {
                    s+="x" + (i+1) +" = "+X[i][j+1];
                    break;
                }
            }
            s+="\n";
        }
        return s;
    }
}
