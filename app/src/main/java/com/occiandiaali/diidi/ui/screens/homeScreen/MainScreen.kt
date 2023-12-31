package com.occiandiaali.diidi.ui.screens.homeScreen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.occiandiaali.diidi.R
import com.occiandiaali.diidi.navigation.CourseScreens


class Photo(
    val id: Int,
    val url: String,
    val highResUrl: String
)

private val rangeForRandom = (0..100000)

fun randomSampleImageUrl(
    seed: Int = rangeForRandom.random(),
    width: Int = 300,
    height: Int = width,
): String {
    return "https://picsum.photos/seed/$seed/$width/$height"
}

/**
 * Remember a URL generated by [randomSampleImageUrl].
 */
@Composable
fun rememberRandomSampleImageUrl(
    seed: Int = rangeForRandom.random(),
    width: Int = 300,
    height: Int = width,
): String = remember { randomSampleImageUrl(seed, width, height) }

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val mCtx = LocalContext.current
    var expanded by remember { mutableStateOf(false)}
    val ddItems = listOf("Notes", "Gallery", "Account", "Sign Out")
    var selectedIdx by remember {mutableIntStateOf(0)}
    val listings = List(50) {
        val url = rememberRandomSampleImageUrl(width = 256)
        Photo(it, url, url.replace("256", "1024"))
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text("Diidi") },
                colors =
                    TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = Color.Transparent
                    ),
                // Toast.makeText(mCtx, "Open options", Toast.LENGTH_SHORT).show()
                actions = {
                    FilledTonalIconButton(onClick = { expanded = true }) {
            Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier
                                .background(Color.LightGray)
                            ) {
                            ddItems.forEachIndexed { index, s ->
                                DropdownMenuItem(
                                    text = { Text(text = s) },
                                    onClick = {
                                        selectedIdx = index
                                        expanded = false
                                        Toast.makeText(mCtx, "Selected: $s", Toast.LENGTH_SHORT).show()
                                    })
                            }
                        }
        }
                }
                )
        }
    ) {
       // Column(modifier = Modifier.fillMaxSize()) {}
        PosterGrid(listings)
    }
}

@Composable
fun PosterGrid(posters: List<Photo>) {
    var activePhotoId by rememberSaveable {
        mutableStateOf<Int?>(null)
    }
//    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 128.dp)) {
//        items(posters, {it.id}) {poster ->
//            ImageItem(
//                poster,
//                Modifier.clickable { activePhotoId = poster.id }
//            )
//        }
//    }
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(150.dp),
        verticalItemSpacing = 4.dp,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            items(posters, {it.id}) {poster ->
                ImageItem(
                poster,
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clickable { activePhotoId = poster.id }
            )
            }
        }, modifier = Modifier.fillMaxSize())
    if (activePhotoId != null) {
        FullScreenImage(
            poster = posters.first { it.id == activePhotoId },
            onDismiss = { activePhotoId = null }
        )
    }
}

@Composable
fun ImageItem(poster: Photo, modifier: Modifier = Modifier) {
    Image(
        painter = rememberAsyncImagePainter(model = poster.url),
        contentDescription = null,
        modifier = modifier.aspectRatio(1f)
    )
}

@Composable
fun FullScreenImage(
    poster: Photo,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Scrim(onDismiss, Modifier.fillMaxSize())
        ImageWithZoom(poster, Modifier.aspectRatio(1f))
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun Scrim(onClose: () -> Unit, modifier: Modifier = Modifier) {
    val strClose = stringResource(androidx.compose.ui.R.string.close_sheet)
    Box(
        modifier
            // handle pointer input
            // [START android_compose_touchinput_pointerinput_scrim_highlight]
            .pointerInput(onClose) { detectTapGestures { onClose() } }
            // [END android_compose_touchinput_pointerinput_scrim_highlight]
            // handle accessibility services
            .semantics(mergeDescendants = true) {
                contentDescription = strClose
                onClick {
                    onClose()
                    true
                }
            }
            // handle physical keyboard input
            .onKeyEvent {
                if (it.key == Key.Escape) {
                    onClose()
                    true
                } else {
                    false
                }
            }
            // draw scrim
            .background(Color.DarkGray.copy(alpha = 0.75f))
    )
}

@Composable
private fun ImageWithZoom(photo: Photo, modifier: Modifier = Modifier) {
    // [START android_compose_touchinput_pointerinput_double_tap_zoom]
    var zoomed by remember { mutableStateOf(false) }
    var zoomOffset by remember { mutableStateOf(Offset.Zero) }
    Image(
        painter = rememberAsyncImagePainter(model = photo.highResUrl),
        contentDescription = null,
        modifier = modifier
            // [START android_compose_touchinput_pointerinput_double_tap_zoom_highlight]
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = { tapOffset ->
                        zoomOffset = if (zoomed) Offset.Zero else
                            calculateOffset(tapOffset, size)
                        zoomed = !zoomed
                    }
                )
            }
            // [END android_compose_touchinput_pointerinput_double_tap_zoom_highlight]
            .graphicsLayer {
                scaleX = if (zoomed) 2f else 1f
                scaleY = if (zoomed) 2f else 1f
                translationX = zoomOffset.x
                translationY = zoomOffset.y
            }
    )
    // [END android_compose_touchinput_pointerinput_double_tap_zoom]
}


private fun calculateOffset(tapOffset: Offset, size: IntSize): Offset {
    val offsetX = (-(tapOffset.x - (size.width / 2f)) * 2f)
        .coerceIn(-size.width / 2f, size.width / 2f)
    return Offset(offsetX, 0f)
}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun TransparentTopBar() {
//    val listItems = getMenuItemsList()
//    val contextForToast = LocalContext.current.applicationContext
//
//    // menu state
//    var expanded by remember { mutableStateOf(false) }
//
//    TopAppBar(title = {  }, actions = {
//        IconButton(onClick = { expanded = true }) {
//            Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
//        }
//        DropdownMenu(
//            modifier = Modifier.width(width = 150.dp),
//            expanded = expanded,
//            onDismissRequest = { expanded = false },
//            // adjusts the position
//            offset = DpOffset(x = (-102).dp, y = (-64).dp),
//            properties = PopupProperties()
//            ) {
//            listItems.forEach { menuItemData: MenuItemData ->
//                DropdownMenuItem(
//                    text = {  },
//                    onClick = {
//                        Toast.makeText(contextForToast, menuItemData.text,
//                            Toast.LENGTH_SHORT).show()
//                        expanded = false
//                    }, enabled = true) {
//                    Icon(
//                        imageVector = menuItemData.icon,
//                        contentDescription = menuItemData.text
//                    )
//                    Spacer(modifier = Modifier.width(width = 8.dp))
//                    Text(
//                        text = menuItemData.text,
//                        fontWeight = FontWeight.Medium,
//                        fontSize = 16.sp,
//                        color = Color.Black
//                    )
//                }
//            }
//        }
//    })
//
//}

fun getMenuItemsList(): ArrayList<MenuItemData> {
    val listItems = ArrayList<MenuItemData>()
    listItems.add(MenuItemData(text = "Notes", icon = Icons.Outlined.Notifications))
    listItems.add(MenuItemData(text = "Options", icon = Icons.Outlined.Menu))
    listItems.add(MenuItemData(text = "Mail", icon = Icons.Outlined.Email))
    listItems.add(MenuItemData(text = "About", icon = Icons.Outlined.Info))

    return listItems
}

data class MenuItemData(val text: String, val icon: ImageVector)