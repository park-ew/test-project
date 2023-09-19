package com.parkew.test.testproject.postgretest.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @auther parkew
 * @sinse 2023.09.12
 */
@Getter
@Setter
public class PtSqlTestVo {
    private long sqlId;
    private long sid;
    private String sqlType;
    private int sqlSize;
    private String sqlText;
    private String reserved;

    public PtSqlTestVo(long sqlId, long sid, String sqlType, int sqlSize, String sqlText, String reserved) {
        this.sqlId = sqlId;
        this.sid = sid;
        this.sqlType = sqlType;
        this.sqlSize = sqlSize;
        this.sqlText = sqlText;
        this.reserved = reserved;
    }

    public static PtSqlTestVo insertAll(long sqlId, long sid, String sqlType, int sqlSize, String sqlText, String reserved){
        return new PtSqlTestVo(sqlId, sid, sqlType, sqlSize, sqlText, reserved);
    }
}
