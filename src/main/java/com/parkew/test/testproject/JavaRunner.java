package com.parkew.test.testproject;

import com.parkew.test.testproject.postgretest.dto.ServiceResDto;
import org.apache.commons.lang.RandomStringUtils;

import java.io.*;
import java.sql.*;

/**
 * @auther parkew
 * @sinse 2023.09.12
 */
public class JavaRunner {

    public static void main(String[] args) {
//        Utils.createRandomTextFile(5242880, "/Users/parkew/dev/dev_utils/", "5mb.txt");
        insert();
    }

    public static void insert(){
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;
        try {
            Class.forName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
            conn = DriverManager.getConnection("jdbc:log4jdbc:postgresql://127.0.0.1:5432/postgres?charSet=UTF-8", "postgres", "postgres");
            conn.setAutoCommit(false);
            preStmt = conn.prepareStatement("INSERT INTO public.PT_SQL_TEST_TEXT(SQL_ID, SID, SQL_TYPE, SQL_SIZE, SQL_TEXT, RESERVED) VALUES (?, ?, ?, ?, ?, ?)");

            String filePath = "/Users/parkew/dev/dev_utils/800mb.txt"; // 읽어올 텍스트 파일 경로
            String content = "";
            try (
                    BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    // 각 줄의 내용을 사용하거나 출력할 수 있습니다.
                    content+= line;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            preStmt.setLong(1, 1);
            preStmt.setLong(2, 1);
            preStmt.setString(3, "SELECT");
            preStmt.setInt(4, 1073741824);
            preStmt.setString(5, content);
            preStmt.setString(6, "");
            preStmt.addBatch();
            preStmt.clearParameters();
            preStmt.executeBatch();
            preStmt.clearBatch();
            conn.commit();
            preStmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(conn != null && !conn.isClosed()) {
                    conn.close();
                }
                if(stmt != null && !stmt.isClosed()) {
                    stmt.close();
                }
                if(preStmt != null && !preStmt.isClosed()) {
                    preStmt.close();
                }
                if(rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

}
