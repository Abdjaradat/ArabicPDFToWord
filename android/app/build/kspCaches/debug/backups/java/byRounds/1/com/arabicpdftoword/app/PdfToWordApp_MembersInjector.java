package com.arabicpdftoword.app;

import androidx.hilt.work.HiltWorkerFactory;
import com.arabicpdftoword.app.core.util.CrashHandler;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class PdfToWordApp_MembersInjector implements MembersInjector<PdfToWordApp> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  private final Provider<CrashHandler> crashHandlerProvider;

  public PdfToWordApp_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider,
      Provider<CrashHandler> crashHandlerProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
    this.crashHandlerProvider = crashHandlerProvider;
  }

  public static MembersInjector<PdfToWordApp> create(
      Provider<HiltWorkerFactory> workerFactoryProvider,
      Provider<CrashHandler> crashHandlerProvider) {
    return new PdfToWordApp_MembersInjector(workerFactoryProvider, crashHandlerProvider);
  }

  @Override
  public void injectMembers(PdfToWordApp instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
    injectCrashHandler(instance, crashHandlerProvider.get());
  }

  @InjectedFieldSignature("com.arabicpdftoword.app.PdfToWordApp.workerFactory")
  public static void injectWorkerFactory(PdfToWordApp instance, HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }

  @InjectedFieldSignature("com.arabicpdftoword.app.PdfToWordApp.crashHandler")
  public static void injectCrashHandler(PdfToWordApp instance, CrashHandler crashHandler) {
    instance.crashHandler = crashHandler;
  }
}
