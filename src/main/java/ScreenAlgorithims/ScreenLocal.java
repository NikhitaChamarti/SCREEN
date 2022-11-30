package ScreenAlgorithims;

import Pojo.TPoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScreenLocal {


    //Window Size
    private int WINDOW;
    //Defined minimum
    private double MINIMUM;
    //Defined Maximum
    private double MAXIMUM;
    private List<TPoint> dirtyValuesList;

    public ScreenLocal(int WINDOW, double MINIMUM, double MAXIMUM) {
        this.WINDOW = WINDOW;
        this.MINIMUM = MINIMUM;
        this.MAXIMUM = MAXIMUM;
        this.dirtyValuesList = new ArrayList<>();
    }

    public ScreenLocal(int WINDOW, List<TPoint> dirtyValuesList, double MINIMUM, double MAXIMUM) {
        this.WINDOW = WINDOW;
        this.dirtyValuesList = dirtyValuesList;
        this.MINIMUM = MINIMUM;
        this.MAXIMUM = MAXIMUM;
    }

    //Algorithmic Function
    public void runLocal() throws Exception {

        double localMin ,localMax;

        for(int i = 0 ; i < this.dirtyValuesList.size() ; i++){
            List<Double> doubles = new ArrayList<>();
            if(i != 0){
                localMin = this.dirtyValuesList.get(i-1).getRestriction() + this.MINIMUM * (this.dirtyValuesList.get(i).getTime() - this.dirtyValuesList.get(i -1).getTime());
                localMax = this.dirtyValuesList.get(i-1).getRestriction() + this.MAXIMUM * (this.dirtyValuesList.get(i).getTime() - this.dirtyValuesList.get(i -1).getTime());
            }else{
                localMin = this.dirtyValuesList.get(i).getValue();
                localMax = this.dirtyValuesList.get(i).getValue();
            }
            if(i < this.dirtyValuesList.size() + this.WINDOW -1 ){
                for(int j = i+1 ; j < this.dirtyValuesList.size(); j++){
                    if(this.dirtyValuesList.get(j).getTime() > this.dirtyValuesList.get(i).getTime() + this.WINDOW){
                        break;
                    }
                    doubles.add(this.dirtyValuesList.get(j).getValue() + this.MINIMUM * (this.dirtyValuesList.get(i).getTime() - this.dirtyValuesList.get(j).getTime()));
                    doubles.add(this.dirtyValuesList.get(j).getValue() + this.MAXIMUM * (this.dirtyValuesList.get(i).getTime() - this.dirtyValuesList.get(j).getTime()));
                }
                doubles.add(this.dirtyValuesList.get(i).getValue());
                double mid = getMidValue( doubles );
                findAndSetRestriction(localMin ,localMax,mid , i);
            }else{
                this.dirtyValuesList.get(i).setRestriction(this.dirtyValuesList.get(i).getValue());
            }
        }
    }

    public double getMidValue(List<Double> list) throws Exception {
        if(list == null || list.isEmpty())
            throw new Exception("Invalid Parameter Passed !");
        Collections.sort(list);
        return list.get((list.size() / 2));
    }

    public void findAndSetRestriction(double min ,double max ,double mid , int index){
        if(max < mid){
            this.dirtyValuesList.get(index).setRestriction(max);
        }else if( min > mid ){
            this.dirtyValuesList.get(index).setRestriction(min);
        }else{
            this.dirtyValuesList.get(index).setRestriction(mid);
        }
    }

    public int getWINDOW() {
        return WINDOW;
    }

    public void setWINDOW(int WINDOW) {
        this.WINDOW = WINDOW;
    }

    public List<TPoint> getDirtyValuesList() {
        return dirtyValuesList;
    }

    public void setDirtyValuesList(List<TPoint> dirtyValuesList) {
        this.dirtyValuesList = dirtyValuesList;
    }

    public double getMINIMUM() {
        return MINIMUM;
    }

    public void setMINIMUM(double MINIMUM) {
        this.MINIMUM = MINIMUM;
    }

    public double getMAXIMUM() {
        return MAXIMUM;
    }

    public void setMAXIMUM(double MAXIMUM) {
        this.MAXIMUM = MAXIMUM;
    }
}
