package com.accounts.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    private String id;
    private String nameClient;
    private String typeClient;
    private String identityDocument;
    private String phoneNumber;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date createDate;
}
