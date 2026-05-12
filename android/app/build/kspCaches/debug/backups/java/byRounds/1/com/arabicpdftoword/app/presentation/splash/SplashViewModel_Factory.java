package com.arabicpdftoword.app.presentation.splash;

import com.arabicpdftoword.app.core.network.ApiService;
import com.arabicpdftoword.app.core.util.CrashHandler;
import com.arabicpdftoword.app.core.util.NoorPreferences;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
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
public final class SplashViewModel_Factory implements Factory<SplashViewModel> {
  private final Provider<NoorPreferences> prefsProvider;

  private final Provider<CrashHandler> crashHandlerProvider;

  private final Provider<ApiService> apiServiceProvider;

  public SplashViewModel_Factory(Provider<NoorPreferences> prefsProvider,
      Provider<CrashHandler> crashHandlerProvider, Provider<ApiService> apiServiceProvider) {
    this.prefsProvider = prefsProvider;
    this.crashHandlerProvider = crashHandlerProvider;
    this.apiServiceProvider = apiServiceProvider;
  }

  @Override
  public SplashViewModel get() {
    return newInstance(prefsProvider.get(), crashHandlerProvider.get(), apiServiceProvider.get());
  }

  public static SplashViewModel_Factory create(Provider<NoorPreferences> prefsProvider,
      Provider<CrashHandler> crashHandlerProvider, Provider<ApiService> apiServiceProvider) {
    return new SplashViewModel_Factory(prefsProvider, crashHandlerProvider, apiServiceProvider);
  }

  public static SplashViewModel newInstance(NoorPreferences prefs, CrashHandler crashHandler,
      ApiService apiService) {
    return new SplashViewModel(prefs, crashHandler, apiService);
  }
}
