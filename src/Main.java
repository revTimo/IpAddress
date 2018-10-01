import java.util.Scanner;

public class Main {
	public static void main (String[] args) {
		Scanner inputReader = new Scanner(System.in);
		String inputIpAddress;
		System.out.println("iPv4 チェックします。");
		do {
			System.out.println("正しいプライベートIPアドレスのバージョン４(Ipv4)を入力してください: ");
			inputIpAddress = inputReader.nextLine();
		} while(!IpCalculator.isValidPrivateIpv4(inputIpAddress));

		System.out.println("指定したIPアドレスのネットワークアドレスを取得する関数: "+ IpCalculator.fetchNetworkAddress("192.168.2.65", "255.255.255.192"));
	}
}
