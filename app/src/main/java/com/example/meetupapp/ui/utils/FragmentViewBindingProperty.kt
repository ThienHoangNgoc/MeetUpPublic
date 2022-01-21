package com.example.meetupapp.ui.utils

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentViewBindingProperty<T : Any>(
        fragment: Fragment,
        private val createViewBinding: (View) -> T,
        private val onDestroy: (() -> Unit)? = null,
) : ReadOnlyProperty<Fragment, T> {
    private var _value: T? = null

    init {
        fragment.viewLifecycleOwnerLiveData.observe(fragment) { viewLifecycleOwner ->
            viewLifecycleOwner?.lifecycle?.addObserver(object : DefaultLifecycleObserver {
                override fun onDestroy(owner: LifecycleOwner) {
                    _value?.let {
                        onDestroy?.invoke()
                        _value = null
                    }
                }
            })
        }
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return _value ?: if (thisRef.viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            createViewBinding.invoke(thisRef.requireView()).apply { _value = this }
        } else {
            throw IllegalStateException("should never call view binding when it might not be available")
        }
    }
}

fun <T : Any> Fragment.viewBinding(
        createViewBinding: (View) -> T,
        onDestroy: (() -> Unit)? = null,
) = FragmentViewBindingProperty(this, createViewBinding, onDestroy)