import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁（递归锁）
 *  同一个线程 外层函数获得锁后，内层递归函数仍然能获取该锁的代码，
 *  在同一个线程在外层方法获取该锁的时候，在进入内层方法会自动获取锁
 *
 *  即，线程可以在外层进入内层方法会自动获取锁
 *
 */

class Phone implements Runnable{

    public synchronized  void sendSMS() throws  Exception {
        System.out.println(Thread.currentThread().getId() + "\t invoked sendSMS()");
        sendEmail();//同一个锁还是两个锁？同一个锁。
    }    
    public synchronized  void sendEmail() throws  Exception {
        System.out.println(Thread.currentThread().getId() + "\t invoked sendEmail()");
    }
    Lock lock = new ReentrantLock();

    @Override
    public void run() {
        get();
    }

    public void get() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t invoked get()");
            set();//同一个锁还是两个锁？同一个锁。

        }finally {
            lock.unlock();
        }
    }

    public void set() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t invoked set()");
        }finally {
            lock.unlock();
        }
    }
}
public class ReenterLockDemo {
    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"t1").start();
        
        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"t2").start();

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();

        try { TimeUnit.SECONDS.sleep(1); }catch (InterruptedException e) { e.printStackTrace(); }
        Thread t3 = new Thread(phone,"t3");
        Thread t4 = new Thread(phone,"t4");
        t3.start();
        t4.start();

    }
}
