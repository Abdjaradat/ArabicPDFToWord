package com.arabicpdftoword.app.presentation.premium;

import android.content.Context;
import com.arabicpdftoword.app.core.util.NoorPreferences;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class PremiumViewModel_Factory implements Factory<PremiumViewModel> {
  private final Provider<NoorPreferences> prefsProvider;

  private final Provider<Context> contextProvider;

  public PremiumViewModel_Factory(Provider<NoorPreferences> prefsProvider,
      Provider<Context> contextProvider) {
    this.prefsProvider = prefsProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public PremiumViewModel get() {
    return newInstance(prefsProvider.get(), contextProvider.get());
  }

  public static PremiumViewModel_Factory create(Provider<NoorPreferences> prefsProvider,
      Provider<Context> contextProvider) {
    return new PremiumViewModel_Factory(prefsProvider, contextProvider);
  }

  public static PremiumViewModel newInstance(NoorPreferences prefs, Context context) {
    return new PremiumViewModel(prefs, context);
  }
}
