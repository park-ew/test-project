
package com.parkew.test.testproject.postgretest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ControllerResponseDto
 * @author ewpark, jmsong, cgpark
 * @since 2023.04.13
 */
@Getter
@AllArgsConstructor
public class ControllerReqDto {
    private int idx;
    private String url;
    private String user;
    private String password;
    private boolean batchFlag;
    private String sqlType;
    private String tableName;
    private String sql;
}
