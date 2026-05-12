package com.arabicpdftoword.app.core.network;

import com.arabicpdftoword.app.core.util.NoorPreferences;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class AuthInterceptor_Factory implements Factory<AuthInterceptor> {
  private final Provider<NoorPreferences> preferencesProvider;

  public AuthInterceptor_Factory(Provider<NoorPreferences> preferencesProvider) {
    this.preferencesProvider = preferencesProvider;
  }

  @Override
  public AuthInterceptor get() {
    return newInstance(preferencesProvider.get());
  }

  public static AuthInterceptor_Factory create(Provider<NoorPreferences> preferencesProvider) {
    return new AuthInterceptor_Factory(preferencesProvider);
  }

  public static AuthInterceptor newInstance(NoorPreferences preferences) {
    return new AuthInterceptor(preferences);
  }
}
