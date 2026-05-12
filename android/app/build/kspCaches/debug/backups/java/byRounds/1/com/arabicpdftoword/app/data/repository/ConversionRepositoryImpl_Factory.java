package com.arabicpdftoword.app.data.repository;

import com.arabicpdftoword.app.data.datasource.LocalConversionDataSource;
import com.arabicpdftoword.app.data.datasource.RemoteConversionDataSource;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class ConversionRepositoryImpl_Factory implements Factory<ConversionRepositoryImpl> {
  private final Provider<LocalConversionDataSource> localDataSourceProvider;

  private final Provider<RemoteConversionDataSource> remoteDataSourceProvider;

  public ConversionRepositoryImpl_Factory(
      Provider<LocalConversionDataSource> localDataSourceProvider,
      Provider<RemoteConversionDataSource> remoteDataSourceProvider) {
    this.localDataSourceProvider = localDataSourceProvider;
    this.remoteDataSourceProvider = remoteDataSourceProvider;
  }

  @Override
  public ConversionRepositoryImpl get() {
    return newInstance(localDataSourceProvider.get(), remoteDataSourceProvider.get());
  }

  public static ConversionRepositoryImpl_Factory create(
      Provider<LocalConversionDataSource> localDataSourceProvider,
      Provider<RemoteConversionDataSource> remoteDataSourceProvider) {
    return new ConversionRepositoryImpl_Factory(localDataSourceProvider, remoteDataSourceProvider);
  }

  public static ConversionRepositoryImpl newInstance(LocalConversionDataSource localDataSource,
      RemoteConversionDataSource remoteDataSource) {
    return new ConversionRepositoryImpl(localDataSource, remoteDataSource);
  }
}
