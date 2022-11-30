package Driver;

import Pojo.TPoint;
import ScreenAlgorithims.ScreenHeuristic;
import ScreenAlgorithims.ScreenLocal;
import ScreenAlgorithims.ScreenUpdate;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ScreenDriver {
    //File Path to Load DataSet
    public static final String filepath = "/Users/d/Downloads/DBMS_MAJOR_PROJECT 2/src/main/resources/stock10k.data";


    /**
     * Load Data Function : Index
     *
     * @param index (To Load Particular Column of the dataset )
     *
     *
     */
    public static List<TPoint> loadData(int index) throws Exception {

        List<TPoint> list = new ArrayList<>();
        File datafile = new File(filepath);
        InputStream is = new FileInputStream(datafile);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] dataPoints;
                dataPoints = line.split(Pattern.quote(","));
                TPoint dataPoint = new TPoint(Long.parseLong(dataPoints[0]), Double.parseDouble(dataPoints[index]));
                list.add(dataPoint);
            }
        } catch (IOException e) {

            e.printStackTrace();
        }

        return list;
    }

    /**
     * Load Data Function to load External DataSet : Index ,FilePath
     *
     * @param index (To Load Particular Column of the dataset )
     * @param eFilePath (External File Path)
     *
     */
    public static List<TPoint> loadData(int index ,String eFilePath) throws Exception {

        List<TPoint> list = new ArrayList<>();
        File datafile = new File(eFilePath);
        InputStream is = new FileInputStream(datafile);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] dataPoints;
                dataPoints = line.split(Pattern.quote(","));
                TPoint dataPoint = new TPoint(Long.parseLong(dataPoints[0]), Double.parseDouble(dataPoints[index]));
                list.add(dataPoint);
            }
        } catch (IOException e) {

            e.printStackTrace();
        }

        return list;
    }

    /**
     * Function to calculate RMS  : truth ,Dirty
     *
     * @param truth (Actual expected values in TimeSeries data  )
     * @param dirty (Modified values Due to constraints )
     * @return RMS ( calculated RMS value)
     */

    public static double calculateRMS(List<TPoint> truth ,List<TPoint> dirty) throws Exception {

        if(truth == null || dirty == null||truth.size() == 0 || dirty.size() == 0)
            throw new Exception("Invalid parameter Passed !!");

        double cost = 0;
        double delta;


        for (int index = 0; index < truth.size(); index++) {
            delta = dirty.get(index).getRestriction()
                    - truth.get(index).getValue();

            cost = cost + delta * delta;
        }
        cost = cost / truth.size();

        return Math.sqrt(cost);
    }

    /**
     * Driver Function to Run Algorithm 1 with other Data Sets (Local)
     *
     * Prints Time Taken by the Algorithm 1
     * prints RMS value before running the Algorithm 1
     * Prints RMS value after Running the Algorithm 1
     * Prints Accuracy For every 1000 Points
     */

    public static void runScreenLocalAlgorithmOtherDataSets(String eFilePath) throws Exception {
        if(eFilePath == null || eFilePath.isEmpty())
            throw new Exception("Invalid File Parameter Passed !!");
        double drms;
        double trms;
        List<TPoint> res;
        ScreenLocal sl;
        System.out.println("Starting Algorithm 1");
        List<TPoint> dirty = loadData(1,eFilePath);
        List<TPoint> truth = loadData(2,eFilePath);
        drms = calculateRMS(truth,dirty);
        sl = new ScreenLocal(1,dirty,-6,6);
        long start = System.currentTimeMillis();
        //Run Algorithm
        sl.runLocal();
        res = sl.getDirtyValuesList();
        System.out.println("Current Dirty RMS Value : " + drms);
        trms = calculateRMS(truth,res);
        System.out.println("Current Dirty RMS Value After Running Algorithm 1  : " + trms);
        System.out.println("Display Accuracy For Every 1000 Points");
        findAccuracyAndPrint(truth ,res);
        long end = System.currentTimeMillis();
        System.out.println("Total Time Taken By Algorithm 1 in milli seconds: " + (end - start));


    }



    /**
     * Driver Function to Run Algorithm 1 (Local)
     *
     * Prints Time Taken by the Algorithm 1
     * prints RMS value before running the Algorithm 1
     * Prints RMS value after Running the Algorithm 1
     * Prints Accuracy For every 1000 Points
     */

    public static void runScreenLocalAlgorithm() throws Exception {
        double drms;
        double trms;
        List<TPoint> res;
        ScreenLocal sl;
        System.out.println("Starting Algorithm 1");
        /* Loading data */
        List<TPoint> dirty = loadData(1);
        List<TPoint> truth = loadData(2);
        drms = calculateRMS(truth,dirty);
        sl = new ScreenLocal(1,dirty,-6,6);
        long start = System.currentTimeMillis();
        //Run Algorithm
        sl.runLocal();
        res = sl.getDirtyValuesList();
        System.out.println("Current Dirty RMS Value : " + drms);
        trms = calculateRMS(truth,res);
        System.out.println("Current Dirty RMS Value After Running Algorithm 1  : " + trms);
        System.out.println("Display Accuracy For Every 1000 Points");
        findAccuracyAndPrint(truth ,res);
        long end = System.currentTimeMillis();
        System.out.println("Total Time Taken By Algorithm 1 in milli seconds: " + (end - start));

    }
    /**
     * Function is print the accuracy
     */

    public static void findAccuracyAndPrint(List<TPoint> truth , List<TPoint> result){
        int incorrect = 0;
        for (int i = 0; i < 10000; i = i + 1000) {
            int tIndex = i + 1000;
            for (int j = i; j < i + 1000; j++) {
                if (truth.get(j).getValue() != result.get(j).getRestriction()) {
                    incorrect++;
                }
            }
            int per = tIndex - incorrect;
            double temp = per * 100;
            double accuracy = temp / tIndex;
            System.out.println("Accuracy :" + accuracy);
        }
    }

    /**
     * Driver Function to Run Algorithm 2 (Update)
     * Prints Time Taken by the Algorithm 2
     * Creates a Delayed Points in the dataset to demonstrate the functionality
     * Prints the corrected data point value
     */
    public static void runScreenUpdateAlgorithm() throws Exception {
        List<TPoint> res;
        ScreenUpdate su;
        System.out.println("Starting Algorithm 2");
        List<TPoint> dirty = loadData(1);
        List<TPoint> truth = loadData(2);
        TPoint p = dirty.get(6);
        System.out.println( "Delayed Point : "+p.getValue() + " " + p.getRestriction() + " " + p.getTime());
        truth.remove(6);
        su = new ScreenUpdate(1,-6,6,6,7,getTestList(truth,6,p));
        su.runUpdate();
        res = su.getRepairedList();
        for(int i = 0 ; i < 10 ; i++){
            System.out.println(res.get(i).getValue() + " " + res.get(i).getRestriction() + " " + res.get(i).getTime());
        }


    }

    /**
     * Sub Function of above function
     */
    public static List<TPoint> getTestList(List<TPoint> truth , int index , TPoint p){
        List<TPoint> result  = new ArrayList<>();
        for(int i = 0 ; i < 10 ; i ++){
            result.add(truth.get(i));
        }
        result.add(index,p);
        return result;
    }

    /**
     * Driver Function to Run Algorithm 3 (Heuristic)
     * Prints Time Taken by the Algorithm 3
     * Uses Algorithm 2 as a sub function
     */
    public static void runScreenHeuristicAlgorithm() throws Exception {
        List<TPoint> res;
        ScreenHeuristic su;
        System.out.println("Starting Algorithm 3");
        List<TPoint> dirty = loadData(1);
        List<TPoint> truth = loadData(2);
        TPoint p = dirty.get(6);
        System.out.println( "Delayed Point : "+p.getValue() + " " + p.getRestriction() + " " + p.getTime());
        truth.remove(6);
        su = new ScreenHeuristic(1,-6,6,6,7,getTestList(truth,6,p));
        su.runHeuristic();
        res = su.getRepairedList();
        for(int i = 0 ; i < 10 ; i++){
            System.out.println(res.get(i).getValue() + " " + res.get(i).getRestriction() + " " + res.get(i).getTime());
        }
    }

    /**
     * Driver Function to Run Algorithm 2 (Update) and Algorithm 1 together
     * Prints Time Taken by the Algorithm 2 and 1
     */
    public static void ScreenUpdateAndScreenLocal() throws Exception {
        double drms;
        System.out.println("Starting Algorithm 1 and Algorithm 2");
        List<TPoint> dirty = loadData(1);
        List<TPoint> truth = loadData(2);
        drms = calculateRMS(truth,dirty);
        System.out.println("Current Dirty RMS Value : " + drms);
        long start = System.currentTimeMillis();
        getTestSetWithUpdate(dirty,truth,120,40,12000);
        long end = System.currentTimeMillis();
        System.out.println("Total Time Taken By Algorithm 1 and 2 in milli seconds:  " + (end - start));
    }


    /**
     * Sub Function of above function
     */
    public static void getTestSetWithUpdate(List<TPoint> dirty ,List<TPoint> truth, int jumpIndex , int posIndex , int len) throws Exception {
        List<TPoint> res = new ArrayList<>();
        ScreenLocal sl;
        ScreenUpdate su;
        for (int i = 0; i < len; i = i + jumpIndex) {
            TPoint tp = new TPoint(dirty.get(i + posIndex).getTime(), dirty.get(i + posIndex).getValue());
            for (int j = i; j < i + jumpIndex; j++) {
                if (j == i + posIndex) {
                    continue;
                }
                res.add(dirty.get(j));
            }
            sl = new ScreenLocal(1, res, -6, 6);

            sl.runLocal();
            List<TPoint> localResult = sl.getDirtyValuesList();
            res.add(i + posIndex, tp);
            su = new ScreenUpdate(1, -6, 6, i + 40, i + 120, dirty);
            su.runUpdate();
            List<TPoint> updateResult = su.getRepairedList();
            if (tp.getTime() >= 11921) {
                double rms = calculateRMS(truth, updateResult);
                System.out.println("Update RMS: " + rms);
            }

        }

    }

    /**
     * Driver Function to Run Algorithm 3 (Heuristic) and Algorithm 1 together
     * Prints Time Taken by the Algorithm 3 and 1
     */
    public static  void ScreenLocalAndHeuristic() throws Exception {
        double drms;
        System.out.println("Starting Algorithm 1 and Algorithm 3");
        List<TPoint> dirty = loadData(1);
        List<TPoint> truth = loadData(2);
        drms = calculateRMS(truth,dirty);
        System.out.println("Current Dirty RMS Value : " + drms);
        long start = System.currentTimeMillis();
        getTestSetWithHeuristic(dirty,truth,120,40,12000);
        long end = System.currentTimeMillis();
        System.out.println("Total Time Taken By Algorithm 1 and 3 in milli seconds:  " + (end - start));
    }
    /**
     * Sub Function of above function
     */
    public static void getTestSetWithHeuristic(List<TPoint> dirty ,List<TPoint> truth, int jumpIndex , int posIndex , int len) throws Exception {
        List<TPoint> res = new ArrayList<>();
        ScreenLocal sl;
        ScreenHeuristic sh;
        for (int i = 0; i < len; i = i + jumpIndex) {
            TPoint tp = new TPoint(dirty.get(i + posIndex).getTime(), dirty.get(i + posIndex).getValue());
            for (int j = i; j < i + jumpIndex; j++) {
                if (j == i + posIndex) {
                    continue;
                }
                res.add(dirty.get(j));
            }
            sl = new ScreenLocal(1, res, -6, 6);

            sl.runLocal();
            List<TPoint> localResult = sl.getDirtyValuesList();
            res.add(i + posIndex, tp);
            sh = new ScreenHeuristic(1, -6, 6, i + 40, i + 120, dirty);
            sh.runHeuristic();
            List<TPoint> updateResult = sh.getRepairedList();
            if (tp.getTime() >= 11921) {
                double rms = calculateRMS(truth, updateResult);
                System.out.println("Update RMS: " + rms);
            }

        }

    }



    public static void main(String[] args) throws Exception {
        runScreenLocalAlgorithm();
        runScreenUpdateAlgorithm();
        runScreenHeuristicAlgorithm();
        ScreenUpdateAndScreenLocal();
        ScreenLocalAndHeuristic();
       // runScreenLocalAlgorithmOtherDataSets("/Users/d/Downloads/DBMS_MAJOR_PROJECT 2/src/main/resources/DatasetFuel-Final.txt");
    }
}

