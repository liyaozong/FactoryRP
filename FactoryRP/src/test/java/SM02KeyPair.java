import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/13
 * @description
 */
public class SM02KeyPair {
    private ECPoint publicKey;
    private BigInteger privateKey;

    SM02KeyPair(ECPoint publicKey, BigInteger privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public ECPoint getPublicKey() {
        return publicKey;
    }

    public BigInteger getPrivateKey() {
        return privateKey;
    }


}
