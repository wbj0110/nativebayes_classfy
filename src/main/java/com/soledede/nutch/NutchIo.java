/**  
 *@Copyright:Copyright (c) 2008 - 2100  
 *@Company:xiaomishu  
 */  
package com.soledede.nutch;  

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Random;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;

import com.soledede.classfy.bayes.controller.BayesChatController;
  
  
/**  
 *@Title:  
 *@Description:  
 *@Author:wengbenjue  
 *@Since:2014年8月20日  
 *@Version:1.1.0  
 */
public class NutchIo {
	private static final Logger log = Logger
			.getLogger(BayesChatController.class);
	
	//static String filename = "D:\\dump";
	static String filename = "/home/storm/solr_test/dump";
	private static final String patch ="20140821000529o_dump";
	
	
	private static final String DEFAULT_URL = "http://192.168.1.230/solr/";
    private static HttpSolrServer server;
    private static final int throftdocnum = 10;
    private static final int existWords=2839070;  //27000 1232700;
    private static final int existDocment=283803 ;//270 17682
    
    static Integer i =0;
    static Integer j =0;
    static String word = "";
    static Integer docnum = 0;
	
	public static void main(String[] args) {
		server = new HttpSolrServer(DEFAULT_URL);
		Random random = new Random(100);
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_43);
		readFileByLines(filename,analyzer,random);
		analyzer.close();
		server = null;
        System.runFinalization();
        System.gc();
	}

	 /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static void readFileByLines(String fileName,Analyzer analyzer,Random random) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
          
            while ((tempString = reader.readLine()) != null) {
            	//读取每一行，并进行分词
        		cutWordsForSingle(analyzer,tempString,random);
            }
            
            reader.close();
            System.out.println();
			if(j%throftdocnum!=0) j=j-(j%throftdocnum);
		
			 log.info("Total words: " +(j+existWords) +" docnum: "+(docnum+existDocment));
			System.out.println("Total words: " +(j+existWords) +" docnum: "+(docnum+existDocment));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }
	
	/**  
	 * @param analyzer  
	 * @Description:  
	 */
	private static void cutWordsForSingle(Analyzer analyzer,String inputs,Random random) {
		try {
		TokenStream ts = analyzer.tokenStream("text", new StringReader(
				inputs));
		CharTermAttribute termAtt = ts.addAttribute(CharTermAttribute.class);
		ts.reset();
			
			while (ts.incrementToken()) {
				if (termAtt.length() > 0) {
					word += ts.getAttribute(CharTermAttribute.class)
							.toString();
					word += "|"+(random.nextFloat()+10)+" ";
					//拼接 word|23.3 word1|43.4

					i++;
					//添加到索引
					if(i>=throftdocnum){//100次一个文档
						i = 0;
						docnum ++;
						//System.out.println(word);
						addDoc(docnum+patch,word);
						word = "";
					}
					j++;

				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block  
			e.printStackTrace();
		}
	}
	
	 public static void addDoc(String docnum,String payloads) {
	        // 创建doc文档
	        SolrInputDocument doc = new SolrInputDocument();
	        doc.addField("id", docnum);
	        doc.addField("payloads", payloads);
	        System.out.println();
	        System.out.println("docnum: "+docnum);
	        System.out.println("payloads: "+payloads);
	        log.info("docnum: "+docnum+"\tpayloads:"+payloads+"\twordcount:"+(j+existWords));
	        try {
	            // 添加一个doc文档
	            UpdateResponse response = server.add(doc);
	            // commit后才保存到索引库
	            server.commit();
	            
	           /* System.out.println("Query Time：" + response.getQTime());
	            System.out.println("Elapsed Time：" + response.getElapsedTime());
	            System.out.println("Status：" + response.getStatus());*/
	 
	        } catch (SolrServerException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	 
	 
}
