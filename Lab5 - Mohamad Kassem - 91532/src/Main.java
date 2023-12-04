public class Main {

    public static void main(String[] args) {

        //Testing the 6 FEM:
        FEM m = new FEM(0,1,100);
        double result;
        result = m.FE_6Methods("Lowest");
        System.out.println("Result Using Lowest: " + result + "\t\t And Error = " + m.Error(result));
        result = m.FE_6Methods("Highest");
        System.out.println("Result Using Highest: " + result + "\t\t And Error = " + m.Error(result));
        result = m.FE_6Methods("Minimum");
        System.out.println("Result Using Minimum: " + result + "\t\t And Error = " + m.Error(result));
        result = m.FE_6Methods("Maximum");
        System.out.println("Result Using Maximum: " + result + "\t\t And Error = " + m.Error(result));
        result = m.FE_6Methods("Midpoint");
        System.out.println("Result Using Midpoint: " + result + "\t\t And Error = " + m.Error(result));
        result = m.FE_6Methods("Trapezoidal");
        System.out.println("Result Using Trapezoidal: " + result + "\t\t And Error = " + m.Error(result));

        //For Midpoint it needs only 10 sub-intervals for the error to go below 0.001
        //For Trapezoidal it needs 12 sub-intervals for the error to go below 0.001
        //While the others needed 900 sub-intervals!!! for the error to go below 0.001

        MonteCarlo M = new MonteCarlo(0,1,1000);
        result = M.MonteCarlo();
        System.out.println("Result Using MonteCarlo (Basic): " + result + "\t\t And Error = " + M.Error(result));
        result = M.EnhancedMC();
        System.out.println("Result Using MonteCarlo (Enhanced): " + result + "\t\t And Error = " + M.Error(result));

        //Enhanced MonteCarlo needed n less than 10000 for the error to go below 0.01
        //While Basic MonteCarlo needs more than 100 000 to converge.
    }
}