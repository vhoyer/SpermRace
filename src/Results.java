public class Results {
	int points, gotRight, gotWrong;
	String position, msg = "You've finished the race!";
	public Results(int points, int gotRight, int gotWrong, String position, boolean died){
		this.position = position;
		this.points = points;
		this.gotRight = gotRight;
		this.gotWrong = gotWrong;
		if (died) msg = "You failed, died in the middle of the way.";
		else if (position != "1st") msg += "\nAlthough, you wasn't born, but hey, at least you finished.";
	}
	public int getPoints() {
		return points;
	}
	public int getGotRight(){
		return gotRight;
	}
	public int getGotWrong(){
		return gotWrong;
	}
	public String getPosition(){
		return position;
	}
	public String getMessage(){
		return msg;
	}
}
