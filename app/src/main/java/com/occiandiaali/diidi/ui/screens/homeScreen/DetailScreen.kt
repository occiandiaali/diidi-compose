package com.occiandiaali.diidi.ui.screens.homeScreen

import android.annotation.SuppressLint
import android.widget.HorizontalScrollView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.occiandiaali.diidi.data.model.Course
import com.occiandiaali.diidi.data.model.getCourses
import java.time.format.TextStyle

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(navController: NavController, courseId: String?) {
    val newCourseList = getCourses().filter { course -> course.id == courseId }
    Scaffold(topBar = {
        TopAppBar(title = { /*TODO*/ },
            navigationIcon = {Icon(Icons.Default.ArrowBack, "Arrow")},
            modifier = Modifier.clickable { navController.popBackStack() })
    }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top) {
                Text(text = "Course Image")
                HorizontalScrollableImageView(newCourseList)
            }
        }
    }
}

@Composable
private fun HorizontalScrollableImageView(newCourseList: List<Course>) {
    LazyRow {
        items(newCourseList[0].images) { image ->
            Card(modifier = Modifier
                .padding(12.dp)
                .size(240.dp)) {
                AsyncImage(model = image, contentDescription = "Course Poster", modifier = Modifier.fillMaxHeight())
            }
        }
    }
}