package id.wendei.store.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<out T: ViewBinding, out V: BaseViewModel>(
    private val bindingInflater: (LayoutInflater) -> T
): AppCompatActivity() {

    protected val viewBinding by lazy { bindingInflater.invoke(layoutInflater) }

    abstract val viewModel: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
    }

}