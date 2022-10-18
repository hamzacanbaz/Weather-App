package com.hamzacanbaz.weatherapp.util

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.*
import androidx.glance.action.Action
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.*
import androidx.glance.layout.*
import androidx.glance.text.*
import com.hamzacanbaz.weatherapp.R
import com.hamzacanbaz.weatherapp.presentation.MainActivity

class BasicGlanceWidget : GlanceAppWidget() {

    @Composable
    override fun Content() {
        val context = LocalContext.current
        StackLayout {
//            ButtonView(context)
//            ImageView(context = context, ContentScale.Fit)
//            ImageView(context = context, ContentScale.Crop)
//            ImageView(context = context, ContentScale.FillBounds)
//            SpacerView()
            TextView(
                context = context,
                stringResId = R.string.widget_description,
                textDecoration = TextDecoration.None
            )
//            TextView(
//                context = context,
//                stringResId = R.string.widget_description,
//                textDecoration = TextDecoration.Underline
//            )
//            TextView(
//                context = context,
//                stringResId = R.string.widget_description,
//                textDecoration = TextDecoration.LineThrough
//            )
//            CheckBoxView(context = context, isChecked = true)
//            CheckBoxView(context = context, isChecked = false)
//
//            SwitchView(context = context, isChecked = true)
//            SwitchView(context = context, isChecked = false)
        }
    }

    //Layouts
    @Composable
    fun VerticalLayout(content: @Composable ColumnScope.() -> Unit) {
        Column(
            modifier = getParentModifier(),
            verticalAlignment = Alignment.Vertical.CenterVertically,
            horizontalAlignment = Alignment.Horizontal.CenterHorizontally,
            content
        )
    }

    @Composable
    fun HorizontalLayout(content: @Composable RowScope.() -> Unit) {
        Row(
            modifier = getParentModifier(),
            verticalAlignment = Alignment.Vertical.CenterVertically,
            horizontalAlignment = Alignment.Horizontal.CenterHorizontally,
            content = content
        )
    }

    @Composable
    fun StackLayout(content: @Composable () -> Unit) {
        Box(
            modifier = getParentModifier(),
            contentAlignment = Alignment.Center,
            content
        )
    }

    private fun getParentModifier(): GlanceModifier {
        return GlanceModifier.fillMaxSize().background(ImageProvider(R.drawable.bg_widget))
            .appWidgetBackground().padding(10.dp)
    }

    //Views
    @Composable
    fun SwitchView(context: Context, isChecked: Boolean) {
        Switch(
            checked = isChecked, onCheckedChange = actionLaunchActivity(),
            modifier = GlanceModifier.padding(8.dp).background(Color.LightGray),
            maxLines = 1,
            text = "${context.resources.getString(R.string.widget_description)} $isChecked",
            style = TextStyle(
                color = androidx.glance.unit.ColorProvider(Color.White),
                fontSize = 22.sp, fontWeight = FontWeight.Medium, fontStyle = FontStyle.Italic,
                textDecoration = TextDecoration.None, textAlign = TextAlign.Center
            ),
            colors = SwitchColors(thumbColor = R.color.black, trackColor = R.color.purple_700)
        )
    }

    @Composable
    fun CheckBoxView(context: Context, isChecked: Boolean) {
        CheckBox(
            checked = isChecked,
            onCheckedChange = actionLaunchActivity(),
            modifier = GlanceModifier.padding(8.dp).background(
                Color.Magenta
            ),
            maxLines = 1,
            text = "${context.resources.getString(R.string.widget_description)} $isChecked",
            style = TextStyle(
                color = androidx.glance.unit.ColorProvider(Color.White),
                fontSize = 12.sp, fontWeight = FontWeight.Medium, fontStyle = FontStyle.Italic,
                textDecoration = TextDecoration.None, textAlign = TextAlign.Center
            ),
            colors = CheckBoxColors(checkedColor = Color.Green, uncheckedColor = Color.Red)
        )
    }

    @Composable
    fun TextView(context: Context, stringResId: Int, textDecoration: TextDecoration) {
        Text(
            text = context.resources.getString(stringResId),
            modifier = GlanceModifier.padding(8.dp).background(Color.Blue)
                .clickable(actionLaunchActivity()),
            maxLines = 1,
            style = TextStyle(
                color = androidx.glance.unit.ColorProvider(Color.White),
                fontSize = 22.sp, fontWeight = FontWeight.Medium, fontStyle = FontStyle.Italic,
                textDecoration = textDecoration, textAlign = TextAlign.Center
            )
        )
    }

    @Composable
    fun SpacerView() {
        Spacer(
            modifier = GlanceModifier.fillMaxWidth().height(10.dp).background(Color.Gray)
                .clickable(actionLaunchActivity())
        )
    }

    @Composable
    fun ImageView(context: Context, contentScale: ContentScale) {
        Image(
            provider = ImageProvider(R.drawable.ic_launcher_foreground),
            contentDescription = context.resources.getString(R.string.widget_description),
            modifier = GlanceModifier.size(40.dp, 100.dp).background(Color.Green)
                .clickable(actionLaunchActivity()),
            contentScale = contentScale
        )
    }

    @Composable
    fun ButtonView(context: Context) {
        Button(
            text = context.resources.getString(R.string.widget_description),
            onClick = actionLaunchActivity(),
            modifier = GlanceModifier.padding(8.dp).background(
                Color.DarkGray
            ),
            style = TextStyle(
                color = androidx.glance.unit.ColorProvider(Color.White),
                fontSize = 12.sp, fontWeight = FontWeight.Medium, fontStyle = FontStyle.Italic,
                textDecoration = TextDecoration.None, textAlign = TextAlign.Center
            )
        )
    }
}

private fun actionLaunchActivity(): Action = actionStartActivity(MainActivity::class.java)


class BasicGlanceWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = BasicGlanceWidget()
}