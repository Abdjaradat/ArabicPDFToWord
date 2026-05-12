package com.arabicpdftoword.app.presentation.settings;

import android.content.Context;
import com.arabicpdftoword.app.core.util.NoorPreferences;
import com.arabicpdftoword.app.domain.repository.ConversionRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast"
})
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<NoorPreferences> prefsProvider;

  private final Provider<ConversionRepository> conversionRepositoryProvider;

  private final Provider<Context> contextProvider;

  public SettingsViewModel_Factory(Provider<NoorPreferences> prefsProvider,
      Provider<ConversionRepository> conversionRepositoryProvider,
      Provider<Context> contextProvider) {
    this.prefsProvider = prefsProvider;
    this.conversionRepositoryProvider = conversionRepositoryProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(prefsProvider.get(), conversionRepositoryProvider.get(), contextProvider.get());
  }

  public static SettingsViewModel_Factory create(Provider<NoorPreferences> prefsProvider,
      Provider<ConversionRepository> conversionRepositoryProvider,
      Provider<Context> contextProvider) {
    return new SettingsViewModel_Factory(prefsProvider, conversionRepositoryProvider, contextProvider);
  }

  public static SettingsViewModel newInstance(NoorPreferences prefs,
      ConversionRepository conversionRepository, Context context) {
    return new SettingsViewModel(prefs, conversionRepository, context);
  }
}
