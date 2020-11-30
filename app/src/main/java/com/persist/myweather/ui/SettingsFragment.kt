package com.persist.myweather.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import com.persist.myweather.R

class SettingsFragment : Fragment() {

    private lateinit var prefs: SharedPreferences
    private lateinit var rgTemperature: RadioGroup
    private lateinit var rgLanguage: RadioGroup
    private lateinit var rbC: RadioButton
    private lateinit var rbF: RadioButton
    private lateinit var rbEnglish: RadioButton
    private lateinit var rbPortuguese: RadioButton
    private lateinit var btnSave: Button

    private lateinit var temperatureUnit: String
    private lateinit var language: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    fun initRadioGroup(view: View){
        // configura RadioGroup
        rgTemperature = view.findViewById(R.id.rg_temperature_unit)
        rgLanguage = view.findViewById(R.id.rg_language)

        // configura RadioButton
        rbC = view.findViewById(R.id.rb_c)
        rbF = view.findViewById(R.id.rb_f)
        rbEnglish = view.findViewById(R.id.rb_english)
        rbPortuguese = view.findViewById(R.id.rb_portuguese)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs = view.context.getSharedPreferences("my_weather_prefs", Context.MODE_PRIVATE)

        btnSave = view.findViewById(R.id.btn_save)
        btnSave.setOnClickListener{
            onSaveClicked(it)
        }

        // inicia radio group e radio buttons
        this.initRadioGroup(view)

        // carrega os valores salvos
        language = prefs?.getString("language", "PT").toString()
        temperatureUnit = prefs?.getString("temperature_unit", "C").toString()

        when (language) {
            "EN" -> rbEnglish.isChecked = true
            "PT" -> rbPortuguese.isChecked = true
        }

        when (temperatureUnit) {
            "C" -> rbC.isChecked = true
            "F" -> rbF.isChecked = true
        }

        rgTemperature.setOnCheckedChangeListener { view, id ->
            var radiobutton = view.findViewById<RadioButton>(id)

            if(radiobutton.isChecked) {
                when (radiobutton.id) {
                    R.id.rb_c -> temperatureUnit = "C"
                    R.id.rb_f -> temperatureUnit = "F"
                }
            }
        }

        rgLanguage.setOnCheckedChangeListener{ view, id ->
            var radiobutton = view.findViewById<RadioButton>(id)

            if(radiobutton.isChecked) {
                when (radiobutton.id) {
                    R.id.rb_english -> language = "EN"
                    R.id.rb_portuguese -> language = "PT"
                }
            }
        }
    }

    fun onSaveClicked(view: View){
        var editor = prefs?.edit()

        editor?.apply{
            putString("temperature_unit", temperatureUnit)
            putString("language", language)
            apply()
        }
    }

}