package Pojo;

/**
 * POJO(Plain Old Java Object) class  : Tpoint
 *
 * Members : restriction,modified ,time ,value
 *
 *
 */

public class TPoint {

    private double restriction;
    private boolean modified;
    private long time;
    private double value;

    public TPoint(double restriction, boolean modified, long time, double value) {
        this.restriction = restriction;
        this.modified = modified;
        this.time = time;
        this.value = value;
    }

    public TPoint(double restriction, long time, double value) {
        this.restriction = restriction;
        this.time = time;
        this.value = value;
    }

    public TPoint (long time, double restriction){
        this.time = time;
        this.restriction = restriction;
        this.value = restriction;

    }
    //getter and setter methods
    public double getRestriction() {
        return restriction;
    }

    public void setRestriction(double restriction) {
        this.restriction = restriction;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
