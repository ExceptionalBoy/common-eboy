package com.eboy.common.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @ClassName PageList
 * @Description TODO
 * @Author wxj
 * @CreateTime 2020-03-07 18:57
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageList<E> implements Serializable {

    private PageInfo pageInfo;

    private ArrayList<E> list;

}
