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
public final class GetConversionStatsUseCase_Factory implements Factory<GetConversionStatsUseCase> {
  private final Provider<ConversionRepository> repositoryProvider;

  public GetConversionStatsUseCase_Factory(Provider<ConversionRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetConversionStatsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetConversionStatsUseCase_Factory create(
      Provider<ConversionRepository> repositoryProvider) {
    return new GetConversionStatsUseCase_Factory(repositoryProvider);
  }

  public static GetConversionStatsUseCase newInstance(ConversionRepository repository) {
    return new GetConversionStatsUseCase(repository);
  }
}
