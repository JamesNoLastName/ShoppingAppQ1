package com.example.shoppingappq1

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.example.shoppingappq1.ui.theme.ShoppingAppQ1Theme

data class Product(val name: String, val price: String, val description: String, val imageRes: Int)

// Rehehehe
val products = listOf(
    Product("Mustard", "$999", "Yellow ketchup but it tastes not like it", R.drawable.mustard),
    Product("Cola", "$1", "Slurp", R.drawable.cola),
    Product("One Apology From Socrates", "$399", "How you have felt, O men of Athens, at hearing the speeches of my accusers, I cannot tell; but I know that their persuasive words almost made me forget who I was - such was the effect of them; and yet they have hardly spoken a word of truth. But many as their falsehoods were, there was one of them which quite amazed me; - I mean when they told you... ", R.drawable.socrates)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingAppQ1Theme {
                ShoppingScreen()
            }
        }
    }
}

@Composable
fun ShoppingScreen() {
    val selectedProduct = remember { mutableStateOf<Product?>(null) }
    val windowInfo = calculateWindowInfo()

    Scaffold { innerPadding ->
        if (windowInfo.orientation == Orientation.PORTRAIT) {
            // In portrait mode, show product list and then details in column forme
            Column(modifier = Modifier.padding(innerPadding)) {
                ProductList(products = products, onProductClick = { product ->
                    selectedProduct.value = product
                })
                if (selectedProduct.value != null) {
                    ProductDetails(selectedProduct = selectedProduct.value!!)
                } else {
                    Text("Select a product for details.", fontSize = 20.sp, modifier = Modifier.padding(16.dp))
                }
            }
        } else {
            // In landscape mode, show both the product list and details in row forme
            Row(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                ProductList(
                    products = products,
                    onProductClick = { product -> selectedProduct.value = product },
                    modifier = Modifier.weight(1f)
                )
                Divider(modifier = Modifier.width(1.dp).fillMaxHeight(), color = Color.Gray)
                if (selectedProduct.value != null) {
                    ProductDetails(selectedProduct = selectedProduct.value!!, modifier = Modifier.weight(1f))
                } else {
                    Text("Select a product for details.", fontSize = 20.sp, modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}

@Composable
fun ProductList(products: List<Product>, onProductClick: (Product) -> Unit, modifier: Modifier = Modifier) {
    // Display list of products with images
    LazyColumn(modifier = modifier.padding(16.dp)) {
        items(products) { product ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { onProductClick(product) },
                shape = CutCornerShape(8.dp)
            ) {
                Row(modifier = Modifier.padding(16.dp)) {
                    Image(
                        painter = painterResource(id = product.imageRes),
                        contentDescription = product.name,
                        modifier = Modifier
                            .size(60.dp) // Adjust the image size as needed
                            .padding(end = 16.dp)
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(product.name, fontSize = 20.sp)
                        Text(product.price, fontSize = 16.sp, fontStyle = FontStyle.Italic)
                    }
                }
            }
        }
    }
}

@Composable
fun ProductDetails(selectedProduct: Product, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(16.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(selectedProduct.name, fontSize = 32.sp)
            Text(selectedProduct.price, fontSize = 24.sp, fontStyle = FontStyle.Italic)
            Spacer(modifier = Modifier.height(8.dp))
            Text(selectedProduct.description, fontSize = 18.sp)
        }
    }
}

// Helper function to get window info (orientation, width, height)
@Composable
fun calculateWindowInfo(): WindowInfo {
    val configuration = LocalConfiguration.current
    val orientation = if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
        Orientation.PORTRAIT
    } else {
        Orientation.LANDSCAPE
    }
    return WindowInfo(configuration.screenWidthDp, configuration.screenHeightDp, orientation)
}

// Data class for window info
data class WindowInfo(val widthDp: Int, val heightDp: Int, val orientation: Orientation)

enum class Orientation {
    PORTRAIT,
    LANDSCAPE
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ShoppingAppQ1Theme {
        ShoppingScreen()
    }
}
