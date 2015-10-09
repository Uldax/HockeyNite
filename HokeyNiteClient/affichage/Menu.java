package affichage;


public class Menu {

	public static void affListMatch(Object[] ListMatch){
		System.out.println(" -- ");
		for(int i=0; i<ListMatch.length;i++){
			int a = i + 1;
			if(ListMatch[i] != null) System.out.println(" "+a+" - "+ListMatch[i].toString());
		}
		System.out.println(" 0 - exit");
		System.out.println(" -- ");
	}
	
	public static class WaitMessage implements Runnable {
		private int timer = 0;

		public WaitMessage(int time) {
			this.timer = time;
		}
		
		@Override
		public void run() {
			while(true) {
				System.out.print(".");	        	
	            try {
	                Thread.sleep(this.timer);
	            } catch (InterruptedException e) {
	            	System.out.println("");
	            	Thread.currentThread().interrupt();
	            	break;
	            }
	        }
		}

	}
	
}
