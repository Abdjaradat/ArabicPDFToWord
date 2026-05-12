package com.arabicpdftoword.app.presentation.about;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class AboutViewModel_Factory implements Factory<AboutViewModel> {
  @Override
  public AboutViewModel get() {
    return newInstance();
  }

  public static AboutViewModel_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static AboutViewModel newInstance() {
    return new AboutViewModel();
  }

  private static final class InstanceHolder {
    private static final AboutViewModel_Factory INSTANCE = new AboutViewModel_Factory();
  }
}
