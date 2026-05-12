package com.arabicpdftoword.app;

import com.arabicpdftoword.app.core.util.NoorPreferences;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<NoorPreferences> preferencesProvider;

  public MainActivity_MembersInjector(Provider<NoorPreferences> preferencesProvider) {
    this.preferencesProvider = preferencesProvider;
  }

  public static MembersInjector<MainActivity> create(
      Provider<NoorPreferences> preferencesProvider) {
    return new MainActivity_MembersInjector(preferencesProvider);
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectPreferences(instance, preferencesProvider.get());
  }

  @InjectedFieldSignature("com.arabicpdftoword.app.MainActivity.preferences")
  public static void injectPreferences(MainActivity instance, NoorPreferences preferences) {
    instance.preferences = preferences;
  }
}
