package com.tensquare.rsa;

/**
 * rsa加解密用的公钥和私钥
 * @author Administrator
 *
 */
public class RsaKeys {

	//生成秘钥对的方法可以参考这篇帖子
	//https://www.cnblogs.com/yucy/p/8962823.html

//	//服务器公钥
//	private static final String serverPubKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6Dw9nwjBmDD/Ca1QnRGy"
//											 + "GjtLbF4CX2EGGS7iqwPToV2UUtTDDemq69P8E+WJ4n5W7Iln3pgK+32y19B4oT5q"
//											 + "iUwXbbEaAXPPZFmT5svPH6XxiQgsiaeZtwQjY61qDga6UH2mYGp0GbrP3i9TjPNt"
//											 + "IeSwUSaH2YZfwNgFWqj+y/0jjl8DUsN2tIFVSNpNTZNQ/VX4Dln0Z5DBPK1mSskd"
//											 + "N6uPUj9Ga/IKnwUIv+wL1VWjLNlUjcEHsUE+YE2FN03VnWDJ/VHs7UdHha4d/nUH"
//											 + "rZrJsKkauqnwJsYbijQU+a0HubwXB7BYMlKovikwNpdMS3+lBzjS5KIu6mRv1GoE"
//											 + "vQIDAQAB";
//
//	//服务器私钥(经过pkcs8格式处理)
//	private static final String serverPrvKeyPkcs8 = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDoPD2fCMGYMP8J"
//				 								 + "rVCdEbIaO0tsXgJfYQYZLuKrA9OhXZRS1MMN6arr0/wT5YniflbsiWfemAr7fbLX"
//				 								 + "0HihPmqJTBdtsRoBc89kWZPmy88fpfGJCCyJp5m3BCNjrWoOBrpQfaZganQZus/e"
//				 								 + "L1OM820h5LBRJofZhl/A2AVaqP7L/SOOXwNSw3a0gVVI2k1Nk1D9VfgOWfRnkME8"
//				 								 + "rWZKyR03q49SP0Zr8gqfBQi/7AvVVaMs2VSNwQexQT5gTYU3TdWdYMn9UeztR0eF"
//				 								 + "rh3+dQetmsmwqRq6qfAmxhuKNBT5rQe5vBcHsFgyUqi+KTA2l0xLf6UHONLkoi7q"
//				 								 + "ZG/UagS9AgMBAAECggEBANP72QvIBF8Vqld8+q7FLlu/cDN1BJlniReHsqQEFDOh"
//				 								 + "pfiN+ZZDix9FGz5WMiyqwlGbg1KuWqgBrzRMOTCGNt0oteIM3P4iZlblZZoww9nR"
//				 								 + "sc4xxeXJNQjYIC2mZ75x6bP7Xdl4ko3B9miLrqpksWNUypTopOysOc9f4FNHG326"
//				 								 + "0EMazVaXRCAIapTlcUpcwuRB1HT4N6iKL5Mzk3bzafLxfxbGCgTYiRQNeRyhXOnD"
//				 								 + "eJox64b5QkFjKn2G66B5RFZIQ+V+rOGsQElAMbW95jl0VoxUs6p5aNEe6jTgRzAT"
//				 								 + "kqM2v8As0GWi6yogQlsnR0WBn1ztggXTghQs2iDZ0YkCgYEA/LzC5Q8T15K2bM/N"
//				 								 + "K3ghIDBclB++Lw/xK1eONTXN+pBBqVQETtF3wxy6PiLV6PpJT/JIP27Q9VbtM9UF"
//				 								 + "3lepW6Z03VLqEVZo0fdVVyp8oHqv3I8Vo4JFPBDVxFiezygca/drtGMoce0wLWqu"
//				 								 + "bXlUmQlj+PTbXJMz4VTXuPl1cesCgYEA6zu5k1DsfPolcr3y7K9XpzkwBrT/L7LE"
//				 								 + "EiUGYIvgAkiIta2NDO/BIPdsq6OfkMdycAwkWFiGrJ7/VgU+hffIZwjZesr4HQuC"
//				 								 + "0APsqtUrk2yx+f33ZbrS39gcm/STDkVepeo1dsk2DMp7iCaxttYtMuqz3BNEwfRS"
//				 								 + "kIyKujP5kfcCgYEA1N2vUPm3/pNFLrR+26PcUp4o+2EY785/k7+0uMBOckFZ7GIl"
//				 								 + "FrV6J01k17zDaeyUHs+zZinRuTGzqzo6LSCsNdMnDtos5tleg6nLqRTRzuBGin/A"
//				 								 + "++xWn9aWFT+G0ne4KH9FqbLyd7IMJ9R4gR/1zseH+kFRGNGqmpi48MS61G0CgYBc"
//				 								 + "PBniwotH4cmHOSWkWohTAGBtcNDSghTRTIU4m//kxU4ddoRk+ylN5NZOYqTxXtLn"
//				 								 + "Tkt9/JAp5VoW/41peCOzCsxDkoxAzz+mkrNctKMWdjs+268Cy4Nd09475GU45khb"
//				 								 + "Y/88qV6xGz/evdVW7JniahbGByQhrMwm84R9yF1mNwKBgCIJZOFp9xV2997IY83S"
//				 								 + "habB/YSFbfZyojV+VFBRl4uc6OCpXdtSYzmsaRcMjN6Ikn7Mb9zgRHR8mPmtbVfj"
//				 								 + "B8W6V1H2KOPfn/LAM7Z0qw0MW4jimBhfhn4HY30AQ6GeImb2OqOuh3RBbeuuD+7m"
//				 								 + "LpFZC9zGggf9RK3PfqKeq30q";

