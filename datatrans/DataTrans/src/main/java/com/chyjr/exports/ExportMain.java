package com.chyjr.exports;

import com.chyjr.configure.Config;
import com.chyjr.configure.ConnConfig;
import com.chyjr.configure.DataTransCfg;
import com.chyjr.configure.Table;
import com.chyjr.connection.JDBCConnection;
import com.chyjr.imports.ImportMain;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.util.*;
import java.util.regex.Pattern;

public class ExportMain {

    private static String isPass = "isPass";
    private static String yes = "yes";
    private static String no = "no";
    private static String value = "value";
    public static int code = 6800000;
    public static Map<String,String> numMap = new HashMap<String,String>();

    static {
        numMap.put("一","1");
        numMap.put("二","2");
        numMap.put("三","3");
        numMap.put("四","4");
        numMap.put("五","5");
        numMap.put("六","6");
        numMap.put("七","7");
        numMap.put("八","8");
        numMap.put("九","9");
        numMap.put("十","10");
    }

    public static synchronized int getResumeCode() {
        return code++;
    }

    public static void main(String[] args) {
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        Connection queryConnection = null;
        try {
            String configDir = "F:\\ideaWorkspace\\project\\web\\datatrans\\DataTrans\\src\\main\\resources\\";
            fos = new FileOutputStream(configDir + "resume_data.sql");
            bos = new BufferedOutputStream(fos);
            DataTransCfg cfg = Config.initConfig(configDir + "mapping.xml");
            Map<String,String> industryMapping = IndustryMappingUtils.getIndustryMapping(configDir + "industryMapping.xml");

            ConnConfig connFrom = cfg.getConnform();
            JDBCConnection.init(connFrom.getUrl(),connFrom.getUser(),connFrom.getPassword());
            queryConnection = JDBCConnection.getConnection();
            Table baseInfocfg = cfg.getTables().get(0);
            String str = " and username != '' and username IS not NULL AND workyears != '' AND workyears IS NOT NULL";
            List<Map<String, String>> baseInfoList = ExportMysql.query(baseInfocfg, queryConnection, str);
            Long startTime = System.currentTimeMillis();
            int num = 0;
            for (Map<String, String> map : baseInfoList) { //所有的简历基本信息
                if(num > 100){
                    break;
                }
                if("沈秋元".equals(map.get("username"))){
                    continue;
                }
                String resumeId = getResumeCode() + "";
                String resumeCode = map.get("resumeid");
                String appendStr = "  and resumecode='" + resumeCode + "'";

                Table jobTable = cfg.getTables().get(1);
                Map<String, String> jobMap = ExportMysql.query(jobTable, queryConnection, appendStr).get(0);
                jobMapFilter(jobMap,industryMapping,queryConnection);
                if(StringUtils.isBlank(jobMap.get("postIDs"))){
                    continue;
                }
                System.out.println(jobMap.get("postIDs"));

                bos.write(getInsertSql(baseInfocfg.getTotabname(), map, resumeId).getBytes());
                bos.write(getInsertSql(jobTable.getTotabname(), jobMap, resumeId).getBytes());
                num++;

                for (int i = 2; i < cfg.getTables().size(); i++) {
                    Table table = cfg.getTables().get(i);
                    String tableName = table.getTotabname();

                    List<Map<String, String>> itemMap = ExportMysql.query(table, queryConnection, appendStr);
                    for (Map<String, String> item : itemMap) {
                        bos.write(getInsertSql(tableName, item, resumeId).getBytes());
                    }
                }
            }
            Long endTime = System.currentTimeMillis();
            long time = (endTime - startTime) / 1000;
            System.out.println("导出完整简历 " + num + "份，所需时间 " + time + " 秒!");
            bos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error error) {
            error.printStackTrace();
        } finally {
            try {
                if (null != fos) {
                    fos.close();
                }
                if (null != bos) {
                    bos.close();
                }
                if (queryConnection != null) {
                    queryConnection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static String getInsertSql(String tableName, Map<String, String> map, String resumeId) {
        map = mapFilter(tableName, map);
        StringBuffer insertBuffer = new StringBuffer();
        insertBuffer.append("insert into ");
        insertBuffer.append(tableName);
        insertBuffer.append("(");
        insertBuffer.append(" ");

        StringBuffer valueBuffer = new StringBuffer();
        valueBuffer.append(" values(");

        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = getSqlStr(map.get(key));
            if (key.equals("resumeid")) {
                value = resumeId;
            }
            if (key.equals("height")) {
                value = value.replaceAll("cm", "").replaceAll("CM", "").replaceAll("米", "").replaceAll("'", "");
                if(StringUtils.isBlank(value)){
                    value = null;
                }
            }

            insertBuffer.append(key + ",");
            valueBuffer.append(value + ",");
        }
        insertBuffer.append("createby,createdate)");
        valueBuffer.append("'sysadmin',now());");
        valueBuffer.append("\n");
        return insertBuffer.append(valueBuffer).toString();
    }

    public static String getSqlStr(String str) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("'");
        if (StringUtils.isNotBlank(str)) {
            buffer.append(str.replace("'","").replaceAll(";","").replaceAll("\\(","").replaceAll("\\)",""));
        }
        buffer.append("'");
        return buffer.toString();
    }

    public static Map<String, String> mapFilter(String tableName, Map<String, String> map) {
        if ("workexperienceinfo".equals(tableName)) {
            map.put("workid", "" + getResumeCode());
        } else if ("educationinfo".equals(tableName)) {
            map.put("educationid", "" + getResumeCode());
        } else if ("traininfo".equals(tableName)) {
            map.put("trainid", "" + getResumeCode());
        } else if ("jobintentinfo".equals(tableName)) {
            map.put("jobIntentCode", "" + getResumeCode());
        } else if ("languageinfo".equals(tableName)) {
            map.put("languageid", "" + getResumeCode());
        } else if ("otherinfo".equals(tableName)) {
            map.put("otherid", "" + getResumeCode());
        } else if ("projectinfo".equals(tableName)) {
            map.put("projectid", "" + getResumeCode());
        } else if ("certinfo".equals(tableName)) {
            map.put("certid", "" + getResumeCode());
        }
        return map;
    }

    private static void jobMapFilter(Map<String,String> map,Map<String,String> industryMapping,Connection connection){
        String postIDs = map.get("postIDs");
        Iterator<String> iterator = industryMapping.keySet().iterator();
        StringBuffer buffer = new StringBuffer("");
        Map<String,String> industryMap = new HashMap<String,String>();
        if(StringUtils.isNotBlank(postIDs)){
            while (iterator.hasNext()){
                String key = iterator.next();
                if(postIDs.contains(key)){
                    industryMap.put(industryMapping.get(key),industryMapping.get(key));
                }
            }
        }
        String realPostIds = ExportMysql.parseIndustryFromMap(industryMap);
        if(StringUtils.isNotBlank(buffer)){
            realPostIds = buffer.substring(0,buffer.length() - 1);
        }
        if(StringUtils.isBlank(realPostIds)){
            String resumeCode = map.get("resumeid");
            realPostIds = ExportMysql.queryIndustryFromOtherInfo(resumeCode,industryMapping,connection);
        }
        map.put("postIDs",realPostIds);
    }

    private static Map<String,String> getWorkYearsInfo(String originalValue){
        Map<String,String> map = new HashMap<String,String>();
        if(StringUtils.isBlank(originalValue) || originalValue.contains("应届") || originalValue.contains("在读") || !originalValue.contains("年")){
            map.put(isPass,no);
        }
        String str = originalValue.replaceAll("年","").replaceAll("工作经验", "").replaceAll("以", "").replaceAll("上", "").replaceAll(" ","");
        if(!isNumeric(str)){
            str = numMap.get(str);
        }
        if(!isNumeric(str)){

        }
        return map;
    }

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }


}
