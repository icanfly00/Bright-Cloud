package com.tml.auth.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @description
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
@Data
public class BindUser implements Serializable {

    private static final long serialVersionUID = -3890998115990166651L;

    @NotBlank(message = "{required}")
    private String bindUsername;
    @NotBlank(message = "{required}")
    private String bindPassword;
}
