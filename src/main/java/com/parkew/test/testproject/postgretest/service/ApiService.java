package com.parkew.test.testproject.postgretest.service;

import com.parkew.test.testproject.Logger;
import com.parkew.test.testproject.Utils;
import com.parkew.test.testproject.postgretest.dto.ControllerReqDto;
import com.parkew.test.testproject.postgretest.dto.ServiceResDto;
import com.parkew.test.testproject.postgretest.vo.PtSeparateTestVo;
import com.parkew.test.testproject.postgretest.vo.PtSessTestVo;
import com.parkew.test.testproject.postgretest.vo.PtSqlTestVo;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @auther parkew
 * @sinse 2023.09.12
 */
@Service
public class ApiService {

    private static Map<Integer, String> userInfo = new HashMap<>();
    private static Map<Integer, String> sqlTypeInfo = new HashMap<>();
    private static Map<Integer, String> sqlSeparateInfo = new HashMap<>();

    private void mapFill(){
        userInfo.put(1, "parkew,박은우,WEB-UI 백엔드 개발,5,010-1234-5678,SINSIWAY,RND,WEB-UI,File Cipher,BACKEND,1,Linux,sqlplus.exe,ORACLE,192.168.13.88,1521,ORA10R2,ORCL");
        userInfo.put(2, "cgpark,박찬규,WEB-UI 백엔드 개발,2,010-1234-8765,SINSIWAY,RND,WEB-UI,Cipher,BACKEND,2,Window11,sqlpreter.exe,SOHA,192.168.13.18,1521,ORA10R2,ORCL");
        userInfo.put(3, "jmsong,송지민,WEB-UI 백엔드 개발,2,010-1234-4321,SINSIWAY,RND,WEB-UI,Sign,BACKEND,3,Window11,sqlpreter.exe,SOHA,192.168.13.82,1521,ORA10R2,ORCL");
        userInfo.put(4, "hskim1,김희수,WEB-UI 프론트 개발,5,010-1234-1212,SINSIWAY,RND,WEB-UI,File Cipher,BACKEND,4,Window10,sqlplus.exe,ORACLE,192.168.13.38,1521,ORA10R2,ORCL");
        userInfo.put(5, "sjchoi,최서진,WEB-UI 프론트 개발,3,010-1234-3131,SINSIWAY,RND,WEB-UI,Cipher,BACKEND,5,Window10,sqlplus.exe,ORACLE,192.168.13.22,1521,ORA10R2,ORCL");
        userInfo.put(6, "sechoi,최서은,WEB-UI 프론트 개발,2,010-1234-5252,SINSIWAY,RND,WEB-UI,Sign,BACKEND,6,Window11,sqlpreter.exe,SOHA,192.168.13.2,1521,ORA10R2,XE");
        userInfo.put(7, "jako,고진아,WEB-UI 팀장,10,010-1234-8585,SINSIWAY,RND,WEB-UI,Petra,Team Leader,7,Window7,sqlplus.exe,MongoDB,192.168.13.12,1521,monTest,md");
        userInfo.put(8, "yjjeong,정영재,클라우드 백엔드 개발,5,010-1234-1234,SINSIWAY,RND,Cloud Dev,Cloud Petra,BACKEND,8,MacOs,sqlpreter.exe,SOHA,192.168.13.3,1521,MASTER,dgadmin");
        userInfo.put(9, "jeongyj,정연재,클라우드 백엔드 개발,2,010-1234-8888,SINSIWAY,RND,Cloud Dev,Cloud Petra,BACKEND,9,MacOs,sqlpreter.exe,SOHA,192.168.13.95,1521,LOGSVR,dgadmin");
        userInfo.put(10, "dhlim,임다혜,클라우드 백엔드 개발,4,010-1234-5465,SINSIWAY,RND,Cloud Dev,Cloud Petra,BACKEND,10,Window10,sqlplus.exe,ORACLE,192.168.13.65,1521,ORA10R2,ORCL");
        userInfo.put(11, "jhpark,박종한,클라우드 팀장,10,010-1234-7432,SINSIWAY,RND,Cloud Dev,Petra4,Team Leader,11,Linux,mysql.exe,MySQL,192.168.13.89,1521,test-server,jhparkmysql");
        userInfo.put(12, "gdhong,홍길동,SinsiOs 팀장,10,010-1234-9154,SINSIWAY,ACCOUNT,SinsiOs,SinsiOs,Team Leader,12,Window10,postgres.exe,PostgreSql,192.168.13.120,5432,postgres,postgres");
        userInfo.put(13, "cskim,김철수,SinsiOs 프론트 개발,5,010-1234-3465,SINSIWAY,ACCOUNT,SinsiOs,SinsiOs,FRONTEND,13,Window10,postgres.exe,PostgreSql,192.168.13.132,5432,postgres,postgres");
        userInfo.put(14, "yhkim,김영희,SinsiOs 백엔드 개발,4,010-1234-1597,SINSIWAY,ACCOUNT,SinsiOs,SinsiOs,BACKEND,14,MacOs,postgres.exe,PostgreSql,192.168.13.165,5432,postgres,postgres");
        userInfo.put(15, "dhkim,김두한,SinsiOs 백엔드 개발,3,010-1234-6485,SINSIWAY,ACCOUNT,SinsiOs,SinsiOs,BACKEND,15,Linux,postgres.exe,PostgreSql,192.168.13.179,5432,postgres,postgres");

        sqlTypeInfo.put(1, "CREATE");
        sqlTypeInfo.put(2, "INSERT");
        sqlTypeInfo.put(3, "DELETE");
        sqlTypeInfo.put(4, "UPDATE");
        sqlTypeInfo.put(5, "SELECT");

        sqlSeparateInfo.put(1, "젊음은 인생의 한 시기가 아니요, 마음의 상태이다. 장밋빛 불과 붉은 입술, 유연한 무릎이 아니라 의지와 풍부한 상상력과 활기찬 감정에 달려 있다. 젊음이란 기질이 소심하기보다는 용기에 넘치고, 수월함을 좋아하기보다는 모험을 좇는 것이고 이는 스무 살 청년에게도, 예순 노인에게도 있다. 단지 나이를 먹는다고 늙는 것은 아니다. 그대와 나의 가슴 한가운데에는 무선국이 있다. 그것이 사람들로부터 또는 하늘로부터 아름다움과 희망과 활기, 용기와 힘의 메시지를 수신하는 한, 그대는 영원히 젊으리라.");
        sqlSeparateInfo.put(2, "\"나이는 숫자에 불과하다\"는 어느 광고 카피가 생각납니다.");
        sqlSeparateInfo.put(3, "옛날 날마다 내일은 오늘과 다르길 바라며 살아가는 한 아이가 있었습니다.");
        sqlSeparateInfo.put(4, "소나기 한번 내리지 않고 바람 한 줄기 없이 햇볕만 가득한 날씨, 소음 하나 없이 아름다운 음악 소리만 가득한 세상, 늘 행복해서 언제나 미소 짓는 사람들만 있는 세상, 걱정거리 하나 없고 미워할 사람 하나 없고 훌륭한 사람들만 가득한 세상, 그런 세상이 꼭 좋은 것만은 아닐지도 모릅니다.");
        sqlSeparateInfo.put(5, "슬픔을 알기에 행복의 의미도 알고, 죽음이 있어서 생명의 귀함을 알게 되지요.");
        sqlSeparateInfo.put(6, "실연의 고통이 있기 때문에 사랑이 더욱 값지고, 눈물이 있기 때문에 웃는 얼굴이 더욱 눈부시지 않은가요.");
        sqlSeparateInfo.put(7, "그래, 삶은 하나의 길을 따라가는 여정이다. 시 속의 화자는 두 갈래 길을 만났지만, 너희들 앞에는 수십 갈래, 수백 갈래 길이 있다. 군중을 따라가지 말고, 사람이 적게 다녀도 정말로 가치 있고 진정 너희들이 좋아할 수 있는 길을 택해라.");

    }

