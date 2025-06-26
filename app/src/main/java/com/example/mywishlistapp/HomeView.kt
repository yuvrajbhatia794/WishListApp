package com.example.mywishlistapp

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mywishlistapp.data.Wish
import com.example.mywishlistapp.ui.theme.Screen
import com.example.mywishlistapp.ui.theme.WishViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeView(modifier: Modifier = Modifier, navController: NavController, viewModel: WishViewModel) {

    val context = LocalContext.current
    Scaffold(
        modifier = modifier,
        topBar = {AppBarView(title = "WishList", onBackClick = { } )},
        floatingActionButton =  { FloatingActionButton(
            modifier = Modifier.padding(20.dp),
            onClick = { navController.navigate(Screen.AddScreen(id = 0L)) },
            contentColor = Color.White,
            containerColor = Color.Black
        )  {
                Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.White)
        }
        }
    ) {

        val wishList = viewModel.getAllWishes.collectAsState(initial = listOf())

        LazyColumn(modifier = Modifier.padding(it)){
            items(wishList.value, key = {wish -> wish.id}){ wish ->

                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
                            viewModel.deleteWish(wish)
                        }
                        true
                    }
                )
                SwipeToDismiss(
                    state = dismissState,
                    background = { Card(modifier = Modifier.fillMaxSize().padding(8.dp)
                    ) {
                        Row(modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                tint = Color.Red,
                                modifier = Modifier.padding(start = 16.dp).size(24.dp)
                            )

                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                tint = Color.Red,
                                modifier = Modifier.padding(end = 16.dp).size(24.dp)
                            )
                        }
                    } },
                    directions = setOf(DismissDirection.EndToStart),
                    dismissThresholds = {FractionalThreshold(0.25f)},
                    dismissContent = {
                        WishItem(wish = wish, onClick = { navController.navigate(Screen.AddScreen(wish.id)) })

                    }
                )

                LaunchedEffect(dismissState.dismissDirection) {
                    if (dismissState.dismissDirection == DismissDirection.StartToEnd) {
                        Toast.makeText(context, "Swipe right to left to delete", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }

}

@Composable
fun WishItem(wish: Wish, onClick: ()-> Unit) {
    Card ( modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp).clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = wish.title, style = MaterialTheme.typography.titleMedium)
            Text(text = wish.description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
