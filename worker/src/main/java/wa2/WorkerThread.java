package wa2;

/**
 * Created by Speedy on 20. 5. 2017.
 */
import wa2.communication.Subscriber;
import wa2.repositories.DBRepository;

import javax.jms.JMSException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class WorkerThread extends Thread {

    private AtomicBoolean run = new AtomicBoolean(true);
    private TaskCalculation task;
    private final Object lock = new Object();
    private final Object poisonLock = new Object();
    static LinkedList<TaskCalculation> poison = new LinkedList<TaskCalculation>();
    static int index = 1;
    private String name;
    private Subscriber subscriber;
    private DBRepository repos;

    public WorkerThread() throws JMSException {
        super("WorkerThread"+index);
        name = "WorkerThread"+index;
        System.out.println("--" + name + " was created. --");
        index++;
        subscriber = new Subscriber();
        repos = new DBRepository();
        UUID id = UUID.randomUUID();
        subscriber.create(String.valueOf(id),"bank.t");
    }

    public void stopWorkerThread() {
        synchronized (lock) {
            System.out.println(name+ " stopped");
            run.set(false);
        }
    }

    public void run() {
        boolean ran = false;
        while (run.get()) {
            synchronized (lock) {
                System.out.println(name+ " waits for task");
                try {
                    waitForRunnable();
                    ran = executeRunnable();
                } catch (Throwable exceptionInRunnable) {
                    System.out.println("Error while executing the Runnable: " + exceptionInRunnable);
                } finally {
                    cleanupRunnable();
                    if (ran) {
                        ran = false;
                    }
                }
            }
        }
        System.out.println(name+ " is down");
    }

    private boolean executeRunnable() throws InterruptedException {
        if (task == null) {
            return false;
        }
        System.out.println(name+ " runs the task");
        Thread.sleep(50000);
        boolean success = repos.updateCalculation(task.uuid,task.result);
        if(success){
            System.out.println("--------------------------------------------------------");
            System.out.println("Calculation: " + task.result);
            System.out.println("--------------------------------------------------------");
        }else{
            poison.add(task);
            System.out.println("--------------------------------------------------------");
            System.out.println("Calculation failed!");
            System.out.println("--------------------------------------------------------");
        }
        return true;
    }

    private void cleanupRunnable() {
        synchronized (lock) {
            System.out.println(name+ " task execution over");
            task = null;
        }
    }

    private void waitForRunnable() throws JMSException {
        while (task == null && run.get()) {
            String msg = subscriber.getMsg(500);
            if(!msg.equals("empty")){
                synchronized(lock) {
                    task = new TaskCalculation(msg);
                    lock.notifyAll();
                }
            }else{
                // System.out.println(name+ " has no task, wait for 1s and then check again");
                try {
                    lock.wait(1000);
                } catch (InterruptedException e) {
                    System.out.println(name+ " was interrupted." + e);
                }
            }
        }
    }

    class TaskCalculation{
        String message;
        String uuid;
        int result;

        public TaskCalculation(String msg){
            message = msg;
            String[] parsed = msg.split(";");
            uuid = parsed[0];
            result = 50000 + (int)(Math.random() * ((3000000 - 50000) + 1));
        }
    }
}

