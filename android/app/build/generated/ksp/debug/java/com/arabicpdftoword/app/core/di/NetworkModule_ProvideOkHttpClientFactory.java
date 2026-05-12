package com.arabicpdftoword.app.core.di;

import android.content.Context;
import com.arabicpdftoword.app.core.network.AuthInterceptor;
import com.arabicpdftoword.app.core.network.RetryInterceptor;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import okhttp3.OkHttpClient;

@ScopeMetadata("javax.inject.Singleton")
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
public final class NetworkModule_ProvideOkHttpClientFactory implements Factory<OkHttpClient> {
  private final Provider<Context> contextProvider;

  private final Provider<AuthInterceptor> authInterceptorProvider;

  private final Provider<RetryInterceptor> retryInterceptorProvider;

  public NetworkModule_ProvideOkHttpClientFactory(Provider<Context> contextProvider,
      Provider<AuthInterceptor> authInterceptorProvider,
      Provider<RetryInterceptor> retryInterceptorProvider) {
    this.contextProvider = contextProvider;
    this.authInterceptorProvider = authInterceptorProvider;
    this.retryInterceptorProvider = retryInterceptorProvider;
  }

  @Override
  public OkHttpClient get() {
    return provideOkHttpClient(contextProvider.get(), authInterceptorProvider.get(), retryInterceptorProvider.get());
  }

  public static NetworkModule_ProvideOkHttpClientFactory create(Provider<Context> contextProvider,
      Provider<AuthInterceptor> authInterceptorProvider,
      Provider<RetryInterceptor> retryInterceptorProvider) {
    return new NetworkModule_ProvideOkHttpClientFactory(contextProvider, authInterceptorProvider, retryInterceptorProvider);
  }

  public static OkHttpClient provideOkHttpClient(Context context, AuthInterceptor authInterceptor,
      RetryInterceptor retryInterceptor) {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideOkHttpClient(context, authInterceptor, retryInterceptor));
  }
}
