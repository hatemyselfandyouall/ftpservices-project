import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

public class CallTask implements Callable<String>{
	
	public CallTask() {
	}
	
	public CallTask(List<Long> ids) {
		this.ids = ids;
	}
	
	private List<Long> ids = null;

	
	@Override
	public String call() throws Exception {
		System.out.println(Thread.currentThread().getName()+"Excute there id:"+ids);
		return "执行成功";
	}
	
	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
	

}
