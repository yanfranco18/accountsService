package com.accounts.clientWebClient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class ClientWebClient {

    private String id;
    private String nameClient;
    private String typeClient;
    private String identityDocument;
    private String phoneNumber;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date createDate;
}
