import java.util.*;
import java.io.File;
import java.io.IOException;

public class Main {

    public static ArrayList<Point> dataset = new ArrayList<Point>();
    public static ArrayList<Point> dataset1 = new ArrayList<Point>();
    public static ArrayList<Point> dataset2 = new ArrayList<Point>();

    public static ArrayList<Point> ReadFile(String filePath){   //Takes file.txt path, Returns ArrayList of Points having X and Y
        File file = new File(filePath);
        ArrayList<Point> Points = new ArrayList<Point>();
        try{
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                if(line.equals("x\ty")){    //To ignore the first Non-Numeric line in the dataset
                    continue;
                }
                String[] XY = line.split("\t");  //Now, XY[0] contains the values of x and XY[1] contains the values of y
                Point p = new Point(Double.parseDouble(XY[0]) , Double.parseDouble(XY[1]));
                Points.add(p);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return Points;
    }

    public static void printDataset(ArrayList<Point> P){    //For Testing, to observe our data
        for(int i=0 ; i<P.size() ; i++)
            System.out.println(P.get(i));
    }
    public static ArrayList<Double> getXValues(ArrayList<Point> P){  //Takes list of Points, returns corresponding list of X's
        ArrayList<Double> X = new ArrayList<Double>();
        for(int i=0 ; i<P.size() ; i++){
            X.add(P.get(i).getX());
        }
        return X;
    }
    public static ArrayList<Double> getYValues(ArrayList<Point> P){  //Takes list of Points, returns corresponding list of Y's
        ArrayList<Double> Y = new ArrayList<Double>();
        for(int i=0 ; i<P.size() ; i++){
            Y.add(P.get(i).getY());
        }
        return Y;
    }
    public static ArrayList<Point> merge(ArrayList<Double> X , ArrayList<Double> Y){ //Takes X and Y, returns list of Points (X,Y)
        ArrayList<Point> P = new ArrayList<Point>();

        if(X.size() != Y.size())    //incase the entered sizes of X and Y doesn't match
            return null;

        for(int i=0 ; i<X.size() ; i++){
            P.add(new Point(X.get(i) , Y.get(i)));
        }
        return P;
    }

    public static double Mean(ArrayList<Double> A){   //Calculates the mean of any list of data
        double mean=0;
        for(int i=0 ; i<A.size() ; i++){
            mean +=A.get(i);
        }
        mean = mean/A.size();
        return mean;
    }

    public static double StandardDeviation(ArrayList<Double> A) {   //Calculates the StandardDeviation of any list of data
        double sd=0;
        for(int i=0 ; i<A.size() ; i++){
            sd += Math.pow( A.get(i) - Mean(A) ,2)/A.size();  //Variance
        }
        sd = Math.sqrt(sd); //StandardDeviation
        return sd;
    }
    public static double Correlation(ArrayList<Double> X , ArrayList<Double> Y){   //Correlation between X and Y
        double cor=0;

        if(X.size() != Y.size())    //incase the entered sizes of X and Y doesn't match
            return -2;              //-2 is a value out of the domain [-1,1] of the correlation

        for(int i=0 ; i<X.size() ; i++){
            cor += ( (X.get(i)-Mean(X))*(Y.get(i)-Mean(Y)) )/X.size();   //Covariance
        }
        cor = cor/(StandardDeviation(X)*StandardDeviation(Y));  //Correlation
        return cor;
    }

    //LINEAR REGRESSION PART:
    public static double[] LinearRegression(ArrayList<Point> P){ //Returns a0 and a1
        double a[] = new double[2];
        ArrayList<Double> X = getXValues(P);
        ArrayList<Double> Y = getYValues(P);
        double r1=0,r2=0,r3=0,r4=0; //used to calculate a1
        r2 = (Mean(X)*X.size())*(Mean(Y)*Y.size());     //(Sum of Xi) * (Sum of Yi)
        r4 = Math.pow(Mean(X)*X.size(),2);             //(Sum of Xi)^2
        for(int i=0 ; i<P.size() ; i++){
            r1 =r1 + X.size()*X.get(i)*Y.get(i);       //n*(Sum of Xi)*(Sum Yi's)
            r3 = r3 + X.size()*Math.pow(X.get(i),2);   //n*(Sum of Xi^2)
        }
        a[1] = (r1 - r2)/(r3 - r4);
        a[0] = Mean(Y) - a[1]*Mean(X);
        return a;
    }

    public static double LinearPrediction(double x , ArrayList<Point> P){   //The model is given by P, to predict yhat of x
        double yhat;
        double a[] = LinearRegression(P);   //Returns a0 and a1 of our model
        yhat = a[0] + a[1]*x;
        return yhat;
    }

    public static ArrayList<Double> LinearApproxSet(ArrayList<Double> X , ArrayList<Point> P){//Model using P, to predict Yhat of X
        ArrayList<Double> Yhat = new ArrayList<Double>();
        double a[] = LinearRegression(P);
        for(int i=0 ; i<X.size() ; i++){
            Yhat.add(a[0]+a[1]*X.get(i));
        }
        return Yhat;
    }

    public static double NRMSE(ArrayList<Double> Y , ArrayList<Double> Yhat){
        double nrmse=0;
        for(int i=0 ; i<Y.size() ; i++) {
            nrmse += Math.pow(Y.get(i)-Yhat.get(i),2);  //SSE
        }
        nrmse = Math.sqrt(nrmse/Y.size())/Mean(Y);  //NRMSE
        return nrmse;
    }

    //KNN PART:
    public static ArrayList<Double> CalculateDistance(double x , ArrayList<Double> X){ //Euclidean distances between x and each Xi
        ArrayList<Double> distances = new ArrayList<Double>();
        for(int i=0 ; i<X.size() ; i++){
            distances.add(Math.sqrt(Math.pow(x-X.get(i),2)));
        }
        return distances;
    }

    public static double KNNPrediction(double x , ArrayList<Point> P) { //Model using P, to predict yhat of x
        double yhat = 0;
        int k = (int) Math.ceil(Math.sqrt(P.size())); //k = (Rounded up value of radical n)
        ArrayList<Double> distances = CalculateDistance(x, getXValues(P));
        ArrayList<Double> Y = getYValues(P);
        HashMap<Double, Double> DY = new HashMap<Double, Double>(); //Using hashmap we can sort the distances and still maintain the values of Yi's beside the correct distance
        for(int i=0 ; i<P.size() ; i++){
            DY.put(distances.get(i) , Y.get(i)); //Key: Distances , Values: Yi's
        }
        ArrayList<Double> SortedKeys = new ArrayList<Double>(DY.keySet()); //used to sort the Hashmap
        Collections.sort(SortedKeys);   //Now keys are sorted in increasing order

        for(int i=0 ; i<k ; i++){   //Taking the k nearest neighbors
            yhat = yhat +  DY.get(SortedKeys.get(i)); //Calculating the sum (y1 + ... + yk)
        }
        yhat = yhat / k;
        return yhat;
    }

    public static ArrayList<Double> KNNApproxSet(ArrayList<Double> X , ArrayList<Point> P){//Model using P, to predict Yhat of X
        ArrayList<Double> Yhat = new ArrayList<Double>();
        for(int i=0 ; i<X.size() ; i++){
            Yhat.add(KNNPrediction(X.get(i) , P));
        }
        return Yhat;
    }

    public static void NRMSEComparison(double NRMSE_LR , double NRMSE_KNN){

        System.out.print("NRMSE_Comparison: ");
        if(NRMSE_LR > NRMSE_KNN)
            System.out.println("[NRMSE(LR) = " + NRMSE_LR +"] > [NRMSE(KNN) = " + NRMSE_KNN + " ] " +
                    ", then LR is more accurate for the given dataset than KNN.\n");
        else if(NRMSE_LR < NRMSE_KNN)
            System.out.println("[NRMSE(LR) = " + NRMSE_LR +"] < [NRMSE(KNN) = " + NRMSE_KNN + " ] " +
                    ", then KNN is more accurate for the given dataset than LR.\n");
        else
            System.out.println("Both LR and KNN have the same accuracy\n");
    }


    //This function does both jobs of CrossValidationLR and CrossValidationKNN by changing the value of String Algorithm:
    public static double CrossValidation(ArrayList<Point> P , int k , String Algorithm){
        if( !(Algorithm.equals("LR")||Algorithm.equals("KNN")) )
            return -1;  //if some invalid string was entered
        double CV=0;
        int n = P.size()  , n_by_k = n/k;
        ArrayList<Point> train = new ArrayList<Point>();
        ArrayList<Point> test = new ArrayList<Point>();
        ArrayList<Double> Yhat=null;
        Point E[][] = new Point[k][n_by_k]; //Consists of k row and n/k columns (Each row represents a subset Ei)

        int count=0;
        for(int i=0 ; i<k ; i++){
            for(int j=0 ; j<n_by_k ; j++){
                E[i][j] = P.get(count);     //Filling the k subsets E using the dataset P
                count++;
            }
        }
        for(int shuffle=0 ; shuffle<k ; shuffle++){ //Used to perform the Train/Test shuffle between the subsets (1 test subset at a time)
            for(int i=0 ; i<k ; i++){
                for(int j=0 ; j<n_by_k ; j++){
                    if(i == shuffle)                //Moving the single testing subset to the arraylist test
                        test.add(E[i][j]);
                    else                            //Moving the training subsets all together into arraylist train
                        train.add(E[i][j]);
                }
            }
            //in this part we choose to do LR or KNN training using the attribute "Algorithm":
            if(Algorithm.equals("LR")) {
                Yhat = LinearApproxSet(getXValues(test) , train);   //LR Model
            } else if(Algorithm.equals("KNN")){
                Yhat = KNNApproxSet(getXValues(test) , train);      //KNN Model
            }

            CV += NRMSE(getYValues(test) , Yhat);
            train.clear();
            test.clear();   //To reset training and testing sets for the next iteration
        }
        CV = CV/k;
        return CV;
    }

    public static void CVComparison(double CV_LR , double CV_KNN){
        System.out.print("CV_Comparison: ");
        if(CV_LR > CV_KNN)
            System.out.println("[CV(LR) = " + CV_LR +"] > [CV(KNN) = " + CV_KNN + " ] " +
                    ", then LR is more accurate for the given dataset than KNN.");
        else if(CV_LR < CV_KNN)
            System.out.println("[CV(LR) = " + CV_LR +"] < [CV(KNN) = " + CV_KNN + " ] " +
                    ", then KNN is more accurate for the given dataset than LR.");
        else
            System.out.println("Both LR and KNN have the same accuracy");
    }

    public static void main(String args[]){
        //Reading the dataset:
        String filePath = "Dataset.txt";
        dataset = ReadFile(filePath);   //dataset arraylist contains all the points (x,y)
        //printDataset(dataset);     //(Un-Comment this line to show the dataset)

        String filePath1 = "Dataset1.txt";
        dataset1 = ReadFile(filePath1);

        String filePath2 = "Dataset2.txt";
        dataset2 = ReadFile(filePath2);

        //dataset Split into X and Y:
        ArrayList<Double> X = getXValues(dataset);
        ArrayList<Double> Y = getYValues(dataset);

        //Mean:
        System.out.println("The Mean of X is: " + Mean(X)); //28.97903
        System.out.println("The Mean of Y is: " + Mean(Y)); //46.833332

        //StandardDeviation:
        System.out.println("The StandardDeviation of X is: " + StandardDeviation(X));   //31.569033
        System.out.println("The StandardDeviation of Y is: " + StandardDeviation(Y));   //17.119942

        //Correlation between X and Y:
        System.out.println("The Correlation between X and Y is: " + Correlation(X,Y) + "\n");  //-0.118334234  (Weak Correlation)

        //Linear Regression:
        double a[] = LinearRegression(dataset);
        System.out.println("The Value of a0 is: " + a[0] + "\nThe Value of a1 is: " + a[1]);

        //Linear Prediction:
        double x1=11.16;
        System.out.println("The predicted value of x="+x1+" by LR is: yhat=" + LinearPrediction(x1,dataset));//yhat=47.9768 ,(real y=68.8)

        //LinearApproxSet:
        ArrayList<Double> Yhat1 = LinearApproxSet(X,dataset);
        ArrayList<Point> predData1 = merge(X,Yhat1);                          //Merges X and Yhat to list of points (X,Yhat).
        System.out.println("The values of approximation (X,Yhat1) are: (Un-Comment the next line of code to show the results)");
        //printDataset(predData1);

        //LR NRMSE:
        System.out.println("The NRMSE of LR is: " + NRMSE(Y,Yhat1) + "\n");   //0.36298

        //KNNPrediction:
        double x2 = 11.16;
        System.out.println("The predicted value of x="+x2+" by KNN is: yhat=" + KNNPrediction(x2,dataset));

        //KNNApproxSet:
        ArrayList<Double> Yhat2 = KNNApproxSet(X,dataset);
        ArrayList<Point> predData2 = merge(X,Yhat2);                          //Merges X and Yhat2 to a list of points (X,Yhat).
        System.out.println("The values of approximation (X,Yhat2) are: (Un-Comment the next line of code to show the results)");
        //printDataset(predData2);

        //KNN NRMSE:
        System.out.println("The NRMSE of KNN is: " + NRMSE(Y,Yhat2) + "\n");   //0.34389

        //Comparison using NRMSE:
        NRMSEComparison(NRMSE(Y,Yhat1) , NRMSE(Y,Yhat2));

        //Cross Validation Part:
        //Comparison:
        System.out.print("Dataset1 ");
        CVComparison(CrossValidation(dataset1 , 10 , "LR") , CrossValidation(dataset1 , 10 , "KNN"));
        System.out.print("Dataset2 ");
        CVComparison(CrossValidation(dataset2 , 10 , "LR") , CrossValidation(dataset2 , 10 , "KNN"));

    }
}


