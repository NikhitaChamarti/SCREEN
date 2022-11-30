package ScreenAlgorithims;

import Pojo.TPoint;

import java.util.ArrayList;
import java.util.List;

public class ScreenHeuristic {


    //Window Size
    private int WINDOW;
    //Defined minimum
    private double MINIMUM;
    //Defined Maximum
    private double MAXIMUM;

    private int startIndex;
    private int endIndex;
    //List having Delayed points
    private List<TPoint> repairedList;

    public ScreenHeuristic(int WINDOW, double MINIMUM, double MAXIMUM, int startIndex, int endIndex) {
        this.WINDOW = WINDOW;
        this.MINIMUM = MINIMUM;
        this.MAXIMUM = MAXIMUM;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.repairedList = new ArrayList<>();
    }

    public ScreenHeuristic(int WINDOW, double MINIMUM, double MAXIMUM, int startIndex, int endIndex, List<TPoint> repairedList) {
        this.WINDOW = WINDOW;
        this.MINIMUM = MINIMUM;
        this.MAXIMUM = MAXIMUM;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.repairedList = repairedList;
    }
    //Algorithmic Function
  public void runHeuristic () throws Exception {
      double min = Math.max(
              this.repairedList.get(this.startIndex - 1).getRestriction() + this.MINIMUM * (this.repairedList.get(this.startIndex).getTime() - this.repairedList.get(this.startIndex - 1).getTime()),
              this.repairedList.get(this.startIndex + 1).getRestriction() + this.MAXIMUM * (this.repairedList.get(this.startIndex).getTime() - this.repairedList.get(this.startIndex + 1).getTime()));
      double max = Math.min(
              this.repairedList.get(this.startIndex - 1).getRestriction() + this.MAXIMUM * (this.repairedList.get(this.startIndex).getTime() - this.repairedList.get(this.startIndex - 1).getTime()),
              this.repairedList.get(this.startIndex + 1).getRestriction() + this.MINIMUM * (this.repairedList.get(this.startIndex).getTime() - this.repairedList.get(this.startIndex + 1).getTime()));
     updateConstraint(min , max);

  }

    public void updateConstraint(double min ,double max ) throws Exception {
        if(min <= max){
            double mid = this.repairedList.get(this.startIndex).getValue();
            if(max < mid){
                this.repairedList.get(this.startIndex).setRestriction(max);
            }else if(min > mid){
                this.repairedList.get(this.startIndex).setRestriction(min);
            }else{
                this.repairedList.get(this.startIndex).setRestriction(mid);
            }
        }else{
            ScreenUpdate up = new ScreenUpdate(this.WINDOW,this.MINIMUM,this.MAXIMUM,this.startIndex,this.endIndex,this.repairedList);
            up.runUpdate();
            this.repairedList = up.getRepairedList();
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
