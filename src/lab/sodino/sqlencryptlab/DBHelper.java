package lab.sodino.sqlencryptlab;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import lab.sodino.util.LogOut;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private SQLiteDatabase sqlDb;

	public static final int VERSION = 1;
	public static final Map<Class<?>, String> TYPES;
	static {
		TYPES = new HashMap<Class<?>, String>();
		TYPES.put(byte.class, "BYTE");
		TYPES.put(boolean.class, "INTEGER");
		TYPES.put(short.class, "SHORT");
		TYPES.put(int.class, "INTEGER");
		TYPES.put(long.class, "LONG");
		TYPES.put(String.class, "TEXT");
		TYPES.put(byte[].class, "BLOB");
		TYPES.put(float.class, "FLOAT"); // REAL
		TYPES.put(double.class, "DOUBLE"); // REAL
	}

	public DBHelper(Context context) {
		super(context, context.getPackageName(), null, VERSION);
		File dbFile = context.getDatabasePath(context.getPackageName());
		if (dbFile.exists() == false) {
			LogOut.out(this, "DBFile does not exist.");
			// 去调用onCreate()和onUpgrade()建表
			getWritableDatabase();
			// initAllDBItem();
			close();
			LogOut.out(this, "InitDB finished!!!");
		} else {
			LogOut.out(this, "DBFile does exist.");
		}
	}

	public void openDBHelper() {
		sqlDb = getWritableDatabase();
	}

	public void close() {
		if (sqlDb != null) {
			sqlDb.close();
		}
		super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sqlTableBuilding = getTableBuildingSQL(GoodsBean.class);
		LogOut.out(this, "sql[" + sqlTableBuilding + "]");
		db.execSQL(sqlTableBuilding);
	}

	/** 根据类结构构造表。 */
	private String getTableBuildingSQL(Class<?> clazz) {
		StringBuilder strBuilder = new StringBuilder("create table if not exists ");
		strBuilder.append(clazz.getSimpleName());
		strBuilder.append("(");
		// getDeclaredFields():只获取该类文件中声明的字段
		// getFields():获取该类文件、其父类、接口的声明字段
		Field[] arrField = clazz.getFields();
		for (int i = arrField.length - 1; i >= 0; i--) {
			Field f = arrField[i];
			String type = TYPES.get(f.getType());
			if (type == null) {
				continue;
			} else {
				strBuilder.append(f.getName() + " " + type);
				if (f.isAnnotationPresent(primary.class)) {
					strBuilder.append(" PRIMARY KEY");
				}
				if (i > 0) {
					strBuilder.append(",");
				}
			}
		}
		strBuilder.append(")");
		return strBuilder.toString();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		LogOut.out(this, "onUpgrade");
		String sql = "drop table if exists" + GoodsBean.class.getSimpleName();
		db.execSQL(sql);
	}

	public int insert(GoodsBean bean) {
		int row = -1;
		row = (int) sqlDb.insert(bean.getClass().getSimpleName(), null, GoodsBean.translate2ContentValues(bean));
		LogOut.out(this, "row=" + row);
		return row;
	}

	public GoodsBean query() {
		Cursor cursor = sqlDb.rawQuery("select * from " + GoodsBean.class.getSimpleName(), null);
		GoodsBean bean = GoodsBean.cursor2GoodsBean(cursor);
		cursor.close();
		return bean;
	}
}