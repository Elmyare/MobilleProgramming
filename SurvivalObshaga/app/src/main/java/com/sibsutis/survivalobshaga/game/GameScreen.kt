package com.sibsutis.survivalobshaga.game

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.sibsutis.survivalobshaga.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.pow
import kotlin.math.sqrt


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun GameScreen() {
    val cockroachImage = ImageBitmap.imageResource(id = R.drawable.cockroach)

    var bugs by remember { mutableStateOf(mutableListOf<Bug>()) }
    var score by remember { mutableIntStateOf(0) }
    var costul by remember { mutableIntStateOf(0) }
    val context = LocalContext.current
    val vibrator = context.getSystemService(Vibrator::class.java)

    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            while (true) {
                costul++
                moveCockroach(context, bugs, 5)
                delay(10)
            }
        }
    }

    Column(
        Modifier.fillMaxSize()
    ) {
        Row(modifier = Modifier.wrapContentSize(Alignment.Center)) {
            Button(
                modifier = Modifier.align(Alignment.CenterVertically),
                onClick = {
                    AddCockroach(context, bugs, 10)
                }
            ) {
                Text(text = "Позвать друзей 🪳")
            }
            Button(
                modifier = Modifier.align(Alignment.CenterVertically),
                onClick = {
                    bugs.add(Bug(800, 1100, 0.3f))
                }
            ) {
                Text(text = "+1")
            }

            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 16.dp)
                    .alpha(0f)
                        ,
                text = "$costul",
            )
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 16.dp),
                text = "Счет $score"
            )

        }
        Canvas(
            modifier = Modifier
                .fillMaxSize().paint(
                    painterResource(id = R.drawable.kitchen),
                    contentScale = ContentScale.Crop)
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        val clickedBug = bugs.find { bug ->
                            val distance = sqrt(
                                (offset.x - bug.x).pow(2.0f) + (offset.y - bug.y).pow(2.0f)
                            )
                            distance <= 200 // Область для нажатия на брата
                        }
                        if (clickedBug != null){
                            bugs.remove(clickedBug)
                            score += 10
                            vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
                        } else{
                            score -= 20
                            vibrator.vibrate(VibrationEffect.createOneShot(100,2))
                        }
                    }
                },
            onDraw = {
                bugs.forEach { bug ->
                    rotate(degrees = bug.direction) {
                        drawImage(
                            image = cockroachImage,
                            dstSize = IntSize(width = 150, height = 150),
                            dstOffset = IntOffset(bug.x, bug.y)
                        )
                    }
                }
            }
        )
    }
}
