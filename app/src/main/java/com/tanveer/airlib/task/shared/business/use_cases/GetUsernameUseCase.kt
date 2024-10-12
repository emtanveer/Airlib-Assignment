package com.tanveer.airlib.task.shared.business.use_cases

import com.tanveer.airlib.task.shared.data.UserEntity
import com.tanveer.airlib.task.shared.data.repository.DatabaseRepository
import javax.inject.Inject

interface GetUsernameUseCase {
    suspend operator fun invoke(): UserEntity
}

class GetUsernameUseCaseImpl @Inject constructor(
    private val repository: DatabaseRepository
) : GetUsernameUseCase {

    override suspend operator fun invoke(): UserEntity {
        return repository.getUsername()
    }
}