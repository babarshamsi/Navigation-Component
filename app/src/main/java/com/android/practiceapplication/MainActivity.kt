package com.android.practiceapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.android.helpers.RetrofitClient
import com.android.models.Results
import jp.shts.android.storiesprogressview.StoriesProgressView
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.system.measureTimeMillis


class MainActivity : AppCompatActivity() {

    private val storiesProgressView: StoriesProgressView? = null

    private val RESULT_1 = "RESULT_1"

    private val superJob = lazy { SupervisorJob()}
    private val job = lazy { Job() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBarWithNavController(findNavController(R.id.fragmentContainerView))

//        storiesProgressView?.setStoriesCount(4); // <- set stories
//        storiesProgressView?.setStoryDuration(1200L); // <- set a story duration
//        storiesProgressView?.setStoriesListener(this); // <- set listener
//        storiesProgressView?.startStories(); // <- start progress

        getSuperHeroes()
        checkSuspendFunction()


    }

    override fun onSupportNavigateUp(): Boolean {
        val navigationUp = findNavController(R.id.fragmentContainerView)
        return navigationUp.navigateUp() || super.onSupportNavigateUp()
    }

    fun ShowStories(view: View?) {
        // on below line we are opening a new activity using intent.
        val i = Intent(this@MainActivity, StoriesActivity::class.java)
        startActivity(i)
    }

    fun onNext() {
        Toast.makeText(this, "onNext", Toast.LENGTH_SHORT).show()
    }

    fun onPrev() {
        // Call when finished revserse animation.
        Toast.makeText(this, "onPrev", Toast.LENGTH_SHORT).show()
    }

    fun onComplete() {
        Toast.makeText(this, "onComplete", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        // Very important !
        storiesProgressView!!.destroy()
        super.onDestroy()
    }

    private suspend fun hitApi1(): String {
        logThread("getResultFromApi1")
        delay(1000)
        return RESULT_1
    }

    private fun logThread(message: String) {
        println("debug:  ${message}: ${Thread.currentThread().name}")
    }

    private fun getSuperHeroes() {
        val call: Call<List<Results>> = RetrofitClient.getInstance().myApi.getsuperHeroes()
        call.enqueue(object : Callback<List<Results>> {
            override fun onResponse(call: Call<List<Results>?>, response:
            Response<List<Results>?>) {
                val myheroList: List<Results> = response.body() ?: arrayListOf()
                val oneHeroes = arrayOfNulls<String>(myheroList?.size ?: 0)
                for (i in myheroList.indices) {
                    oneHeroes[i] = myheroList[i].name
                }
//                setAdapter(
//                    ArrayAdapter(
//                        applicationContext,
//                        android.R.layout.simple_list_item_1,
//                        oneHeroes
//                    )
//                )
            }

            override fun onFailure(call: Call<List<Results>>, t: Throwable) {
                Toast.makeText(applicationContext, "An error has occured", Toast.LENGTH_LONG).show()

            }
        })
    }

    private fun checkSuspendFunction() {

        GlobalScope.launch {
            val time = measureTimeMillis {
                val one = sampleOne()
                val two = sampleTwo()
                println("The answer is ${one + two}")
            }
            println("Completed in $time ms")
        }
        println("EOF")
    }

    private suspend fun sampleOne(): Int {
        println( "sampleOne"+System.currentTimeMillis())
        delay(1000L)
        return 0
    }

    private suspend fun sampleTwo(): Int {
        println( "sampleTwo"+System.currentTimeMillis())
        delay(1000L)
        return 0
    }

}