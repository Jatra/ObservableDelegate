package uk.co.jatra.observabledelegate

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class MainActivity : AppCompatActivity() {

    var clickCount = 0
    private var count: Int? by observable { updateText(it.toString()) }
    private var count2: Int? by observable { updateText() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            count = ++clickCount
        }
        button2.setOnClickListener {
            count2 = ++clickCount
        }
    }

    fun updateText(text: String) {
        textview.text = text
    }

    fun updateText() {
        textview.text = count2.toString()
    }
}

private inline fun <T> observable(crossinline onChange: (newValue: T?) -> Unit):
        ReadWriteProperty<Any?, T?> = object : ReadWriteProperty<Any?, T?> {
    private var value : T? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>) = value
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        this.value = value
        onChange(value)
    }

}