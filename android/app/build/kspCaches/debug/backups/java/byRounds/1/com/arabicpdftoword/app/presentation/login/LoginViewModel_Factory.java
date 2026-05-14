package com.arabicpdftoword.app.presentation.login;

import com.arabicpdftoword.app.domain.repository.AuthRepository;
import com.arabicpdftoword.app.domain.usecase.LoginUseCase;
import com.arabicpdftoword.app.domain.usecase.RegisterUseCase;
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
public final class LoginViewModel_Factory implements Factory<LoginViewModel> {
  private final Provider<LoginUseCase> loginUseCaseProvider;

  private final Provider<RegisterUseCase> registerUseCaseProvider;

  private final Provider<AuthRepository> authRepositoryProvider;

  public LoginViewModel_Factory(Provider<LoginUseCase> loginUseCaseProvider,
      Provider<RegisterUseCase> registerUseCaseProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    this.loginUseCaseProvider = loginUseCaseProvider;
    this.registerUseCaseProvider = registerUseCaseProvider;
    this.authRepositoryProvider = authRepositoryProvider;
  }

  @Override
  public LoginViewModel get() {
    return newInstance(loginUseCaseProvider.get(), registerUseCaseProvider.get(), authRepositoryProvider.get());
  }

  public static LoginViewModel_Factory create(Provider<LoginUseCase> loginUseCaseProvider,
      Provider<RegisterUseCase> registerUseCaseProvider,
      Provider<AuthRepository> authRepositoryProvider) {
    return new LoginViewModel_Factory(loginUseCaseProvider, registerUseCaseProvider, authRepositoryProvider);
  }

  public static LoginViewModel newInstance(LoginUseCase loginUseCase,
      RegisterUseCase registerUseCase, AuthRepository authRepository) {
    return new LoginViewModel(loginUseCase, registerUseCase, authRepository);
  }
}
