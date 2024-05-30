package com.sibsutis.survivalobshaga.game

import android.content.Context
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

fun AddCockroach(context: Context, bugs: MutableList<Bug>, times: Int) {
    repeat(times) {
        val x = Random.Default.nextInt(0, context.resources.displayMetrics.widthPixels)
        val y = Random.Default.nextInt(0, context.resources.displayMetrics.heightPixels)
        val direction = Random.Default.nextFloat()

        bugs.add(Bug(x, y, direction))
    }
}

fun moveCockroach(context: Context, bugs: MutableList<Bug>, speed: Int) {
    val screenWidth = context.resources.displayMetrics.widthPixels
    val screenHeight = context.resources.displayMetrics.heightPixels

    bugs.forEach { bug ->
        val newX = bug.x + (speed * cos(bug.direction))
        val newY = bug.y - (speed * sin(bug.direction))
        //Log.i(TAG,"newX=$newX newY=$newY")
        val finalX = when {
            newX < 0 -> screenWidth
            newX > screenWidth -> 0
            else -> newX
        }
        val finalY = when {
            newY < 0 -> screenHeight
            newY > screenHeight -> 0
            else -> newY
        }
        //Log.i(TAG,"finalX=$finalX finalY=$finalY")
        bug.x = finalX.toInt()
        bug.y = finalY.toInt()
    }
}