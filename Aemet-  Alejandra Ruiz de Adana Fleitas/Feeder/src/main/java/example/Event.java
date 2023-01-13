package example;


import java.time.Instant;

public class Event {
    public Instant ts;
    private String place; /* lugar de donde viene el dato*/
    private String  idema;
    private Double ta;
    private Double tamax;
    private Double tamin;

    public Event(Instant ts, String place, String idema, Double ta, Double tamax, Double tamin) {
        this.ts = ts;
        this.place = place;
        this.idema = idema;
        this.ta = ta;
        this.tamax = tamax;
        this.tamin = tamin;
    }

    public Instant getTs() {
        return ts;
    }

}

