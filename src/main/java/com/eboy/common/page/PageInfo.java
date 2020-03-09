package com.eboy.common.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName PageInfo
 * @Description TODO
 * @Author wxj
 * @CreateTime 2020-03-07 19:06
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo implements Serializable {

    private Integer page;

    private Integer limit;

    private Long totalCount;

}
