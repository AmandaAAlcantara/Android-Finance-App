package com.example.mob_dev_portfolio

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.example.mob_dev_portfolio.R


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val cardViewNavigateToActivityB = view.findViewById<CardView>(R.id.contributeCard)
        cardViewNavigateToActivityB.setOnClickListener {
            navigateToActivityB()
        }

        return view
    }

    private fun navigateToActivityB() {
        val intent = Intent(activity, SetGoal::class.java)
        startActivity(intent)
    }
}

