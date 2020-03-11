import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class ContainrNoSafeDemo {
    public static  void main(String[] args) {
        //listUnSafe();

        Set<String> set =  new CopyOnWriteArraySet<>();//Collections.synchronizedSet(new HashSet<>());//new HashSet();


        for(int i = 0; i < 30; i++) {
            new Thread(()->{
                set.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(set);
            },String.valueOf(i)).start();
        }


        /**
         * 1 故障java.util.ConcurrentModificationException
         * 2 导致原因
         *      并发争抢修改导致，参考花名册签名情况
         *      一个人正在写入，另一个人过来抢，导致数据不一致异常，并发修改异常。
         *
         * 3 解决方法
         *      3.1 new Vector();
         *      3.2 Collections.synchronizedList(new ArrayList<>());
         *      3.3 new CopyOnWriteArrayList<>();
         * 4 优化建议（同样错误不再犯）
         */
    }

    private static void listUnSafe() {
        List<String> list =  new CopyOnWriteArrayList<>();//Collections.synchronizedList(new ArrayList<>());//new ArrayList<>();
//        list.add("a");
//        list.add("b");
//        list.add("c");
//        for (String s: list
//             ) {
//            System.out.println(s);
//        }

        //多线程ArrayList add不安全演示
        for(int i = 0; i < 30; i++) {//java.util.ConcurrentModificationException并发修改异常
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            },String.valueOf(i)).start();
        }
    }
}
