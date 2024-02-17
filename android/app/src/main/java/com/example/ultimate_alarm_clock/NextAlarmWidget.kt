package com.example.ultimate_alarm_clock

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant

class NextAlarmWidget : AppWidgetProvider() {

    private val CHANNEL = "nextAlarmChannel"

    override fun onUpdate(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    internal fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
    ) {
        val views = RemoteViews(context.packageName, R.layout.next_alarm_widget)
        // Set initial text (placeholder)
        // views.setTextViewText(R.id.appwidget_text, "Loading...")

        // Initialize Flutter engine
        val flutterEngine = FlutterEngine(context.applicationContext)
        GeneratedPluginRegistrant.registerWith(flutterEngine)

        // Handle the method channel call to update the widget text
        val channel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
        channel.setMethodCallHandler { call, result ->
            if (call.method == "updateAlarmTime") {
                val alarmTime = call.arguments.toString()
                // Update your widget views with the alarmTime
                views.setTextViewText(R.id.appwidget_text, alarmTime)
                appWidgetManager.updateAppWidget(appWidgetId, views)
                result.success(null)
            } else {
                result.notImplemented()
            }
        }
    }
}
