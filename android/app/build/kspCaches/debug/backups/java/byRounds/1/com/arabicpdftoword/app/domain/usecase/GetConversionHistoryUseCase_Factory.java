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
public final class GetConversionHistoryUseCase_Factory implements Factory<GetConversionHistoryUseCase> {
  private final Provider<ConversionRepository> repositoryProvider;

  public GetConversionHistoryUseCase_Factory(Provider<ConversionRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetConversionHistoryUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetConversionHistoryUseCase_Factory create(
      Provider<ConversionRepository> repositoryProvider) {
    return new GetConversionHistoryUseCase_Factory(repositoryProvider);
  }

  public static GetConversionHistoryUseCase newInstance(ConversionRepository repository) {
    return new GetConversionHistoryUseCase(repository);
  }
}
