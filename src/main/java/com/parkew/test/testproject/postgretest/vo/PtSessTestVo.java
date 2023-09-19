package com.parkew.test.testproject.postgretest.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @auther parkew
 * @sinse 2023.09.12
 */
@Getter
@Setter
public class PtSessTestVo {
    private long sid;
    private String id;
    private String name;
    private String role;
    private int level;
    private String phone;
    private String orgOne;
    private String orgTwo;
    private String orgThree;
    private String orgFour;
    private String orgFive;
    private long dbId;
    private String os;
    private String program;
    private String db;
    private String ip;
    private int port;
    private String instance;
    private String schema;
    private String reserved;

    public PtSessTestVo(long sid, String id, String name, String role, int level, String phone, String orgOne, String orgTwo, String orgThree, String orgFour, String orgFive, long dbId, String os, String program, String db, String ip, int port, String instance, String schema, String reserved) {
        this.sid = sid;
        this.id = id;
        this.name = name;
        this.role = role;
        this.level = level;

        this.phone = phone;
        this.orgOne = orgOne;
        this.orgTwo = orgTwo;
        this.orgThree = orgThree;
        this.orgFour = orgFour;

        this.orgFive = orgFive;
        this.dbId = dbId;
        this.os = os;
        this.program = program;
        this.db = db;

        this.ip = ip;
        this.port = port;
        this.instance = instance;
        this.schema = schema;
        this.reserved = reserved;
    }

    public static PtSessTestVo insertAll(long sid, String id, String name, String role, int level, String phone, String orgOne, String orgTwo, String orgThree, String orgFour, String orgFive, long dbId, String os, String program, String db, String ip, int port, String instance, String schema, String reserved){
        return new PtSessTestVo(sid, id, name, role, level, phone, orgOne, orgTwo, orgThree, orgFour, orgFive, dbId, os, program, db, ip, port, instance, schema, reserved);
    }
}
