package com.arabicpdftoword.app.data.datasource;

import com.arabicpdftoword.app.core.database.dao.ConversionDao;
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
public final class LocalConversionDataSource_Factory implements Factory<LocalConversionDataSource> {
  private final Provider<ConversionDao> conversionDaoProvider;

  public LocalConversionDataSource_Factory(Provider<ConversionDao> conversionDaoProvider) {
    this.conversionDaoProvider = conversionDaoProvider;
  }

  @Override
  public LocalConversionDataSource get() {
    return newInstance(conversionDaoProvider.get());
  }

  public static LocalConversionDataSource_Factory create(
      Provider<ConversionDao> conversionDaoProvider) {
    return new LocalConversionDataSource_Factory(conversionDaoProvider);
  }

  public static LocalConversionDataSource newInstance(ConversionDao conversionDao) {
    return new LocalConversionDataSource(conversionDao);
  }
}
