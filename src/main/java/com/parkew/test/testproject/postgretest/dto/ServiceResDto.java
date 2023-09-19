
package com.parkew.test.testproject.postgretest.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * ControllerResponseDto
 * @author ewpark, jmsong, cgpark
 * @since 2023.04.13
 */
@Getter
@Setter
public class ServiceResDto {

    private String jdbcTime;
    private Object data;

    public ServiceResDto() {
    }

    public ServiceResDto(String jdbcTime, Object data) {
        this.jdbcTime = jdbcTime;
        this.data = data;
    }

    public static ServiceResDto result(String jdbcTime, Object data) {
        return new ServiceResDto(jdbcTime, data);
    }
}
