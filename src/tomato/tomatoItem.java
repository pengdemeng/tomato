package tomato;

import java.util.Date;


public class tomatoItem {
	private int id;
	private Activity ownerActivity;
	private TomatoSatus status;
	private Date startTime;
	private Date endTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Activity getOwnerActivity() {
		return ownerActivity;
	}
	public void setOwnerActivity(Activity ownerActivity) {
		this.ownerActivity = ownerActivity;
	}
	public TomatoSatus getStatus() {
		return status;
	}
	public void setStatus(TomatoSatus status) {
		this.status = status;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
}
