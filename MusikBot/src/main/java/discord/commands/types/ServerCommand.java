package discord.commands.types;



import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

//Kommandos Entwurf
public interface ServerCommand {
	
	public void preformCommand(Member member, TextChannel channel, Message message);
	
	

}
