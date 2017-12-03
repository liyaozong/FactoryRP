package cn.tech.yozo.factoryrp.page;

/**
 * 可分页接口
 * 
 * 
 */
public interface Paginable {
    /**
     * 总记录数
     * 
     * @return
     */
    public long getTotalCount();

    /**
     * 总页数
     * 
     * @return
     */
    public int getTotalPage();

    /**
     * 每页记录数
     * 
     * @return
     */
    public int getItemsPerPage();

    /**
     * 当前页号
     * 
     * @return
     */
    public int getCurrentPage();

}
