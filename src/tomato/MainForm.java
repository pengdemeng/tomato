package tomato;

import java.sql.SQLException;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class MainForm {
	private Shell shell;

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		try {
			MainForm window = new MainForm();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void open() throws ClassNotFoundException, SQLException {
		/**
		 * 第一步创建Display,对应操作系统的控件，使用完必须释放
		 */
		Display display = new Display();
		/**
		 * 第二部创建shell
		 * 
		 * @style
		 */
		shell = new Shell(display);
		shell.setText("tomato working");
		shell.setLayout(new org.eclipse.swt.layout.FillLayout());
		// CreatToDoListTable();
		createContents();
		 String path = "img/番茄.png";  
		Image image =new Image(display, path);
		shell.setImage(image);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

		display.dispose();

	}

	protected void createContents() throws ClassNotFoundException, SQLException {
		// 定义tabFolder
		final TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		// tabFolder.setLayout(new org.eclipse.swt.layout.FillLayout());
		// 每一个tabItem内包含一个Composite的子类，因此我们可以将其分离出来，
		// 这里继承composite就好了。
		Font labelFont = new Font(shell.getDisplay(), "仿宋", 18, SWT.NONE);
		tabFolder.setFont(labelFont);
		final TabItem comaTabItem = new TabItem(tabFolder, SWT.NONE);
		comaTabItem.setText("今天事务");
		// 实例化一个我们定制的composite
		final Composite composite = new ToDoListPage(tabFolder, SWT.NONE);
		// 将我们定制的composite附加到tabItem上。
		comaTabItem.setControl(composite);
		//
		final TabItem combTabItem = new TabItem(tabFolder, SWT.NONE);
		combTabItem.setText("未完成事务");
		final Composite composite_1 = new ActivityListPage(tabFolder, SWT.NONE);
		combTabItem.setControl(composite_1);
		//
		final TabItem comcTabItem = new TabItem(tabFolder, SWT.NONE);
		comcTabItem.setText("已完成事务");
		final Composite composite_2 = new ActivityListPage_done(tabFolder, SWT.NONE);
		comcTabItem.setControl(composite_2);
		//
		final TabItem comdTabItem = new TabItem(tabFolder, SWT.NONE);
		comdTabItem.setText("当月分数统计");
		final Composite composite_3 = new ActivityListPage(tabFolder, SWT.NONE);
		comdTabItem.setControl(composite_3);
		//
		final TabItem comeTabItem = new TabItem(tabFolder, SWT.NONE);
		comeTabItem.setText("当年分数统计");
		final Composite composite_4 = new ActivityListPage(tabFolder, SWT.NONE);
		comeTabItem.setControl(composite_4);
		
		tabFolder.addListener(SWT.MouseDown, new Listener() {
			public void handleEvent(Event event) {
				if(tabFolder.getSelectionIndex()==0){
					try {
						((ToDoListPage)composite).SetListValues();
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if(tabFolder.getSelectionIndex()==1){
					try {
						((ActivityListPage)composite_1).SetListValues();
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if(tabFolder.getSelectionIndex()==2){
					try {
						((ActivityListPage_done)composite_2).SetListValues();
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}

}

