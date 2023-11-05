package com.shoes.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO representing a bank.
 */
@Data
public class BankDTO implements Serializable {
    private int id;
    private String name;
    private String code;
    private String bin;
}
