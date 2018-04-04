package com.example.uph.pertemuan7.api.Model;

import java.util.List;

/**
 * Created by denly93 on 8/2/17.
 */

public class Repository {

    private Integer totalCount;
    private List<Project> items;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<Project> getItems() {
        return items;
    }

    public void setItems(List<Project> items) {
        this.items = items;
    }
}
