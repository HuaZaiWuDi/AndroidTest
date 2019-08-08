package com.wesmarclothing.jniproject


import com.alibaba.fastjson.annotation.JSONField

data class TestJson(
    @JSONField(name = "age")
    val age: Int,
    @JSONField(name = "athlDate")
    val athlDate: String,
    @JSONField(name = "athlDesc")
    val athlDesc: String,
    @JSONField(name = "avgHeart")
    val avgHeart: Int,
    @JSONField(name = "avgPace")
    val avgPace: Int,
    @JSONField(name = "birthday")
    val birthday: String,
    @JSONField(name = "cadence")
    val cadence: Int,
    @JSONField(name = "calorie")
    val calorie: Int,
    @JSONField(name = "complete")
    val complete: Double,
    @JSONField(name = "createTime")
    val createTime: Long,
    @JSONField(name = "createUser")
    val createUser: Int,
    @JSONField(name = "dataFlag")
    val dataFlag: Int,
    @JSONField(name = "duration")
    val duration: Int,
    @JSONField(name = "endTime")
    val endTime: String,
    @JSONField(name = "gid")
    val gid: Int,
    @JSONField(name = "heartCount")
    val heartCount: Int,
    @JSONField(name = "height")
    val height: Int,
    @JSONField(name = "kilometers")
    val kilometers: Int,
    @JSONField(name = "maxHeart")
    val maxHeart: Int,
    @JSONField(name = "maxPace")
    val maxPace: Int,
    @JSONField(name = "minHeart")
    val minHeart: Int,
    @JSONField(name = "minPace")
    val minPace: Int,
    @JSONField(name = "sex")
    val sex: Int,
    @JSONField(name = "startTime")
    val startTime: String,
    @JSONField(name = "status")
    val status: Int,
    @JSONField(name = "stepNumber")
    val stepNumber: Int,
    @JSONField(name = "updateTime")
    val updateTime: Long,
    @JSONField(name = "updateUser")
    val updateUser: Int,
    @JSONField(name = "userId")
    val userId: String
)