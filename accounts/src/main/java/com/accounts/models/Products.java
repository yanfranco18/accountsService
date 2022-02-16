package com.accounts.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Products {

    private String id;
    private String nameProduct;
    private String numberCard;
    private String typeProduct;
    private String accountNumber;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date createDate;
}