    public ServiceResDto apiService(ControllerReqDto controllerReqDto) {
        mapFill();

        Connection conn = null;
        Statement stmt = null;
        PreparedStatement preStmt = null;
        ResultSet rs = null;

        try {
            Class.forName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");

            if(controllerReqDto.isBatchFlag()) {
                if(Utils.isEqualText(controllerReqDto.getSqlType(), "insert1")) {
                    conn = DriverManager.getConnection(controllerReqDto.getUrl(), controllerReqDto.getUser(), controllerReqDto.getPassword());
                    conn.setAutoCommit(false);


                    String insertSql = "INSERT INTO public.PT_SESS_TEST(SID, USER_ID, USER_NAME, USER_ROLE, USER_LEVEL, USER_PHONE, ORG_ONE_NAME, ORG_TWO_NAME, ORG_THREE_NAME, ORG_FOUR_NAME, ORG_FIVE_NAME, DB_ID, DB_OS_TYPE, DB_PROGRAM, DB_TYPE, DB_IP, DB_PORT, DB_INSTANCE, DB_SCHEMA, RESERVED) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    if(Utils.isEqualText(controllerReqDto.getTableName(), "pt_sess_test_soha")){
                        insertSql = "INSERT INTO PT_SESS_TEST(SID, USER_ID, USER_NAME, USER_ROLE, USER_LEVEL, USER_PHONE, ORG_ONE_NAME, ORG_TWO_NAME, ORG_THREE_NAME, ORG_FOUR_NAME, ORG_FIVE_NAME, DB_ID, DB_OS_TYPE, DB_PROGRAM, DB_TYPE, DB_IP, DB_PORT, DB_INSTANCE, DB_SCHEMA, RESERVED) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    }
                    Logger.info("sql: "+insertSql);

                    preStmt = conn.prepareStatement(insertSql);
                    long startJdbcTime = System.currentTimeMillis();
                    int idx = controllerReqDto.getIdx();
                    for(int i = 1; i <= idx; i++) {
                        int infoIdx = Utils.makeRandomInteger(15);
                        String str = String.valueOf(userInfo.get(Integer.valueOf(infoIdx)));
                        String[] strSplit = str.split(",");

                        preStmt.setLong(1, i);
                        preStmt.setString(2, strSplit[0]);
                        preStmt.setString(3, strSplit[1]);
                        preStmt.setString(4, strSplit[2]);
                        preStmt.setInt(5, Integer.valueOf(strSplit[3]));
                        preStmt.setString(6, strSplit[4]);
                        preStmt.setString(7, strSplit[5]);
                        preStmt.setString(8, strSplit[6]);
                        preStmt.setString(9, strSplit[7]);
                        preStmt.setString(10, strSplit[8]);
                        preStmt.setString(11, strSplit[9]);
                        preStmt.setLong(12, Long.valueOf(strSplit[10]));
                        preStmt.setString(13, strSplit[11]);
                        preStmt.setString(14, strSplit[12]);
                        preStmt.setString(15, strSplit[13]);
                        preStmt.setString(16, strSplit[14]);
                        preStmt.setInt(17, Integer.valueOf(strSplit[15]));
                        preStmt.setString(18, strSplit[16]);
                        preStmt.setString(19, strSplit[17]);
                        preStmt.setString(20, "");
                        preStmt.addBatch();
                        preStmt.clearParameters();

                        if(i%10000 == 0) {
                            preStmt.executeBatch();
                            preStmt.clearBatch();
                            conn.commit();
                            System.out.println("진행 상황: " + i + "건");
                        }

                        int percentage = (i * 100) / idx;
                        if (i % (idx / 10) == 0) {
                            System.out.println("진행 상황: " + percentage + "%");
                        }
                    }

                    preStmt.close();
                    conn.close();

                    long endJdbcTime = System.currentTimeMillis();
                    long jdbcTime = endJdbcTime - startJdbcTime;

                    return ServiceResDto.result(jdbcTime+"ms", "Insert1 Success");

                } else if(Utils.isEqualText(controllerReqDto.getSqlType(), "insert2")) {
                    int idx = controllerReqDto.getIdx();

                    /* PT_SQL_TEST_TEXT */
                    conn = DriverManager.getConnection(controllerReqDto.getUrl(), controllerReqDto.getUser(), controllerReqDto.getPassword());
                    preStmt = conn.prepareStatement("INSERT INTO public.PT_SQL_TEST_TEXT(SQL_ID, SID, SQL_TYPE, SQL_SIZE, SQL_TEXT, RESERVED) VALUES (?, ?, ?, ?, ?, ?)");
                    Logger.info("sql: "+"INSERT INTO public.PT_SQL_TEST_TEXT(SQL_ID, SID, SQL_TYPE, SQL_SIZE, SQL_TEXT, RESERVED) VALUES (?, ?, ?, ?, ?, ?)");
                    long startJdbcTime = System.currentTimeMillis();
                    addBatchPtSqlTestVo(idx, "PT_SQL_TEST_TEXT", conn, preStmt);
                    long endJdbcTime = System.currentTimeMillis();
                    long jdbcTime1 = endJdbcTime - startJdbcTime;
                    preStmt = null;
                    conn = null;

                    /* PT_SQL_TEST_VARCHAR */
                    conn = DriverManager.getConnection(controllerReqDto.getUrl(), controllerReqDto.getUser(), controllerReqDto.getPassword());
                    preStmt = conn.prepareStatement("INSERT INTO public.PT_SQL_TEST_VARCHAR(SQL_ID, SID, SQL_TYPE, SQL_SIZE, SQL_TEXT, RESERVED) VALUES (?, ?, ?, ?, ?, ?)");
                    Logger.info("sql: "+"INSERT INTO public.PT_SQL_TEST_VARCHAR(SQL_ID, SID, SQL_TYPE, SQL_SIZE, SQL_TEXT, RESERVED) VALUES (?, ?, ?, ?, ?, ?)");
                    long startJdbcTime2 = System.currentTimeMillis();
                    addBatchPtSqlTestVo(idx, "PT_SQL_TEST_VARCHAR", conn, preStmt);
                    long endJdbcTime2 = System.currentTimeMillis();
                    long jdbcTime2 = endJdbcTime2 - startJdbcTime2;
                    preStmt = null;
                    conn = null;

                    String jdbcTime = "TEXT: "+jdbcTime1+"ms, VARCHAR: "+jdbcTime2+"ms";
                    return ServiceResDto.result(jdbcTime, "Insert2 Success");
                } else if(Utils.isEqualText(controllerReqDto.getSqlType(), "insert3")) {
                    /* PT_SEPARATE_TEST */
                    conn = DriverManager.getConnection(controllerReqDto.getUrl(), controllerReqDto.getUser(), controllerReqDto.getPassword());
                    preStmt = conn.prepareStatement("INSERT INTO public.PT_SEPARATE_TEST(SQL_ID, SID, SQL_NO, SQL_TYPE, SQL_SIZE, SQL_TEXT, RESERVED) VALUES (?, ?, ?, ?, ?, ?, ?)");
                    Logger.info("sql: "+"INSERT INTO public.PT_SEPARATE_TEST(SQL_ID, SID, SQL_NO, SQL_TYPE, SQL_SIZE, SQL_TEXT, RESERVED) VALUES (?, ?, ?, ?, ?, ?, ?)");

                    long startJdbcTime = System.currentTimeMillis();
                    addBatchPtSeparateTest(controllerReqDto.getIdx(), "PT_SEPARATE_TEST", conn, preStmt);
                    long endJdbcTime = System.currentTimeMillis();
                    long jdbcTime = endJdbcTime - startJdbcTime;

                    preStmt = null;
                    conn = null;

                    return ServiceResDto.result("SEPARATE: "+jdbcTime+"ms", "Insert2 Success");
                }
            }  else {
                String tenTime = "";
                if(Utils.isEqualText(controllerReqDto.getSqlType(), "select")) {
                    conn = DriverManager.getConnection(controllerReqDto.getUrl(), controllerReqDto.getUser(), controllerReqDto.getPassword());
                    stmt = conn.createStatement();

                    Logger.info("sql: "+controllerReqDto.getSql());
                    for(int i = 1; i <= 10; i++){
                        long startJdbcTime = System.currentTimeMillis();
                        rs = stmt.executeQuery(controllerReqDto.getSql());
                        long endJdbcTime = System.currentTimeMillis();
                        long jdbcTime = endJdbcTime - startJdbcTime;
                        String time = "["+i+"회: "+jdbcTime+"ms]";
                        Logger.info(time);

                        rs.close();
                        rs = null;
                        tenTime += time+" ";
                    }

                    stmt.close();
                    conn.close();
                    stmt = null;
                    conn = null;

//                    switch (controllerReqDto.getTableName()) {
//                        case "pt_sess_test" :
//                            return ServiceResDto.result(jdbcTime, Utils.makePtSessTestVoList(rs));
//                    }

                    return ServiceResDto.result(tenTime, "");

                } else if(Utils.isEqualText(controllerReqDto.getSqlType(), "delete")) {
                    conn = DriverManager.getConnection(controllerReqDto.getUrl(), controllerReqDto.getUser(), controllerReqDto.getPassword());
                    stmt = conn.createStatement();

                    long startJdbcTime = System.currentTimeMillis();
                    Logger.info("sql: delete from pt_sess_test");
                    stmt.execute("delete from pt_sess_test");
                    Logger.info("sql: delete from pt_sql_test_text");
                    stmt.execute("delete from pt_sql_test_text");
                    Logger.info("sql: delete from pt_sql_test_varchar");
                    stmt.execute("delete from pt_sql_test_varchar");
                    Logger.info("sql: delete from pt_separate_test");
                    stmt.execute("delete from pt_separate_test");
                    long endJdbcTime = System.currentTimeMillis();
                    long jdbcTime = endJdbcTime - startJdbcTime;
                    Logger.info(jdbcTime+"ms");

                    stmt.close();
                    conn.close();
                    stmt = null;
                    conn = null;

                    return ServiceResDto.result(jdbcTime+"ms", "");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ServiceResDto.result(0+"ms", "API - Fail");
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

        return ServiceResDto.result(0+"ms", "");
    }

    private void addBatchPtSqlTestVo(int idx, String table, Connection conn, PreparedStatement preStmt) throws SQLException {
        conn.setAutoCommit(false);
        String filePath = "/Users/parkew/dev/dev_utils/5mb.txt"; // 읽어올 텍스트 파일 경로
        String content = "";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // 각 줄의 내용을 사용하거나 출력할 수 있습니다.
                content+= line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i = 1; i <= idx; i++) {
            String sqlType = sqlTypeInfo.get(Integer.valueOf(Utils.makeRandomInteger(5)));
            int sid = Utils.makeRandomInteger(idx);

            preStmt.setLong(1, i);
            preStmt.setLong(2, sid);
            preStmt.setString(3, sqlType);
            preStmt.setInt(4, content.length());
            preStmt.setString(5, content);
            preStmt.setString(6, "");
            preStmt.addBatch();
            preStmt.clearParameters();

            if(i%10 == 0) {
                preStmt.executeBatch();
                preStmt.clearBatch();
                conn.commit();
            }

            int percentage = (i * 100) / idx;
            if (i % (idx / 10) == 0) {
                System.out.println(table+" insert 진행 상황: " + percentage + "%");
            }
        }
        preStmt.executeBatch();
        preStmt.clearBatch();
        conn.commit();

        preStmt.close();
        conn.close();
    }

    private void addBatchPtSeparateTest(int idx, String table, Connection conn, PreparedStatement preStmt) throws SQLException {
        conn.setAutoCommit(false);
        int i = 1;
        boolean flag = false;

        for(int seqId = 1; seqId <= idx; seqId++) {
            flag = true;
            String sqlType = sqlTypeInfo.get(Integer.valueOf(Utils.makeRandomInteger(5)));
            String sqlText = sqlSeparateInfo.get(Integer.valueOf(Utils.makeRandomInteger(7)));
            int sid = Utils.makeRandomInteger(idx);

            int j = 1;
            for(String str : splitString(sqlText, 100)) {
                preStmt.setLong(1, seqId);
                preStmt.setLong(2, sid);
                preStmt.setLong(3, j);
                preStmt.setString(4, sqlType);
                preStmt.setInt(5, str.length());
                preStmt.setString(6, str);
                preStmt.setString(7, "");
                preStmt.addBatch();
                preStmt.clearParameters();
                j++;
                i++;
            }

            if(i%1000 == 0) {
                preStmt.executeBatch();
                preStmt.clearBatch();
                conn.commit();
                flag = false;
            }

            if(i%1000 == 0) {
                System.out.println("insert " + i + "개 완료");
            }

        }

        preStmt.executeBatch();
        preStmt.clearBatch();
        conn.commit();

        if(flag){
            System.out.println(table+" - insert " + (i-1) + "개 완료");
        }

        preStmt.close();
        conn.close();
    }

    public String[] splitString(String input, int chunkSize) {
        int length = input.length();
        int numOfChunks = (int) Math.ceil((double) length / chunkSize);
        String[] chunks = new String[numOfChunks];

        for (int i = 0; i < numOfChunks; i++) {
            int start = i * chunkSize;
            int end = Math.min((i + 1) * chunkSize, length);
            chunks[i] = input.substring(start, end);
        }

        return chunks;
    }

}
