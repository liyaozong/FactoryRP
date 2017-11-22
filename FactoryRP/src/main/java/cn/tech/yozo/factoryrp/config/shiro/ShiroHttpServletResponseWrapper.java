/*
package cn.tech.yozo.factoryrp.config.shiro;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.PrintWriter;

*/
/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/22
 * @description
 *//*

public class ShiroHttpServletResponseWrapper extends HttpServletResponseWrapper{
    private final PrintWriter body;

    */
/**
     * Constructs a response adaptor wrapping the given response.
     *
     * @param response The response to be wrapped
     * @throws IllegalArgumentException if the response is null
     *//*

    public ShiroHttpServletResponseWrapper(HttpServletResponse response) {
        super(response);
        super(request);
        body = StreamUtil.readBytes(response.getWriter(),"UTF-8");
    }
}
*/
