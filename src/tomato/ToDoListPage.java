package tomato;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class ToDoListPage extends Composite {
	Table table;

	public ToDoListPage(Composite parent, int style) throws ClassNotFoundException, SQLException {
		super(parent, style);
		CreatToDoListTable();
		//
	}

	private void CreatToDoListTable() throws ClassNotFoundException, SQLException {
		// 表格样式是多选 垂直 水平滚动条，显示边缘
		table = new Table(this, SWT.SINGLE | SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER | SWT.FULL_SELECTION);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		table.setBounds(10, 10, 1100, 480);
		table.setHeaderVisible(true);// 设置表格头可见
		table.setLinesVisible(true);// 设置表格线可见
		// 创建表格头
		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setText("描述");
		tableColumn.setWidth(600);
		tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setText("开始时间");
		tableColumn.setWidth(260);
		tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setText("获取");
		tableColumn.setWidth(80);
		tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setText("放弃");
		tableColumn.setWidth(80);
		SetListValues();
		createMenu(table);

		Font labelFont = new Font(table.getShell().getDisplay(), "仿宋", 15, SWT.NONE);
		table.setFont(labelFont);
	}

	public void SetListValues() throws ClassNotFoundException, SQLException {
		table.removeAll();
		DBMgr dbmgr = new DBMgr();
		List<Activity> activities = dbmgr.GetTodayActivities();
		for (Activity activity : activities) {
			TableItem item1 = new TableItem(table, SWT.NONE);
			Date theDate_today=new Date();
			theDate_today.setHours(0);
			theDate_today.setMinutes(0);
			theDate_today.setSeconds(0);
			int tomatoSuccessCount=dbmgr.GetTomatoCount(activity.getId(),TomatoSatus.finish,theDate_today);
			int tomatoCancelCount=dbmgr.GetTomatoCount(activity.getId(),TomatoSatus.cancel,theDate_today);
			item1.setText(new String[] { activity.getName(),
					activity.getStartTime() != null ? activity.getStartTime().toString() : "",
					String.valueOf(tomatoSuccessCount),String.valueOf(tomatoCancelCount) });
			item1.setData("bindingAct", activity);
			if (activity.getStatus() == ActivitySatus.underway)
				item1.setBackground(0, new Color(null, 153, 204, 0));
		}
	}

	public void AddOneTomato(Activity theAct, tomatoItem theTomato) {
		MessageBox messageBox = new MessageBox(table.getShell(), SWT.OK | SWT.CANCEL | SWT.ICON_QUESTION);
		table.getShell().forceActive();
		messageBox.setMessage("领取西红柿？");
		if (messageBox.open() == SWT.OK)
			theTomato.setStatus(TomatoSatus.finish);
		else
			theTomato.setStatus(TomatoSatus.cancel);
		DBMgr theDBMgr;
		try {
			theDBMgr = new DBMgr();
			theDBMgr.AddOneTomato(theTomato);
			SetListValues();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 创建上下文菜单
	private void createMenu(Table table) {
		// 创建弹出式菜单
		Menu menu = new Menu(this.getShell(), SWT.POP_UP);
		// 设置该菜单为表格菜单
		table.setMenu(menu);
		// 创建添加菜单项
		MenuItem add = new MenuItem(menu, SWT.PUSH);
		add.setText("添加");
		ToDoListPage thePage = this;
		add.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				new AddActivityDialog(Display.getCurrent().getActiveShell(), thePage).open();
			}
		});
		// 创建删除菜单项
		MenuItem del = new MenuItem(menu, SWT.PUSH);
		del.setText("删除");
		del.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (table.getSelectionCount() == 0) {
					MessageBox messageBox1 = new MessageBox(table.getShell(), SWT.OK | SWT.ICON_WARNING);
					messageBox1.setMessage("请选择要删除的事务");
					messageBox1.open();
					return;
				}
				MessageBox messageBox = new MessageBox(table.getShell(), SWT.OK | SWT.CANCEL | SWT.ICON_QUESTION);
				messageBox.setMessage("确定要删除选中的事务吗？");
				if (messageBox.open() == SWT.OK) {
					TableItem item = table.getItem(table.getSelectionIndices()[0]);
					Activity theAct = (Activity) item.getData("bindingAct");
					DBMgr theDBMgr;
					try {
						theDBMgr = new DBMgr();
						theDBMgr.DeleteOneActivity(theAct.getId());
						SetListValues();
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		// 创建查看菜单项
		MenuItem alter = new MenuItem(menu, SWT.PUSH);
		alter.setText("修改");
		alter.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (table.getSelectionCount() == 0) {
					MessageBox messageBox1 = new MessageBox(table.getShell(), SWT.OK | SWT.ICON_WARNING);
					messageBox1.setMessage("请选择要修改的事务");
					messageBox1.open();
					return;
				}

				TableItem item = table.getItem(table.getSelectionIndices()[0]);
				Activity theAct = (Activity) item.getData("bindingAct");
				new EditActivityDialog(Display.getCurrent().getActiveShell(), thePage, theAct).open();
			}
		});
		// 创建移出今天菜单项
		MenuItem moveToday = new MenuItem(menu, SWT.PUSH);
		moveToday.setText("移出今天");
		moveToday.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (table.getSelectionCount() == 0) {
					MessageBox messageBox1 = new MessageBox(table.getShell(), SWT.OK | SWT.ICON_WARNING);
					messageBox1.setMessage("请选择要移出的事务");
					messageBox1.open();
					return;
				}
				TableItem item = table.getItem(table.getSelectionIndices()[0]);
				Activity theAct = (Activity) item.getData("bindingAct");
				DBMgr theDBMgr;
				try {
					theDBMgr = new DBMgr();
					theDBMgr.MoveOutTodayActivity(theAct);
					SetListValues();
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		// 创建设置完成菜单项
		MenuItem end = new MenuItem(menu, SWT.PUSH);
		end.setText("设置完成");
		end.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (table.getSelectionCount() == 0) {
					MessageBox messageBox1 = new MessageBox(table.getShell(), SWT.OK | SWT.ICON_WARNING);
					messageBox1.setMessage("请选择要设置完成的事务");
					messageBox1.open();
					return;
				}
				TableItem item = table.getItem(table.getSelectionIndices()[0]);
				Activity theAct = (Activity) item.getData("bindingAct");
				if (theAct.getStatus() == ActivitySatus.created || theAct.getStartTime() == null) {
					MessageBox messageBox1 = new MessageBox(table.getShell(), SWT.OK | SWT.ICON_ERROR);
					messageBox1.setMessage("任务未开始无法设置完成!");
					messageBox1.open();
					return;
				}
				MessageBox messageBox = new MessageBox(table.getShell(), SWT.OK | SWT.CANCEL | SWT.ICON_QUESTION);
				messageBox.setMessage("确定要将选中的事务设置为完成状态吗？");
				if (messageBox.open() == SWT.OK) {
					DBMgr theDBMgr;
					try {
						theDBMgr = new DBMgr();
						theDBMgr.SetActivityDone(theAct);
						SetListValues();
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		// 创建开始菜单项
		MenuItem start = new MenuItem(menu, SWT.PUSH);
		start.setText("开始");
		ToDoListPage page = this;
		start.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (table.getSelectionCount() == 0) {
					MessageBox messageBox1 = new MessageBox(table.getShell(), SWT.OK | SWT.ICON_WARNING);
					messageBox1.setMessage("请选择要开始的事务");
					messageBox1.open();
					return;
				}
				TableItem item = table.getItem(table.getSelectionIndices()[0]);
				Activity theAct = (Activity) item.getData("bindingAct");
				DBMgr theDBMgr;
				try {
					if (theAct.getStatus() == ActivitySatus.created) {
						theDBMgr = new DBMgr();
						theDBMgr.StartActivity(theAct);
						SetListValues();
					}
					TimeDilog timeDilog = new TimeDilog(table.getShell(), page,theAct);
					timeDilog.open();
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		table.setMenu(menu);
	}
}
