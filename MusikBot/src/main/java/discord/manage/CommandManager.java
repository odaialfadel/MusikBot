package discord.manage;

import java.util.concurrent.ConcurrentHashMap;

import discord.commands.ClearCommand;
import discord.commands.ClientInfo;
import discord.commands.HelpCommand;
import discord.commands.HugCommand;
import discord.commands.PreviewCommand;
import discord.commands.ReactCommand;
import discord.commands.ReactRolesCommand;
import discord.commands.RoleCreation;
import discord.commands.SQLCommand;
import discord.commands.StatschannelCommand;
import discord.commands.TimeRank;
import discord.commands.types.ServerCommand;
import discord.musik.commands.PlayCommand;
import discord.musik.commands.StopCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class CommandManager {
	
	
	public ConcurrentHashMap<String, ServerCommand> commands;
	
	public CommandManager() {
		this.commands=new ConcurrentHashMap<>();
		
		this.commands.put("clear", new ClearCommand());
		this.commands.put("help", new HelpCommand());
		this.commands.put("hug", new HugCommand());
		this.commands.put("info", new ClientInfo());
		this.commands.put("sql", new SQLCommand());
		this.commands.put("preview", new PreviewCommand());
		this.commands.put("react", new ReactCommand());
		this.commands.put("reactrole", new ReactRolesCommand());
		this.commands.put("timerank", new TimeRank());
		this.commands.put("statchannel", new StatschannelCommand());
		this.commands.put("createrole", new RoleCreation());
		this.commands.put("play", new PlayCommand());
		this.commands.put("stop", new StopCommand());
	}
	public boolean preform(String command,Member m, TextChannel channel, Message message) {
		
		ServerCommand cmd;
		if((cmd = this.commands.get(command.toLowerCase())) != null){
			cmd.preformCommand(m, channel, message);
			return true;
		}
		
		return false;
	}
}
