package com.cuixbo.mvvm.ui.adapter

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/**
 * 使用@BindingMethods（指定自定义方法名称）如果你想创建一个XML属性并且和View中的函数关联(即会自动使用属性值作为参数调用该函数).
 *    @BindingAdapter（提供自定义的逻辑）创建一个XML属性和函数, 然后在属性中进行设置数据操作会进入该函数.
 */
object ViewAdapter {
    /**
     * BindAdapter 用于给xml中View的自定义属性添加适配处理
     *   app:isGone="@{user.age > 5}"
     *  在object ViewAdapter 中使用需要加 @JvmStatic，否则运行时报错
     */
    @JvmStatic
    @BindingAdapter("isGone")
    fun bindIsGone(view: View, gone: Boolean) {
        view.visibility = if (gone) View.GONE else View.VISIBLE
    }

    @JvmStatic
    @BindingAdapter("isVisible")
    fun bindIsVisible(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }

    /**
     * 您还可以使用接收多个属性的适配器:(布局中必须得要同时使用三个属性)
     * @BindingAdapter("imageUrl","errorD","placeholderD")
     * 如果想要使的多个属性变成可选，那么只需要在添加一个requireAll变量即可。
     * @BindingAdapter(value=["imageUrl","errorD","placeholderD"],requireAll = false)
     */
}

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    if (url != null) {
        Glide.with(imageView).load(url).into(imageView)
    }
}
