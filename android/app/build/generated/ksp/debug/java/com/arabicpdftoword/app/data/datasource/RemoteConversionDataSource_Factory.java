package com.arabicpdftoword.app.data.datasource;

import com.arabicpdftoword.app.core.network.ApiService;
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
public final class RemoteConversionDataSource_Factory implements Factory<RemoteConversionDataSource> {
  private final Provider<ApiService> apiServiceProvider;

  public RemoteConversionDataSource_Factory(Provider<ApiService> apiServiceProvider) {
    this.apiServiceProvider = apiServiceProvider;
  }

  @Override
  public RemoteConversionDataSource get() {
    return newInstance(apiServiceProvider.get());
  }

  public static RemoteConversionDataSource_Factory create(Provider<ApiService> apiServiceProvider) {
    return new RemoteConversionDataSource_Factory(apiServiceProvider);
  }

  public static RemoteConversionDataSource newInstance(ApiService apiService) {
    return new RemoteConversionDataSource(apiService);
  }
}
