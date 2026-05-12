package com.arabicpdftoword.app.presentation.home;

import com.arabicpdftoword.app.core.util.NoorPreferences;
import com.arabicpdftoword.app.domain.repository.ConversionRepository;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<ConversionRepository> repositoryProvider;

  private final Provider<NoorPreferences> prefsProvider;

  public HomeViewModel_Factory(Provider<ConversionRepository> repositoryProvider,
      Provider<NoorPreferences> prefsProvider) {
    this.repositoryProvider = repositoryProvider;
    this.prefsProvider = prefsProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(repositoryProvider.get(), prefsProvider.get());
  }

  public static HomeViewModel_Factory create(Provider<ConversionRepository> repositoryProvider,
      Provider<NoorPreferences> prefsProvider) {
    return new HomeViewModel_Factory(repositoryProvider, prefsProvider);
  }

  public static HomeViewModel newInstance(ConversionRepository repository, NoorPreferences prefs) {
    return new HomeViewModel(repository, prefs);
  }
}
