package com.gaebaljip.exceed.screens.home

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.math.PI
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt


class Engine(private val maxY: Double, private val maxX: Double) {
    private var objList: MutableList<Obj> = mutableListOf()
    private val maxVelocity = 1400.0//초당
    private val mutex = Mutex()
    private val g = 20.0F//0.01초당
    private val additionalDistance = 0.5
    private val percentageOfPower = 85

    init {
        objList.add(
            Obj(
                (maxY).toInt(),
                0.0,
                maxY / 10 * 6,
                maxX / 10 * 2,
                0.0,
                0.0,
                0.0,
                0.0,
                true,
                false,
            )
        )

        objList.add(
            Obj(
                (maxY).toInt(),
                0.0,
                maxY / 10 * 6,
                maxX / 10 * 8,
                0.0,
                0.0,
                0.0,
                0.0,
                true,
                false,
            )
        )

        objList.add(
            Obj(
                (maxX / 20 * 7).toInt(),
                0.0,
                maxY / 10 * 9,
                maxX / 10 * 5,
                0.0,
                0.0,
                0.0,
                0.0,
                true,
                true,
            )
        )
    }

    suspend fun update() {
        mutex.withLock {
            objList = objList.filter { it.y < maxY }.toMutableList() // 범위 벗어난 오브젝트 제거

            for (i in 1..3) { // 전역해를 위해 여러번 반복
                for (base in objList) {
                    for (target in objList) {
                        if (base.isWall) continue // 벽이 기준이 될 수 없음
                        if (base == target) continue // 자기 자신이 대상이 될 수 없음
                        if (target.isWall) { // 만약 벽이라면
                            if (target.isHorizontal) {
                                if (target.y in base.y - base.r..base.y + base.r && base.x in target.x - target.r..target.x + target.r) {
                                    val duplicationDistance =
                                        base.y + base.r - target.y + additionalDistance
                                    base.velocityX /= 2
                                    base.velocityY *= -1
                                    base.velocityY /= 2
                                    base.y -= duplicationDistance
                                }
                            } else {
                                if (target.x in base.x - base.r..base.x + base.r && base.y in target.y - target.r..target.y + target.r) {
                                    val duplicationDistance =
                                        if (base.x < target.x) (base.x + base.r - target.x + additionalDistance) else -(target.x - (base.x - base.r) + additionalDistance)
                                    base.velocityY /= 2
                                    base.velocityX *= -1
                                    base.velocityX /= 2
                                    base.x -= duplicationDistance
                                }
                            }

                        } else {
                            if (base.y <= target.y) continue
                            val distance = sqrt(
                                (base.x - target.x).square() + (base.y - target.y).square()
                            )
                            if (base.r + target.r > distance) {
                                val duplicationDistance =
                                    base.r + target.r - distance + additionalDistance

                                //base 먼저 진행
                                var directionVectorX = target.x - base.x
                                var directionVectorY = target.y - base.y
                                var directionVectorSize =
                                    sqrt(directionVectorX.square() + directionVectorY.square())
                                var velocitySize =
                                    sqrt(base.velocityX.square() + base.velocityY.square())
                                var setaDirectionAndVelocity = acos(
                                    (base.velocityX * directionVectorX + base.velocityY * directionVectorY) / (velocitySize * directionVectorSize)
                                )
                                //base는 이제 속도를 완전히 잃음
                                var cosSeta = cos(setaDirectionAndVelocity)//충돌당한 물체의 비율 계산
                                cosSeta = cosSeta / 100 * percentageOfPower
                                target.temporalVelocityX += velocitySize * cosSeta * (directionVectorX / directionVectorSize) // 적용
                                target.temporalVelocityY += velocitySize * cosSeta * (directionVectorY / directionVectorSize)

                                var sinSeta = sin(PI.toFloat() / 2 - setaDirectionAndVelocity)
                                sinSeta = sinSeta / 100 * percentageOfPower
                                var verticalVectorX = directionVectorY
                                var verticalVectorY = directionVectorX
                                var setaVerticalAndBase =
                                    (base.velocityX * -verticalVectorX + base.velocityY * verticalVectorY) / (velocitySize * directionVectorSize)//충돌각에 90도인 게 2개기에 체크

                                if (setaVerticalAndBase >= 0) {//base와 수직벡터의 각이 예각이라면 적용
                                    base.temporalVelocityX += velocitySize * sinSeta * (-verticalVectorX / directionVectorSize)
                                    base.temporalVelocityY += velocitySize * sinSeta * (verticalVectorY / directionVectorSize)

                                } else {//아니라면 반대편으로 적용
                                    base.temporalVelocityX += velocitySize * sinSeta * (verticalVectorX / directionVectorSize)
                                    base.temporalVelocityY += velocitySize * sinSeta * (-verticalVectorY / directionVectorSize)
                                }

                                //target진행
                                directionVectorX = base.x - target.x
                                directionVectorY = base.y - target.y
                                directionVectorSize =
                                    sqrt(directionVectorX.square() + directionVectorY.square())
                                velocitySize =
                                    sqrt(target.velocityX.square() + target.velocityY.square())
                                setaDirectionAndVelocity = acos(
                                    (target.velocityX * directionVectorX + target.velocityY * directionVectorY) / (velocitySize * directionVectorSize)
                                )
                                //target는 이제 속도를 완전히 잃음
                                cosSeta = cos(setaDirectionAndVelocity)
                                cosSeta = cosSeta / 100 * percentageOfPower
                                base.temporalVelocityX += velocitySize * cosSeta * (directionVectorX / directionVectorSize)
                                base.temporalVelocityY += velocitySize * cosSeta * (directionVectorY / directionVectorSize)
                                sinSeta = sin(PI.toFloat() / 2 - setaDirectionAndVelocity)
                                sinSeta = sinSeta / 100 * percentageOfPower
                                verticalVectorX = directionVectorY
                                verticalVectorY = directionVectorX
                                setaVerticalAndBase =
                                    (target.velocityX * -verticalVectorX + target.velocityY * verticalVectorY) / (velocitySize * directionVectorSize)

                                if (setaVerticalAndBase >= 0) {
                                    target.temporalVelocityX += velocitySize * sinSeta * (-verticalVectorX / directionVectorSize)
                                    target.temporalVelocityY += velocitySize * sinSeta * (verticalVectorY / directionVectorSize)

                                } else {
                                    target.temporalVelocityX += velocitySize * sinSeta * (verticalVectorX / directionVectorSize)
                                    target.temporalVelocityY += velocitySize * sinSeta * (-verticalVectorY / directionVectorSize)
                                }


                                target.apply()
                                base.apply()

                                base.temporalVelocityX = 0.0
                                base.temporalVelocityY = 0.0
                                target.temporalVelocityY = 0.0
                                target.temporalVelocityX = 0.0

                                base.x += duplicationDistance / 2 * directionVectorX / directionVectorSize
                                base.y += duplicationDistance / 2 * directionVectorY / directionVectorSize
                                target.x += duplicationDistance / 2 * -directionVectorX / directionVectorSize
                                target.y += duplicationDistance / 2 * -directionVectorY / directionVectorSize
                            }
                        }
                    }
                }


            }
            for (item in objList) {
                if (item.temporalVelocityY > 0 || item.temporalVelocityX > 0) {
                    item.apply()
                }
                item.temporalVelocityY = 0.0
                item.temporalVelocityX = 0.0
                if (item.isWall.not()) item.velocityY += g
                if (item.velocityY > maxVelocity) {
                    item.velocityY = maxVelocity
                } else if (item.velocityY < -maxVelocity) {
                    item.velocityY = -maxVelocity
                }


                if (item.velocityX > maxVelocity) {
                    item.velocityX = maxVelocity
                } else if (item.velocityX < -maxVelocity) {
                    item.velocityX = -maxVelocity
                }

                item.update(0.015)
            }

        }
    }

    suspend fun add(obj: Obj) {
        mutex.withLock {
            objList.add(obj)
        }
    }

    suspend fun get(): List<Obj> {
        mutex.withLock {
            return objList.toList()
        }
    }
}

fun Double.square(): Double {
    return this * this
}

data class Obj(
    val r: Int,
    val rotate: Double,
    var y: Double,
    var x: Double,
    var velocityY: Double,
    var velocityX: Double,
    var temporalVelocityX: Double,
    var temporalVelocityY: Double,
    val isWall: Boolean = false,
    val isHorizontal: Boolean = false
) {
    fun apply() {
        velocityY = temporalVelocityY
        velocityX = temporalVelocityX
    }

    fun update(time: Double) {
        y += velocityY * time
        x += velocityX * time
    }
}