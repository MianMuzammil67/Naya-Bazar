package com.example.nayabazar.di

import com.example.nayabazar.model.Address
import com.example.nayabazar.model.Order
import com.example.nayabazar.model.Payment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object MyModule {
    @Provides
    @Singleton
    fun provideAddress(): Address {
        return Address()
    }
    @Provides
    @Singleton
    fun providePayment(): Payment {
        return Payment()
    }

}