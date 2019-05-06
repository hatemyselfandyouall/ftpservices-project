import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import star.util.NumberUtil;

/**
 * @author Zengzp
 *  执行方法主类
 */
public class ExcuteMain {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
		//初始化执行数据
    	List<Long> sumIds = new ArrayList<Long>();
    	for (int i = 0; i < 104; i++) {
    		sumIds.add(NumberUtil.parseLong(i+""));
		}
    	System.out.println("init:"+sumIds);
    	ExecutorService callexc = Executors.newCachedThreadPool();
		Future<String> result = callexc.submit(new CallTask(sumIds));
		System.out.println(result.get());	
    	int i = 0;
    	while (sumIds.size()%10 > 0) {
    		if (sumIds.size() >= 10) {
    			List<Long> excuteIds = sumIds.subList(0, 10);
        		new Thread(new ExcuteTask(excuteIds)).start();
    			//使用excutor执行器管理线程
//    			ExecutorService exc = Executors.newCachedThreadPool();
//    			exc.execute(new ExcuteTask(excuteIds));
        		sumIds = sumIds.subList(i+10, sumIds.size());
			}
    		//代表最后的一批数据
    		if (sumIds.size() > 0 && sumIds.size() <10) {
    			List<Long> excuteIds = sumIds.subList(0, sumIds.size());
        		new Thread(new ExcuteTask(excuteIds)).start();
        		break;
			}
			
		}
//    		System.out.println(i);
//    		List<Long> excuteIds = sumIds.subList(0, 10);
//			new Thread(new ExcuteTask(excuteIds)).start();
//			sumIds = sumIds.subList(i+10, sumIds.size());
//			System.out.println("after:"+sumIds);
	}
}
