package com.tml.common.core.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 
 * @author JacksonTu
 * @since 2020-08-10 20:30
 * @version 1.0
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TreeNode<T> {

    private String id;

    private String label;

    private List<TreeNode<T>> children;

    private String parentId;

    private boolean hasParent = false;

    private boolean hasChildren = false;

    public void initChildren() {
        this.children = new ArrayList<>();
    }

}
