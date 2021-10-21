package discord.listener;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class JoinListener extends ListenerAdapter {

	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		Member member = event.getMember();
		TextChannel channel;
		
		if((channel = event.getGuild().getDefaultChannel()) !=null) {
			channel.sendMessage(member.getAsMention()+" has just slid like super sonic").queue();
		}
	}
	
	@Override
	public void onGuildMemberRemove(GuildMemberRemoveEvent event) {
		Member member = event.getMember();
		TextChannel channel;
		
		if((channel = event.getGuild().getDefaultChannel()) !=null) {
			channel.sendMessage(member.getAsMention()+" has just left the Server like a pussy").queue();
			
		}
	}
	
	
}
