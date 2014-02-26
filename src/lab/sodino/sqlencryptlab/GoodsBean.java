package lab.sodino.sqlencryptlab;

import java.lang.reflect.Field;

import lab.sodino.util.LogOut;
import android.content.ContentValues;
import android.database.Cursor;

public class GoodsBean {
	@primary
	/** 标明了是主键*/
	public long _id;
	public String name;
	public int price;
	public boolean isPaid;
	/** 测试用。 */
	public byte testByte;
	public byte[] arrByte;
	/** 测试用。 */
	public short testShort;
	public float cicle;
	public double testDouble;

	public String toString() {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("_id[" + _id + "]\n");
		strBuffer.append("name[" + name + "]\n");
		strBuffer.append("price[" + price + "]\n");
		strBuffer.append("isPaid[" + isPaid + "]\n");
		strBuffer.append("cicle[" + cicle + "]\n");
		strBuffer.append("testByte[" + testByte + "]\n");
		strBuffer.append("arrByte.len[" + (arrByte == null ? "N/A" : arrByte.length) + "]\n");
		strBuffer.append("testShort[" + testShort + "]\n");
		strBuffer.append("testDouble[" + testDouble + "]\n");
		return strBuffer.toString();
	}

	public static GoodsBean newInstance() {
		GoodsBean bean = new GoodsBean();
		bean._id = 128l;
		bean.name = "AutoSave";
		bean.price = 1024;
		bean.isPaid = true;
		bean.cicle = 2.356f;
		bean.arrByte = new String("SodinoArrBytes").getBytes();
		bean.testByte = 8;
		bean.testShort = 128;
		bean.testDouble = 9856.2145d;
		return bean;
	}

	public static ContentValues translate2ContentValues(GoodsBean bean) {
		ContentValues cv = new ContentValues();

		Field[] arrField = GoodsBean.class.getFields();
		try {
			for (Field f : arrField) {
				if (f.isAccessible() == false) {
					f.setAccessible(true);
				}
				String name = f.getName();
				Object value = f.get(bean);
				LogOut.out(GoodsBean.class.getName(), "name:" + name + " " + String.valueOf(value));
				if (value instanceof Byte) {
					cv.put(name, (Byte) value);
				} else if (value instanceof Short) {
					cv.put(name, (Short) value);
				} else if (value instanceof Integer) {
					cv.put(name, (Integer) value);
				} else if (value instanceof Long) {
					cv.put(name, (Long) value);
				} else if (value instanceof String) {
					cv.put(name, (String) value);
				} else if (value instanceof byte[]) {
					cv.put(name, (byte[]) value);
				} else if (value instanceof Boolean) {
					cv.put(name, (Boolean) value);
				} else if (value instanceof Float) {
					cv.put(name, (Float) value);
				} else if (value instanceof Double) {
					cv.put(name, (Double) value);
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return cv;
	}

	public static GoodsBean cursor2GoodsBean(Cursor cursor) {
		GoodsBean bean = new GoodsBean();
		if (cursor.isBeforeFirst()) {
			cursor.moveToFirst();
		}
		Field[] arrField = GoodsBean.class.getFields();
		try {
			for (Field f : arrField) {
				String columnName = f.getName();
				int columnIdx = cursor.getColumnIndex(columnName);
				if (columnIdx != -1) {
					if (f.isAccessible()) {
						f.setAccessible(true);
					}
					Class<?> type = f.getType();
					if (type == byte.class) {
						f.set(bean, (byte) cursor.getShort(columnIdx));
					} else if (type == short.class) {
						f.set(bean, cursor.getShort(columnIdx));
					} else if (type == int.class) {
						f.set(bean, cursor.getInt(columnIdx));
					} else if (type == long.class) {
						f.set(bean, cursor.getLong(columnIdx));
					} else if (type == String.class) {
						f.set(bean, cursor.getString(columnIdx));
					} else if (type == byte[].class) {
						f.set(bean, cursor.getBlob(columnIdx));
					} else if (type == boolean.class) {
						f.set(bean, cursor.getInt(columnIdx) == 1);
					} else if (type == float.class) {
						f.set(bean, cursor.getFloat(columnIdx));
					} else if (type == double.class) {
						f.set(bean, cursor.getDouble(columnIdx));
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return bean;
	}
}