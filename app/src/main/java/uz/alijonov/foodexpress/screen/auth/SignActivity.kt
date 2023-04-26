package uz.alijonov.foodexpress.screen.auth

import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.redmadrobot.inputmask.MaskedTextChangedListener
import uz.alijonov.foodexpress.R
import uz.alijonov.foodexpress.base.BaseActivity
import uz.alijonov.foodexpress.databinding.ActivitySignBinding
import uz.alijonov.foodexpress.model.request.LoginRequest
import uz.alijonov.foodexpress.model.request.RegisterRequest
import uz.alijonov.foodexpress.screen.main.MainActivity
import uz.alijonov.foodexpress.utils.Prefs
import uz.bdm.base.base.showError
import uz.bdm.base.base.showWarning
import uz.bdm.base.base.startClearTopActivity

enum class SignState {
    LOGIN,
    REGISTRATION
}

class SignActivity : BaseActivity<ActivitySignBinding>() {

    var state: SignState = SignState.LOGIN
    lateinit var viewModel: SignViewModel
    override fun getViewBinding(): ActivitySignBinding {
        return ActivitySignBinding.inflate(layoutInflater)
    }

    override fun initViews() {

        viewModel = ViewModelProvider(this).get(SignViewModel::class.java)

        viewModel.error.observe(this) {
            showError(it)
        }

        viewModel.progress.observe(this) {
            setProgress(it)
        }

        viewModel.authData.observe(this) {
            Prefs.setToken(it.token)
            Prefs.setUser(it)
            startClearTopActivity<MainActivity>()
            finish()
        }

        val phoneListener = MaskedTextChangedListener(
            "+998 ([00]) [000] [00] [00]",
            true,
            binding.edPhone,
            null,
            null
        )

        binding.edPhone.addTextChangedListener(phoneListener)
        binding.edPhone.onFocusChangeListener = phoneListener

        binding.tvRegistration.setOnClickListener {
            state = SignState.REGISTRATION
            initData()
        }

        binding.btnLogin.setOnClickListener {
            when (state) {
                SignState.LOGIN -> {
                    if (binding.edPhone.text.isEmpty() || binding.edPassword.text.isEmpty()) {
                        showWarning("Iltimos barcha maydonlarni to'ldiring!")
                    } else {
                        val phone =
                            binding.edPhone.text.toString().replace("(", "").replace(")", "")
                                .replace(" ", "").replace("+", "")
                        val password = binding.edPassword.text.toString()
                        viewModel.login(LoginRequest(phone, password))
                    }
                }

                SignState.REGISTRATION -> {
                    if (binding.edPhone.text.isEmpty() || binding.edPassword.text.isEmpty() || binding.edFullname.text.isEmpty() || binding.edRePassword.text.isEmpty()) {
                        showWarning("Iltimos barcha maydonlarni to'ldiring!")
                    } else {
                        val phone =
                            binding.edPhone.text.toString().replace("(", "").replace(")", "")
                                .replace(" ", "").replace("+", "")
                        val password = binding.edPassword.text.toString()
                        val rePassword = binding.edRePassword.text.toString()
                        if (password != rePassword) {
                            showError("Parollar bir-biriga mos emas!")
                        } else {
                            viewModel.register(
                                RegisterRequest(
                                    binding.edFullname.text.toString(),
                                    phone,
                                    password
                                )
                            )
                        }
                    }
                }
            }
        }

    }

    override fun loadData() {

    }

    override fun initData() {
        when (state) {
            SignState.LOGIN -> {
                binding.edFullname.visibility = View.GONE
                binding.tilRePassword.visibility = View.GONE
                binding.tvStatus.text = resources.getString(R.string.sign_in)
                binding.tvRegistration.visibility = View.VISIBLE
                binding.tvRepassword.visibility = View.GONE
                binding.tvFullname.visibility = View.GONE
            }

            SignState.REGISTRATION -> {
                binding.edFullname.visibility = View.VISIBLE
                binding.tilRePassword.visibility = View.VISIBLE
                binding.tvStatus.text = resources.getString(R.string.sign_up)
                binding.tvRegistration.visibility = View.GONE
                binding.tvRepassword.visibility = View.VISIBLE
                binding.tvFullname.visibility = View.VISIBLE
            }
        }
    }

    override fun updateData() {

    }

}