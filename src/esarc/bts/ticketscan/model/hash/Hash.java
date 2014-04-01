package esarc.bts.ticketscan.model.hash;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

	public static String toSHA(String mdp) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		digest.reset();

		byte[] str = digest.digest(mdp.getBytes());
		return String.format("%0" + (str.length * 2) + "x", new BigInteger(1,
				str));

	}
}
