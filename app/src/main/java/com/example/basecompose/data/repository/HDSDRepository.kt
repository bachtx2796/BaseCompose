package com.example.basecompose.data.repository

import com.example.basecompose.domain.model.Hdsd
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

interface HDSDRepository {
    fun get(): Flow<List<Hdsd>>
}

@Singleton
class HDSDRepositoryImpl @Inject constructor() : HDSDRepository {

    override fun get(): Flow<List<Hdsd>> = flowOf(
        listOf(
            Hdsd(
                id = 1,
                title = "Bước 1",
                content = "Đăng nhập vào ứng dụng bằng tài khoản của bạn."
            ),
            Hdsd(
                id = 2,
                title = "Bước 2",
                content = "Chọn màn hình chính để bắt đầu sử dụng."
            ),
            Hdsd(
                id = 3,
                title = "Bước 3",
                content = "Vào phần hồ sơ để xem thông tin cá nhân."
            )
        )
    )
}
