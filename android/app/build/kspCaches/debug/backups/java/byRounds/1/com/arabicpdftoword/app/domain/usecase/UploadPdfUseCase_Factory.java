package com.arabicpdftoword.app.domain.usecase;

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
public final class UploadPdfUseCase_Factory implements Factory<UploadPdfUseCase> {
  private final Provider<ConversionRepository> repositoryProvider;

  public UploadPdfUseCase_Factory(Provider<ConversionRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public UploadPdfUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static UploadPdfUseCase_Factory create(Provider<ConversionRepository> repositoryProvider) {
    return new UploadPdfUseCase_Factory(repositoryProvider);
  }

  public static UploadPdfUseCase newInstance(ConversionRepository repository) {
    return new UploadPdfUseCase(repository);
  }
}
