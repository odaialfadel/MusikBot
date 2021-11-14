package discord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import javax.security.auth.login.LoginException;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;

//import discord.commands.StatschannelCommand;
import discord.listener.CommandListner;
import discord.listener.JoinListener;
import discord.listener.ReactionListener;
//import discord.listener.VoiceListener;
import discord.manage.CommandManager;
import discord.manage.LiteSQL;
import discord.manage.SQLManager;
import discord.manage.CONFIG;
import discord.musik.PlayerManager;
//import discord.listener.VoiceListener;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;




/*
 * @Authur Odai Al Fadel
 * @Date 12.11.2021
 * @Name MusikBot
 * @Version Beta_1.0
 */
public class Launch {

	public static Launch INSTANCE;
	
	private CommandManager cmdMan;	
	public ShardManager shardMan;
	private Thread loop;
	public AudioPlayerManager audioPlayerManager;
	public PlayerManager playerManager;
	
	
	public static void main(String[] args) {
		try {
			new Launch();
		} catch (LoginException e) {
			e.printStackTrace();
		}

	}

	/*
	 * Using ShardManger
	 */
	public Launch() throws LoginException {
		//damit kann man genau auf dieses INSTANCE zugreifen
		INSTANCE = this;

		LiteSQL.connect();
		SQLManager.onCreate();

		// um den BOT TOKEN von dem Config-Datei zu lesen!
		CONFIG TOKEN = new CONFIG();
		TOKEN.readProperty();

		//damit das Bot startet benoetigt ein Token zu erkennen welches Bot ist es
		DefaultShardManagerBuilder jdaBuilder = DefaultShardManagerBuilder.createDefault(TOKEN.getToken());

		jdaBuilder.setActivity(Activity.playing(TOKEN.getPrefix() + " is the Current Prefix"));
		jdaBuilder.setStatus(OnlineStatus.DO_NOT_DISTURB);
		
		this.audioPlayerManager = new DefaultAudioPlayerManager();
		this.playerManager = new PlayerManager();
		
		this.cmdMan = new CommandManager();

		// auf die Aenderungen zu hoeren
		jdaBuilder.addEventListeners(new CommandListner());
		//jdaBuilder.addEventListeners(new VoiceListener());
		jdaBuilder.addEventListeners(new ReactionListener());
		jdaBuilder.addEventListeners(new JoinListener());
		shardMan = jdaBuilder.build();

		System.out.println("Yeaaaay ich bin Online!");
		
		AudioSourceManagers.registerRemoteSources(audioPlayerManager);
		audioPlayerManager.getConfiguration().setFilterHotSwapEnabled(true);
		
		shutdown();
		runLoop();
	}

	public void shutdown() {
		new Thread(() -> {
			String line = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			try {
				while ((line = reader.readLine()) != null) {
					if (line.equalsIgnoreCase("exit")) {
						shutdown = true;
						if (shardMan != null) {
							shardMan.setStatus(OnlineStatus.OFFLINE);
							shardMan.shutdown();
							LiteSQL.disconnect();
							
							System.out.println("Oh neeein!! nie wieder..");

							if (loop != null) {
								loop.interrupt();
							}

							reader.close();
							break;
						}
					}else if(line.equalsIgnoreCase("info")) {
						for(Guild guild : shardMan.getGuilds()) {
							System.out.println(guild.getName() + " " + guild.getIdLong());
							
						}
						}else {
						System.out.println("Use *exit* to shutdown!");
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}).start();
	}

	public boolean shutdown = false;
	public boolean hasStarted = false;

	public void runLoop() {
		this.loop = new Thread(() -> {

			Long time = System.currentTimeMillis();
			while (!shutdown) {
				if (System.currentTimeMillis() >= time + 1000) {
					time = System.currentTimeMillis();
					onSecond();
				}
			}

		});
		this.loop.setName("Loop");
		this.loop.start();
	}

	String[] status = new String[] { "Programmieren.", "Discord", "Bazboz", "with Odai.", "Rocket League.","with myself",
			"%members online." };
//	int[] colors = new int[] {0xff9478, 0xd2527f, 0x00b5cc, 0x19b5fe, 0x2ecc71, 0x23cba7, 0x00e640, 0x8c14fc, 0x9f5afd, 0x663399};
	int next = 180;

	//damit das Bot sein Status aendert
	public void onSecond() {
		if (next <= 0) {
			Random rand = new Random();
			int i = rand.nextInt(status.length);
			shardMan.getShards().forEach(jda -> {
				//um zu wissen, wieviele benutzer im Server sind
				String text = status[i].replaceAll("%members", "" + jda.getUsers().size());
				jda.getPresence().setActivity(Activity.playing(text));
			});
			//nach 180 sekunden das Status zufaellig aendrn
			next = 180;
		} else {
			next--;
		}
	}
	
/*
	public void onCheckTimeRanks() {
		try {
			ResultSet set = LiteSQL.onQueryRAW(
					"SELECT userid, guildid FROM timeranks WHERE ((julianday(CURRENT_TIMESTAMP) - julianday(time)) * 1000) >= 1");
			List<Long> users = new ArrayList<>();
			// int count = 0;
			while (set.next()) {
				long userid = set.getLong("userid");
				long guildid = set.getLong("guildid");

				Guild guild = this.shardMan.getGuildById(guildid);
				guild.removeRoleFromMember(guild.getMemberById(userid), guild.getRoleById(899709570908315689l))
						.complete();
				System.out.println("Role entfernt.");

				users.add(userid);
			}

			for (long userid : users) {
				LiteSQL.onUpdate("DELETE FROM timeranks WHERE userid = " + userid);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	*/
	

	public CommandManager getCmdMan() {
		return cmdMan;
	}
	

}
