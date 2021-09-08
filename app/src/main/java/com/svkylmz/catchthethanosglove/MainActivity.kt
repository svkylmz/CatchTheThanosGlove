package com.svkylmz.catchthethanosglove

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.svkylmz.catchthethanosglove.R
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var score = 0
    var imageArray = ArrayList<ImageView>()
    // what we need for random image view
    var handler = Handler(Looper.getMainLooper())
    var runnable = Runnable {  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //image array
        imageArray.add(imageView1)
        imageArray.add(imageView2)
        imageArray.add(imageView3)
        imageArray.add(imageView4)
        imageArray.add(imageView5)
        imageArray.add(imageView6)
        imageArray.add(imageView7)
        imageArray.add(imageView8)
        imageArray.add(imageView9)

        startButton.setOnClickListener {
            //TODO("make images isNotClickable before startGame()")
            startGame()
        }
    }

    fun startGame() {
        //CountDownTimer
        object : CountDownTimer(15500, 1000) {
            override fun onTick(p0: Long) {
                timeText.text = "Time : ${p0/1000}"
            }

            override fun onFinish() {
                timeText.text = "Time : 0"
                //stop random image visibility after game finished
                handler.removeCallbacks(runnable)
                for (image in imageArray) {
                    image.visibility = View.INVISIBLE
                }
                //Alert
                var alert = AlertDialog.Builder(this@MainActivity)
                alert.setTitle("Game Over")
                alert.setTitle("Restart The Game?")
                alert.setPositiveButton("Yes") {dialog, which ->
                    //Restart
                    val intent = intent
                    finish()    // first we need to destroy the app
                    startActivity(intent)
                }
                alert.setNegativeButton("No") {dialog, which ->
                    Toast.makeText(this@MainActivity, "Game Over", Toast.LENGTH_LONG).show()
                    finish()
                    startActivity(intent)
                    startButton.visibility = View.VISIBLE
                }
                alert.show()
            }
        }.start()

        hideImages()

        startButton.visibility = View.INVISIBLE
    }

    fun hideImages() {
        runnable = object : Runnable {
            override fun run() {
                for (image in imageArray) {
                    image.visibility = View.INVISIBLE
                }
                val random = Random()
                val randomIndex = random.nextInt(9)
                imageArray[randomIndex].visibility = View.VISIBLE
                handler.postDelayed(runnable, 500)
            }
        }
        handler.post(runnable)
    }

    fun increaseScore (v: View) {
        score ++
        scoreText.text = "Score : $score"
    }
}