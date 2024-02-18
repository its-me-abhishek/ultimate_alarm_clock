package com.example.ultimate_alarm_clock

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import es.antonborri.home_widget.HomeWidgetPlugin
import es.antonborri.home_widget.HomeWidgetLaunchIntent

/** Implementation of App Widget functionality. */
class NewAppWidget : AppWidgetProvider() {
    override fun onUpdate(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            val widgetData = HomeWidgetPlugin.getData(context)
            val views =
                    RemoteViews(context.packageName, R.layout.new_app_widget).apply {
                        val title = widgetData.getString("next_alarm_data", null)
                        setTextViewText(R.id.label_next_alarm, "Next alarm")
                        setTextViewText(R.id.next_alarm_data, title ?: "No upcoming alarms!")

                        // Open App on Widget Click
                        val pendingIntent =
                                HomeWidgetLaunchIntent.getActivity(
                                        context,
                                        MainActivity::class.java
                                )
                        setOnClickPendingIntent(R.id.container_layout, pendingIntent)
                    }

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}
