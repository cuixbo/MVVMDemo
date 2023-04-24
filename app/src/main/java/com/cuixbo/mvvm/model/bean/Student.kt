package com.cuixbo.mvvm.model.bean

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import com.cuixbo.mvvm.BR

class Student() : BaseObservable() {
    constructor(name: String?, gender: Int?) : this() {
        this.name.set(name)
        this.gender.set(gender)
    }

    var name: ObservableField<String> = ObservableField("")
    var gender: ObservableField<Int> = ObservableField(0)
}



