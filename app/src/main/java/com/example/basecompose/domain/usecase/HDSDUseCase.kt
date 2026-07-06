package com.example.basecompose.domain.usecase

import com.example.basecompose.data.repository.HDSDRepository
import com.example.basecompose.domain.model.Hdsd
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HDSDUseCase @Inject constructor(
    private val hdsdRepository: HDSDRepository,
) {
    fun get(): Flow<List<Hdsd>> = hdsdRepository.get()
}
