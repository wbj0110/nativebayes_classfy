import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.soledede.classfy.bayes.entity.Dictiona;
import com.soledede.classfy.bayes.service.DicService;

/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu  
 */

/**  
 *@Title:  
 *@Description:  
 *@Author:wengbenjue  
 *@Since:2014年7月11日  
 *@Version:1.1.0  
 */
@Transactional  
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)  
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations={"classpath:spring-hibernate.xml"})  
public class DicTest  
{  
    @Autowired 
    private DicService dicService;  
  
    @Before  
    public void setUp() throws Exception  
    {  
    }  
  
    @Test
    public void save(){
    	Dictiona d = new Dictiona();
    	d.setCityId(1);
    	d.setWord("长宁");
    	try {
			dicService.saveDic(d);
		} catch (Exception e) {
			// TODO Auto-generated catch block  
			e.printStackTrace();
		}
    }
    
    @Test  
    public void testMyDao()  
    {  
        try  
        {  
        	List<Dictiona> list = dicService.findByTagTypeId(9);
        	
        	System.out.println(list.size()+"########################################");
        	List<Dictiona> list1 = dicService.findByCityId(4);
        	System.out.println(list1.size()+"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        	
        	List<Dictiona> list2 = dicService.findByCityIdOrTagTypeId(4, 9);
        	System.out.println(list2.size()+"^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        }  
        catch (Exception e)  
        {  
        	e.printStackTrace();
            fail("Test failed!");  
        }  
    }  
  
}  