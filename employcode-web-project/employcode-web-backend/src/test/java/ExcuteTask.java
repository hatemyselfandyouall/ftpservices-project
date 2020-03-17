import java.util.List;

public class ExcuteTask implements Runnable{
    
	public ExcuteTask() {
	}
	
	public ExcuteTask(List<Long> ids) {
		this.ids = ids;
	}
	
	private List<Long> ids = null;
	
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName()+"Excute there id:"+ids);
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
	
	

}