	//服务器公钥
	private static final String serverPubKey ="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAv28VJVpGdGpYOEhGcsfZHM+FJf/49X5Et8aglCYwQ4yF7N3vq2jzpXsrJU4YNmRA8bEx1eeS41R3zA1ZJnj6uxZzi3lv9fkml/jx1F94EJJTWzpH3d8w6Dir3aOuJjNgaa4fnV+pAswnB/AxJkM/DPD2Og+PpMPp98xCWBJ5Wlu8UoJAGdDJbLDcTI3SBN35jdrg0c7Ls4aN2IevEtKfikGfDQHHy5SOgoklV5PGhRtcMH/ZlcNIoKveydErEWRGoMptfORuMpCWsDjZVIi78M/rhmwllTmlAlBVQBFSh2CJ/uRKDjWzxjPSdZowQ1YLMpxvougD42O7Y/NKh1tBsQIDAQAB";


	//服务器私钥(经过pkcs8格式处理)
	private static final String serverPrvKeyPkcs8 = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC/bxUlWkZ0alg4\n" +
			"SEZyx9kcz4Ul//j1fkS3xqCUJjBDjIXs3e+raPOleyslThg2ZEDxsTHV55LjVHfM" +
			"DVkmePq7FnOLeW/1+SaX+PHUX3gQklNbOkfd3zDoOKvdo64mM2Bprh+dX6kCzCcH" +
			"8DEmQz8M8PY6D4+kw+n3zEJYEnlaW7xSgkAZ0MlssNxMjdIE3fmN2uDRzsuzho3Y" +
			"h68S0p+KQZ8NAcfLlI6CiSVXk8aFG1wwf9mVw0igq97J0SsRZEagym185G4ykJaw" +
			"ONlUiLvwz+uGbCWVOaUCUFVAEVKHYIn+5EoONbPGM9J1mjBDVgsynG+i6APjY7tj" +
			"80qHW0GxAgMBAAECggEADoXy6L6MkdBvmPWCwrp+sjzcMSHWVWma9QZQsGylRLqo" +
			"fmvn/u0bbvaFM3NVSusYMj3rNfs3nZ+O5yAsH0sWWpM9eNthrxuT3fu4/Q+xIDC5" +
			"ujYVtbN1FmkWQN5mG+fR7LKVG29idx/d5jBGykxXwDeXtpfvoeRrH62TavMb2cry" +
			"MREwNIQv3MpPKrO8I/cfia8f2Mu8Kg1aLq25pBUEwbm7q9dcW3GjKRJT4jwGU0EZ" +
			"3stQcdC+Zo8hv8WvAVqWqWrF/k1kDYLASwziWDU0fUGAVil5DcNH/6MEy6FvTR6x" +
			"xnLmIhGUiXIpedPrbzkpF8cQZKP4Esd7PXmZCD7AAQKBgQDvdATqHPMMhMJQQCyk" +
			"QSPMrKae9JhPHmR80T3k1oRzQukI9gCphyVeTtEMffBpRXAPUkslg+U54jfE0etP" +
			"PqbW3obcE7R6T4i6001hXYlJS48CVMAWClMwc1ip/c/7/x9JZffBp/NgX8UPPBEC" +
			"Xz2cVlY/lgcL3iyYyKG0nOlqsQKBgQDMqZdtLDcaJww04MYnzq5BbvgKQsT0+SX3" +
			"sdKGy7fIqXUkUpMhAZpN7QAhrVzmPqs+C3Rh1hRrDATe6opFu5dVmjwvHaJXazo5" +
			"ep6TgaCsZEaYekfNQ9Q9n+f+iJORgmKsdUJiqQCyzoGPUzVKkAktHO94+Ravltjp" +
			"GLN9hLcHAQKBgQCJTJMM0jCiuvTYIxP10sv+LfkUJ9e1dg3Ua/37Wu1zUahhVP0o" +
			"N6+7laHUcdoJ4qDQiDVCe5BSUl374wnz1E7B4bKeOkY/457Y0RQLgiCYAFUt1v6V" +
			"JLJyk5EBV+GL8sszwN4BEpZAammJxL1UCNxA6lhl+VZs/aXArTnbYCWO4QKBgQDD" +
			"bNEWQxkelNiYCWXNjxJfksmwao9NaDaz7Q2c/xuvtPgRRfQNmU0xSbRNdA/4HzE+" +
			"3osz/MqkaOahwQ1lhrn72HnNd5uTo+UGC6suKKI22s16PZEgVLtuWKmbJ+jZEcqM" +
			"6K8KLFxpYLzgEdEaMK1CcZXNSvQ5mG3pLlf96+MlAQKBgQC0T+CxvoEo76J22Ra2" +
			"ZT8JApaZYoJIkVCzoDHpDSvnUg1eBOPAYgo75Qnf0R9F619UcLU+gwqIMNoicAo4" +
			"wLHQ4Cf67JSIkPA8LINHomK2uQaVazq0PZlGRp/IPb6/QvBBJ+PHEEGvLIYIYBwp" +
			"NoDXK9j1vDxJuODAyTAszAdd+Q==";

	public static String getServerPubKey() {
		return serverPubKey;
	}

	public static String getServerPrvKeyPkcs8() {
		return serverPrvKeyPkcs8;
	}
	
}
