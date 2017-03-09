
package tomato;

import java.awt.Toolkit;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class TimeDilog extends Dialog {

	protected Shell shell;
	private ToDoListPage inPage;
	public Label label1;
	public int timeValue = 1500;
	private Activity act;
	private tomatoItem tomato;

	public TimeDilog(Shell parent, ToDoListPage _inPage,Activity _act) {
		super(parent);
		inPage = _inPage;
		act=_act;
		tomato=new tomatoItem();
		tomato.setOwnerActivity(act);
		tomato.setStatus(TomatoSatus.underway);
		tomato.setStartTime(new Date());
		// TODO Auto-generated constructor stub
	}

	public void open() {
		createContents();
		// 显示到屏幕左上方
		shell.setLocation(0, 0);
		shell.open();
		shell.layout();

		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	protected void createContents() {
		shell = new Shell(getParent(), SWT.ON_TOP);
		shell.setSize(100, 30);
		shell.setText("新建事务");

		label1 = new Label(shell, SWT.NONE);
		label1.setText("任务描述：");
		Font labelFont = new Font(shell.getDisplay(), "微软雅黑", 20, SWT.NONE);
		label1.setBounds(0, 0, 100, 30);
		label1.setFont(labelFont);

		Display display = getParent().getDisplay();
		label1.setBackground(display.getSystemColor(SWT.COLOR_BLACK));
		label1.setForeground(display.getSystemColor(SWT.COLOR_GREEN));
		TimeDilog theTimeDilog = this;
		Runnable timer = new Runnable() {
			public void run() {
				// your code
				int totalSec = theTimeDilog.timeValue;
				int minute = totalSec / 60;
				int sec = totalSec % 60;
				theTimeDilog.label1.setText(String.valueOf(minute) + "：" + String.valueOf(sec));
				if (totalSec > 0) {
					theTimeDilog.timeValue--;
					display.timerExec(1000, this);
				} else {
					tomato.setEndTime(new Date());
					inPage.AddOneTomato(act, tomato);
					theTimeDilog.shell.close();
				}
			}
		};
		display.timerExec(1, timer);

	}

}
