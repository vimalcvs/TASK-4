package com.vimal.healthy.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup.GONE
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.RangeSlider.OnSliderTouchListener
import com.vimal.healthy.R
import kotlinx.android.synthetic.main.fragment_info.btnBmiCalculate
import kotlinx.android.synthetic.main.fragment_info.btnClear
import kotlinx.android.synthetic.main.fragment_info.checkBoxFemale
import kotlinx.android.synthetic.main.fragment_info.checkBoxMale
import kotlinx.android.synthetic.main.fragment_info.heightSlider
import kotlinx.android.synthetic.main.fragment_info.llBmiResult
import kotlinx.android.synthetic.main.fragment_info.ll_one
import kotlinx.android.synthetic.main.fragment_info.ll_two
import kotlinx.android.synthetic.main.fragment_info.tvBmiValue
import kotlinx.android.synthetic.main.fragment_info.tvBmiValueDescription
import kotlinx.android.synthetic.main.fragment_info.tv_height
import kotlinx.android.synthetic.main.fragment_info.tv_weight
import kotlinx.android.synthetic.main.fragment_info.weightSlider


class InfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    var heightValue: List<Float>? = null
    var weightValue: List<Float>? = null

    override fun onPause() {
        super.onPause()
        resetValues()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        btnClear.setOnClickListener {
            resetValues()
            tv_height.text = "0cm"
            tv_weight.text = "0kg"
            btnClear.visibility = View.GONE
            llBmiResult.visibility = GONE
            ll_one.visibility = View.VISIBLE
            ll_two.visibility = View.VISIBLE
            btnBmiCalculate.visibility = View.VISIBLE
        }

        btnBmiCalculate.setOnClickListener {
            when {
                (!checkBoxMale.isChecked) && (!checkBoxFemale.isChecked) -> {
                    Toast.makeText(
                        requireContext(),
                        "Please select your gender",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                heightValue.isNullOrEmpty() -> {
                    Toast.makeText(
                        requireContext(),
                        "Please enter height value",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                weightValue.isNullOrEmpty() -> {
                    Toast.makeText(
                        requireContext(),
                        "Please enter weight value",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    ll_one.visibility = View.GONE
                    ll_two.visibility = View.GONE
                    btnClear.visibility = View.VISIBLE
                    btnBmiCalculate.visibility = View.GONE
                    llBmiResult.visibility = View.VISIBLE
                    bmiCalculation()
                }

            }

//       resetValues()


        }




        checkBoxMale.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                checkBoxFemale.isEnabled = false
                checkBoxFemale.isClickable = false

            } else {
                checkBoxFemale.isEnabled = true
                checkBoxFemale.isClickable = true

            }
        }

        checkBoxFemale.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                checkBoxMale.isEnabled = false
                checkBoxMale.isClickable = false
            } else {
                checkBoxMale.isEnabled = true
                checkBoxMale.isClickable = true

            }

        }


        heightSlider.addOnSliderTouchListener(object : OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {
                heightValue = slider.values
                tv_height.text = "%.2f".format((heightValue as MutableList<Float>)[0]) + "cm"

            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                heightValue = slider.values
                tv_height.text = "%.2f".format((heightValue as MutableList<Float>)[0]) + "cm"

            }

        })

        weightSlider.addOnSliderTouchListener(object : OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {
                weightValue = slider.values
                tv_weight.text = "%.2f".format((weightValue as MutableList<Float>)[0]) + "kg"
            }

            override fun onStopTrackingTouch(slider: RangeSlider) {
                weightValue = slider.values
                tv_weight.text = "%.2f".format((weightValue as MutableList<Float>)[0]) + "kg"
            }

        })

    }

    private fun bmiCalculation() {
        if (checkBoxFemale.isChecked) {
            val bmiValue = ((weightValue!![0]) / (heightValue!![0] * heightValue!![0]) * 10000)
            tvBmiValue.text = "%.2f".format(bmiValue)
            if (bmiValue < 18.50) {
                tvBmiValueDescription.text = "Underweight"
            } else if (bmiValue >= 18.50 && bmiValue <= 24.9) {
                tvBmiValueDescription.text = "Healthy Weight"
            } else if (bmiValue >= 25.00 && bmiValue <= 29.9) {
                tvBmiValueDescription.text = "Overweight"
            } else if (bmiValue > 30.00) {
                tvBmiValueDescription.text = "Obesity"
            }

            Toast.makeText(requireContext(), "BMI FEMALE", Toast.LENGTH_SHORT).show()
//            tvBmiValue.text = (heightValue!![0] + weightValue!![0]).toString()
        } else if (checkBoxMale.isChecked) {
            val bmiValue = ((weightValue!![0]) / (heightValue!![0] * heightValue!![0]) * 10000)
            tvBmiValue.text = "%.2f".format(bmiValue)
            if (bmiValue < 18.50) {
                tvBmiValueDescription.text = "Underweight"
            } else if (bmiValue >= 18.50 && bmiValue <= 24.9) {
                tvBmiValueDescription.text = "Healthy Weight"
            } else if (bmiValue >= 25.00 && bmiValue <= 29.9) {
                tvBmiValueDescription.text = "Overweight"
            } else if (bmiValue > 30.00) {
                tvBmiValueDescription.text = "Obesity"
            }
            Toast.makeText(requireContext(), "BMI MALE", Toast.LENGTH_SHORT).show()
        }

    }

    private fun resetValues() {
        heightValue = null
        weightValue = null
        checkBoxMale.isChecked = false
        checkBoxFemale.isChecked = false
    }


}