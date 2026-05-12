package com.arabicpdftoword.app.data.repository;

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
public final class PreferencesRepositoryImpl_Factory implements Factory<PreferencesRepositoryImpl> {
  private final Provider<NoorPreferences> preferencesProvider;

  public PreferencesRepositoryImpl_Factory(Provider<NoorPreferences> preferencesProvider) {
    this.preferencesProvider = preferencesProvider;
  }

  @Override
  public PreferencesRepositoryImpl get() {
    return newInstance(preferencesProvider.get());
  }

  public static PreferencesRepositoryImpl_Factory create(
      Provider<NoorPreferences> preferencesProvider) {
    return new PreferencesRepositoryImpl_Factory(preferencesProvider);
  }

  public static PreferencesRepositoryImpl newInstance(NoorPreferences preferences) {
    return new PreferencesRepositoryImpl(preferences);
  }
}
