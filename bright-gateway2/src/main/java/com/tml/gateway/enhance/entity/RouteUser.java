package com.tml.gateway.enhance.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @description 
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
@Data
@Builder
@Document
@NoArgsConstructor
@AllArgsConstructor
public class RouteUser {

    @Id
    private String id;

    private String username;
    @JsonIgnore
    private String password;

    private String roles;

    private String createTime;
}
