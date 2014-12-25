/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu  
 */  
package com.soledede.classfy.bayes.quartz;  

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.soledede.classfy.bayes.util.BayesConfigUtil;
  
  
/**  
 *@Title:  
 *@Description:  
 *@Author:wengbenjue  
 *@Since:2014年7月16日  
 *@Version:1.1.0  
 */
public class SpringQuartzClearMapSession extends QuartzJobBean{
	/*业务实现*/
    public void work() {
    	//清空session
    	BayesConfigUtil.questionAnswerQuene.clear();
    	BayesConfigUtil.sessionTempNodeCacheList.clear();
    	BayesConfigUtil.lastSessionInputSum.clear();
    }

    @Override
    protected void executeInternal(JobExecutionContext arg0)
            throws JobExecutionException {
        this.work();
    }
}
