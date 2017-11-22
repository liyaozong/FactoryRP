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

    private String password;

    public StatelessToken(String username, String sequence,String password) {
        this.username = username;
        this.sequence = sequence;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object getPrincipal() {  return this.username;}
    public Object getCredentials() {  return this.password;}

}
