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
public final class DeleteConversionUseCase_Factory implements Factory<DeleteConversionUseCase> {
  private final Provider<ConversionRepository> repositoryProvider;

  public DeleteConversionUseCase_Factory(Provider<ConversionRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public DeleteConversionUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static DeleteConversionUseCase_Factory create(
      Provider<ConversionRepository> repositoryProvider) {
    return new DeleteConversionUseCase_Factory(repositoryProvider);
  }

  public static DeleteConversionUseCase newInstance(ConversionRepository repository) {
    return new DeleteConversionUseCase(repository);
  }
}
