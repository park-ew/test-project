
package com.parkew.test.testproject.postgretest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * ControllerResponseDto
 * @author ewpark, jmsong, cgpark
 * @since 2023.04.13
 */
@Getter
@Setter
public class ControllerResDto {

    private String apiTime;
    private String jdbcTime;
    private Map<String, Object> returnData;

    public ControllerResDto() {
    }

    public ControllerResDto(String apiTime, String jdbcTime, Map<String, Object> returnData) {
        this.apiTime = apiTime;
        this.jdbcTime = jdbcTime;
        this.returnData = returnData;
    }

    public static ControllerResDto result(String apiTime, String jdbcTime, Object data) {
        Map<String, Object> returnData = new HashMap<>();
        returnData.put("data", data);
        return new ControllerResDto(apiTime, jdbcTime, returnData);
    }
}
