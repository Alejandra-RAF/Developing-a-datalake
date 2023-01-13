package example;

public abstract class DatabaseWriter {

    public abstract void addmin(Event minEventtoday, Event minEventyesterday);

    public abstract void addmax(Event maxEventtoday, Event maxEventyesterday);
}
