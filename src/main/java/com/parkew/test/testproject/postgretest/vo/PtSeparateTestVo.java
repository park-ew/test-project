package com.parkew.test.testproject.postgretest.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @auther parkew
 * @sinse 2023.09.12
 */
@Getter
@Setter
public class PtSeparateTestVo {
    private long sqlId;
    private long sid;
    private int sqlNo;
    private String sqlType;
    private int sqlSize;
    private String sqlText;
    private String reserved;

    public PtSeparateTestVo(long sqlId, long sid, int sqlNo, String sqlType, int sqlSize, String sqlText, String reserved) {
        this.sqlId = sqlId;
        this.sid = sid;
        this.sqlNo = sqlNo;
        this.sqlType = sqlType;
        this.sqlSize = sqlSize;
        this.sqlText = sqlText;
        this.reserved = reserved;
    }

    public static PtSeparateTestVo insertAll(long sqlId, long sid, int sqlNo, String sqlType, int sqlSize, String sqlText, String reserved){
        return new PtSeparateTestVo(sqlId, sid, sqlNo, sqlType, sqlSize, sqlText, reserved);
    }
}
