package com.lusion.animationbug

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task

class MainActivity : ComponentActivity(), LocationListener, OnSharedPreferenceChangeListener,
    OnCompleteListener<Void?> {

    // TODO when the API can't be reached, like with debug and server is offline, the app never stops polling until it is closed.  Could be a battery drain.  Need to look into it.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // A surface container using the 'background' color from the theme
            SGTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    MainLayout()
                }
            }
        }
    }



    @Composable
    fun MainLayout() {
        Row(verticalAlignment = Alignment.Bottom)
        {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            )
            {
                Column {
                    GarageIndicator()
                }
            }
        }
    }

    @Preview
    @Composable
    fun MainLayoutPreview() {
        MainLayout()
    }


    @Composable
    fun GarageArrow(arrowID: Int) {
        Image(
            painter = painterResource(id = arrowID),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }

    @Composable
    fun MoveGarageArrow(
        yOffset: Int,
        item: @Composable () -> Unit
    ) {
        // outer box
        Box {
            // inner box
            Box(
                Modifier
                    .absoluteOffset(y = yOffset.dp)
                    .align(Alignment.Center)
            ) {
                item()
            }
        }
    }

    @Composable
    fun GarageIndicatorOpening() {
        val backgroundVector =
            ImageVector.vectorResource(id = R.drawable.garage_door_action_background)
        val garageBackgroundPainter = rememberVectorPainter(image = backgroundVector)

        var yState by remember { mutableIntStateOf(150) }
        val yOffset = animateIntAsState(
            targetValue = yState,
            animationSpec =
            infiniteRepeatable(animation = tween(durationMillis = 1200, easing = LinearEasing)),
            label = "opening animation"
        )
        Box {
            Image(painter = garageBackgroundPainter, contentDescription = null)
            MoveGarageArrow(yOffset = yOffset.value) {
                GarageArrow(arrowID = R.drawable.garage_door_opening_arrow_anim)
            }
        }
        yState = 0

    }

    @Composable
    fun GarageIndicator() {
            GarageIndicatorOpening()
        }

    override fun onLocationChanged(p0: Location) {
        TODO("Not yet implemented")
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        TODO("Not yet implemented")
    }

    override fun onComplete(p0: Task<Void?>) {
        TODO("Not yet implemented")
    }
}
