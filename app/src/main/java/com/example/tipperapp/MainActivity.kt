package com.example.tipperapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.TokenWatcher
import android.text.Editable
import android.text.TextWatcher
import android.util.Log.d
import android.widget.SeekBar
import com.example.tipperapp.databinding.ActivityMainBinding

private const val INITIAL_TIP_PERCENT = 15
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.seekBar.progress = INITIAL_TIP_PERCENT
        binding.tvTipPercentLabel.text = "$INITIAL_TIP_PERCENT%"

        binding.seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                d("cliff", "onProgressChanged $progress")
                binding.tvTipPercentLabel.text = "$progress%"
                computeTipAndTotal()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        binding.etBillAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
//                d("cliff", "afterTextChanged $s")

                computeTipAndTotal()
            }


        })


    }

    private fun computeTipAndTotal() {
        if (binding.etBillAmount.text.isEmpty()) {

            binding.tvTipPercent.text = ""
            binding.tvTotalAmount.text = ""
            return
        }
       val baseAmount = binding.etBillAmount.text.toString().toDouble()
       val tipPercent = binding.seekBar.progress

       val tipAmount = baseAmount * tipPercent / 100
       val totalAmount = baseAmount + tipAmount

        binding.tvTipPercent.text = "%.2f".format(tipAmount)
        binding.tvTotalAmount.text = "%.2f".format(totalAmount)

    }
}