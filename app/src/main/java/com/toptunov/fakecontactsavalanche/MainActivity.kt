package com.toptunov.fakecontactsavalanche

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.widget.addTextChangedListener
import com.toptunov.fakecontactsavalanche.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var evQuantity : EditText
    private lateinit var generateButton : Button
    private lateinit var progress : ProgressBar
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        evQuantity = binding.contactsQuantity
        generateButton = binding.generateButton
        progress = binding.progressBar

        progress.visibility = View.GONE
        generateButton.isEnabled = false

        evQuantity.addTextChangedListener{
            generateButton.isEnabled = true
        }

        generateButton.setOnClickListener {
            Handler(Looper.getMainLooper()).postDelayed({
                progress.visibility = View.VISIBLE
                progress.animate()
            }, 10)

            val numberOfContactsString = evQuantity.text.toString()
            val numberOfContacts : Int
            if (numberOfContactsString != ""){
                numberOfContacts = numberOfContactsString.toInt()
                Log.d("[AppDebugLog]", numberOfContacts.toString())
                generateButton.isEnabled = false
                createContacts(numberOfContacts)
            }else{
                Toast.makeText(this, "Nothing to create.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun createContacts(numberOfContacts : Int){
        Thread {
            for (i in 0..numberOfContacts) {
                FakeAgent().generateFakeContactsData(this)
            }
            runOnUiThread {
                progress.visibility = View.GONE
                generateButton.isEnabled = true
                evQuantity.text.clear()
            }
            Looper.prepare()
            Handler(Looper.myLooper()!!).post {
                Toast.makeText(this, "Contacts have been added.", Toast.LENGTH_LONG).show()
            }
            Looper.loop()
        }.start()
    }
}