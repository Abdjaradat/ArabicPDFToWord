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
public final class WorkManagerHelper_Factory implements Factory<WorkManagerHelper> {
  private final Provider<Context> contextProvider;

  public WorkManagerHelper_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public WorkManagerHelper get() {
    return newInstance(contextProvider.get());
  }

  public static WorkManagerHelper_Factory create(Provider<Context> contextProvider) {
    return new WorkManagerHelper_Factory(contextProvider);
  }

  public static WorkManagerHelper newInstance(Context context) {
    return new WorkManagerHelper(context);
  }
}
