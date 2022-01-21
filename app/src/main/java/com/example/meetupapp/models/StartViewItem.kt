package com.example.meetupapp.models

import androidx.annotation.StringRes

sealed class StartViewItem {

    data class SectionHeaderItem(@StringRes val headerText: Int) : StartViewItem()

    data class DateHeaderItem(val headerText: String) : StartViewItem()

    data class StartViewMeetingEventItem(val meetingEventItem: MeetingEventItem) : StartViewItem()

    object ShowAllItemsBtn : StartViewItem()

    data class MyEventsList(val myMeetingEventItemList: List<MyMeetingEventItem>) : StartViewItem()

    object EmptyStartViewMeetingEventItem : StartViewItem()

    object EmptyMyEventsList : StartViewItem()

    data class ShowAllItemsTextBtn(val showTextBtn: Boolean, val isMyEvents: Boolean) : StartViewItem()

}