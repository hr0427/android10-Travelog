package com.thequietz.travelog.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.thequietz.travelog.schedule.model.ScheduleDetailModel

@Dao
abstract class ScheduleDetailDao : BaseDao<ScheduleDetailModel> {
    @Query("SELECT * FROM ScheduleDetail WHERE scheduleId =:id")
    abstract fun loadScheduleDetailsByScheduleId(id: Int): List<ScheduleDetailModel>
}
