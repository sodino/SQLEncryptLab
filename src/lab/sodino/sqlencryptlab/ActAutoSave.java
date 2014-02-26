package lab.sodino.sqlencryptlab;

import lab.sodino.util.LogOut;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ActAutoSave extends Activity implements OnClickListener {
	private DBHelper dbHelper;
	private Button btnAction;
	private GoodsBean goodsBean;
	private TextView txtDetail, txtRead;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_auto_save);

		LogOut.out(this, "onCreate");
		dbHelper = new DBHelper(this);

		btnAction = (Button) findViewById(R.id.btnAction);
		btnAction.setOnClickListener(this);
		txtDetail = (TextView) findViewById(R.id.txtDetail);
		txtRead = (TextView) findViewById(R.id.txtRead);

		goodsBean = GoodsBean.newInstance();
		txtDetail.setText("detail:\n"+goodsBean.toString());
	}

	public void onClick(View v) {
		if (v == btnAction) {
			doSave(goodsBean);
			GoodsBean beanRead = doRead();
			showReadBean(beanRead);
		}
	}

	private void showReadBean(GoodsBean bean) {
		txtRead.setText(bean.toString());
	}

	private GoodsBean doRead() {
		dbHelper.openDBHelper();
		GoodsBean bean = dbHelper.query();
		return bean;
	}

	private void doSave(GoodsBean bean) {
		dbHelper.openDBHelper();
		dbHelper.insert(bean);
		dbHelper.close();
	}
}