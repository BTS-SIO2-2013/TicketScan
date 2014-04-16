package esarc.bts.ticketscan.model.hash;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Hash {

    private Hash() {

    }

    public static String toSHA(final String mdp) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            digest.reset();
            byte[] str = digest.digest(mdp.getBytes());

            return String.format("%0" + str.length * 2 + "x", new BigInteger(1,
                    str));

        } catch (NoSuchAlgorithmException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        return "ca00fccfb408989eddc401062c4d1219a6aceb6b9b55412357f1790862e8f178";

    }
}
