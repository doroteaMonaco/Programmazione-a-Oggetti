package diet;

public class WorkingHours implements Comparable<WorkingHours>{

    private Time open;
    private Time close;

    public WorkingHours(Time open, Time close){
        this.open = open;
        this.close = close;
    }

    public Time getOpen(){
        return open;
    }

    public Time getClose(){
        return close;
    }

    @Override
    public int compareTo(WorkingHours o){
        return open.compareTo(o.open);
    }

    public boolean includes(Time t) {
       return open.compareTo(t) <= 0 && close.compareTo(t) >= 0;
    }
    
}
