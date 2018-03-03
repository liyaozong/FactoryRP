import java.io.Serializable;

/**
 * 基本的测试反射VO
 */
public class BaseTestVO implements Serializable{

    private Long id;

    private String certNo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }
}
