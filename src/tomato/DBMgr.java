package tomato;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.swt.widgets.DateTime;

public class DBMgr {
	Connection m_conn;

	public DBMgr() throws SQLException, ClassNotFoundException {
		OpenDBConn();
	}

	private void OpenDBConn() throws SQLException, ClassNotFoundException {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/tomato?useUnicode=true&characterEncoding=utf-8&useSSL=false";
		String user = "root";
		String password = "root";

		Class.forName(driver);
		m_conn = DriverManager.getConnection(url, user, password);
	}

	public List<Activity> GetTodayActivities() throws SQLException, ClassNotFoundException {
		if (m_conn.isClosed())
			OpenDBConn();
		List<Activity> ativities = new ArrayList<Activity>();
		String sqlStr = "select * from activity where isintoday=true";
		Statement stmt = m_conn.createStatement();
		ResultSet rs = stmt.executeQuery(sqlStr);
		while (rs.next()) {
			Activity oneActivity = new Activity(rs.getInt(1));
			oneActivity.setName(rs.getString(2));
			oneActivity.setStatus(ActivitySatus.values()[rs.getInt(3)]);
			oneActivity.setStartTime(rs.getTimestamp(4));
			oneActivity.setEndTime(rs.getTimestamp(5));
			oneActivity.setInToday(rs.getBoolean(6));
			oneActivity.setTomatoCount(rs.getInt(7));
			ativities.add(oneActivity);
		}
		rs.close();
		stmt.close();
		m_conn.close();
		return ativities;
	}
	public boolean AddOneActivity(Activity theAct) throws SQLException, ClassNotFoundException {
		if (m_conn.isClosed())
			OpenDBConn();
		String sqlStr = MessageFormat.format("insert into activity (name,status,isintoday) values (''{0}'',{1},{2})",
				theAct.getName(),theAct.getStatus().ordinal(),theAct.isInToday());
		Statement stmt = m_conn.createStatement();
		boolean beSuccess = stmt.execute(sqlStr);
		stmt.close();
		m_conn.close();
		return beSuccess;
	}
	public boolean DeleteOneActivity(int id) throws SQLException, ClassNotFoundException {
		if (m_conn.isClosed())
			OpenDBConn();
		String sqlStr = MessageFormat.format("delete from activity where id={0}",id);
		Statement stmt = m_conn.createStatement();
		boolean beSuccess = stmt.execute(sqlStr);
		stmt.close();
		m_conn.close();
		return beSuccess;
	}
	public boolean EditOneActivity(Activity theAct) throws SQLException, ClassNotFoundException {
		if (m_conn.isClosed())
			OpenDBConn();
		String sqlStr = MessageFormat.format("update activity set name=''{0}'',tomatocount={1} where id={2}",
				theAct.getName(),theAct.getTomatoCount(),theAct.getId());
		Statement stmt = m_conn.createStatement();
		boolean beSuccess = stmt.execute(sqlStr);
		stmt.close();
		m_conn.close();
		return beSuccess;
	}
	public boolean MoveOutTodayActivity(Activity theAct) throws SQLException, ClassNotFoundException {
		if (m_conn.isClosed())
			OpenDBConn();
		String sqlStr = MessageFormat.format("update activity set isintoday=false where id={0}",theAct.getId());
		Statement stmt = m_conn.createStatement();
		boolean beSuccess = stmt.execute(sqlStr);
		stmt.close();
		m_conn.close();
		return beSuccess;
	}
	public boolean MoveTodayActivity(Activity theAct) throws SQLException, ClassNotFoundException {
		if (m_conn.isClosed())
			OpenDBConn();
		String sqlStr = MessageFormat.format("update activity set isintoday=true where id={0}",theAct.getId());
		Statement stmt = m_conn.createStatement();
		boolean beSuccess = stmt.execute(sqlStr);
		stmt.close();
		m_conn.close();
		return beSuccess;
	}
	public boolean SetActivityDone(Activity theAct) throws SQLException, ClassNotFoundException {
		if (m_conn.isClosed())
			OpenDBConn();
		java.util.Date currTime = new java.util.Date();
		String sqlStr = MessageFormat.format("update activity set status=2,endtime=''{0}'' where id={1}",
				currTime.toLocaleString(),theAct.getId());
		Statement stmt = m_conn.createStatement();
		boolean beSuccess = stmt.execute(sqlStr);
		stmt.close();
		m_conn.close();
		return beSuccess;
	}
	public boolean StartActivity(Activity theAct) throws SQLException, ClassNotFoundException {
		if (m_conn.isClosed())
			OpenDBConn();
		java.util.Date currTime = new java.util.Date();
		String sqlStr = MessageFormat.format("update activity set status=1,starttime=''{0}'' where id={1}",
				currTime.toLocaleString(),theAct.getId());
		Statement stmt = m_conn.createStatement();
		boolean beSuccess = stmt.execute(sqlStr);
		stmt.close();
		m_conn.close();
		return beSuccess;
	}
	public List<Activity> GetNoDoneActivities() throws SQLException, ClassNotFoundException {
		if (m_conn.isClosed())
			OpenDBConn();
		List<Activity> ativities = new ArrayList<Activity>();
		String sqlStr = "select * from activity where status=0 or status=1";
		Statement stmt = m_conn.createStatement();
		ResultSet rs = stmt.executeQuery(sqlStr);
		while (rs.next()) {
			Activity oneActivity = new Activity(rs.getInt(1));
			oneActivity.setName(rs.getString(2));
			oneActivity.setStatus(ActivitySatus.values()[rs.getInt(3)]);
			oneActivity.setStartTime(rs.getTimestamp(4));
			oneActivity.setEndTime(rs.getTimestamp(5));
			oneActivity.setInToday(rs.getBoolean(6));
			oneActivity.setTomatoCount(rs.getInt(7));
			ativities.add(oneActivity);
		}
		rs.close();
		stmt.close();
		m_conn.close();
		return ativities;
	}
	public List<Activity> GetDoneActivities() throws SQLException, ClassNotFoundException {
		if (m_conn.isClosed())
			OpenDBConn();
		List<Activity> ativities = new ArrayList<Activity>();
		String sqlStr = "select * from activity where status>1";
		Statement stmt = m_conn.createStatement();
		ResultSet rs = stmt.executeQuery(sqlStr);
		while (rs.next()) {
			Activity oneActivity = new Activity(rs.getInt(1));
			oneActivity.setName(rs.getString(2));
			oneActivity.setStatus(ActivitySatus.values()[rs.getInt(3)]);
			oneActivity.setStartTime(rs.getTimestamp(4));
			oneActivity.setEndTime(rs.getTimestamp(5));
			oneActivity.setInToday(rs.getBoolean(6));
			oneActivity.setTomatoCount(rs.getInt(7));
			ativities.add(oneActivity);
		}
		rs.close();
		stmt.close();
		m_conn.close();
		return ativities;
	}
	public boolean AddOneTomato(tomatoItem theTomato) throws SQLException, ClassNotFoundException {
		if (m_conn.isClosed())
			OpenDBConn();
		String sqlStr = MessageFormat.format("insert into tomato (activityid,statusid,starttime,endtime) values ({0},{1},''{2}'',''{3}'')",
				theTomato.getOwnerActivity().getId(),theTomato.getStatus().ordinal(),theTomato.getStartTime().toLocaleString(),
				theTomato.getEndTime().toLocaleString());
		Statement stmt = m_conn.createStatement();
		boolean beSuccess = stmt.execute(sqlStr);
		stmt.close();
		m_conn.close();
		return beSuccess;
	}
	public int GetTomatoCount(int actId,TomatoSatus status,Date beforeDate) throws SQLException, ClassNotFoundException {
		if (m_conn.isClosed())
			OpenDBConn();
		int count=0;
		String sqlStr = MessageFormat.format("select count(*) from tomato where activityid={0} and statusid={1} and starttime>''{2}''",
				String.valueOf(actId),String.valueOf(status.ordinal()),beforeDate.toLocaleString());
		Statement stmt = m_conn.createStatement();
		ResultSet rs = stmt.executeQuery(sqlStr);
		while (rs.next()) {
			count=rs.getInt(1);
			break;
		}
		rs.close();
		stmt.close();
		m_conn.close();
		return count;
	}
}
