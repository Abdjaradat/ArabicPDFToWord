package com.arabicpdftoword.app.core.di;

import com.arabicpdftoword.app.core.network.AuthInterceptor;
import com.arabicpdftoword.app.core.util.NoorPreferences;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class NetworkModule_ProvideAuthInterceptorFactory implements Factory<AuthInterceptor> {
  private final Provider<NoorPreferences> preferencesProvider;

  public NetworkModule_ProvideAuthInterceptorFactory(
      Provider<NoorPreferences> preferencesProvider) {
    this.preferencesProvider = preferencesProvider;
  }

  @Override
  public AuthInterceptor get() {
    return provideAuthInterceptor(preferencesProvider.get());
  }

  public static NetworkModule_ProvideAuthInterceptorFactory create(
      Provider<NoorPreferences> preferencesProvider) {
    return new NetworkModule_ProvideAuthInterceptorFactory(preferencesProvider);
  }

  public static AuthInterceptor provideAuthInterceptor(NoorPreferences preferences) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideAuthInterceptor(preferences));
  }
}
