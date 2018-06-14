package uk.co.jatra.observabledelegate

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val presenter = ViewModelProviders.of(this).get(ViewModel::class.java)

        button.setOnClickListener {
            presenter.updateModel()
        }

        presenter.viewState.observe(this, Observer<ViewState> { t ->
            t?.let {
                textview.text = t.countText
            }
        }
        )
    }

}