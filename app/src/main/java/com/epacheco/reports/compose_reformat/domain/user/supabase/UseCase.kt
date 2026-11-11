package com.epacheco.reports.compose_reformat.domain.user.supabase

interface UseCase<InputT, OutputT> {
    suspend fun execute(input: InputT): OutputT
}