package items;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import protocols.database.ItemDatabase;
import databases.GameDatabase;

public abstract class ItemsManager {
	private static final short MAX_ITEMS = 120;
	private static final ConcurrentHashMap<Integer,ItemDatabase> itemBuffer = new ConcurrentHashMap<Integer,ItemDatabase>();
	private static final ConcurrentLinkedQueue<Integer> itemBufferOrder = new ConcurrentLinkedQueue<Integer>();

	
	//get
	public static ItemDatabase getItemDatabase(int index) throws SQLException{
		if (itemBuffer.containsKey(index)){
			itemBufferOrder.remove(index);
			itemBufferOrder.add(index);
			return itemBuffer.get(index);
		}
		return getSQLDatabase(index);
	}

	private static ItemDatabase itemDb = null;
	private static ResultSet rs;
	private static synchronized ItemDatabase getSQLDatabase(int index) throws SQLException{	
		rs = GameDatabase.instance().selectQuery("SELECT * FROM " +
				"\"Items\" WHERE id='"+index+"' LIMIT 1");
		
		
		while (rs.next()){
			itemDb = new ItemDatabase();
			itemDb.setId(rs.getInt("id"));
			itemDb.setName(rs.getString("name"));
			itemDb.setShopPrice(rs.getShort("shopprice"));
			itemDb.setDescription(rs.getString("description"));
			itemDb.setMAttack(rs.getShort("mattack"));
			itemDb.setPAttack(rs.getShort("pattack"));
			itemDb.setMDefense(rs.getShort("mdefense"));
			itemDb.setPDefense(rs.getShort("pdefense"));
			itemDb.setHP(rs.getShort("hp"));
			itemDb.setMP(rs.getShort("mp"));
			itemDb.setAP(rs.getShort("ap"));
			itemDb.setIntelligence(rs.getShort("intelligence"));
			itemDb.setWisdom(rs.getShort("wisdom"));
			itemDb.setHatred(rs.getShort("hatred"));
			itemDb.setFaith(rs.getShort("faith"));
			itemDb.setSalePrice(rs.getInt("saleprice"));
			itemDb.setType(rs.getShort("type"));
			itemDb.setFocus(rs.getShort("focus"));
			itemDb.setPrecision(rs.getShort("precision"));
			itemDb.setEvasion(rs.getShort("evasion"));
			itemDb.setAgility(rs.getShort("agility"));
			itemDb.setStockable(rs.getBoolean("stockable"));
			itemDb.setTextureName(rs.getShort("textureindex"));
			itemDb.setTextureRegion(rs.getShort("regionindex"));
			itemDb.setUsable(rs.getBoolean("usable"));
			
			itemBuffer.put(itemDb.getID(), itemDb);
			if (itemBufferOrder.contains(itemDb.getID())) itemBufferOrder.remove(itemDb.getID());
			itemBufferOrder.add(itemDb.getID());
			
			reduceBuffer();
		}
		
		rs.close();
		return itemDb;
	}
	public static ConcurrentHashMap<Integer, ItemDatabase> getItemBuffer(){return itemBuffer;}
	
	
	private static void reduceBuffer(){
		while (ItemsManager.itemBuffer.size() > ItemsManager.MAX_ITEMS)
			ItemsManager.itemBuffer.remove(ItemsManager.itemBufferOrder.poll());
	}
	
}
