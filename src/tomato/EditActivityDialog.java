
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

public class EditActivityDialog extends Dialog {

	protected Shell shell;
	private ToDoListPage inPage;
	Activity act;

	public EditActivityDialog(Shell parent, ToDoListPage _inPage, Activity _act) {
		super(parent);
		inPage = _inPage;
		act = _act;
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
		shell.setSize(550, 320);
		shell.setText("修改事务");

		Label label_name = new Label(shell, SWT.NONE);
		label_name.setText("任务描述：");
		Font labelFont = new Font(shell.getDisplay(), "微软雅黑", 12, SWT.NONE);
		label_name.setBounds(10, 10, 60, 30);

		Text text_name = new Text(shell, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		text_name.setFont(labelFont);
		text_name.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		text_name.setBounds(100, 10, 400, 200);
		text_name.setText(act.getName());

		Label label_marks = new Label(shell, SWT.NONE);
		label_marks.setText("分数：");
		label_marks.setBounds(10, 210, 60, 30);

		Text text_marks = new Text(shell, SWT.BORDER);
		text_marks.setFont(labelFont);
		text_marks.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		text_marks.setBounds(100, 210, 60, 30);
		text_marks.setText(String.valueOf(act.getTomatoCount()));

		final Button button = new Button(shell, SWT.NONE);
		button.setText("确定");
		button.setBounds(200, 250, 60, 30);
		button.addListener(SWT.MouseUp, new Listener() {
			public void handleEvent(Event event) {
				if (text_name.getText() == null || text_name.getText().equals("")) {
					MessageBox dialog = new MessageBox(shell, SWT.OK | SWT.ICON_ERROR);
					dialog.setText("错误");
					dialog.setMessage("事务描述不能为空！");
					dialog.open();
					return;
				}
				String marksStr = text_marks.getText();
				if (!marksStr.matches("[0-9]+")) {
					MessageBox dialog = new MessageBox(shell, SWT.OK | SWT.ICON_ERROR);
					dialog.setText("错误");
					dialog.setMessage("分数必须为数字！");
					dialog.open();
					return;
				}
				int tomatoCount = Integer.parseInt(marksStr);

				act.setName(text_name.getText());
				act.setTomatoCount(tomatoCount);
				;
				DBMgr theDBMgr;
				try {
					theDBMgr = new DBMgr();
					theDBMgr.EditOneActivity(act);
					inPage.SetListValues();
					shell.close();
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			}
		});
		final Button button_cancel = new Button(shell, SWT.NONE);
		button_cancel.setText("取消");
		button_cancel.setBounds(280, 250, 60, 30);
		button_cancel.addListener(SWT.MouseUp, new Listener() {
			public void handleEvent(Event event) {
				shell.close();
			}
		});
	}
}
