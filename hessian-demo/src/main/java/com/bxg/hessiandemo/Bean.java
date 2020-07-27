package com.bxg.hessiandemo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Bean:
 *
 * @version 0.0.1  @date: 2020-07-19
 * @author: <a href="mailto:chengjs_minipa@outlook.com">chengjs_minipa</a>,
 */
@Data
@AllArgsConstructor
public class Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String value;

}
