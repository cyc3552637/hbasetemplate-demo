package com.digital.hbase.dao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import com.digital.hbase.entity.HbaseModel;
import com.digital.login.service.LoginService;

@Repository("hbasedao")
public class HbaseDao {
//ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "application_hbase.xml" });  
//ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "conf/hbase/spring_hbase.xml" });  
//BeanFactory factory = (BeanFactory) context; 
//HbaseTemplate htemplate = (HbaseTemplate) factory.getBean("htemplate");
	@Resource(name = "htemplate")
	HbaseTemplate htemplate;

	
	public HbaseTemplate getHtemplate() {
		return htemplate;
	}
	public void setHtemplate(HbaseTemplate htemplate) {
		this.htemplate = htemplate;
	}


	Map hMap = new HashMap<String, List<HbaseModel>>();
	public String key;
	public String familyName ;
	public String qualifier;
	public String value;
		
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getFamilyName() {
		return familyName;
	}
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	public String getQualifier() {
		return qualifier;
	}
	public void setQualifier(String qualifier) {
		this.qualifier = qualifier;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	

	/**
	 * 写数据
	 * @param tableName
	 * @param action
	 * @return
	 */
	public Boolean execute(String tableName, TableCallback<Boolean> action) {
		return htemplate.execute(tableName, new TableCallback<Boolean>() {
            public Boolean doInTable(HTableInterface table) throws Throwable {
                boolean flag = false;
                try{
                	byte[] rowkey = key.getBytes();
                	Put put = new Put(rowkey);
                	put.add(Bytes.toBytes(familyName),Bytes.toBytes(qualifier), Bytes.toBytes(value));
                	table.put(put);
                 flag = true;
                }catch(Exception e){
                    e.printStackTrace();
                }
                return flag;
            }
        });
    }  
	 /**
	  * 通过表名和key获取一行数据
	  * @param tableName
	  * @param rowName
	  * @return
	  */
	public Map<String, Object> get(String tableName, String rowName) {
		 return htemplate.get(tableName, rowName,new RowMapper<Map<String,Object>>(){
	           public Map<String,Object> mapRow(Result result, int rowNum) throws Exception {	   
	        	   List<Cell> ceList =   result.listCells();
	        	   Map<String,Object> map = new HashMap<String, Object>();
	        	   		if(ceList!=null&&ceList.size()>0){
	        	   			for(Cell cell:ceList){
	        	   				map.put(Bytes.toString(cell.getFamilyArray(),cell.getFamilyOffset(),cell.getFamilyLength())+
	        					   "_"+Bytes.toString( cell.getQualifierArray(),cell.getQualifierOffset(),cell.getQualifierLength()), 
	        					   Bytes.toString( cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
	        		   }
	        	   }
					return  map;
	           }
		     });
	}
	
	/**
	 * 通过表名  key 和 列族 和列 获取一个数据
	 * @param tableName
	 * @param rowName
	 * @param familyName
	 * @param qualifier
	 * @return
	 */
	public String get(String tableName ,String rowName, String familyName, String qualifier) {
		  return htemplate.get(tableName, rowName,familyName,qualifier ,new RowMapper<String>(){
		         public String mapRow(Result result, int rowNum) throws Exception {	  
		        	 List<Cell> ceList =   result.listCells();
		        	 String res = "";
		        	 if(ceList!=null&&ceList.size()>0){
		        		 for(Cell cell:ceList){
		        			 res = Bytes.toString( cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
		        		 }
		        	 }
		      	   return res;
		         }
		  });
	}
	
	
	 /**
	  * 通过表名，开始行键和结束行键获取数据
	  * @param tableName
	  * @param startRow
	  * @param stopRow
	  * @return
	  */
	public List<Map<String,Object>> find(String tableName , String startRow,String stopRow) {
		 Scan scan = new Scan();
		 if(startRow==null){
			 startRow="";
		 }
		 if(stopRow==null){
			 stopRow="";	
		 }
		 scan.setStartRow(Bytes.toBytes(startRow));
		 scan.setStopRow(Bytes.toBytes(stopRow));
		/* PageFilter filter = new PageFilter(5);
		 scan.setFilter(filter);*/
		 return 	htemplate.find(tableName, scan,new RowMapper<Map<String,Object>>(){
	           public Map<String,Object> mapRow(Result result, int rowNum) throws Exception {	
	        	  
		        	 List<Cell> ceList =   result.listCells();
		        	 Map<String,Object> map = new HashMap<String,Object>();
		        	 Map<String,Map<String,Object>> returnMap = new HashMap<String,Map<String,Object>>();
		        	 String  row = "";
		        	 if(ceList!=null&&ceList.size()>0){
		        		   for(Cell cell:ceList){
							row =Bytes.toString( cell.getRowArray(), cell.getRowOffset(), cell.getRowLength());
							String value =Bytes.toString( cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
							String family =  Bytes.toString(cell.getFamilyArray(),cell.getFamilyOffset(),cell.getFamilyLength());
							String quali = Bytes.toString( cell.getQualifierArray(),cell.getQualifierOffset(),cell.getQualifierLength());
							map.put(family+"_"+quali, value);
		        		   }
		        		   map.put("row",row );
 		        	   }
		        	   return  map;
		           }
			     });
	}

	
	/* public  void scanValueByFilter(String tableName,String row) throws IOException{
         HTable table = new HTable(conf, tableName);  
         Scan scan = new Scan();
         scan.setFilter(new PrefixFilter(row.getBytes()));
         ResultScanner resultScanner = table.getScanner(scan);
         for(Result rs:resultScanner){
    
             
         }
         
     }*/
}
