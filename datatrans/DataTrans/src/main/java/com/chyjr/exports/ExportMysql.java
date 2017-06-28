package com.chyjr.exports;

import java.io.BufferedOutputStream;
import java.sql.*;
import java.util.*;

import com.chyjr.configure.DataTransCfg;
import com.chyjr.configure.Field;
import com.chyjr.configure.Table;
import com.chyjr.connection.JDBCConnection;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

public class ExportMysql {

    private int start = 0;
    private int size = 0;
    private java.sql.Connection connection = null;
    private DataTransCfg cfg;

    public ExportMysql(java.sql.Connection connection, int start, int size, DataTransCfg cfg) {
        this.size = size;
        this.start = start;
        this.connection = connection;
        this.cfg = cfg;
    }

    public static List<Map<String, String>> query(Table table, Connection connection,String append) {
        Field[] fields = table.getFields();
        StringBuilder sql = new StringBuilder();
        sql.append("select ");
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].getFrom().length() > 0) {
                sql.append(fields[i].getFrom());
                sql.append(",");
            }
        }
        sql.delete(sql.length() - 1, sql.length());
        sql.append(" from " + table.getFromtabname() + " where activeFlag = '1'");
        if(StringUtils.isNotBlank(append)){
            sql.append(" " + append);
        }
        Statement statement;
        try {
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql.toString());
            List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
            while (result.next()) {
                Map<String, String> map = new HashMap<String, String>();
                for (int i = 0; i < fields.length; i++) {
                    if (fields[i].getFrom().length() > 0){
                        map.put(fields[i].getTo(), result.getString(fields[i].getFrom()));
                    }
                }
                mapList.add(map);
            }
            result.close();
            return mapList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 如果期望行业为空时，才应该调用本方法
     * 从工作经历、项目经验
     * @param resumeCode
     * @param industryMapping
     */
    public static String queryIndustryFromOtherInfo(String resumeCode,Map<String,String> industryMapping,Connection connection){
        //搜索工作经历
        Map<String,String> myMap = new HashMap<String,String>();
        String workSql = "SELECT corpname,industry,department,postcode FROM workexperienceinfo WHERE resumecode = '" + resumeCode +
                                "' AND corpname != '' AND corpname IS NOT NULL";
        List<Map<String,String>> workList = executeQuerySql(workSql,connection);
        if(null == workList || workList.size() == 0){
            return null;
        }
        parseIndustry(industryMapping, myMap, workList);
        if(!myMap.isEmpty()){
            return parseIndustryFromMap(myMap);
        }

        //搜索项目经验
        String projectSql = "SELECT projectname FROM projectexperienceinfo WHERE resumecode = '" + resumeCode + "';";
        List<Map<String,String>> projectList = executeQuerySql(projectSql,connection);
        parseIndustry(industryMapping, myMap, projectList);
        if(!myMap.isEmpty()){
            return parseIndustryFromMap(myMap);
        }
        return "";
    }

    private static void parseIndustry(Map<String, String> industryMapping, Map<String, String> myMap, List<Map<String, String>> list) {
        for(Map<String,String> map : list){
            Iterator<String> workKeyIterator = map.keySet().iterator();
            while (workKeyIterator.hasNext()){
                String value = map.get(workKeyIterator.next());
                Iterator<String> iterator = industryMapping.keySet().iterator();
                while (iterator.hasNext()){
                    String key = iterator.next();
                    if(value.contains(key)){
                        myMap.put(industryMapping.get(key),industryMapping.get(key));
                    }
                }
            }
        }
    }

    public static String parseIndustryFromMap(Map<String,String> map){
        if(map.isEmpty()){
            return "";
        }
        Iterator<String> iterator = map.keySet().iterator();
        StringBuffer buffer = new StringBuffer();
        while (iterator.hasNext()){
            buffer.append(iterator.next());
            buffer.append("、");
        }
        return buffer.substring(0,buffer.length() - 1);
    }

    private static List<Map<String,String>> executeQuerySql(String sql,Connection connection){
        Statement statement;
        try{
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql.toString());
            ResultSetMetaData metaData = result.getMetaData();
            int columnCount = metaData.getColumnCount();
            List<String> labelList = new ArrayList<String>();
            for(int i = 0;i<columnCount;i++){
                int num = i+1;
                labelList.add(metaData.getColumnLabel(num));
            }

            List<Map<String,String>> list = new ArrayList<Map<String,String>>();
            while (result.next()) {
                Map<String,String> map = new HashMap<String,String>();
                for(String label : labelList){
                    map.put(label,result.getString(label));
                }
                list.add(map);
            }
            result.close();
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String args[])throws Exception{
        /*String url = "jdbc:mysql://localhost:3306/resume";
        String username = "root";
        String password = "wanglulu";
        JDBCConnection.init(url,username,password);;
        Connection conn = JDBCConnection.getConnection();*/
        //Map<String,String> industryMapping = IndustryMappingUtils.getIndustryMapping("F:\\ideaWorkspace\\project\\web\\datatrans\\DataTrans\\src\\main\\resources\\industryMapping.xml");
        //String str = queryIndustryFromOtherInfo("550834", industryMapping, conn);

        System.out.println(ExportMain.isNumeric("五"));
    }

}
