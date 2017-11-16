package cn.tech.yozo.factoryrp.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/16
 * @description 无状态的token
 */
public class StatelessToken implements AuthenticationToken {

    private String username;
    //private Map<String, ?> params;
    private String sequence;

    public StatelessToken(String username, String sequence) {
        this.username = username;
        this.sequence = sequence;
    }

    public Object getPrincipal() {  return username;}
    public Object getCredentials() {  return sequence;}

}
