package com.ethan.crmsystem.web.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @version : CustomerForm.java,2019/11/9 15:50 $
 * @author<a href="ethanchen2698@163.com">ethan chen</a>
 */
@Data
public class CustomerForm {

    private String code;

    private String name;

    private List<String> regionIds = new ArrayList<>();

    private String region;

    private String level;

    private String phonenum;

    private String address;

    private String[] dateRange;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer page;

    private Integer pageSize;

    private Integer offset;

}
