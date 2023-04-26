package uz.alijonov.foodexpress.view.custom

import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import uz.alijonov.foodexpress.databinding.JwalertDialogBinding

enum class JWAlertType {
    INFO,
    SUCCESS,
    WARNING,
    ERROR
}

interface JWAlertDialogListener {
    fun onClickDismiss()
}

class JWAlertDialog(
    var type: JWAlertType,
    var message: String,
    var listener: JWAlertDialogListener? = null
) : DialogFragment() {

    lateinit var binding: JwalertDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = JwalertDialogBinding.inflate(layoutInflater)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvMessage.text = message
        when (type) {
            JWAlertType.INFO -> {
                binding.animationView.setAnimation("info.json")
            }

            JWAlertType.SUCCESS -> {
                binding.animationView.setAnimation("success_.json")
            }

            JWAlertType.WARNING -> {
                binding.animationView.setAnimation("warning.json")
            }

            JWAlertType.ERROR -> {
                binding.animationView.setAnimation("error.json")
            }

        }

        binding.cardViewOk.setOnClickListener {
            dismiss()
        }
        binding.animationView.playAnimation()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        listener?.onClickDismiss()
    }
}