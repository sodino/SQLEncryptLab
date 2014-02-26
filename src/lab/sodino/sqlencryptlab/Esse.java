package lab.sodino.sqlencryptlab;
/**
 * @author Sodino E-mail:sodino@qq.com
 * @version Time：2014年1月13日 下午4:10:09
 */
public abstract class Esse {
	/**唯一的key.*/
	int _id = -1;
	
	public String getSimpleName(){
		return getClass().getSimpleName();
	}
}
