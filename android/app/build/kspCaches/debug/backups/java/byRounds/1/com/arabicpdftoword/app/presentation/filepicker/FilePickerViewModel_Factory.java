package com.arabicpdftoword.app.presentation.filepicker;

import android.content.Context;
import com.arabicpdftoword.app.core.util.AdManager;
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
public final class FilePickerViewModel_Factory implements Factory<FilePickerViewModel> {
  private final Provider<ConversionRepository> repositoryProvider;

  private final Provider<NoorPreferences> prefsProvider;

  private final Provider<AdManager> adManagerProvider;

  private final Provider<Context> contextProvider;

  public FilePickerViewModel_Factory(Provider<ConversionRepository> repositoryProvider,
      Provider<NoorPreferences> prefsProvider, Provider<AdManager> adManagerProvider,
      Provider<Context> contextProvider) {
    this.repositoryProvider = repositoryProvider;
    this.prefsProvider = prefsProvider;
    this.adManagerProvider = adManagerProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public FilePickerViewModel get() {
    return newInstance(repositoryProvider.get(), prefsProvider.get(), adManagerProvider.get(), contextProvider.get());
  }

  public static FilePickerViewModel_Factory create(
      Provider<ConversionRepository> repositoryProvider, Provider<NoorPreferences> prefsProvider,
      Provider<AdManager> adManagerProvider, Provider<Context> contextProvider) {
    return new FilePickerViewModel_Factory(repositoryProvider, prefsProvider, adManagerProvider, contextProvider);
  }

  public static FilePickerViewModel newInstance(ConversionRepository repository,
      NoorPreferences prefs, AdManager adManager, Context context) {
    return new FilePickerViewModel(repository, prefs, adManager, context);
  }
}
