package com.example.mywishlistapp.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mywishlistapp.AppBarView
import com.example.mywishlistapp.data.Wish
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AddEditDetailView(
    id: Long,
    viewModel: WishViewModel,
    navController: NavController
) {

    val snackMessage = remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    val scaffoldState = rememberScaffoldState()

    if (id != 0L){
        val wish = viewModel.getWishById(id).collectAsState(initial = Wish(title = "", description = "") )
        viewModel.wishTitleState = wish.value.title
        viewModel.wishDescriptionState = wish.value.description
    } else{
        viewModel.wishTitleState = ""
        viewModel.wishDescriptionState = ""
    }

    Scaffold (
        modifier = Modifier.statusBarsPadding(),
        scaffoldState = scaffoldState,
        topBar = {AppBarView(
            title = if (id != 0L) "Edit Wish" else "Add Wish",
            onBackClick = { navController.navigateUp() }
        )}
    ){
        Column (modifier = Modifier
            .padding(it)
            .wrapContentSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center)
        {
            Spacer(modifier = Modifier.height(10.dp))

            WishTextField(label = "Title", value = viewModel.wishTitleState, onValueChange = { viewModel.onWishTitleChange(it) })

            Spacer(modifier = Modifier.height(10.dp))

            WishTextField(label = "Description", value = viewModel.wishDescriptionState, onValueChange = { viewModel.onWishDescriptionChange(it) })

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = {
                if (viewModel.wishTitleState.isNotEmpty() && viewModel.wishDescriptionState.isNotEmpty()) {
                        // add or update wish
                    if(id!=0L){
                        //update
                        viewModel.updateWish(Wish(
                            id = id,
                            title = viewModel.wishTitleState.trim(),
                            description = viewModel.wishDescriptionState.trim()
                        ))
                        snackMessage.value = "Wish edited successfully"


                    }else{
                        //add
                        viewModel.addWish(
                            Wish(
                                title = viewModel.wishTitleState.trim(),
                                description = viewModel.wishDescriptionState.trim()
                            )
                        )
                        snackMessage.value = "Wish created successfully"
                    }
                }else{
                    snackMessage.value = "Enter fields for wish to be created"
                }

                scope.launch {
                    val snack = launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = snackMessage.value,
                            duration = SnackbarDuration.Indefinite
                        )
                    }
                    delay(1000)
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                    navController.navigateUp()
                }

            })
            {
                Text(
                    text = if (id != 0L) "Edit Wish" else "Add Wish",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}


@Composable
fun WishTextField(label: String, value: String, onValueChange: (String) -> Unit) {

    OutlinedTextField(value = value,
        onValueChange= onValueChange,
        label = { Text(text = label, color = Color.Black) },
        modifier = Modifier.fillMaxWidth().padding(start = 8.dp,end = 8.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Gray,
            cursorColor = Color.Black
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun WishTextFieldPrev() {
    WishTextField("hello", " how are you", { })
}