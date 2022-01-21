package com.example.meetupapp.ui.addEvent

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.meetupapp.AppApplication
import com.example.meetupapp.R
import com.example.meetupapp.app_extensions.navigateToFragment
import com.example.meetupapp.databinding.FragmentAddEventBinding
import com.example.meetupapp.models.EventCategory
import com.example.meetupapp.models.getColor
import com.example.meetupapp.models.isChecked
import com.example.meetupapp.models.resetStates
import com.example.meetupapp.ui.BaseFragment
import com.example.meetupapp.ui.start.StartFragment
import com.example.meetupapp.ui.utils.viewBinding
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class AddEventFragment : BaseFragment(R.layout.fragment_add_event) {

    private val binding: FragmentAddEventBinding by viewBinding(FragmentAddEventBinding::bind)

    private val addEventViewModel: AddEventViewModel by viewModels(factoryProducer = { viewModelFactory })

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context.applicationContext as AppApplication).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addEventTitleSection.sectionHeaderText.text = requireContext().getString(R.string.add_event_title)
        binding.addEventLocationSection.sectionHeaderText.text = requireContext().getString(R.string.add_event_location)
        binding.addEventStartDateSection.sectionHeaderText.text =
            requireContext().getString(R.string.add_event_start_date)
        binding.addEventEndDateSection.sectionHeaderText.text = requireContext().getString(R.string.add_event_end_date)
        binding.generalCategoryLabel.meetingEventItemCategoryLabel.text = EventCategory.GENERAL.name
        binding.generalCategoryLabel.meetingEventItemCategoryLabel.setBackgroundColor(
            EventCategory.GENERAL.getColor(
                requireContext()
            )
        )
        binding.socialCategoryLabel.meetingEventItemCategoryLabel.text = EventCategory.SOCIAL.name
        binding.socialCategoryLabel.meetingEventItemCategoryLabel.setBackgroundColor(
            EventCategory.SOCIAL.getColor(
                requireContext()
            )
        )
        binding.sportCategoryLabel.meetingEventItemCategoryLabel.text = EventCategory.SPORT.name
        binding.sportCategoryLabel.meetingEventItemCategoryLabel.setBackgroundColor(
            EventCategory.SPORT.getColor(
                requireContext()
            )
        )
        binding.onlineCategoryLabel.meetingEventItemCategoryLabel.text = EventCategory.ONLINE.name
        binding.onlineCategoryLabel.meetingEventItemCategoryLabel.setBackgroundColor(
            EventCategory.ONLINE.getColor(
                requireContext()
            )
        )

        //Categories
        binding.generalCategoryLabel.categoryCard.setOnClickListener {
            checkCategory(it as MaterialCardView, EventCategory.GENERAL)
            binding.socialCategoryLabel.categoryCard.isChecked = EventCategory.SOCIAL.checked
            binding.sportCategoryLabel.categoryCard.isChecked = EventCategory.SPORT.checked
            binding.onlineCategoryLabel.categoryCard.isChecked = EventCategory.ONLINE.checked
        }

        binding.socialCategoryLabel.categoryCard.setOnClickListener {
            checkCategory(it as MaterialCardView, EventCategory.SOCIAL)
            binding.generalCategoryLabel.categoryCard.isChecked = EventCategory.GENERAL.checked
            binding.sportCategoryLabel.categoryCard.isChecked = EventCategory.SPORT.checked
            binding.onlineCategoryLabel.categoryCard.isChecked = EventCategory.ONLINE.checked
        }
        binding.sportCategoryLabel.categoryCard.setOnClickListener {
            checkCategory(it as MaterialCardView, EventCategory.SPORT)
            binding.generalCategoryLabel.categoryCard.isChecked = EventCategory.GENERAL.checked
            binding.socialCategoryLabel.categoryCard.isChecked = EventCategory.SOCIAL.checked
            binding.onlineCategoryLabel.categoryCard.isChecked = EventCategory.ONLINE.checked
        }
        binding.onlineCategoryLabel.categoryCard.setOnClickListener {
            checkCategory(it as MaterialCardView, EventCategory.ONLINE)
            binding.generalCategoryLabel.categoryCard.isChecked = EventCategory.GENERAL.checked
            binding.sportCategoryLabel.categoryCard.isChecked = EventCategory.SPORT.checked
            binding.socialCategoryLabel.categoryCard.isChecked = EventCategory.SOCIAL.checked
        }

        binding.navBackButton.setOnClickListener {
            this.navigateToFragment(StartFragment())
        }

        with(binding.addEventTitleSection) {
            userInput.doOnTextChanged { s, _, _, _ ->
                varLetterCount.text = "${s?.toString()?.length}"
            }
        }

        with(binding.addEventLocationSection) {
            userInput.doOnTextChanged { s, _, _, _ ->
                varLetterCount.text = "${s?.toString()?.length}"
            }
        }

        //start date section is clicked
        with(binding.addEventStartDateSection) {
            userInput.setOnClickListener {
                openStartDateTimePickerDialog(
                    requireContext(),
                    userInput,
                    binding.addEventEndDateSection.userInput.text.toString()
                )
            }
        }

        //end date section is clicked
        with(binding.addEventEndDateSection) {
            userInput.setOnClickListener {
                openEndDateTimePickerDialog(
                    requireContext(),
                    userInput,
                    binding.addEventStartDateSection.userInput.text.toString()
                )
            }
        }

        binding.maxParticipantsCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.maxParticipantsSection.visibility = View.VISIBLE
                binding.maxParticipantsUserInput.visibility = View.VISIBLE
            } else {
                binding.maxParticipantsSection.visibility = View.INVISIBLE
                binding.maxParticipantsUserInput.visibility = View.INVISIBLE
            }
        }

        binding.createEventButton.setOnClickListener {
            handleErrorHint(binding.addEventTitleSection.userInput, binding.addEventTitleSection.errorHintText)

            handleErrorHint(binding.addEventLocationSection.userInput, binding.addEventLocationSection.errorHintText)

            handleErrorHint(binding.addEventStartDateSection.userInput, binding.addEventStartDateSection.errorHintText)

            if (binding.addEventLocationSection.errorHintText.visibility == View.INVISIBLE &&
                binding.addEventTitleSection.errorHintText.visibility == View.INVISIBLE &&
                binding.addEventStartDateSection.errorHintText.visibility == View.INVISIBLE
            ) {
                viewLifecycleOwner.lifecycleScope.launch {

                    val activity = this@AddEventFragment.requireActivity()
                    val dialog = Dialog(activity)
                    showLoadingDialog(dialog,activity)

                    addEventViewModel.addEventToDatabase(
                        binding.addEventTitleSection.userInput.text.toString(),
                        binding.addEventLocationSection.userInput.text.toString(),
                        addEventViewModel.getDateFromDateTime(binding.addEventStartDateSection.userInput.text.toString()),
                        addEventViewModel.getEndDate(binding.addEventEndDateSection.userInput.text.toString()),
                        addEventViewModel.getTimeFromDateTime(binding.addEventStartDateSection.userInput.text.toString()),
                        addEventViewModel.getEndTime(binding.addEventEndDateSection.userInput.text.toString()),
                        addEventViewModel.getCheckedCategory(requireContext()),
                        getMaxParticipants(
                            binding.maxParticipantsCheckBox,
                            binding.maxParticipantsUserInput
                        ),
                        active = true,
                        canceled = false,
                        canceledMessage = ""
                    )
                    addEventViewModel.updateUserEvents()

                    delay(1000L)
                    hideLoadingDialog(dialog,activity)

                    this@AddEventFragment.navigateToFragment(StartFragment())
                }
            }
        }

    }

    private fun handleErrorHint(userInput: EditText, errorText: TextView) {
        if (userInput.text.toString().isNotEmpty()) {
            if (errorText.visibility == View.VISIBLE) {
                errorText.visibility = View.INVISIBLE
            }
        } else {
            errorText.visibility = View.VISIBLE
        }
    }

    private fun handleErrorHint(userInput: TextView, errorText: TextView) {
        if (userInput.text.toString().isNotEmpty()) {
            if (errorText.visibility == View.VISIBLE) {
                errorText.visibility = View.INVISIBLE
            }
        } else {
            errorText.visibility = View.VISIBLE
        }
    }

    private fun checkCategory(cardView: MaterialCardView, category: EventCategory) {
        if (category.checked) {
            resetStates()
            cardView.apply {
                isChecked = !isChecked
            }
        } else {
            category.isChecked()
            cardView.apply {
                isChecked = category.checked
            }
        }
    }

    private fun getMaxParticipants(checkBox: CheckBox, maxParticipantsUserInput: EditText): String {
        return if (checkBox.isChecked && maxParticipantsUserInput.text.isNotEmpty()) {
            maxParticipantsUserInput.text.toString()
        } else {
            "0"
        }
    }

    private fun openStartDateTimePickerDialog(
            context: Context,
            startDateTextView: TextView,
            endDate: String
    ) {
        val currentDateTime = Calendar.getInstance()
        val savedYear = currentDateTime.get(Calendar.YEAR)
        val savedMonth = currentDateTime.get(Calendar.MONTH)
        val savedDay = currentDateTime.get(Calendar.DAY_OF_MONTH)
        val savedHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
        val savedMinute = currentDateTime.get(Calendar.MINUTE)

        val datePickerDialog = DatePickerDialog(context, { _, year, month, day ->
            TimePickerDialog(context, { _, hour, minute ->
                val pickedDateTime = Calendar.getInstance()
                pickedDateTime.set(year, month, day, hour, minute)
                val dateFormat = "dd.MM.yyyy HH:mm"
                val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.getDefault())
                val dateString = simpleDateFormat.format(pickedDateTime.time).toString()
                startDateTextView.text = dateString

            }, savedHour, savedMinute, false).show()
        }, savedYear, savedMonth, savedDay)
        if (endDate.isNotEmpty()) {
            datePickerDialog.datePicker.maxDate = addEventViewModel.convertStringToDate(endDate).time
        }
        datePickerDialog.datePicker.minDate = Calendar.getInstance().timeInMillis
        datePickerDialog.show()

    }

    private fun openEndDateTimePickerDialog(
            context: Context,
            endDateTextView: TextView,
            startDate: String
    ) {
        val currentDateTime = Calendar.getInstance()
        val savedYear = currentDateTime.get(Calendar.YEAR)
        val savedMonth = currentDateTime.get(Calendar.MONTH)
        val savedDay = currentDateTime.get(Calendar.DAY_OF_MONTH)
        val savedHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
        val savedMinute = currentDateTime.get(Calendar.MINUTE)

        val datePickerDialog = DatePickerDialog(context, { _, year, month, day ->
            TimePickerDialog(context, { _, hour, minute ->
                val pickedDateTime = Calendar.getInstance()
                pickedDateTime.set(year, month, day, hour, minute)
                val dateFormat = "dd.MM.yyyy HH:mm"
                val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.getDefault())
                val dateString = simpleDateFormat.format(pickedDateTime.time).toString()
                endDateTextView.text = dateString

            }, savedHour, savedMinute, false).show()

        }, savedYear, savedMonth, savedDay)

        if (startDate.isNotEmpty()) {
            datePickerDialog.datePicker.minDate = addEventViewModel.convertStringToDate(startDate).time

        }
        datePickerDialog.show()
    }

    private fun showLoadingDialog(dialog: Dialog,activity: Activity){
        activity.window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        (dialog.window?:throw Exception("")).setBackgroundDrawableResource(R.color.progress_dialog_bg)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.progress_dialog_layout)
        dialog.show()
    }

    private fun hideLoadingDialog(dialog: Dialog, activity: Activity){
        activity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        dialog.dismiss()

    }

}






