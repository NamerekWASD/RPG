package com.company.rpgame.configuration;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.company.rpgame.Application;
import com.company.rpgame.helper.Constants;
import com.company.rpgame.service.ApplicationService;
import com.github.czyzby.autumn.annotation.Component;
import com.github.czyzby.autumn.annotation.Initiate;
import com.github.czyzby.autumn.annotation.Inject;
import com.github.czyzby.autumn.mvc.component.asset.AssetService;
import com.github.czyzby.autumn.mvc.component.ui.SkinService;
import com.github.czyzby.autumn.mvc.stereotype.preference.*;
import com.github.czyzby.kiwi.util.gdx.asset.lazy.provider.ObjectProvider;
import com.github.czyzby.lml.parser.LmlSyntax;
import com.github.czyzby.lml.parser.action.ActionContainer;
import com.github.czyzby.lml.parser.impl.DefaultLmlSyntax;
import com.github.czyzby.lml.util.Lml;
import lombok.val;

import java.util.ArrayList;

/** Thanks to the Component annotation, this class will be automatically found and processed.
 * This is a utility class that configures application settings. */
@Component
public class Configuration implements ActionContainer {
	/** Name of the application's preferences file. */
	public static final String PREFERENCES = "RPGame";
	/** Path to the internationalization bundle. */
	@I18nBundle private final String bundlePath = "i18n/bundle";

	@Skin() private final String skinPath = "ui/atlas/main/uiskin.json";

	@LmlParserSyntax private final LmlSyntax syntax = new DefaultLmlSyntax();

	/** Parsing macros available in all views. */
	@LmlMacro private final String globalMacro = "lml/macros/global.lml";

	@StageViewport private final ObjectProvider<Viewport> viewportProvider = () ->
			new FillViewport(Application.WIDTH/ Constants.APPLICATION_SCALE, Application.HEIGHT/Constants.APPLICATION_SCALE);


	/** These i18n-related fields will allow LocaleService to save game's locale in preferences file. Locale changing
	 * actions will be automatically added to LML templates - see settings.lml template. */
	@I18nLocale(propertiesPath = PREFERENCES) private final String localePreference = "locale";

	@AvailableLocales private final String[] availableLocales = new String[] { "en", "ru", "ua"};

	/** Setting the default Preferences object path. */
	@Preference private final String preferencesPath = PREFERENCES;

	@Inject private ApplicationService applicationService;

	/** Thanks to the Initiate annotation, this method will be automatically invoked during context building. All
	 * method's parameters will be injected with values from the context. */
	@Initiate(priority = 10)
	public void initiateConfiguration(final AssetService assetService, final SkinService skinService) {
		Lml.EXTRACT_UNANNOTATED_METHODS = false;
		Lml.INFO_LOGS_ON = true;

		ArrayList<String> directories = new ArrayList<>();
		directories.add("images");
		directories.add("animations");
		for (val directory :
				directories) {
			FileHandle assetsPath = Gdx.files.internal(directory);
			loadAssets(assetService, assetsPath);
		}
		skinService.addSkin("main", new com.badlogic.gdx.scenes.scene2d.ui.Skin(Gdx.files.internal(skinPath)));
	}
	private void loadAssets(AssetService manager, FileHandle assetsPath) {
		for (val file :
				assetsPath.list()) {
			if(file.isDirectory()){
				loadAssets(manager, file);
			}
			else{
				try {
					manager.load(file.path(), Texture.class);
				}catch (Exception e){
					System.out.println(e.getMessage());
				}
			}
		}
	}
}