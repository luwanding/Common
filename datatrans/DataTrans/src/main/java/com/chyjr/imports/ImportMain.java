package com.chyjr.imports;

import com.chyjr.connection.JDBCInsertConnection;

import java.sql.Connection;
import java.util.List;

public class ImportMain {

    public static void insert(List<String> list){
        Connection insertConn = null;
        String errorSql = "";
        try{
            insertConn = JDBCInsertConnection.getConnection();
            java.sql.Statement statement = insertConn.createStatement();
            for(String sql : list){
                statement.executeUpdate(sql);
            }
            statement.executeBatch();
        }catch (Exception e){
            System.out.println("****************");
            e.printStackTrace();
            System.out.println("****************");

        }finally {
            try{
                if(null != insertConn){
                    insertConn.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
