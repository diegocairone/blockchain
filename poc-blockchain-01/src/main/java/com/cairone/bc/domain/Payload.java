package com.cairone.bc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Payload<T> {
    
    private T data;
}
