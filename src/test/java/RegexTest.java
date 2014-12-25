/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu  
 */

/**  
 *@Title:  
 *@Description:  
 *@Author:wengbenjue  
 *@Since:2014年7月31日  
 *@Version:1.1.0  
 */
public class RegexTest {

	/**  
	 * @param args  
	 * @Description:  
	 */
	public static void main(String[] args) {
		System.out.println("sdfsdf,中国?你好.你说什么？?哎!".replaceAll("\\,", "，").replaceAll("\\.", "。").replaceAll("\\?", "？").replaceAll("\\!", "！"));
	}

}
