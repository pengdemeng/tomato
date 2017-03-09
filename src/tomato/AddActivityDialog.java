package tomato;

import java.awt.Toolkit;
import java.sql.SQLException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class AddActivityDialog extends Dialog {

	protected Shell shell;
	private ToDoListPage inPage;

	public AddActivityDialog(Shell parent,ToDoListPage _inPage) {
		super(parent);
		inPage=_inPage;
		// TODO Auto-generated constructor stub
	}

	public void open() {
		createContents();
		// 显示到屏幕中央
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int shellHeight = shell.getBounds().height;
		int shellWidth = shell.getBounds().width;
		shell.setLocation(((screenWidth - shellWidth) / 2), ((screenHeight - shellHeight) / 2));
		shell.open();
		shell.layout();

		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	protected void createContents() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setSize(550, 280);
		shell.setText("新建事务");

		Label label1 = new Label(shell, SWT.NONE);
		label1.setText("任务描述：");
		Font labelFont = new Font(shell.getDisplay(), "微软雅黑", 12, SWT.NONE);
		label1.setBounds(10, 10, 60, 30);

		Text text1 = new Text(shell, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		text1.setFont(labelFont);
		text1.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		text1.setBounds(100, 10, 400, 200);

		final Button button = new Button(shell, SWT.NONE);
		button.setText("确定");
		button.setBounds(200, 210, 60, 30);
		button.addListener(SWT.MouseUp, new Listener() {
			public void handleEvent(Event event) {
				if (text1.getText() == null || text1.getText().equals("")) {
					MessageBox dialog = new MessageBox(shell, SWT.OK | SWT.ICON_ERROR);
					dialog.setText("错误");
					dialog.setMessage("没有填写事务描述！");
					dialog.open();
					return;
				}
				Activity oneActivity = new Activity(0);
				oneActivity.setInToday(true);
				oneActivity.setName(text1.getText());
				oneActivity.setStatus(ActivitySatus.created);
				DBMgr theDBMgr;
				try {
					theDBMgr = new DBMgr();
					theDBMgr.AddOneActivity(oneActivity);
					inPage.SetListValues();
					shell.close();
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			}
		});
		final Button button_cancel = new Button(shell, SWT.NONE);
		button_cancel.setText("取消");
		button_cancel.setBounds(280, 210, 60, 30);
		button_cancel.addListener(SWT.MouseUp, new Listener() {
			public void handleEvent(Event event) {
				shell.close();
			}
		});
	}
}
