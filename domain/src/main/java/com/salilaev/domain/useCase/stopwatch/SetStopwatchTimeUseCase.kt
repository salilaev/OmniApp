package com.salilaev.domain.useCase.stopwatch

import com.salilaev.domain.repository.TimeRepository
import javax.inject.Inject

class SetStopwatchTimeUseCase @Inject constructor(
    private val timeRepository: TimeRepository
) {
    suspend operator fun invoke(time: Long) = timeRepository.setTime(time)
}