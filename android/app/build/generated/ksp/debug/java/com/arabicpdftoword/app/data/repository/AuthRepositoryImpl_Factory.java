package com.arabicpdftoword.app.data.repository;

import com.arabicpdftoword.app.core.network.ApiService;
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
public final class AuthRepositoryImpl_Factory implements Factory<AuthRepositoryImpl> {
  private final Provider<ApiService> apiServiceProvider;

  private final Provider<NoorPreferences> preferencesProvider;

  public AuthRepositoryImpl_Factory(Provider<ApiService> apiServiceProvider,
      Provider<NoorPreferences> preferencesProvider) {
    this.apiServiceProvider = apiServiceProvider;
    this.preferencesProvider = preferencesProvider;
  }

  @Override
  public AuthRepositoryImpl get() {
    return newInstance(apiServiceProvider.get(), preferencesProvider.get());
  }

  public static AuthRepositoryImpl_Factory create(Provider<ApiService> apiServiceProvider,
      Provider<NoorPreferences> preferencesProvider) {
    return new AuthRepositoryImpl_Factory(apiServiceProvider, preferencesProvider);
  }

  public static AuthRepositoryImpl newInstance(ApiService apiService, NoorPreferences preferences) {
    return new AuthRepositoryImpl(apiService, preferences);
  }
}
