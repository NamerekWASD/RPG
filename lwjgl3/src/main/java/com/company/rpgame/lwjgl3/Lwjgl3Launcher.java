package com.company.rpgame.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.company.rpgame.Application;
import com.github.czyzby.autumn.fcs.scanner.DesktopClassScanner;
import com.github.czyzby.autumn.mvc.application.AutumnApplication;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
	public static void main(String[] args) {
		Lwjgl3Application application = createApplication();
	}

	private static Lwjgl3Application createApplication() {
		return new Lwjgl3Application(new AutumnApplication(new DesktopClassScanner(), Application.class),
				getDefaultConfiguration());
	}

	private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
		Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
		configuration.setIdleFPS(60);
		configuration.setForegroundFPS(60);
		configuration.setTitle("RPGame");
		configuration.setWindowedMode(Application.WIDTH, Application.HEIGHT);
		configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
		return configuration;
	}
}