package com.example.mywishlistapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp



@Composable
fun AppBarView(
    title: String,
    onBackClick: () -> Unit ={ }
) {

    val navigationIcon: (@Composable () -> Unit)? = {

        if(!title.contains("WishList")){
            IconButton(onClick = { onBackClick() }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    tint = Color.White,
                    contentDescription = null
                )
            }
        }
    }

    TopAppBar(
        title = {Text (text = title,
        color = colorResource(id = R.color.white),
        modifier = Modifier.padding(start = 8.dp)) },
        backgroundColor = colorResource(id = R.color.app_bar_color),
        contentColor = colorResource(id = R.color.black),
        elevation = 4.dp,
        navigationIcon = navigationIcon

    )

}