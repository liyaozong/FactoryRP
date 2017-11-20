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
    public int getTotalCount();

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

    /**
     * 是否第一页
     * 
     * @return
     */
    public boolean isFirstPage();

    /**
     * 是否最后一页
     * 
     * @return
     */
    public boolean isLastPage();

    /**
     * 返回下页的页号
     */
    public int getNextPage();

    /**
     * 返回上页的页号
     */
    public int getPrePage();
    
    /**
     * 分页样式
     */
    public int getPageStyle();
    
    /**
     * 页面路径
     */
    public String getPageURL();
}
