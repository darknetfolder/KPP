package TCPClient;

import TCPInterfaces.Executable;

import java.io.Serializable;
import java.math.BigInteger;

public class JobOne implements Executable, Serializable {
    private final static long serialVersionUID = -1L;
    private int n;

    public JobOne(int num) {
        this.n = num;
    }

    @Override
    public Object execute() {
        BigInteger res = BigInteger.ONE;
        for (int i = 2; i <= n; i++){
            res = res.multiply(BigInteger.valueOf(i));
        }
        return res;
    }
}