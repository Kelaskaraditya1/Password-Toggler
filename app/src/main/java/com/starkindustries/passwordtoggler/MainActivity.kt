package com.starkindustries.passwordtoggler
import android.content.Context
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.starkindustries.passwordtoggler.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    var passed:Boolean=false
    val RIGHT=2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.password.setOnTouchListener(object: View.OnTouchListener{
            override fun onTouch(v: View?, event: MotionEvent?): Boolean
            {
                if (event != null) {
                    if(event.action==MotionEvent.ACTION_UP) {
                        var selection= binding.password.selectionEnd
                        if(event.rawX>=(binding.password.right-binding.password.compoundDrawables[RIGHT].bounds.width()))
                        {

                            if(passed)
                            {
                                binding.password.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.visibility_off,0)
                                binding.password.transformationMethod=PasswordTransformationMethod()
                                passed=false
                            }
                            else
                            {
                                binding.password.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.visibility_on,0)
                                binding.password.transformationMethod=HideReturnsTransformationMethod()
                                passed=true
                            }
                            binding.password.setSelection(selection)
                            return true
                        }

                    }
                }
                return false
            }
        })
        binding.login.setOnClickListener()
        {
            var view = this.currentFocus
            if(view!=null)
            {
                var manager:InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(view.windowToken,0)
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}