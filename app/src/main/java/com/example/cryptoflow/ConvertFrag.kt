package com.example.cryptoflow

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import coil.load
import com.example.cryptoflow.placeholder.Coin

// Fragment to convert crypto's value in euros
class ConvertFrag : Fragment(R.layout.fragment_convert_), AdapterView.OnItemSelectedListener {

    private var coinAdapter: ArrayAdapter<Coin>? = null
    private val viewModel: MainViewModel by viewModels()
    private lateinit var mainActivity: MainActivity
    private lateinit var crypto : String
    private var device = "€"

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = activity as MainActivity
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val spinner = view.findViewById<Spinner>(R.id.spinner)
        val buttonConvert = view.findViewById<Button>(R.id.button_convert)
        val textCoin = view.findViewById<TextView>(R.id.convert_coin)
        val imageCoin = view.findViewById<ImageView>(R.id.convert_image)
        val numberCoin = view.findViewById<TextView>(R.id.convert_number)
        val numberToConvert = view.findViewById<EditText>(R.id.search_number)
        val imageConvert = view.findViewById<ImageView>(R.id.image_convert)
        imageConvert.isVisible = false

        coinAdapter = activity?.let { ArrayAdapter(it.baseContext, android.R.layout.simple_spinner_item, mainActivity.coins) }.also { adapter -> adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
        spinner.adapter = coinAdapter
        spinner.onItemSelectedListener = this



        val coinObserver = Observer<List<Coin>> { newCoin ->
            mainActivity.coins.clear()
            mainActivity.coins.addAll(newCoin)
            coinAdapter?.notifyDataSetChanged()

        }
        viewModel.coin.observe(viewLifecycleOwner,coinObserver)
        viewModel.cryptoAPI()

        // Function Convert crypto in Euro
        fun convert(){
            // check if a number value is set
            if (numberToConvert.text.isEmpty()){ // if no number is given display a warning
                val toast = Toast.makeText(context, context?.getString(R.string.Toast_no_number_value), Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER,20,30)
                toast.show()
            }
            else{
                // recovery of the selected coin
                val coinFound : Coin? = mainActivity.coins.firstOrNull{ coin -> coin.name == crypto}
                // make the conversion icon visible
                imageConvert.isVisible = true
                // set the text with number and coin
                textCoin.text = numberToConvert.text.toString() + " " + coinFound?.name.toString()
                imageCoin.load(coinFound?.image)
                // conversion to keep only one digit after the decimal point
                val convert : Double = numberToConvert.text.toString().toDouble() * coinFound!!.current_price
                val convertFormat = String.format("%.1f",convert)
                numberCoin.text = "$convertFormat $device"
            }

        }

        // bind the virtual keyboard (done) control to call a specific function
        fun EditText.onDone(callback: () -> Unit) {
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    callback.invoke()
                }
                false
            }
        }

        // Call action to convert coin
        numberToConvert.onDone { convert() }
        buttonConvert.setOnClickListener{
            convert()
        }

    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        crypto = parent?.getItemAtPosition(pos).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}