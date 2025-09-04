package diet;

import java.util.ArrayList;

public class Time implements Comparable<Time>{
    
    private int h, m;

    Time(String time){
        String[] hm = time.split(":");
        h = Integer.parseInt(hm[0]);
        m = Integer.parseInt(hm[1]);
    }

    public Time(int h, int m){
        this.h = h;
        this.m = m;
    }

    @Override
    public int compareTo(Time o){
        return 60* h + m - (o.h * 60 + o.m);
    }
}
