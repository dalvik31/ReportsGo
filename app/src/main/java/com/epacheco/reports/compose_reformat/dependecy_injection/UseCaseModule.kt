package com.epacheco.reports.compose_reformat.dependecy_injection



import com.epacheco.reports.compose_reformat.domain.user.supabase.CurrentUserUseCase
import com.epacheco.reports.compose_reformat.domain.user.supabase.SignInUseCase
import com.epacheco.reports.compose_reformat.domain.user.supabase.imp.SignInUseCaseImpl
import com.epacheco.reports.compose_reformat.domain.user.supabase.SignInWithGoogleUseCase
import com.epacheco.reports.compose_reformat.domain.user.supabase.SignOutUseCase
import com.epacheco.reports.compose_reformat.domain.user.supabase.SignUpUseCase
import com.epacheco.reports.compose_reformat.domain.user.supabase.imp.CurrentUserUseCaseImpl
import com.epacheco.reports.compose_reformat.domain.user.supabase.imp.SignOutUseCaseImpl
import com.epacheco.reports.compose_reformat.domain.user.supabase.imp.SignInWithGoogleUseCaseImpl
import com.epacheco.reports.compose_reformat.domain.user.supabase.imp.SignUpUseCaseImpl

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class UseCaseModule {

    @Binds
    abstract fun bindAuthenticateUseCase(impl: SignInUseCaseImpl): SignInUseCase

    @Binds
    abstract fun bindSignUpUseCase(impl: SignUpUseCaseImpl): SignUpUseCase

    @Binds
    abstract fun bindSignInWithGoogleUseCase(impl: SignInWithGoogleUseCaseImpl): SignInWithGoogleUseCase

    @Binds
    abstract fun bindCurrentUserUseCase(impl: CurrentUserUseCaseImpl): CurrentUserUseCase

    @Binds
    abstract fun bindSignOutUseCase(impl: SignOutUseCaseImpl): SignOutUseCase
}