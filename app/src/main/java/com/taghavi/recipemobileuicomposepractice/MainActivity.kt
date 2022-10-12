package com.taghavi.recipemobileuicomposepractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.statusBarsPadding
import com.taghavi.recipemobileuicomposepractice.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipeMobileUIComposePracticeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainFragment(strawberryCakeRecipe)
                }
            }
        }
    }
}

@Composable
fun MainFragment(recipe: Recipe) {
    Box {
        Content(recipe)
        ParallaxToolbar(recipe)
    }
}

@Composable
fun ParallaxToolbar(recipe: Recipe) {
    val imageHeight = AppBarExpendedHeight - AppBarCollapsedHeight
    TopAppBar(
        contentPadding = PaddingValues(),
        backgroundColor = White,
        modifier = Modifier.height(AppBarExpendedHeight),
    ) {
        Column {
            Box(Modifier.height(imageHeight)) {
                Image(
                    painter = painterResource(id = R.drawable.strawberry_pie_1),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colorStops = arrayOf(
                                    Pair(0.4f, Transparent),
                                    Pair(1f, White),
                                )
                            )
                        )
                )

                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        recipe.category,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .clip(Shapes.small)
                            .background(LightGray)
                            .padding(horizontal = 6.dp, vertical = 6.dp)
                    )
                }
            }
            Column(
                Modifier
                    .fillMaxSize()
                    .height(AppBarCollapsedHeight)
            ) {
                Text(
                    text = recipe.title,
                    fontSize = 26.sp,
                    fontWeight = Bold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(AppBarCollapsedHeight)
            .padding(horizontal = 16.dp)
    ) {
        CircularButton(iconResource = R.drawable.ic_arrow_back)
        CircularButton(iconResource = R.drawable.ic_favorite)
    }
}

@Composable
fun CircularButton(
    @DrawableRes iconResource: Int,
    color: Color = Gray,
    elevation: ButtonElevation? = ButtonDefaults.elevation(),
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        contentPadding = PaddingValues(),
        shape = Shapes.small,
        colors = ButtonDefaults.buttonColors(backgroundColor = White, contentColor = color),
        elevation = elevation,
        modifier = Modifier
            .width(38.dp)
            .height(38.dp)
    ) {
        Icon(painter = painterResource(id = iconResource), null)
    }
}

@Composable
fun Content(recipe: Recipe) {
    LazyColumn(contentPadding = PaddingValues(top = AppBarExpendedHeight)) {
        item {
            BasicInfo(recipe)
            Description(recipe)
            ServingCalculator()
            IngredientsHeader()
            IngredientsList(recipe)
        }
    }
}

@Composable
fun IngredientsList(recipe: Recipe) {
    EasyGrid(nColumn = 3, items = recipe.ingredients) {
        IngredientCard(
            it.image,
            it.title,
            it.subTitle,
            Modifier
        )
    }
}

@Composable
fun <T> EasyGrid(nColumn: Int, items: List<T>, content: @Composable (T) -> Unit) {
    Column(Modifier.padding(16.dp)) {
        for (i in items.indices step nColumn) {
            Row {
                for (j in 0 until nColumn) {
                    if (i + j < items.size) {
                        Box(
                            contentAlignment = Alignment.TopCenter,
                            modifier = Modifier.weight(1f)
                        ) {
                            content(items[i + j])
                        }
                    } else {
                        Spacer(modifier = Modifier.weight(1f, fill = true))
                    }
                }
            }
        }
    }
}

@Composable
fun IngredientCard(
    @DrawableRes iconResource: Int,
    title: String,
    subtitle: String,
    modifier: Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(bottom = 16.dp)
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            backgroundColor = LightGray,
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .padding(bottom = 8.dp)
        ) {
            Image(
                painter = painterResource(id = iconResource),
                contentDescription = null,
                modifier = Modifier.padding(16.dp)
            )
        }
        Text(
            text = title,
            modifier = Modifier.width(100.dp),
            fontSize = 14.sp,
            fontWeight = Medium
        )
        Text(text = subtitle, color = DarkGray, modifier = Modifier.width(100.dp), fontSize = 14.sp)
    }
}

@Composable
fun IngredientsHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .clip(Shapes.medium)
            .background(LightGray)
            .fillMaxWidth()
            .height(44.dp)
    ) {
        TabButton(text = "Ingredients", active = true, modifier = Modifier.weight(1f))
        TabButton(text = "Tools", active = false, modifier = Modifier.weight(1f))
        TabButton(text = "Steps", active = false, modifier = Modifier.weight(1f))
    }
}

@Composable
fun TabButton(text: String, active: Boolean, modifier: Modifier) {
    Button(
        onClick = {},
        shape = RoundedCornerShape(72.dp),
        modifier = modifier.fillMaxHeight(),
        elevation = null,
        colors = if (active) ButtonDefaults.buttonColors(
            backgroundColor = Pink,
            contentColor = White
        ) else ButtonDefaults.buttonColors(
            backgroundColor = LightGray,
            contentColor = DarkGray
        )
    ) {
        Text(text = text)
    }
}

@Composable
fun ServingCalculator() {
    var value by remember { mutableStateOf(6) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(Shapes.medium)
            .background(LightGray)
            .padding(horizontal = 16.dp)
    ) {
        Text(text = "Serving", Modifier.weight(1f), fontWeight = Medium)
        CircularButton(iconResource = R.drawable.ic_minus, elevation = null, onClick = { value-- })
        Text(text = "$value", Modifier.padding(16.dp), fontWeight = Medium)
        CircularButton(iconResource = R.drawable.ic_plus, elevation = null, onClick = { value++ })
    }
}

@Composable
fun Description(recipe: Recipe) {
    Text(
        text = recipe.description,
        fontWeight = Medium,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
    )
}

@Composable
fun BasicInfo(recipe: Recipe) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        InfoColumn(R.drawable.ic_clock, recipe.cookingTime)
        InfoColumn(R.drawable.ic_flame, recipe.energy)
        InfoColumn(R.drawable.ic_star, recipe.rating)
    }
}

@Composable
fun InfoColumn(@DrawableRes iconResource: Int, text: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painter = painterResource(id = iconResource),
            contentDescription = null,
            tint = Pink
        )
        Text(text = text, fontWeight = Bold)
    }
}

@Preview(showBackground = true, widthDp = 380, heightDp = 720)
@Composable
fun DefaultPreview() {
    RecipeMobileUIComposePracticeTheme {
        MainFragment(strawberryCakeRecipe)
    }
}