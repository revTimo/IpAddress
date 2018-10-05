import java.util.Scanner;

import ipcalculator.IpCalculator;

public class Main {
	public static void main (String[] args) {
		IpCalculator ipc = new IpCalculator();
		Scanner inputReader = new Scanner(System.in);
		String inputIpAddress;
		System.out.println("iPv4 チェックします。");
		do {
			System.out.println("正しいプライベートIPアドレスのバージョン４(Ipv4)を入力してください: ");
			inputIpAddress = inputReader.nextLine();
		} while(!IpCalculator.isValidPrivateIpv4(inputIpAddress));

		System.out.println("指定したIPアドレスのネットワークアドレスを取得する関数: "+ ipc.fetchNetworkAddress("192.168.2.65", "255.255.255.192"));
		System.out.println("指定したIPアドレスのブロードキャストアドレスを取得する関数: "+ ipc.fetchBroadcastAddress("192.168.2.65", "255.255.255.192"));
		System.out.println("指定したIPアドレスが所属するネットワークで使用できる最大ホスト数を取得する関数: "+ ipc.fetchMaxHostNum("192.168.2.65", "255.255.255.192"));
	}
}
