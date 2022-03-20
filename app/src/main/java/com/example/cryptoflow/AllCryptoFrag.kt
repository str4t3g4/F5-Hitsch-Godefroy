package com.example.cryptoflow

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoflow.placeholder.Coin

// main fragment to display crypto's flow
class AllCryptoFrag : Fragment() {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var mainActivity: MainActivity
    private var recyclerView: RecyclerView? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = activity as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view : View = inflater.inflate(R.layout.fragment_all_crypto_, container, false)

        // recyclerview to display cryptos
        recyclerView = view.findViewById(R.id.recyclerview_coins)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = CoinListAdapter(mainActivity.coins)

        return view

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.cryptoAPI()
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar_crypto) // progress bar while loading cryptos

        // observer to track any update
        val coinObserver = Observer<List<Coin>> { newCoin ->
            mainActivity.coins.clear()
            mainActivity.coins.addAll(newCoin)
            recyclerView?.adapter?.notifyDataSetChanged()
            if(mainActivity.coins.size > 0) {
                // delete progressBar
                progressBar.visibility = View.GONE
            }

        }
        viewModel.coin.observe(viewLifecycleOwner,coinObserver)
    }
}