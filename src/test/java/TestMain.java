import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu  
 */

/**  
 *@Title:  
 *@Description:  
 *@Author:wengbenjue  
 *@Since:2014年7月16日  
 *@Version:1.1.0  
 */
public class TestMain {

	/**  
	 * @param args  
	 * @Description:  
	 */
	public static void main(String[] args) {
		//System.out.println(System.getProperties().getProperty("os.name"));
		Pattern p = Pattern.compile("^Window|window|WINDOW+\\*");
		Matcher m =p.matcher("Windows7");
		if(m.find()){
			System.out.println(m.group());
		}
	}

}
