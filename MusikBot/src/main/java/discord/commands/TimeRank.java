package discord.commands;

import java.util.List;

import discord.commands.types.ServerCommand;
import discord.manage.LiteSQL;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

public class TimeRank implements ServerCommand {

	@Override
	public void preformCommand(Member member, TextChannel channel, Message message) {

		     //-timerank @User
			//RollenID 582279555021012992
			
			
		List<Member> members = message.getMentionedMembers();
		
		if(members.size() >= 1) {
			for(Member memb : members) {
				Guild guild = channel.getGuild();
				Role role = guild.getRoleById(899709570908315689l);
				if(!memb.getRoles().contains(role)) {
					guild.addRoleToMember(memb, role).queue();
					
					LiteSQL.onUpdate("INSERT INTO timeranks(userid, guildid) VALUES(" + memb.getIdLong() + ", " + guild.getIdLong() + ")");
					
					channel.sendMessage("Rolle hinzugefügt.").queue();
				}
			}
		}
	
		
	}

}
