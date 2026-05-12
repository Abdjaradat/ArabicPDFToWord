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
public final class CheckConversionStatusUseCase_Factory implements Factory<CheckConversionStatusUseCase> {
  private final Provider<ConversionRepository> repositoryProvider;

  public CheckConversionStatusUseCase_Factory(Provider<ConversionRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public CheckConversionStatusUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static CheckConversionStatusUseCase_Factory create(
      Provider<ConversionRepository> repositoryProvider) {
    return new CheckConversionStatusUseCase_Factory(repositoryProvider);
  }

  public static CheckConversionStatusUseCase newInstance(ConversionRepository repository) {
    return new CheckConversionStatusUseCase(repository);
  }
}
