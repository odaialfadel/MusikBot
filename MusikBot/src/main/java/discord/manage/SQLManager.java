package discord.manage;

public class SQLManager {

	public static void onCreate() {
		
		LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS reactroles (id integer PRIMARY KEY AUTOINCREMENT, guildid INTEGER, channelid INTEGER, messageid INTEGER, emote TEXT, rollenid INTEGER)");
		
		LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS timeranks(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, userid INTEGER, guildid INTEGER, time TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
		
		LiteSQL.onUpdate("CREATE TABLE IF NOT EXISTS musicchannel(id integer PRIMARY KEY AUTOINCREMENT, guildid INTEGER, channelid INTEGER)");
	}
	
	
}
