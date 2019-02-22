package com.bbahaida.dataqualitymanagement.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataSourceDTO {
    private int type;
    private int port;
    private String databaseName;
    private String username;
    private String password;
    private String host;


}
