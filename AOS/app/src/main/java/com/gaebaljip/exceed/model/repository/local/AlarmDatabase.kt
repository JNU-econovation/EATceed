package com.gaebaljip.exceed.model.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [AlarmEntity::class], version = 1)
@TypeConverters(TypeConverter::class)
abstract class AlarmDatabase : RoomDatabase(){
    abstract fun alarmDao() : AlarmDAO
}