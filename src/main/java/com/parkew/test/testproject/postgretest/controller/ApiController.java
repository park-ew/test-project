package com.parkew.test.testproject.postgretest.controller;

import com.parkew.test.testproject.Logger;
import com.parkew.test.testproject.postgretest.dto.ControllerReqDto;
import com.parkew.test.testproject.postgretest.dto.ControllerResDto;
import com.parkew.test.testproject.postgretest.dto.ServiceResDto;
import com.parkew.test.testproject.postgretest.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther parkew
 * @sinse 2023.09.12
 */
@RestController
public class ApiController {

    @Autowired
    ApiService apiService;

    @PostMapping(value ="/api/test", produces = "application/json;charset=UTF-8")
    public ControllerResDto apiController(@RequestBody ControllerReqDto controllerReqDto){

        System.out.println("apiController");

        long startApiTime = System.currentTimeMillis();
        ServiceResDto serviceResDto = apiService.apiService(controllerReqDto);
        long endApiTime = System.currentTimeMillis();
        long apiTime = endApiTime - startApiTime;

        return ControllerResDto.result(apiTime + "ms", serviceResDto.getJdbcTime(), serviceResDto.getData());
    }

    @PostMapping(value ="/api/test/complex", produces = "application/json;charset=UTF-8")
    public ControllerResDto testController(@RequestBody ControllerReqDto controllerReqDto) {
        long startApiTime = System.currentTimeMillis();
        String jdbcTime = "";
        String oneTime = "";
        String str;
        ServiceResDto serviceResDto;

//        int[] idxList = {1000000, 2000000, 3000000, 4000000, 5000000};
        int[] idxList = {1000000};
        String url = controllerReqDto.getUrl();
        String user = controllerReqDto.getUser();
        String password = controllerReqDto.getPassword();

        String ptSessSql                    = "SELECT SID, USER_ID, USER_NAME, USER_ROLE, USER_LEVEL, USER_PHONE, ORG_ONE_NAME, ORG_TWO_NAME, ORG_THREE_NAME, ORG_FOUR_NAME, ORG_FIVE_NAME, DB_ID, DB_OS_TYPE, DB_PROGRAM, DB_TYPE, DB_IP, DB_PORT, DB_INSTANCE, DB_SCHEMA, RESERVED FROM public.PT_SESS_TEST";
        String ptSqlTextSql                 = "SELECT SQL_ID, SID, SQL_TYPE, SQL_SIZE, SQL_TEXT, RESERVED FROM public.PT_SQL_TEST_TEXT";
        String ptSqlVarcharSql              = "SELECT SQL_ID, SID, SQL_TYPE, SQL_SIZE, SQL_TEXT, RESERVED FROM public.PT_SQL_TEST_VARCHAR";
        String ptSeparateSql                = "SELECT SQL_ID, SID, SQL_NO, SQL_TYPE, SQL_SIZE, SQL_TEXT, RESERVED FROM public.PT_SEPARATE_TEST";

        String ptSessJoinTextSql            = "SELECT PST.SID, PST.USER_ID, PST.USER_NAME, PST.USER_ROLE, PST.USER_LEVEL, PST.USER_PHONE, PST.ORG_ONE_NAME, PST.ORG_TWO_NAME, PST.ORG_THREE_NAME, PST.ORG_FOUR_NAME, PST.ORG_FIVE_NAME, PST.DB_ID, PST.DB_OS_TYPE, PST.DB_PROGRAM, PST.DB_TYPE, PST.DB_IP, PST.DB_PORT, PST.DB_INSTANCE, PST.DB_SCHEMA, PSTT.SQL_ID, PSTT.SQL_TYPE, PSTT.SQL_SIZE, PSTT.SQL_TEXT, PST.RESERVED FROM public.PT_SESS_TEST PST, public.PT_SQL_TEST_TEXT PSTT WHERE PST.SID = PSTT.SID";
        String ptSessJoinVarcharSql         = "SELECT PST.SID, PST.USER_ID, PST.USER_NAME, PST.USER_ROLE, PST.USER_LEVEL, PST.USER_PHONE, PST.ORG_ONE_NAME, PST.ORG_TWO_NAME, PST.ORG_THREE_NAME, PST.ORG_FOUR_NAME, PST.ORG_FIVE_NAME, PST.DB_ID, PST.DB_OS_TYPE, PST.DB_PROGRAM, PST.DB_TYPE, PST.DB_IP, PST.DB_PORT, PST.DB_INSTANCE, PST.DB_SCHEMA, PSTV.SQL_ID, PSTV.SQL_TYPE, PSTV.SQL_SIZE, PSTV.SQL_TEXT, PST.RESERVED FROM public.PT_SESS_TEST PST, public.PT_SQL_TEST_VARCHAR PSTV WHERE PST.SID = PSTV.SID";
        String ptSessJoinSeparateSql        = "SELECT PST.SID, PST.USER_ID, PST.USER_NAME, PST.USER_ROLE, PST.USER_LEVEL, PST.USER_PHONE, PST.ORG_ONE_NAME, PST.ORG_TWO_NAME, PST.ORG_THREE_NAME, PST.ORG_FOUR_NAME, PST.ORG_FIVE_NAME, PST.DB_ID, PST.DB_OS_TYPE, PST.DB_PROGRAM, PST.DB_TYPE, PST.DB_IP, PST.DB_PORT, PST.DB_INSTANCE, PST.DB_SCHEMA, PSPT.SQL_ID, PSPT.SQL_TYPE, PSPT.SQL_SIZE, PSPT.SQL_TEXT, PST.RESERVED FROM PUBLIC.PT_SESS_TEST PST, (SELECT SID, SQL_ID, SQL_TYPE, SUM(SQL_SIZE) AS SQL_SIZE, ARRAY_TO_STRING(ARRAY_AGG(SQL_TEXT ORDER BY SQL_NO),'') AS SQL_TEXT FROM PUBLIC.PT_SEPARATE_TEST GROUP BY SID, SQL_ID, SQL_TYPE) PSPT WHERE PST.SID = PSPT.SID";

        String ptSessJoinTextSql10000       = "SELECT PST.SID, PST.USER_ID, PST.USER_NAME, PST.USER_ROLE, PST.USER_LEVEL, PST.USER_PHONE, PST.ORG_ONE_NAME, PST.ORG_TWO_NAME, PST.ORG_THREE_NAME, PST.ORG_FOUR_NAME, PST.ORG_FIVE_NAME, PST.DB_ID, PST.DB_OS_TYPE, PST.DB_PROGRAM, PST.DB_TYPE, PST.DB_IP, PST.DB_PORT, PST.DB_INSTANCE, PST.DB_SCHEMA, PSTT.SQL_ID, PSTT.SQL_TYPE, PSTT.SQL_SIZE, PSTT.SQL_TEXT, PST.RESERVED FROM public.PT_SESS_TEST PST, public.PT_SQL_TEST_TEXT PSTT WHERE PST.SID = PSTT.SID ORDER BY PST.SID DESC LIMIT 10000";
        String ptSessJoinVarcharSql10000    = "SELECT PST.SID, PST.USER_ID, PST.USER_NAME, PST.USER_ROLE, PST.USER_LEVEL, PST.USER_PHONE, PST.ORG_ONE_NAME, PST.ORG_TWO_NAME, PST.ORG_THREE_NAME, PST.ORG_FOUR_NAME, PST.ORG_FIVE_NAME, PST.DB_ID, PST.DB_OS_TYPE, PST.DB_PROGRAM, PST.DB_TYPE, PST.DB_IP, PST.DB_PORT, PST.DB_INSTANCE, PST.DB_SCHEMA, PSTV.SQL_ID, PSTV.SQL_TYPE, PSTV.SQL_SIZE, PSTV.SQL_TEXT, PST.RESERVED FROM public.PT_SESS_TEST PST, public.PT_SQL_TEST_VARCHAR PSTV WHERE PST.SID = PSTV.SID ORDER BY PST.SID DESC LIMIT 10000";
        String ptSessJoinSeparateSql10000   = "SELECT PST.SID, PST.USER_ID, PST.USER_NAME, PST.USER_ROLE, PST.USER_LEVEL, PST.USER_PHONE, PST.ORG_ONE_NAME, PST.ORG_TWO_NAME, PST.ORG_THREE_NAME, PST.ORG_FOUR_NAME, PST.ORG_FIVE_NAME, PST.DB_ID, PST.DB_OS_TYPE, PST.DB_PROGRAM, PST.DB_TYPE, PST.DB_IP, PST.DB_PORT, PST.DB_INSTANCE, PST.DB_SCHEMA, PSPT.SQL_ID, PSPT.SQL_TYPE, PSPT.SQL_SIZE, PSPT.SQL_TEXT, PST.RESERVED FROM PUBLIC.PT_SESS_TEST PST, (SELECT SID, SQL_ID, SQL_TYPE, SUM(SQL_SIZE) AS SQL_SIZE, ARRAY_TO_STRING(ARRAY_AGG(SQL_TEXT ORDER BY SQL_NO),'') AS SQL_TEXT FROM PUBLIC.PT_SEPARATE_TEST GROUP BY SID, SQL_ID, SQL_TYPE) PSPT WHERE PST.SID = PSPT.SID ORDER BY PST.SID DESC LIMIT 10000";

        for(int idx : idxList){
            ControllerReqDto deleteAll = new ControllerReqDto(idx, url, user, password, false, "delete", "", "");

            ControllerReqDto insertPtSessTest = new ControllerReqDto(idx, url, user, password, true, "insert1", "PT_SESS_TEST", "");
            ControllerReqDto insertPtSqlTest = new ControllerReqDto(idx, url, user, password, true, "insert2", "PT_SQL_TEST_TEXT, PT_SQL_TEST_VARCHAR", "");
            ControllerReqDto insertPtSeparateTest = new ControllerReqDto(idx, url, user, password, true, "insert3", "PT_SEPARATE_TEST", "");

            ControllerReqDto selectPtSessTest = new ControllerReqDto(idx, url, user, password, false, "select", "PT_SESS_TEST", ptSessSql);
            ControllerReqDto selectPtSqlTestText = new ControllerReqDto(idx, url, user, password, false, "select", "PT_SQL_TEST_TEXT", ptSqlTextSql);
            ControllerReqDto selectPtSqlTestVarchar = new ControllerReqDto(idx, url, user, password, false, "select", "PT_SQL_TEXT_VARCHAR", ptSqlVarcharSql);
            ControllerReqDto selectPtSeparateTest = new ControllerReqDto(idx, url, user, password, false, "select", "PT_SEPARATE_TEST", ptSeparateSql);

            ControllerReqDto selectPtSessTestJoinText = new ControllerReqDto(idx, url, user, password, false, "select", "PT_SESS_TEST, PT_SQL_TEST_TEXT", ptSessJoinTextSql);
            ControllerReqDto selectPtSessTestJoinVarchar = new ControllerReqDto(idx, url, user, password, false, "select", "PT_SESS_TEST, PT_SQL_TEXT_VARCHAR", ptSessJoinVarcharSql);
            ControllerReqDto selectPtSessTestJoinSeparate = new ControllerReqDto(idx, url, user, password, false, "select", "PT_SESS_TEST, PT_SEPARATE_TEST", ptSessJoinSeparateSql);

            ControllerReqDto selectPtSessTestJoinText10000 = new ControllerReqDto(idx, url, user, password, false, "select", "PT_SESS_TEST, PT_SQL_TEST_TEXT", ptSessJoinTextSql10000);
            ControllerReqDto selectPtSessTestJoinVarchar10000 = new ControllerReqDto(idx, url, user, password, false, "select", "PT_SESS_TEST, PT_SQL_TEXT_VARCHAR", ptSessJoinVarcharSql10000);
            ControllerReqDto selectPtSessTestJoinSeparate10000 = new ControllerReqDto(idx, url, user, password, false, "select", "PT_SESS_TEST, PT_SEPARATE_TEST", ptSessJoinSeparateSql10000);

            oneTime = "";
            oneTime += "\n{\n"+idx+"회 테스트: ";

            serviceResDto = apiService.apiService(deleteAll);
            str = "[delete all: "+serviceResDto.getJdbcTime()+"]";
            Logger.info(str);
            oneTime += "\n"+str;

            serviceResDto = apiService.apiService(insertPtSessTest);
            str = "[insert pt_sess_test: "+serviceResDto.getJdbcTime()+"]";
            Logger.info(str);
            oneTime += "\n"+str;
            serviceResDto = apiService.apiService(insertPtSqlTest);
            str = "[insert pt_sql_test: "+serviceResDto.getJdbcTime()+"]";
            Logger.info(str);
            oneTime += "\n"+str;
            serviceResDto = apiService.apiService(insertPtSeparateTest);
            str = "[insert pt_separate_test: "+serviceResDto.getJdbcTime()+"]";
            Logger.info(str);
            oneTime += "\n"+str;

            serviceResDto = apiService.apiService(selectPtSessTest);
            str = "[select pt_sess_test: "+serviceResDto.getJdbcTime()+"]";
            Logger.info(str);
            oneTime += "\n"+str;
            serviceResDto = apiService.apiService(selectPtSqlTestText);
            str = "[select pt_sql_test_text: "+serviceResDto.getJdbcTime()+"]";
            Logger.info(str);
            oneTime += "\n"+str;
            serviceResDto = apiService.apiService(selectPtSqlTestVarchar);
            str = "[select pt_sql_test_varchar: "+serviceResDto.getJdbcTime()+"]";
            Logger.info(str);
            oneTime += "\n"+str;
            serviceResDto = apiService.apiService(selectPtSeparateTest);
            str = "[select pt_separate_test: "+serviceResDto.getJdbcTime()+"]";
            Logger.info(str);
            oneTime += "\n"+str;

            serviceResDto = apiService.apiService(selectPtSessTestJoinText);
            str = "[select join text: "+serviceResDto.getJdbcTime()+"]";
            Logger.info(str);
            oneTime += "\n"+str;
            serviceResDto = apiService.apiService(selectPtSessTestJoinVarchar);
            str = "[select join varchar: "+serviceResDto.getJdbcTime()+"]";
            Logger.info(str);
            oneTime += "\n"+str;
            serviceResDto = apiService.apiService(selectPtSessTestJoinSeparate);
            str = "[select join separate: "+serviceResDto.getJdbcTime()+"]";
            Logger.info(str);
            oneTime += "\n"+str;

            serviceResDto = apiService.apiService(selectPtSessTestJoinText10000);
            str = "[select join text 10000row: "+serviceResDto.getJdbcTime()+"]";
            Logger.info(str);
            oneTime += "\n"+str;
            serviceResDto = apiService.apiService(selectPtSessTestJoinVarchar10000);
            str = "[select join varchar 10000row: "+serviceResDto.getJdbcTime()+"]";
            Logger.info(str);
            oneTime += "\n"+str;
            serviceResDto = apiService.apiService(selectPtSessTestJoinSeparate10000);
            str = "[select join separate 10000row: "+serviceResDto.getJdbcTime()+"]";
            Logger.info(str);
            oneTime += "\n"+str;

            oneTime += "\n}";

            Logger.info(oneTime);

            jdbcTime += oneTime;
        }

        long endApiTime = System.currentTimeMillis();
        long apiTime = endApiTime - startApiTime;

        return ControllerResDto.result(apiTime+"ms", jdbcTime, "");
    }
}
