package tomato;

import java.sql.Timestamp;

public class Activity {
	private int id;
	private String name;
	private ActivitySatus status;
	private Timestamp startTime;
	private Timestamp endTime;
	private boolean isInToday;
	private int tomatoCount;
	
	public int getTomatoCount() {
		return tomatoCount;
	}

	public void setTomatoCount(int tomatoCount) {
		this.tomatoCount = tomatoCount;
	}

	public Activity(int _id){
		id=_id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ActivitySatus getStatus() {
		return status;
	}
	public void setStatus(ActivitySatus status) {
		this.status = status;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}
	public boolean isInToday() {
		return isInToday;
	}
	public void setInToday(boolean isInToday) {
		this.isInToday = isInToday;
	}
	
	
}
