package com.company.rpgame.configuration;

	import com.badlogic.gdx.assets.AssetManager;
	import com.badlogic.gdx.utils.viewport.FitViewport;
	import com.badlogic.gdx.utils.viewport.Viewport;
	import com.company.rpgame.helpers.Constants;
	import com.github.czyzby.autumn.annotation.Component;
	import com.github.czyzby.autumn.annotation.Initiate;
	import com.github.czyzby.autumn.mvc.component.ui.SkinService;
	import com.github.czyzby.autumn.mvc.stereotype.preference.AvailableLocales;
	import com.github.czyzby.autumn.mvc.stereotype.preference.I18nBundle;
	import com.github.czyzby.autumn.mvc.stereotype.preference.I18nLocale;
	import com.github.czyzby.autumn.mvc.stereotype.preference.LmlMacro;
	import com.github.czyzby.autumn.mvc.stereotype.preference.LmlParserSyntax;
	import com.github.czyzby.autumn.mvc.stereotype.preference.Preference;
	import com.github.czyzby.autumn.mvc.stereotype.preference.Skin;
	import com.github.czyzby.autumn.mvc.stereotype.preference.StageViewport;
	import com.github.czyzby.kiwi.util.gdx.asset.lazy.provider.ObjectProvider;
	import com.github.czyzby.lml.parser.LmlSyntax;
	import com.github.czyzby.lml.parser.action.ActionContainer;
	import com.github.czyzby.lml.parser.impl.DefaultLmlSyntax;
	import com.github.czyzby.lml.util.Lml;
	import com.company.rpgame.Application;
/** Thanks to the Component annotation, this class will be automatically found and processed.
	 *
	 * This is a utility class that configures application settings. */
	@Component
	public class Configuration implements ActionContainer {
		/** Name of the application's preferences file. */
		public static final String PREFERENCES = "RPGame";
		/** Path to the internationalization bundle. */
		@I18nBundle private final String bundlePath = "i18n/bundle";

		@Skin private final String skinPath = "ui/uiskin.json";

		@LmlParserSyntax private final LmlSyntax syntax = new DefaultLmlSyntax();

		/** Parsing macros available in all views. */
		@LmlMacro private final String globalMacro = "ui/templates/macros/global.lml";

		@StageViewport private final ObjectProvider<Viewport> viewportProvider = () ->
				new FitViewport(Application.WIDTH/ Constants.APPLICATION_SCALE, Application.HEIGHT/Constants.APPLICATION_SCALE);


		/** These i18n-related fields will allow LocaleService to save game's locale in preferences file. Locale changing
		 * actions will be automatically added to LML templates - see settings.lml template. */
		@I18nLocale(propertiesPath = PREFERENCES) private final String localePreference = "locale";

		@AvailableLocales private final String[] availableLocales = new String[] { "en", "pl", "ru"};

		/** Setting the default Preferences object path. */
		@Preference private final String preferencesPath = PREFERENCES;

		/** Thanks to the Initiate annotation, this method will be automatically invoked during context building. All
		 * method's parameters will be injected with values from the context. */
		@Initiate
		public void initiateConfiguration(final AssetManager manager, final SkinService skinService) {

			Lml.EXTRACT_UNANNOTATED_METHODS = false;
			Lml.INFO_LOGS_ON = true;
		}
	}