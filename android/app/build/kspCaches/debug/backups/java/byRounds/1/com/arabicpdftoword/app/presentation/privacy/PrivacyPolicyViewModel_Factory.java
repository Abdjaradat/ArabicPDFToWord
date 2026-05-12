package com.arabicpdftoword.app.presentation.privacy;

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
public final class PrivacyPolicyViewModel_Factory implements Factory<PrivacyPolicyViewModel> {
  @Override
  public PrivacyPolicyViewModel get() {
    return newInstance();
  }

  public static PrivacyPolicyViewModel_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static PrivacyPolicyViewModel newInstance() {
    return new PrivacyPolicyViewModel();
  }

  private static final class InstanceHolder {
    private static final PrivacyPolicyViewModel_Factory INSTANCE = new PrivacyPolicyViewModel_Factory();
  }
}
