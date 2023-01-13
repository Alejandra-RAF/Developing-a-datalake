package example;

public class Event {
    public String ts;
    public String date;
    public String time;


    public void setDate(String date) {
        this.date = ts.substring(0,10);
    }
    public void setTime(String time) {
        this.time = ts.substring(11,19);
    }
    public String place; //El item
    public String idema;
    public double ta;
    public double tamax;
    public double tamin;

    public Event(String ts, String date, String time, String place, String idema, double ta, double tamax, double tamin) {
        this.ts = ts;
        this.date = date;
        this.time = time;
        this.place = place;
        this.idema = idema;
        this.ta = ta;
        this.tamax = tamax;
        this.tamin = tamin;
    }


    public double getTamax() {
        return tamax;
    }

    public double getTamin() {
        return tamin;
    }

    public String getTs() {
        return ts;
    }

}

