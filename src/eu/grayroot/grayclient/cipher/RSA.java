package eu.grayroot.grayclient.cipher;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSA {

	public RSA() {
		super();
		// TODO Auto-generated constructor stub
	}

	public KeyPair getRSAKey() {
		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
			kpg.initialize(2048);
			KeyPair kp = kpg.generateKeyPair();
			return kp;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public PrivateKey loadPrivateKey() {
		PrivateKey priv = null;
		String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC1nc6YuSAjgmzTS5zSks/1MUQwzixPrEx/1pyRKghtxc9FCErjdCHMz+9nQYUqxxEsCF/9FMmMafg7NKbTG1WhkZuZ+G9Cm9LpcNpXeQ874WRgxGxOiXWxgXJN33+LvDTYrh20t5qDiaz/Ew9lfJH5GMSITqVyfZbESLlrJQ8USdtM77YLC2ym1/FEcMLSogGjKn5QWBbVxlyQDt+12LaN8Uig2FK7HSsTqlgdGSujmy7BgAocaMF5blCUmCs6NupizQhY4zZlAybip8yE+6391Zy4QAIIBcel+mKA4kTjZbmcoHWVEZV0zzrtNQHxJ/S/cRKmHbeWSyDWbmkQZciBAgMBAAECggEBAINMt1PKYuXJIQwRGitSYG4PVBFEvNxM4HF7om2ASd+ypopt2FZgWx9LJPUtdSzcBhojY+H/6UZORlvnUMRGer34GtpRtX4eUXjbUb7zkoPkC0G6P+L3ldLiltSdxAQt6LbOCkPgiSCXpC40hWkFijLgKRWyZgJDsX/uY76GaERtdUa7vWLdQF9+qetVvLy0KdbviAqcqiCfT5ynMGp34y6czeV9v3roBnEKcxz1scK0n36MbDEwWh70dfPLsi/enUHKfqvLfdMowSPrQpGvga2w3MCIXyzcBvFi6rxZeF1RNc+K0U6fgfwW1beGFq79VbQw5zjtZ9qpeSwrgbFyKwECgYEA+9w2T6jzSBtWmGcEqLBBOXEZlkA6MCppJ2QEG5UQrGqlwLD0ZUqJRfkoULnO8EBc6yQ0rj84T78rhTtmkrUagfobJTspHcuEyE0L0s9Vm9xAlu/K6bGnrXprziYoLCIdrp8sn/PhZglpUVpSM8cIrtC8guIvKCPZif6G+kHektkCgYEAuJoFLDW7MQhuQXKjBOBCtI3/iRuRTsqbpRp0ufYSz1af8lg3PQj4V6/ukOPFml9XEDlx8abJoyi649sIXrsCGW9Ju+uYi3Sm6bNNfK0ZvoyX7VTuu1UILAk57DaRcJK4N2vYgDsbpmuLgiUulqLe/m4YzbJml4ed5U85EySciekCgYAZO7jdRkoJgWOtpTScSfxe6uvrV8f8p2MdpTGcKdUf0ReGNLRnqY4TSqE1ZUhMXcZxcJEum/riUn7jvE3MiQR83Tf3BPRbndZR0xZKKqejE/AMrz2fKENqaAeBAeWodkqPAjeQVvjQJkU891WhBmHiUdtxKYQb+2uCHdQcIplQEQKBgAWoqwns570cDg0xj2/B1CmmxLOWRcENkvHfur5Mncz/Uc3jCGZ1lO9TNFP6t38A9zFHIYgP3dSDlh8QAtSslObks3TwRu/XdpppZbGxuL1PoASleuABPkvjSIRGM/Uoej8wriVYKoPSfidSLnhtq7pO87Gyi1SP0rZJ1G0SzT2pAoGBAPk8JQSkUzVBZz4UQNIwUyGL+bLOhVldwuIUPrOaEgTZ8/xyFldsDmqIwGFsYIv22MStH8tKjq32Duq+SE5GHduoBwcj7LRupFbfCs0UyMBIkOBeQnbEL9OApOlM3+hnJrG63FYgMiAJQWAJTJyqQNTfGf41gW27aUInOnn/Lz/o";

		byte[] buffer = Base64.getDecoder().decode(privateKey);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
		KeyFactory keyFactory = null;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			priv = keyFactory.generatePrivate(keySpec);
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return priv;
	}

	public PublicKey loadPublicKey() {
		PublicKey pub = null;
		String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAog7GKn13PV0fqRzrfKXIwlH7o6t5c7u5nBuBVDfcMRU11vfpZrvDSYtRLi5gd0Uq6vWAXcf6nXXLja4lPRB9BL4/lEx8mxWmqSWAPWo6cppZIrthnbGtUzTInTHcdKpGgVsCt+k/aYDQLKm5y1YvJGsIDx5/nyUA+ZLtiLfOXwIi8V4Gy0CuRIekny/ftpFVFlS77ckVrSnMJi+CUj5klAMM63PkO5QqhYmYwG6mm6oM6CqJqgzK1VEhrrf/Ikydz5/PNR1XgiyFRkYlm8GihoRB/afABFrW5nCnBSkBdzilBnnPxpIjHCEKvdjTJeyiVCFp7RdRl7AJC3avq3rk9QIDAQAB";
		byte[] buffer = Base64.getDecoder().decode(publicKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
		KeyFactory keyFactory = null;
		try {
			keyFactory = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			pub = keyFactory.generatePublic(keySpec);
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pub;
	}

	public byte[] encryptText(String text, PublicKey publicKey) {
		byte[] encryptedText = null;

		try {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			encryptedText = cipher.doFinal(text.getBytes());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return encryptedText;
	}

	public String decrypt(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return new String(cipher.doFinal(data));
	}

	public String decrypt(String data, PrivateKey privateKey) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
		return decrypt(Base64.getDecoder().decode(data.getBytes()), privateKey);
	}

}
