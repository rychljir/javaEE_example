
import wa2.WorkerThread;

import javax.jms.JMSException;

public class Main {

    public static void main(String[] args) throws JMSException {
        for (int i = 0; i < 2; i++) {
            WorkerThread worker = new WorkerThread();
            worker.start();
        }
        //worker.stopWorkerThread();
    }
}


