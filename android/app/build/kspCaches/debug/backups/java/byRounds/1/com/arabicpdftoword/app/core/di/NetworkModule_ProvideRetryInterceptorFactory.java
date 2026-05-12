package com.arabicpdftoword.app.core.di;

import com.arabicpdftoword.app.core.network.RetryInterceptor;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class NetworkModule_ProvideRetryInterceptorFactory implements Factory<RetryInterceptor> {
  @Override
  public RetryInterceptor get() {
    return provideRetryInterceptor();
  }

  public static NetworkModule_ProvideRetryInterceptorFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static RetryInterceptor provideRetryInterceptor() {
    return Preconditions.checkNotNullFromProvides(NetworkModule.INSTANCE.provideRetryInterceptor());
  }

  private static final class InstanceHolder {
    private static final NetworkModule_ProvideRetryInterceptorFactory INSTANCE = new NetworkModule_ProvideRetryInterceptorFactory();
  }
}
