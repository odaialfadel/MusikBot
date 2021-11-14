package discord.commands;



import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import discord.commands.types.ServerCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;

public class ClearCommand implements ServerCommand{

	@Override
	public void preformCommand(Member member, TextChannel channel, Message message) {
		
		if(member.hasPermission(channel,Permission.MESSAGE_MANAGE)) {
			
			//-clear 3
			String[] args = message.getContentDisplay().split("\\s+");
			if(args.length == 2) {
								
				try {
					int amount = Integer.parseInt(args[1]);
					channel.purgeMessages(get(channel,amount));
					channel.sendMessage("```diff\r\n+ "+amount+" Deleted Messages!!!\r\n```").complete().delete().queueAfter(4, TimeUnit.SECONDS);
					return;
				}catch(NumberFormatException e){
					e.printStackTrace();
				}
			}
		}
	}
	public List<Message> get(MessageChannel channel, int amount){
		List<Message> messages = new ArrayList<>();
		
		int i = amount + 1;
		
		for(Message message : channel.getIterableHistory().cache(false)) {
			if(!message.isPinned()) {
				messages.add(message);
			}
			if(--i <=0) {
				break;
			}
		}
		return messages;
	}

}
