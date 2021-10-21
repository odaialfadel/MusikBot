package discord.commands;



import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import discord.commands.types.ServerCommand;
import discord.manage.LiteSQL;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class SQLCommand implements ServerCommand {

	@Override
	public void preformCommand(Member member, TextChannel channel, Message message) {
			
			String sql = message.getContentDisplay().substring(5);
			
			String test = sql.toLowerCase();
			if(test.startsWith("select")) {
				try {
					ResultSet set = LiteSQL.onQueryRAW(sql);
					ResultSetMetaData rsmd = set.getMetaData();
					int count = rsmd.getColumnCount();
					
					String output = "";
					
					for(int col = 1; col <= count; col++) {
						output += rsmd.getColumnLabel(col) + " ";
					}
					output += "\n--------------\n";
					
					while(set.next()) {
						
						for(int col = 1; col <= count; col++) {
							output += set.getObject(col).toString() + " ";
						}
						output += "\n";
					}
					
					channel.sendMessage("Output:```" + output + "```").queue();
					
					
				} catch (SQLException e) {
					channel.sendMessage("Error while performing sql query: " + e.getMessage()).queue();
				}
			}
			else {
				try {
					LiteSQL.onUpdateRAW(sql);
					channel.sendMessage("ok.").queue();
				} catch (SQLException e) {
					channel.sendMessage("Error while performing sql update: " + e.getMessage()).queue();
				}
			}
	
	}

}
