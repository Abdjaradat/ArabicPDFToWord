package com.arabicpdftoword.app.core.util;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class AdManager_Factory implements Factory<AdManager> {
  private final Provider<Context> contextProvider;

  private final Provider<NoorPreferences> preferencesProvider;

  public AdManager_Factory(Provider<Context> contextProvider,
      Provider<NoorPreferences> preferencesProvider) {
    this.contextProvider = contextProvider;
    this.preferencesProvider = preferencesProvider;
  }

  @Override
  public AdManager get() {
    return newInstance(contextProvider.get(), preferencesProvider.get());
  }

  public static AdManager_Factory create(Provider<Context> contextProvider,
      Provider<NoorPreferences> preferencesProvider) {
    return new AdManager_Factory(contextProvider, preferencesProvider);
  }

  public static AdManager newInstance(Context context, NoorPreferences preferences) {
    return new AdManager(context, preferences);
  }
}
