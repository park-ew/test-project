package com.parkew.test.testproject;

import com.parkew.test.testproject.postgretest.vo.PtSessTestVo;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;

import java.sql.ResultSet;
import java.util.*;

/**
 * @auther parkew
 * @sinse 2023.09.12
 */
public class Utils {

    public static boolean isEqualText(String str1, String str2){
        if(StringUtils.equals(str1, str2)) {
            return true;
        }

        return false;
    }

    public static boolean isNullOrEmpty(String str) {
        if(StringUtils.equals(str, "") || str == null){
            return true;
        }

        return false;
    }

    public static boolean isConnectionInfoFine(String url, String user, String password) {

        if(isNullOrEmpty(url)){
            return false;
        }

        if(isNullOrEmpty(user)){
            return false;
        }

        if(isNullOrEmpty(password)){
            return false;
        }

        return true;
    }

    public static int makeRandomInteger(int range) {
        // Random 객체 생성
        Random rand = new Random();

        // 범위 내의 랜덤 숫자 생성
        int randomNumber = rand.nextInt((range - 1) + 1) + 1;

        return randomNumber;
    }

    public static PtSessTestVo makePtSessTestVo(int idx, String str) {
        String[] strSplit = str.split(",");
        return PtSessTestVo.insertAll(
                idx,
                strSplit[0],
                strSplit[1],
                strSplit[2],
                Integer.valueOf(strSplit[3]),
                strSplit[4],
                strSplit[5],
                strSplit[6],
                strSplit[7],
                strSplit[8],
                strSplit[9],
                Long.valueOf(strSplit[10]),
                strSplit[11],
                strSplit[12],
                strSplit[13],
                strSplit[14],
                Integer.valueOf(strSplit[15]),
                strSplit[16],
                strSplit[17],
                ""
        );
    }

    public static List<PtSessTestVo> makePtSessTestVoList(ResultSet rs) throws Exception{
        List<PtSessTestVo> ptSessTestVoList = new ArrayList<>();

        while (rs.next()) {
            ptSessTestVoList.add(PtSessTestVo.insertAll(
                    rs.getLong("SID"),
                    rs.getString("USER_ID"),
                    rs.getString("USER_NAME"),
                    rs.getString("USER_ROLE"),
                    rs.getInt("USER_LEVEL"),
                    rs.getString("USER_PHONE"),
                    rs.getString("ORG_ONE_NAME"),
                    rs.getString("ORG_TWO_NAME"),
                    rs.getString("ORG_THREE_NAME"),
                    rs.getString("ORG_FOUR_NAME"),
                    rs.getString("ORG_FIVE_NAME"),
                    rs.getLong("DB_ID"),
                    rs.getString("DB_OS_TYPE"),
                    rs.getString("DB_PROGRAM"),
                    rs.getString("DB_TYPE"),
                    rs.getString("DB_IP"),
                    rs.getInt("DB_PORT"),
                    rs.getString("DB_INSTANCE"),
                    rs.getString("DB_SCHEMA"),
                    rs.getString("RESERVED")
            ));
        }

        return ptSessTestVoList;
    }
}
