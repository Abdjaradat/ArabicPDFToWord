package com.arabicpdftoword.app.presentation.conversion;

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
public final class ConversionProgressViewModel_Factory implements Factory<ConversionProgressViewModel> {
  private final Provider<ConversionRepository> repositoryProvider;

  private final Provider<NoorPreferences> prefsProvider;

  private final Provider<Context> contextProvider;

  public ConversionProgressViewModel_Factory(Provider<ConversionRepository> repositoryProvider,
      Provider<NoorPreferences> prefsProvider, Provider<Context> contextProvider) {
    this.repositoryProvider = repositoryProvider;
    this.prefsProvider = prefsProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public ConversionProgressViewModel get() {
    return newInstance(repositoryProvider.get(), prefsProvider.get(), contextProvider.get());
  }

  public static ConversionProgressViewModel_Factory create(
      Provider<ConversionRepository> repositoryProvider, Provider<NoorPreferences> prefsProvider,
      Provider<Context> contextProvider) {
    return new ConversionProgressViewModel_Factory(repositoryProvider, prefsProvider, contextProvider);
  }

  public static ConversionProgressViewModel newInstance(ConversionRepository repository,
      NoorPreferences prefs, Context context) {
    return new ConversionProgressViewModel(repository, prefs, context);
  }
}
