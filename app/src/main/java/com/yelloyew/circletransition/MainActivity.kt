package com.yelloyew.circletransition

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yelloyew.circletransition.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            binding.custom.start(
                containerView = binding.root,
                animatedView = it,
                doOnEnd = {
                    Toast.makeText(this, "do on end", Toast.LENGTH_SHORT).show()
                }
            )
        }
        binding.reset.setOnClickListener {
            this.recreate()
        }
        binding.onemore.setOnClickListener {
            binding.custom.start(
                containerView = binding.root,
                animatedView = it
            )
        }

        binding.first.setOnClickListener {
            binding.custom.start(
                containerView = binding.root,
                animatedView = it,
                color = Color.parseColor("#bbbbbb")
            )
        }

        binding.second.setOnClickListener {
            binding.custom.start(
                containerView = binding.root,
                animatedView = it,
                color = Color.parseColor("#ff7f50")
            )
        }
        binding.third.setOnClickListener {
            binding.custom.start(
                containerView = binding.root,
                animatedView = it,
                radius = 10f,
                color = Color.parseColor("#007d34")
            )
        }
        
        binding.fourth.setOnClickListener {
            binding.custom.start(
                containerView = binding.root,
                animatedView = it,
                radius = 200f,
                color = Color.parseColor("#8000ff")
            )
        }
    }


}
