import java.util.Scanner;

public class Lotto {
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("podaj liczbe liczb: ");
		int quantity = scan.nextInt();
		System.out.println("podaj poczatek przedzialu: ");
		int min = scan.nextInt();
		System.out.println("podaj koniec przedzialu: ");
		int max = scan.nextInt();
		int randoms[] = new int[quantity];
		
		for(int i=1; i<=quantity; i++) {			
			int range = max - min + 1;		
			int random = (int)(Math.random()*range)+min;

			checkIfDouble(random);
			
			if(i == 1) {
				randoms[i-1] = random;
			} else {
				boolean sibling = checkIfDouble(i-1, randoms, random, false);
				if(!siblig) {
					randoms[i-1] = random;
				} else {
					
				}
			}
		}
		
		for(int i=0; i<quantity; i++) {
			System.out.println(randoms[i]);
		}
	}
	
	public static boolean checkIfDouble(int index, int arr[], int random, boolean sibling) {	
		if(arr[index] == random) {
			sibling = true;
		} 
		
		if(index == 0) {
			return sibling;
		} else {
			checkIfDouble(index-1, randoms[], random, sibling);
		}
	}
}
