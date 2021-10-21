package discord.musik;

import java.nio.ByteBuffer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;

import net.dv8tion.jda.api.audio.AudioSendHandler;

public class AudioPlayerSendHandler implements AudioSendHandler{
	
	private final AudioPlayer audioPlayer;
	private AudioFrame lastFrame;
	
	
	//
	public AudioPlayerSendHandler(AudioPlayer audioPlayer) {
		this.audioPlayer = audioPlayer;
	}
	
	
	/*
	 * bot fragt ob was neues bekommt
	 * dazu gibt der audioPlayer provide
	 * wenn was drin wird in lastFrame gespeichert
	 * ansosten wird eine null = false uebergeben bekommen
	 */
	@Override
	public boolean canProvide() {
		lastFrame = audioPlayer.provide();
		return lastFrame !=null;
	}

	@Override
	public ByteBuffer provide20MsAudio() {
		return ByteBuffer.wrap(lastFrame.getData());
	}
	
	@Override
	public boolean isOpus() {
		return true;
	}

}
