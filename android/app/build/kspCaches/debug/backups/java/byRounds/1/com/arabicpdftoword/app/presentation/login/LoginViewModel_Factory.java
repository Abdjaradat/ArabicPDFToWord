package com.arabicpdftoword.app.presentation.login;

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

  public LoginViewModel_Factory(Provider<LoginUseCase> loginUseCaseProvider,
      Provider<RegisterUseCase> registerUseCaseProvider) {
    this.loginUseCaseProvider = loginUseCaseProvider;
    this.registerUseCaseProvider = registerUseCaseProvider;
  }

  @Override
  public LoginViewModel get() {
    return newInstance(loginUseCaseProvider.get(), registerUseCaseProvider.get());
  }

  public static LoginViewModel_Factory create(Provider<LoginUseCase> loginUseCaseProvider,
      Provider<RegisterUseCase> registerUseCaseProvider) {
    return new LoginViewModel_Factory(loginUseCaseProvider, registerUseCaseProvider);
  }

  public static LoginViewModel newInstance(LoginUseCase loginUseCase,
      RegisterUseCase registerUseCase) {
    return new LoginViewModel(loginUseCase, registerUseCase);
  }
}
