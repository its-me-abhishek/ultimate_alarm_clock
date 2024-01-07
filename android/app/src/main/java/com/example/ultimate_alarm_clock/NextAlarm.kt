package com.example.ultimate_alarm_clock

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import es.antonborri.home_widget.HomeWidgetPlugin

/**
 * Implementation of App Widget functionality.
 */
class NextAlarm : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            val widgetData = HomeWidgetPlugin.getData(context)
            val views = RemoteViews(context.packageName, R.layout.next_alarm).apply {
            val data = widgetData.getString("next_alarm", null)
            setTextViewText(R.id.next_alarm, title ?: "No Alarms set")
            }
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}