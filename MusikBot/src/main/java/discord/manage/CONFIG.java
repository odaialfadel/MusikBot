package discord.manage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class CONFIG {
	
	private String TOKEN, PREFIX;
	Properties config = new Properties();
	
	FileInputStream fis;
	FileOutputStream fos;
	File file = new File("Target//Config.properties");


	public void readProperty() {
		
		try {
			fis = new FileInputStream(configFile());
			config.load(fis);
			setToken(config.getProperty("token"));
			setPrefix(config.getProperty("prefix"));
			
			
		} catch (IOException e) {
			System.err.println("File doesent exist");
		}

	}
	public boolean isValidConfig() {
		if(file.exists()) {
			
			return true;
		}
		return false;
	}
	public boolean isValidToken() {
		if(getToken()!=null) {
			
			return true;
		}
		return false;
	}



	public File configFile() {
		
		if (!file.exists()) {
			file.getParentFile().mkdir();
			try {
				file.createNewFile();
				return file;
			} catch (IOException e) {
				System.err.println("This File cannt be created!");
			}
		}
		return file;
	}

	public void writeConfig(String token, String prefix, File property) {
		try {
			readProperty();
			fos = new FileOutputStream(property);
			config.setProperty("token", token);
			config.setProperty("prefix", prefix);
			config.store(fos, null);
			fos.close();
		} catch (IOException ex) {
			System.err.println(("Datei kann nicht gelesen werden oder nicht gefunden!!"));
		}
	}
	public String changePrefix( String prefix) {
		try {
			readProperty();
			fos = new FileOutputStream(file);
			config.setProperty("prefix", prefix);
			config.store(fos, null);
			fos.close();
		} catch (IOException ex) {
			System.err.println(("Datei kann nicht gelesen werden oder nicht gefunden!!"));
		}
		
		return getPrefix();
	}
	
	
	public String getToken() {
		return TOKEN;
	}

	public void setToken(String token) {
		this.TOKEN = token;
	}

	public String getPrefix() {
		return PREFIX;
	}

	public void setPrefix(String prefix) {
		this.PREFIX = prefix;
	}
	

}
