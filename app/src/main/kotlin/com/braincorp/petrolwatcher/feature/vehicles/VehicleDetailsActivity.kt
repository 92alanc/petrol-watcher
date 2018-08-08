package com.braincorp.petrolwatcher.feature.vehicles

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.AdapterView
import android.widget.Spinner
import com.braincorp.petrolwatcher.R
import com.braincorp.petrolwatcher.feature.vehicles.api.VehicleApi
import com.braincorp.petrolwatcher.feature.vehicles.contract.VehicleDetailsActivityContract
import com.braincorp.petrolwatcher.feature.vehicles.presenter.VehicleDetailsActivityPresenter
import com.braincorp.petrolwatcher.utils.GenericSpinnerAdapter
import com.braincorp.petrolwatcher.utils.dependencyInjection
import com.braincorp.petrolwatcher.utils.toRange
import kotlinx.android.synthetic.main.activity_vehicle_details.*
import kotlinx.android.synthetic.main.content_vehicle_details.*

/**
 * The activity where vehicle details are
 * shown and edited
 */
class VehicleDetailsActivity : AppCompatActivity(), VehicleDetailsActivityContract.View,
                               AdapterView.OnItemSelectedListener {

    companion object {
        private const val KEY_INPUT_TYPE = "input_type"
        private const val KEY_YEAR_RANGE = "year_range"
        private const val KEY_MANUFACTURERS = "manufacturers"
        private const val KEY_MODELS = "models"
        private const val KEY_TRIM_LEVELS = "trim_levels"
        private const val KEY_SELECTED_YEAR = "selected_year"
        private const val KEY_SELECTED_MANUFACTURER = "selected_manufacturer"
        private const val KEY_SELECTED_MODEL = "model"
        private const val KEY_SELECTED_TRIM_LEVEL = "trim_level"
    }

    override lateinit var presenter: VehicleDetailsActivityContract.Presenter

    private var yearRange = IntRange.EMPTY
    private var selectedYear = 0
    private var manufacturers = ArrayList<String>()
    private var selectedManufacturer = ""
    private var models = ArrayList<String>()
    private var selectedModel = ""
    private var trimLevels = ArrayList<String>()
    private var selectedTrimLevel = ""

    private var yearSelectedCount = 0
    private var manufacturerSelectedCount = 0
    private var modelSelectedCount = 0
    private var trimLevelSelectedCount = 0

    private var inputType = InputType.AUTO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vehicle_details)
        setupToolbar()
        val baseUrl = dependencyInjection().getVehiclesApiBaseUrl()
        presenter = VehicleDetailsActivityPresenter(VehicleApi.getApi(baseUrl), view = this)
        if (savedInstanceState != null)
            restoreInstanceState(savedInstanceState)
        else
            presenter.getYearRange()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_vehicle_details, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.isChecked = true

        when (item?.itemId) {
            R.id.item_auto_input -> setupAutoInput()
            R.id.item_manual_input -> setupManualInput()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (outState == null) return

        with (outState) {
            putSerializable(KEY_INPUT_TYPE, inputType)

            yearRange.let {
                if (!it.isEmpty())
                    putIntArray(KEY_YEAR_RANGE, it.toList().toIntArray())
            }

            manufacturers.let {
                if (it.isNotEmpty())
                    putStringArrayList(KEY_MANUFACTURERS, it)
            }

            models.let {
                if (it.isNotEmpty())
                    putStringArrayList(KEY_MODELS, it)
            }

            trimLevels.let {
                if (it.isNotEmpty())
                    putStringArrayList(KEY_TRIM_LEVELS, it)
            }

            selectedYear.let {
                if (it != 0)
                    putInt(KEY_SELECTED_YEAR, it)
            }

            selectedManufacturer.let {
                if (it != "")
                    putString(KEY_SELECTED_MANUFACTURER, it)
            }

            selectedModel.let {
                if (it != "")
                    putString(KEY_SELECTED_MODEL, it)
            }

            selectedTrimLevel.let {
                if (it != "")
                    putString(KEY_SELECTED_TRIM_LEVEL, it)
            }
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null)
            restoreInstanceState(savedInstanceState)
    }

    override fun onItemSelected(spinner: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (spinner?.id) {
            R.id.spn_year -> {
                if (++yearSelectedCount > 1) {
                    selectedYear = yearRange.toList()[position]
                    presenter.getManufacturers(selectedYear)
                }
            }

            R.id.spn_manufacturer -> {
                if (++manufacturerSelectedCount > 1) {
                    selectedManufacturer = manufacturers[position]
                    presenter.getModels(selectedYear, selectedManufacturer)
                }
            }

            R.id.spn_name -> {
                if (++modelSelectedCount > 1) {
                    selectedModel = models[position]
                    presenter.getDetails(selectedYear, selectedManufacturer, selectedModel)
                }
            }

            R.id.spn_trim_level -> {
                if (++trimLevelSelectedCount > 1)
                    selectedTrimLevel = trimLevels[position]
            }
        }
    }

    override fun onNothingSelected(adapterView: AdapterView<*>?) { }

    /**
     * Sets the year range
     *
     * @param range the range
     */
    override fun setYearRange(range: IntRange) {
        this.yearRange = range
        populateSpinner(spn_year, range)
    }

    /**
     * Sets the manufacturer list
     *
     * @param manufacturers the list
     */
    override fun setManufacturerList(manufacturers: ArrayList<String>) {
        this.manufacturers = manufacturers
        populateSpinner(spn_manufacturer, manufacturers)
    }

    /**
     * Sets the models list
     *
     * @param models the list
     */
    override fun setModelsList(models: ArrayList<String>) {
        this.models = models
        populateSpinner(spn_name, models)
    }

    /**
     * Sets the trim level list
     *
     * @param trimLevels the list
     */
    override fun setTrimLevelList(trimLevels: ArrayList<String>) {
        this.trimLevels = trimLevels
        populateSpinner(spn_trim_level, trimLevels)
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupAutoInput() {
        inputType = InputType.AUTO

        group_manual_input.visibility = GONE
        group_auto_input.visibility = VISIBLE
        if (yearRange == IntRange.EMPTY)
            presenter.getYearRange()
    }

    private fun setupManualInput() {
        inputType = InputType.MANUAL

        group_auto_input.visibility = GONE
        group_manual_input.visibility = VISIBLE

        setEditTextValue(edt_year, selectedYear)
        setEditTextValue(edt_manufacturer, selectedManufacturer)
        setEditTextValue(edt_name, selectedModel)
        setEditTextValue(edt_trim_level, selectedTrimLevel)
    }

    private fun restoreInstanceState(savedInstanceState: Bundle) {
        with (savedInstanceState) {
            if (containsKey(KEY_INPUT_TYPE))
                inputType = getSerializable(KEY_INPUT_TYPE) as InputType

            if (containsKey(KEY_YEAR_RANGE)) {
                yearRange = getIntArray(KEY_YEAR_RANGE)!!.toRange()
                setYearRange(yearRange)
            }

            if (containsKey(KEY_MANUFACTURERS)) {
                manufacturers = getStringArrayList(KEY_MANUFACTURERS)!!
                setManufacturerList(manufacturers)
            }

            if (containsKey(KEY_MODELS)) {
                models = getStringArrayList(KEY_MODELS)!!
                setModelsList(models)
            }

            if (containsKey(KEY_TRIM_LEVELS)) {
                trimLevels = getStringArrayList(KEY_TRIM_LEVELS)!!
                setTrimLevelList(trimLevels)
            }

            if (containsKey(KEY_SELECTED_YEAR)) {
                selectedYear = getInt(KEY_SELECTED_YEAR)
                if (inputType == InputType.AUTO)
                    spn_year.setSelection(yearRange.toList().indexOf(selectedYear))
                else
                    edt_year.setText(selectedYear.toString())
            }

            if (containsKey(KEY_SELECTED_MANUFACTURER)) {
                selectedManufacturer = getString(KEY_SELECTED_MANUFACTURER)!!
                if (inputType == InputType.AUTO)
                    spn_manufacturer.setSelection(manufacturers.indexOf(selectedManufacturer))
                else
                    edt_manufacturer.setText(selectedManufacturer)
            }

            if (containsKey(KEY_SELECTED_MODEL)) {
                selectedModel = getString(KEY_SELECTED_MODEL)!!
                if (inputType == InputType.AUTO)
                    spn_name.setSelection(models.indexOf(selectedModel))
                else
                    edt_name.setText(selectedModel)
            }

            if (containsKey(KEY_SELECTED_TRIM_LEVEL)) {
                selectedTrimLevel = getString(KEY_SELECTED_TRIM_LEVEL)!!
                if (inputType == InputType.AUTO)
                    spn_trim_level.setSelection(trimLevels.indexOf(selectedTrimLevel))
                else
                    edt_trim_level.setText(selectedTrimLevel)
            }

            if (inputType == InputType.AUTO)
                setupAutoInput()
            else
                setupManualInput()
        }
    }

    private fun <T> populateSpinner(spinner: Spinner, values: T) {
        with (spinner) {
            adapter = null
            if (values is IntRange)
                adapter = GenericSpinnerAdapter(this@VehicleDetailsActivity, values.toList())
            else if (values is ArrayList<*>)
                adapter = GenericSpinnerAdapter(this@VehicleDetailsActivity, values)
            onItemSelectedListener = this@VehicleDetailsActivity
        }
    }

    private fun <T> setEditTextValue(editText: TextInputEditText, value: T) {
        value.let {
            if (it is Int && it != 0)
                editText.setText(it.toString())
            else if (it is String && it != "")
                editText.setText(it)
        }
    }

    private enum class InputType {
        AUTO,
        MANUAL
    }

}