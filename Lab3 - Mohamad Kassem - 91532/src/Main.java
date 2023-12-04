public class Main {
        public static void main(String[] args) {
            double[][] A = { {3,-0.1,-0.2,7.85} , {0.1,7,-0.3,-19.3}, {0.3,-0.2,10,71.4} };
            double[][] B = { {10,2,-1,27} , {-3,-6,2,-61.5} , {1,1,5,-21.5} };
            double y[]={0.5,8,-6};
            System.out.println("Gauss Jordan:");
            GaussJordan gj = new GaussJordan(B);
            gj.GJ_Elimination();
            System.out.println(gj);

            System.out.println("Gauss Seidel:");
            GaussSeidel gs=new GaussSeidel(A,y);
            gs.GS_Elimination();
            System.out.println("x1= " + gs.X[0] + "\nx2= "+gs.X[1] + "\nx3= "+gs.X[2]);
        }
}
