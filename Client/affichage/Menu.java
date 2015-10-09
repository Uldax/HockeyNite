package affichage;

public class Menu {

	public static void affListMatch(Object[] ListMatch){
		System.out.println(" -- ");
		for(int i=0; i<ListMatch.length;i++){
			int a = i + 1;
			System.out.println(" "+a+" - "+ListMatch[i].toString());
		}
		System.out.println(" 0 - exit");
		System.out.println(" -- ");
	}
	
}
