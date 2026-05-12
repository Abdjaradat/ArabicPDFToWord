package com.arabicpdftoword.app.core.util;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class NoorPreferences_Factory implements Factory<NoorPreferences> {
  private final Provider<Context> contextProvider;

  public NoorPreferences_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public NoorPreferences get() {
    return newInstance(contextProvider.get());
  }

  public static NoorPreferences_Factory create(Provider<Context> contextProvider) {
    return new NoorPreferences_Factory(contextProvider);
  }

  public static NoorPreferences newInstance(Context context) {
    return new NoorPreferences(context);
  }
}
