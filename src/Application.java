public class Application {
	public static void main(String[] args){
		//Menu menu = new Menu();
		//menu.run();
		SpermRace spermRace = new SpermRace();
		spermRace.run();
		try{
			Results r = spermRace.getResults();
			System.out.println(
					"\n"+r.getMessage()+"\n"+
					"\nscore: "+r.getPoints()+
					"\nfinal placement: "+r.getPosition()+
					"\nhow many right key strokes: "+r.getGotRight()+
					"\nhow many wrong key strokes: "+r.getGotWrong());
		}catch(Exception e) { }
	}
}
