package com.example.cryptoflow

import android.annotation.SuppressLint
import android.content.Context

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import coil.load
import com.example.cryptoflow.placeholder.Coin

class ConvertFrag : Fragment(R.layout.fragment_convert_), AdapterView.OnItemSelectedListener {

    private var monSuperAdapter: ArrayAdapter<Coin>? = null
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

        monSuperAdapter = activity?.let { ArrayAdapter(it.baseContext, android.R.layout.simple_spinner_item, mainActivity.coins) }.also { adapter -> adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
        spinner.adapter = monSuperAdapter
        spinner.onItemSelectedListener = this



        val coinObserver = Observer<List<Coin>> { newCoin ->
            mainActivity.coins.clear()
            mainActivity.coins.addAll(newCoin)
            monSuperAdapter?.notifyDataSetChanged()

        }
        viewModel.coin.observe(viewLifecycleOwner,coinObserver)
        viewModel.cryptoAPI()


        buttonConvert.setOnClickListener{
            val coinFound : Coin? = mainActivity.coins.firstOrNull{ coin -> coin.name == crypto}
            val textCoin = view.findViewById<TextView>(R.id.convert_coin)
            val imageCoin = view.findViewById<ImageView>(R.id.convert_image)
            val numberCoin = view.findViewById<TextView>(R.id.convert_number)
            val numberToConvert = view.findViewById<TextView>(R.id.search_number)
            val imageConvert = view.findViewById<ImageView>(R.id.image_convert)
            imageConvert.load(R.drawable.ic_round_convert_24)
            textCoin.text = numberToConvert.text.toString() + " " + coinFound?.name.toString()
            imageCoin.load(coinFound?.image)
            val convert : Double = numberToConvert.text.toString().toDouble() * coinFound!!.current_price
            numberCoin.text = "$convert $device"
        }

    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        crypto = parent?.getItemAtPosition(pos).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}