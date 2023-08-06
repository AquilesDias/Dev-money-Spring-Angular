package com.github.aquiles.devmoneyapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StandardError implements Serializable {

    private static final long serialVersionUID = 1l;

    private Long timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
