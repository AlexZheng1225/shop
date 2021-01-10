package com.alexzheng.onlineshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

/**
 * @Author Alex Zheng
 * @Date created in 13:51 2020/5/8
 * @Annotation
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageFileHolder {

    private String imageFileName;
    private InputStream imageFileInputStream;

}
