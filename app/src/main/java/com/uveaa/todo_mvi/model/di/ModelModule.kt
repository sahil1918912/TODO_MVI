package com.uveaa.todo_mvi.model.di

import android.content.Context
import com.uveaa.todo_mvi.model.local.TodoDao
import com.uveaa.todo_mvi.model.local.TodoDatabase
import com.uveaa.todo_mvi.model.repository.TodoRepoImpl
import com.uveaa.todo_mvi.model.repository.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ModelModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) : TodoDatabase{
        return TodoDatabase.getInstance(context)
    }

    @Provides
    fun provideDao(todoDatabase: TodoDatabase): TodoDao{
        return todoDatabase.getTodoDao()
    }

    @Provides
    fun provideTodoRepository(todoDao: TodoDao) : TodoRepository{
        return TodoRepoImpl(todoDao)
    }
}