package br.com.dgss.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class ConfigMailUtil {
	private File configFile = new File("smtp.properties");
	private Properties configProps;

	public Properties loadProperties() throws IOException {
		Properties defaultProps = new Properties();
		// sets default properties
		defaultProps.setProperty("mail.smtp.host", "smtp.gmail.com");
		defaultProps.setProperty("mail.smtp.port", "587");
		defaultProps.setProperty("mail.user", "binaryselenium@gmail.com");
		defaultProps.setProperty("mail.password", "selenium123");
		defaultProps.setProperty("mail.smtp.starttls.enable", "true");
		defaultProps.setProperty("mail.smtp.auth", "true");
		defaultProps.setProperty("mail.debug", "true");

		configProps = new Properties(defaultProps);

		// loads properties from file
		if (configFile.exists()) {
			InputStream inputStream = new FileInputStream(configFile);
			configProps.load(inputStream);
			inputStream.close();
		}

		return configProps;
	}

	public void saveProperties(String host, String port, String user, String pass) throws IOException {
		configProps.setProperty("mail.smtp.host", host);
		configProps.setProperty("mail.smtp.port", port);
		configProps.setProperty("mail.user", user);
		configProps.setProperty("mail.password", pass);
		configProps.setProperty("mail.smtp.starttls.enable", "true");
		configProps.setProperty("mail.smtp.auth", "true");
		configProps.setProperty("mail.debug", "true");


		OutputStream outputStream = new FileOutputStream(configFile);
		configProps.store(outputStream, "host setttings");
		outputStream.close();
	}
}
