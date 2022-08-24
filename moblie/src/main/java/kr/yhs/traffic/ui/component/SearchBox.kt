package kr.yhs.traffic.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.charlex.compose.BottomDrawerState
import de.charlex.compose.rememberBottomDrawerScaffoldState
import kr.yhs.traffic.ui.theme.AppTheme


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun SearchBox(
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    modifier: Modifier = Modifier,
    onDone: (() -> Unit)? = null
) {
    val focusRequester = remember { FocusRequester() }

    var text by remember { mutableStateOf("") }
    var query by remember { mutableStateOf("버스") }
    Column(
        modifier = modifier
            .background(Color.LightGray)
            .fillMaxWidth()
            .imePadding()
            .clip(RoundedCornerShape(8.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBoxGuideline()
        val keyboardController = LocalSoftwareKeyboardController.current
        TextField(
            value = text,
            onValueChange = { text = it },
            placeholder = { Text("$query 검색") },
            leadingIcon = { Icon(Icons.Filled.Search, "Search Icon") },
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            singleLine = true,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
                .focusRequester(focusRequester),
            interactionSource = interactionSource,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                    if (onDone != null)
                        onDone()
                }
            )
        )
        Row(
            modifier = Modifier
                .padding(end = 10.dp)
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            val buttonModifier = Modifier
                .padding(5.dp)
                .weight(1f)
            for (name in listOf("버스", "정류장"))
                SearchBoxTypeButton(
                    name = name,
                    enabled = query == name,
                    modifier = buttonModifier
                ) {
                    query = name
                }
        }

        LazyColumn (modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .padding(10.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
        ) {

        }
    }
}


@Composable
fun SearchBoxTypeButton(
    name: String,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    color: Color = Color(0xff5681ef).copy(),
    onClick: () -> Unit
) {
    var buttonColor = ButtonDefaults.outlinedButtonColors(backgroundColor = Color(0x00000000))
    var textColor = Color.DarkGray
    if (enabled) {
        buttonColor = ButtonDefaults.outlinedButtonColors(backgroundColor=color)
        textColor = Color.White
    }
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        border= BorderStroke(1.5.dp, color),
        colors = buttonColor
    ) {
        Text(name, color = textColor, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun SearchBoxGuideline() {
    Row(
        modifier = Modifier
            .padding(6.dp)
            .height(8.dp)
    ) {
        val modifier = Modifier.padding(start = 3.dp, end = 3.dp)
        val colorFilter = ColorFilter.tint(Color.DarkGray)
        for (i in 1..3)
            Image(
                Icons.Filled.Circle,
                "Swipe circle${i}",
                modifier = modifier,
                colorFilter = colorFilter
            )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun SearchBoxPreview() {
    AppTheme {
        SearchBox()
    }
}