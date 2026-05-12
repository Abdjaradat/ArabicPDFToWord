package com.arabicpdftoword.app.core.di;

import com.arabicpdftoword.app.core.database.AppDatabase;
import com.arabicpdftoword.app.core.database.dao.ConversionDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideConversionDaoFactory implements Factory<ConversionDao> {
  private final Provider<AppDatabase> databaseProvider;

  public AppModule_ProvideConversionDaoFactory(Provider<AppDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public ConversionDao get() {
    return provideConversionDao(databaseProvider.get());
  }

  public static AppModule_ProvideConversionDaoFactory create(
      Provider<AppDatabase> databaseProvider) {
    return new AppModule_ProvideConversionDaoFactory(databaseProvider);
  }

  public static ConversionDao provideConversionDao(AppDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideConversionDao(database));
  }
}
