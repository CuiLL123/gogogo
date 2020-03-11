import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyData {
   volatile int number = 0;

    public void addTo60() {
        this.number = 60;
    }

    public void add1(){
        number++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();
    public void  addAtomic() {
        atomicInteger.getAndIncrement();
    }


}
/*
    1可见性演示
        1.1 int number = 0,没有加关键字，没有可见性
        1.2 添加了volatile关键字，保证了可见性

    2 验证volatile不保证原子性
        2.1 什么是原子性？不可分割，完整性。
        2.2 不保证原子性演示
        2.3 why
        2.4 解决
            a.syn
            b.使用juc下的AtomicInteger
 */
public class VolatileDemo {
    public static void main(String[] args) {
//        seeOkByVolatile();

        MyData myData = new MyData();
        for(int i = 0; i < 20; i++) {
            new Thread(()-> {
                for(int j = 1; j <= 1000; j++) {
                    myData.add1();
                    myData.addAtomic();
                }
            },String.valueOf(i)).start();
        }

        //等待20个线程都计算完,
//        try{
//            TimeUnit.SECONDS.sleep(5);
//        }catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        while(Thread.activeCount() > 2) {
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName() + "\t int type finally number value is " + myData.number);
        System.out.println(Thread.currentThread().getName() + "\t AtomicInteger type finally number value is " + myData.atomicInteger);

    }

    //volatile可见性演示，通知其他线程，主物理内存中的值已经修改
    private static void seeOkByVolatile() {
        MyData myData = new MyData();//资源类

        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + "\t come in");

            try{
                TimeUnit.SECONDS.sleep(3);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.addTo60();
            System.out.println(Thread.currentThread().getName() + "\t updated number is " + myData.number);

        },"AAA").start();

        while (myData.number == 0) {

        }
        System.out.println(Thread.currentThread().getName() + "\t mission is over,main get number is " + myData.number );
    }
}
