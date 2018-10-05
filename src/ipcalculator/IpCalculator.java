package ipcalculator;
public class IpCalculator {
	// 指定したIPアドレスがプライベートであることを判定する関数 
	public static boolean isValidPrivateIpv4(String _ipAddress) {
		// whitespace 空白を削除
		_ipAddress = _ipAddress.replaceAll("\\s", "");

		// 3桁の数字がドット区切りで4つ存在する
		String[] ipAddressArray = _ipAddress.split("\\.");
		if (ipAddressArray.length > 4 || ipAddressArray.length < 4) {
			return false;
		}

		int[] ipParts = new int[ipAddressArray.length];
		for (int i = 0; i < ipAddressArray.length; i++) {
			try {
				ipParts[i] = Integer.parseInt(ipAddressArray[i]);
			} catch (NumberFormatException e) {
				return false;
			}
			// dot ドットで区切られた各数字は0~255である
			if ((ipParts[i] < 0) || (ipParts[i] > 255)) {
				return false;
			}
		}

		// プライベートアドレスである
		switch (ipParts[0]) {
		case 10:
		case 127:
			/* fall through */
			return true;
		case 172:
			// 172.16.0.0 から 172.31.255.255
			return (ipParts[1] >= 16) && (ipParts[1] < 32);
		case 192:
			// 192.168.0.0 から 192.168.255.255
			return (ipParts[1] == 168);
		default:
			break;
		}
		return false;
	}

	// 指定したIPアドレスのネットワークアドレスを取得する関数 
	public static String fetchNetworkAddress (String _ipAddress, String _subnetMask) {
		// IPアドレスとサブネットマスクの各数値の論理積
		Long longNetworkValue = ipStringToLong(_ipAddress) & ipStringToLong(_subnetMask);
		return longToipAddress(longNetworkValue);
	}

	// 指定したIPアドレスのブロードキャストアドレスを取得する関数 
	public static String fetchBroadcastAddress (String _ipAddress, String _subnetMask) {
		// subnetMaskの各数値のビットを全て反転し、ネットワーク アドレスの各数値と論理和
		Long longBroadcastValue = ipStringToLong(_ipAddress) | ~ipStringToLong(_subnetMask);
		return longToipAddress(longBroadcastValue);
	}

	// 指定したIPアドレスが所属するネットワークで使用できる最大ホスト数を取得する関数 
	public static int fetchMaxHostNum(String _ipAddress, String _subnetMask){
		int maxHostNum = 0;
		String[] subnetArray = _subnetMask.split("\\.");
		String subnetBinary = "";
		for (String ipString : subnetArray) {
			// int -> 2進数
			subnetBinary = Integer.toBinaryString(Integer.parseInt(ipString));
		}
		// aホスト部のビット数（0の個数を数える）
		int x = subnetBinary.length() - subnetBinary.replace("0", "").length();
		
		/*
		 * http://www.geocities.jp/cwqnx949/subnetka/
		 * ホスト台数 = 2の[X乗]-2
		 */
		maxHostNum = (int) (Math.pow(2, x) - 2);
		return maxHostNum;
	}

	// ロングIPに変更
	public static long ipStringToLong (String _address) {
		/*
		 * https://ja.wikipedia.org/wiki/IPアドレス
		 * n x (256)^3 + n x (256)^2 + n x (256)^1 + n (256)^0
		 */
		long addressLong = 0;
		String[] addressArray = _address.split("\\.");
		for (int i = 0; i < addressArray.length; i++) {
			int power = 3 - i;
			addressLong += Integer.parseInt(addressArray[i]) * Math.pow(256, power);
		}
		return addressLong;
	}

	// ロング => String IP(x.x.x.x)
	public static String longToipAddress (long _longResult) {
		StringBuilder sb_ipAddress = new StringBuilder(15);
		for (int i = 0; i < 4; i++) {
			sb_ipAddress.insert(0, Long.toString(_longResult & 0xff));
			if (i < 3) {
				sb_ipAddress.insert(0, '.');
			}
			_longResult = _longResult >> 8;
		}
		return sb_ipAddress.toString();
	}
}
