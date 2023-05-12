package uz.alijonov.foodexpress.screen.main.profile

import android.app.Dialog
import android.os.CountDownTimer
import android.view.View
import androidx.core.app.DialogCompat
import androidx.lifecycle.ViewModelProvider
import uz.alijonov.foodexpress.base.BaseFragment
import uz.alijonov.foodexpress.base.showError
import uz.alijonov.foodexpress.base.showSuccess
import uz.alijonov.foodexpress.base.startClearTopActivity
import uz.alijonov.foodexpress.databinding.FragmentProfileBinding
import uz.alijonov.foodexpress.databinding.SuccessPasswordLayoutBinding
import uz.alijonov.foodexpress.model.request.ConfirmSmsRequest
import uz.alijonov.foodexpress.model.request.UpdatePasswordRequest
import uz.alijonov.foodexpress.screen.main.MainActivity
import uz.alijonov.foodexpress.screen.main.MainViewModel
import uz.alijonov.foodexpress.utils.Prefs

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    lateinit var viewModel: MainViewModel
    override fun getViewBinding(): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(layoutInflater)
    }

    override fun setupViews() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.error.observe(this) {
            activity?.showError(it)
        }

        viewModel.progress.observe(this) {
            setProgress(it)
        }

        viewModel.sendSmsData.observe(this) {
            startTime()
        }

        viewModel.userData.observe(this) {
            Prefs.setToken(it?.token ?: "")
            Prefs.setUser(it!!)
            showSuccessDialog()
        }

        binding.edPhone.isFocusable = false
        binding.edPhone.setText(Prefs.getUser().phone)

        viewModel.sendSms(ConfirmSmsRequest(Prefs.getUser().phone))

        binding.btnReply.setOnClickListener {
            binding.btnReply.isEnabled = true
            startTime()
        }

        binding.btnConfirm.setOnClickListener {
            val resPwd = UpdatePasswordRequest(
                binding.edOldPwd.text.toString(),
                binding.edNewPwd.toString(),
                binding.smsConfirmator.enteredCode
            )

            viewModel.resetPassword(resPwd)
        }
    }

    override fun loadData() {

    }

    override fun setData() {

    }

    companion object {

        @JvmStatic
        fun newInstance() = ProfileFragment()
    }

    fun startTime() {
        val timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.tvRemainingTime.text = if (millisUntilFinished / 1000 < 10) {
                    "00:0${millisUntilFinished / 1000}"
                } else {
                    "00:${millisUntilFinished / 1000}"
                }
            }

            override fun onFinish() {
                binding.tvRemainingTime.visibility = View.GONE
                binding.btnReply.isEnabled = true
            }
        }
        timer.start()
    }

    private fun showSuccessDialog(){
        val dialog = Dialog(activity!!)
        val view = SuccessPasswordLayoutBinding.inflate(layoutInflater)
        dialog.setCancelable(false)
        dialog.setContentView(view.root)

        view.btnOk.setOnClickListener {
            context?.startClearTopActivity<MainActivity>()
            dialog.dismiss()
        }
        view.btnExit.setOnClickListener {
            context?.startClearTopActivity<MainActivity>()
            dialog.dismiss()
        }

        dialog.show()
    }
}