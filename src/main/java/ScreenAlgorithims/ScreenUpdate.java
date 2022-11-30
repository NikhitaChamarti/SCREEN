package ScreenAlgorithims;

import Pojo.TPoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScreenUpdate {

    private int WINDOW;
    private double MINIMUM;
    private double MAXIMUM;
    private int startIndex;
    private int endIndex;
    private List<TPoint> repairedList;

    public ScreenUpdate(int WINDOW, double MINIMUM, double MAXIMUM, int startIndex, int endIndex) {
        this.WINDOW = WINDOW;
        this.MINIMUM = MINIMUM;
        this.MAXIMUM = MAXIMUM;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.repairedList = new ArrayList<>();
    }

    public ScreenUpdate(int WINDOW, double MINIMUM, double MAXIMUM, int startIndex, int endIndex, List<TPoint> repairedList) {
        this.WINDOW = WINDOW;
        this.MINIMUM = MINIMUM;
        this.MAXIMUM = MAXIMUM;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.repairedList = repairedList;
    }

    public void runUpdate() throws Exception {
        int index;
        for(index = startIndex -1 ; index >= 0 ; index--){
            List<Double> doubleList = new ArrayList<>();
            if(this.repairedList.get(index).getTime() < this.repairedList.get(startIndex).getTime() - this.WINDOW){
                break;
            }
            if(getBoundary(index,this.startIndex) < MINIMUM || getBoundary(index,this.startIndex) > MAXIMUM){
                doubleList.add(this.repairedList.get(startIndex).getValue() + MINIMUM * (this.repairedList.get(index).getTime() - this.repairedList.get(startIndex).getTime()));
                doubleList.add(this.repairedList.get(startIndex).getValue() + MAXIMUM * (this.repairedList.get(index).getTime() - this.repairedList.get(startIndex).getTime()));
                doubleList.add(this.repairedList.get(index).getValue());
                this.repairedList.get(index).setRestriction(getMidValue(doubleList));
            }

        }
        for( int j = index + 1 ; j <= this.endIndex ; j++ ){
            double min = this.repairedList.get(j - 1).getRestriction() + MINIMUM * (this.repairedList.get(j).getTime() - this.repairedList.get(j - 1).getTime());
            double max = this.repairedList.get(j - 1).getRestriction() + MAXIMUM * (repairedList.get(j).getTime() - repairedList.get(j - 1).getTime());
            updateConstraint(min,max,index);
        }
    }

    public double getBoundary(int currentIndex , int startIndex){
        return  (repairedList.get(currentIndex).getRestriction() - repairedList.get(startIndex).getValue()) / (repairedList.get(currentIndex).getTime() - repairedList.get(startIndex).getTime());
    }

    public double getMidValue(List<Double> list) throws Exception {
        if(list == null || list.isEmpty())
            throw new Exception("Invalid Parameter Passed !");
        Collections.sort(list);
        return list.get((list.size() / 2));
    }



    public void updateConstraint(double min ,double max , int currentIndex){
        if(min <= max){
            double mid = this.repairedList.get(currentIndex).getValue();
            if(max < mid){
                this.repairedList.get(this.startIndex).setRestriction(max);
            }else if(min > mid){
                this.repairedList.get(this.startIndex).setRestriction(min);
            }else{
                this.repairedList.get(this.startIndex).setRestriction(mid);
            }
        }
    }

    public int getWINDOW() {
        return WINDOW;
    }

    public void setWINDOW(int WINDOW) {
        this.WINDOW = WINDOW;
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

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public List<TPoint> getRepairedList() {
        return repairedList;
    }

    public void setRepairedList(List<TPoint> repairedList) {
        this.repairedList = repairedList;
    }


}
