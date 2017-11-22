package cn.tech.yozo.factoryrp.config.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/16
 * @description 无状态的Session工厂
 */
public class StatelessDefaultSubjectFactory extends DefaultWebSubjectFactory {


    /**
     * 通过调用 context.setSessionCreationEnabled(false) 表示不创建会话；
     * 如果之后调用 Subject.getSession() 将抛出 DisabledSessionException 异常。
     * @param context
     * @return
     */
    public Subject createSubject(SubjectContext context) {
        //不创建session
        context.setSessionCreationEnabled(true);
        return super.createSubject(context);
    }
}
