package com.tanveer.airlib.task.shared.business.use_cases

import com.tanveer.airlib.task.shared.data.repository.DatabaseRepository
import javax.inject.Inject

interface SaveUsernameUseCase {
    suspend operator fun invoke(username: String)
    suspend fun isUsernameExists(username: String): Boolean
}

class SaveUsernameUseCaseImpl @Inject constructor(
    private val repository: DatabaseRepository
) : SaveUsernameUseCase {

    override suspend operator fun invoke(username: String) {
        repository.saveUsername(username)
    }

    override suspend fun isUsernameExists(username: String): Boolean {
        return repository.isUsernameExists(username)
    }
}