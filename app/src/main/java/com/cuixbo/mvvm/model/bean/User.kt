package com.cuixbo.mvvm.model.bean

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.cuixbo.mvvm.BR

class User(

) : BaseObservable() {

    constructor(name: String?, age: Int?) : this() {
        this.name = name
        this.age = age
    }

    /**
     * notifyPropertyChanged(BR.xx) 仅更新对应的字段
     * notifyChange()               更新所有的Bindable字段
     */

    @get:Bindable
    var name: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)

        }

    @get:Bindable
    var age: Int? = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.age)
        }
    var skill: ArrayList<String>? = null
}



