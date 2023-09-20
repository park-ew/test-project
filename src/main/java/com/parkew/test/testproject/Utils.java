package com.parkew.test.testproject;

import com.parkew.test.testproject.postgretest.vo.PtSessTestVo;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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

    public static int makeRandomInteger(int range) {
        // Random 객체 생성
        Random rand = new Random();

        // 범위 내의 랜덤 숫자 생성
        int randomNumber = rand.nextInt((range - 1) + 1) + 1;

        return randomNumber;
    }

    public static void createRandomTextFile(int size, String filePath, String fileName) {
        String content = RandomStringUtils.randomAlphabetic(size);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath+fileName));
            writer.write(content);
            writer.close();
            System.out.println("텍스트 파일이 생성되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
