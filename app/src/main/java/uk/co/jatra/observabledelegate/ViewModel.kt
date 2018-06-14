package uk.co.jatra.observabledelegate

import android.arch.lifecycle.MutableLiveData
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ViewModel : android.arch.lifecycle.ViewModel() {
    var viewState: MutableLiveData<ViewState> = MutableLiveData()
    private var convertor = Converter()
    private var localData: Int by observable(0) { updateView() }

    fun updateModel() {
        localData = localData++
    }

    private fun updateView() {
        viewState.value = convertor.convert(localData)
    }

//    private inline fun <T> observable(crossinline onChange: (newValue: T?) -> Unit):
//            ReadWriteProperty<Any?, T?> = object : ReadWriteProperty<Any?, T?> {
//        private var value : T? = null
//
//        override fun getValue(thisRef: Any?, property: KProperty<*>) = value
//        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
//            this.value = value
//            onChange(value)
//        }
//    }

    private inline fun <T> observable(initial: T, crossinline onChange: (newValue: T) -> Unit):
            ReadWriteProperty<Any?, T> = object : ReadWriteProperty<Any?, T> {
        private var value : T = initial

        override fun getValue(thisRef: Any?, property: KProperty<*>) = value
        override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
            this.value = value
            onChange(value)
        }
    }

}

data class ViewState(val countText: String)

class Converter {
    //trivial in this case
    fun convert(data: Int?) = ViewState(data?.toString() ?: "")
}