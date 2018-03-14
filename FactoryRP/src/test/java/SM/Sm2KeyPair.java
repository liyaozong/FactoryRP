package SM;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/13
 * @description
 */
public class Sm2KeyPair {

    private byte[] priKey;
    private byte[] pubKey;
    public Sm2KeyPair(byte[] priKey, byte[] pubKey){
        this.priKey = priKey;
        this.pubKey = pubKey;
    }

    public byte[] getPriKey() {
        return priKey;
    }

    public void setPriKey(byte[] priKey) {
        this.priKey = priKey;
    }

    public byte[] getPubKey() {
        return pubKey;
    }

    public void setPubKey(byte[] pubKey) {
        this.pubKey = pubKey;
    }

}
