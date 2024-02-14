package com.example.ultimate_alarm_clock

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.widget.RemoteViews
import es.antonborri.home_widget.HomeWidgetBackgroundIntent
import es.antonborri.home_widget.HomeWidgetLaunchIntent
import es.antonborri.home_widget.HomeWidgetProvider

class HomeScreenWidgetProvider : HomeWidgetProvider() {
    override fun onUpdate(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetIds: IntArray,
            widgetData: SharedPreferences
    ) {
        appWidgetIds.forEach { widgetId ->
            val views =
                    RemoteViews(context.packageName, R.layout.widget_layout).apply {
                        val pendingIntent =
                                HomeWidgetLaunchIntent.getActivity(
                                        context,
                                        MainActivity::class.java
                                )
                        setOnClickPendingIntent(R.id.widget_root, pendingIntent)

                        // val widgetText = widget.getString('_nextAlarm', 'No Alarm Scheduled')
                        val alarmTime: String =
                                try {
                                    val result =
                                            MethodChannel(context.packageName + "/alarmTime")
                                                    .invokeMethod("getAlarmTime") as
                                                    String
                                    result ?: "No upcoming alarms!" // Default value if the result
                                    // is null
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    "No upcoming alarms!" // Default value if an exception occurs
                                }
                        setTextViewText(R.id.idAlarmValue, alarmTime)

                        val backgroundIntent =
                                HomeWidgetBackgroundIntent.getBroadcast(
                                        context,
                                        Uri.parse("homeWidgetExample://updateAlarm")
                                )
                        setOnClickPendingIntent(R.id.idAlarmValue, backgroundIntent)
                    }
            appWidgetManager.updateAppWidget(widgetId, views)
        }
    }
}
