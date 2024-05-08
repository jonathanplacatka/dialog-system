package dataproviders;

import eventsystem.AbstractEventGenerator;

public abstract class DataProvider extends AbstractEventGenerator implements Runnable {
    public void start() {
        Thread t = new Thread(this);
        t.start();
    }
}
