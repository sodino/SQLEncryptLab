package lab.sodino.sqlencryptlab;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	private Button btnWriteOriginal,btnWriteEncrypt,btnReadOriginal,btnReadEncrypt;
	private EditText editTxt;
	private TextView txtReadOriginal,txtReadEncrypt;
	private DBHelper dbHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnWriteOriginal = (Button)findViewById(R.id.btnWriteOriginal);
		btnWriteEncrypt = (Button)findViewById(R.id.btnWriteEncrypt);
		btnReadOriginal = (Button)findViewById(R.id.btnReadOriginal);
		btnReadEncrypt = (Button)findViewById(R.id.btnReadEncrypt);
		
		btnWriteOriginal.setOnClickListener(this);
		btnWriteEncrypt.setOnClickListener(this);
		btnReadOriginal.setOnClickListener(this);
		btnReadEncrypt.setOnClickListener(this);
		
		txtReadOriginal = (TextView)findViewById(R.id.txtReadOriginal);
		txtReadEncrypt = (TextView)findViewById(R.id.txtReadEncrypt);
		
		editTxt = (EditText) findViewById(R.id.editTxt);
		
		dbHelper = new DBHelper(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btnReadEncrypt:
			break;
		case R.id.btnReadOriginal:
			break;
		case R.id.btnWriteEncrypt:
			writeString(true);
			break;
		case R.id.btnWriteOriginal:
			writeString(false);
			break;
		}
	}
	
	private void writeString(boolean encrypt){
		String txt = editTxt.toString();
		TxtBean bean = TxtBean.newInstance();
		dbHelper.openDBHelper();
		
	}
}