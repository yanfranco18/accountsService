package com.accounts.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Accounts")
public class Account {

    @Id
    private String id;
    private Double lineAvailable;
    private Double lineUsed;
    private String balancePast;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date createDate;

    private Client idClient;
    private Products nameProducts;
}
