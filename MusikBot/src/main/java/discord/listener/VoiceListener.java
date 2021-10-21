package discord.listener;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.entities.Category;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class VoiceListener extends ListenerAdapter{
	long id;
	public List<Long> tempChannels;
	
	public VoiceListener() {
		this.tempChannels = new ArrayList<>();
	}
	
	@Override
	public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
		//807975119083143198l
		VoiceChannel joined;
		if((joined = event.getChannelJoined()).getIdLong() == event.getChannelJoined().getIdLong()) {
			id=event.getChannelJoined().getIdLong();
			Category cat = joined.getParent();
			Member member = event.getEntity();
			VoiceChannel vc = cat.createVoiceChannel("Temporaer "+member.getEffectiveName()+" Kanal").complete();
			vc.getManager().setUserLimit(joined.getUserLimit()).queue();
			vc.getGuild().moveVoiceMember(member, vc).queue();
			
			this.tempChannels.add(vc.getIdLong());
		}
		
	}
	
	@Override
	public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
		VoiceChannel channel = event.getChannelLeft();
		if(channel.getMembers().size() <= 0) {
			if(this.tempChannels.contains(channel.getIdLong())) {				
				this.tempChannels.remove(channel.getIdLong());
				channel.delete().queue();
			}
		}
	}
	@Override
	public void onGuildVoiceMove(GuildVoiceMoveEvent event) {
		onLeave(event.getChannelLeft());
		onJoin(event.getChannelJoined(),event.getEntity());
	}
	public void onJoin(VoiceChannel joined, Member member) {
		if(joined.getIdLong() == id) {
			Category cat = joined.getParent();
			VoiceChannel vc = cat.createVoiceChannel("Temporaer "+member.getEffectiveName()+" Kanal").complete();
			vc.getManager().setUserLimit(joined.getUserLimit()).queue();
			vc.getGuild().moveVoiceMember(member, vc).queue();
			
			this.tempChannels.add(vc.getIdLong());
		}
	}
	public void onLeave(VoiceChannel channel) {
		if(channel.getMembers().size() <= 0) {
			if(this.tempChannels.contains(channel.getIdLong())) {				
				this.tempChannels.remove(channel.getIdLong());
				channel.delete().queue();
			}
		}
	}

}
