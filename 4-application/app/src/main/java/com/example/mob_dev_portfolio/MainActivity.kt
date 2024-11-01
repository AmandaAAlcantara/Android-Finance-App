package com.example.mob_dev_portfolio


import SavingsFragment
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.mob_dev_portfolio.budget_transaction.TransactionActivity
import com.example.mob_dev_portfolio.databinding.ActivityMainBinding
import com.example.mob_dev_portfolio.login.UserLoginActivity
import com.example.mob_dev_portfolio.nav_menu.FoodFragment
import com.example.mob_dev_portfolio.nav_menu.HomeFragment
import com.example.mob_dev_portfolio.nav_menu.ShoppingFragment
import com.example.mob_dev_portfolio.nav_menu.SubscriptionFragment
import com.example.mob_dev_portfolio.nav_menu.TransportFragment
import com.example.mob_dev_portfolio.nav_menu.UtilitiesFragment
import com.example.mob_dev_portfolio.news.NewsActivity
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var  fragmentManager: FragmentManager
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        setSupportActionBar(binding.toolbar)

        val toggle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.nav_open, R.string.nav_close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navigationDrawer.setNavigationItemSelectedListener(this)

        binding.bottomNavigation.background = null

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.bottom_home -> openFragment(HomeFragment())
                R.id.bottom_overview -> {
                    // Redirect to Overview
                    val intent = Intent(this@MainActivity, com.example.mob_dev_portfolio.graph.GraphView::class.java)
                    startActivity(intent)
                }
                R.id.bottom_budget -> {
                    // Redirect to BudgetActivity
                    val intent = Intent(this@MainActivity, TransactionActivity::class.java)
                    startActivity(intent)
                }

                R.id.bottom_profile -> {
                    // Redirect to UserLoginActivity
                    val intent = Intent(this@MainActivity, UserLoginActivity::class.java)
                    startActivity(intent)
                }

                R.id.bottom_news -> {
                    // Redirect to UserLoginActivity
                    val intent = Intent(this@MainActivity, NewsActivity::class.java)
                    startActivity(intent)
                }

            }
            true
        }
        fragmentManager = supportFragmentManager
        openFragment(HomeFragment())
    }




    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_transportation -> openFragment(TransportFragment())
            R.id.nav_subscriptions -> openFragment(SubscriptionFragment())
            R.id.nav_utilities -> openFragment(UtilitiesFragment())
            R.id.nav_food -> openFragment(FoodFragment())
            R.id.nav_savings -> openFragment(SavingsFragment())
            R.id.nav_shopping -> openFragment(ShoppingFragment())

        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }else {
            super.getOnBackPressedDispatcher().onBackPressed()
        }
    }
    private fun openFragment(fragment: Fragment){
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }
}