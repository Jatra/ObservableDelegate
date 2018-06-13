package uk.co.jatra.observabledelegate

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class MainActivity : AppCompatActivity() {

    var clickCount = 0
    private var count: Int? by Delegates.observable { _ -> updateText(count.toString())}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            count = ++clickCount
        }
    }

    fun updateText(text: String) {
        textview.text = text
    }
}

public inline fun <T> Delegates.observable(crossinline onChange: (newValue: T?) -> Unit):
        ReadWriteProperty<Any?, T?> = object : ReadWriteProperty<Any?, T?> {
    private var value : T? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>) = value
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        this.value = value
        onChange(value)
    }

}


//public inline fun <T> Delegates.observabl(crossinline onChange: (property: KProperty<*>, newValue: T?) -> Unit):
//        ReadWriteProperty<Any?, T?> = object : ObservableNullableProperty<T>() {
//    override fun afterChange(property: KProperty<*>, newValue: T?) = onChange(property, newValue)
//}
//
//public abstract class ObservableNullableProperty<T> : ReadWriteProperty<Any?, T?> {
//    private var value : T? = null
//
//    /**
//     * The callback which is called after the change of the property is made. The value of the property
//     * has already been changed when this callback is invoked.
//     */
//    protected open fun afterChange (property: KProperty<*>, newValue: T?): Unit {}
//
//    public override fun getValue(thisRef: Any?, property: KProperty<*>): T? {
//        return value
//    }
//
//    public override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
//        this.value = value
//        afterChange(property, value)
//    }
//}
