package affichage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import dataObject.ListMatchName;

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
	
	//Display all match with juste Domicile vs Exterieur and current time
	public static void affListMatchName(ListMatchName ListMatch){
		System.out.println(" -- ");
		
		HashMap<Integer, String> map = ListMatch.getMatchName();
		Iterator<Entry<Integer, String>> it = map.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        System.out.println("(id match :" +pair.getKey()+ " Press "+ ((int)pair.getKey()+1) + " for detail) = " + pair.getValue());
	        //it.remove(); // avoids a ConcurrentModificationException
	    }	
		System.out.println(" 0 - exit");
		System.out.println(" -- ");
	}
	
	//Update a little more
	public static void affDetailsMatch(Object Match){
		System.out.println(" -- ");
		System.out.println(Match.toString());
		System.out.println(" 0 - back");
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
