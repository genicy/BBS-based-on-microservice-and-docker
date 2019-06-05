package entity;

import java.util.List;

/**
 * @introduction: xucheng_parent
 * @Description: 返回结果实体类
 * @Author: yangxucheng
 * @Date: 2019/1/16 15:50
 * @Version: 1.0
 */
public class PageResult <T> {
    private Long total;
    private List<T> rows;

    public PageResult() {
    }

    public PageResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
