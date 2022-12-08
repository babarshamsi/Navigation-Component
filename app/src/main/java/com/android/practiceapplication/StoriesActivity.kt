package com.android.practiceapplication

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import jp.shts.android.storiesprogressview.StoriesProgressView
import kotlinx.android.synthetic.main.activity_stories.*


class StoriesActivity: AppCompatActivity() , StoriesProgressView.StoriesListener {


    // on below line we are creating a int array
    // in which we are storing all our image ids.
    private val resources = intArrayOf(
        com.android.practiceapplication.R.drawable.ic_launcher_foreground,
        com.android.practiceapplication.R.drawable.ic_launcher_background,
        com.android.practiceapplication.R.drawable.ic_launcher_background,
        com.android.practiceapplication.R.drawable.ic_launcher_background

    )

    // on below line we are creating variable for
    // our press time and time limit to display a story.
    var pressTime = 0L
    var limit = 500L

    // on below line we are creating variables for
    // our progress bar view and image view .
//    private var storiesProgressView: StoriesProgressView? = null
    private var image: ImageView? = null

    // on below line we are creating a counter
    // for keeping count of our stories.
    private var counter = 0

    // on below line we are creating a new method for adding touch listener
    private val onTouchListener: View.OnTouchListener = object : View.OnTouchListener {
        override fun onTouch(v: View?, event: MotionEvent): Boolean {
            // inside on touch method we are
            // getting action on below line.
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {

                    // on action down when we press our screen
                    // the story will pause for specific time.
                    pressTime = System.currentTimeMillis()

                    // on below line we are pausing our indicator.
                    storiesProgressView!!.pause()
                    return false
                }
                MotionEvent.ACTION_UP -> {

                    // in action up case when user do not touches
                    // screen this method will skip to next image.
                    val now = System.currentTimeMillis()

                    // on below line we are resuming our progress bar for status.
                    storiesProgressView!!.resume()

                    // on below line we are returning if the limit < now - presstime
                    return limit < now - pressTime
                }
            }
            return false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_stories)

        storiesProgressView?.setStoriesCount(4)
        storiesProgressView?.setStoryDuration(3000L)
        storiesProgressView?.setStoriesListener(this)
        storiesProgressView?.startStories(counter)
        image?.setImageResource(resources[counter])

        reverse?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                storiesProgressView?.reverse()
            }
        })
        reverse.setOnTouchListener(onTouchListener)
        skip.setOnClickListener(object : View.OnClickListener {
           override fun onClick(v: View?) {
                storiesProgressView?.skip()
            }
        })
        skip.setOnTouchListener(onTouchListener)
    }

    override fun onNext() {
        image?.setImageResource(resources[++counter])
    }

    override fun onPrev() {
        if (counter - 1 < 0) return
        image?.setImageResource(resources[--counter])
    }

    override fun onComplete() {
        val i = Intent(this@StoriesActivity, MainActivity::class.java)
        startActivity(i)
        finish()
    }

    override fun onDestroy() {
        storiesProgressView!!.destroy()
        super.onDestroy()
    }
}