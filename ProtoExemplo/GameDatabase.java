package databases;

public class GameDatabase extends Database{
	private static String url = "jdbc:postgresql://localhost/game";
	private static GameDatabase instance = new GameDatabase(url);
	//
	
	GameDatabase(String url) {
		super(url);
		// TODO Auto-generated constructor stub
	}
	
	public static GameDatabase instance(){
		return instance;
	}
	
}
