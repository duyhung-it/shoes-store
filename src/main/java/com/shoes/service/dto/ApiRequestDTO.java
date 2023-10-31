package com.shoes.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * A DTO represents the body requests api to create a QR code.
 */
@Data
public class ApiRequestDTO implements Serializable {
    public String accountNo;
    public String accountName;
    public int acqId;
    public int amount;
    public String addInfo;
    public String format;
    public String template;
}
