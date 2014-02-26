package lab.sodino.sqlencryptlab;
/**
 * @author Sodino E-mail:sodino@qq.com
 * @version Time：2014年1月13日 下午3:53:21
 */
public class TxtBean {
	@primary
	/** 标明了是主键*/
	public long _id;
	public String txtStr;
	public static TxtBean newInstance() {
		TxtBean bean = new TxtBean();
		bean._id = 123;
		bean.txtStr = "!!QQQddd";
		return bean;
	}
}
