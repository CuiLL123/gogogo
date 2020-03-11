import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
    public static void main(String[] args) throws  Exception {
        closeDoor();


    }

    private static void closeDoor() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);

        for(int i = 1; i <= 6; i++) {//减到0，主线程才能开始执行
            new Thread(()->{
                System.out.println(Thread.currentThread().getName() + "\t上完自习，离开教室");
                countDownLatch.countDown();
            },CountyEnum.forEach_countryEnum(i).getRetMessage()).start();
         }

        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "\t班长走人");
    }
}
