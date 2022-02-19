package ir.esfandune.calculatorlibe

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.simplemobiletools.calculator.helpers.*
import me.grantland.widget.AutofitHelper
import java.lang.NumberFormatException
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.ParseException
import java.util.*
import kotlin.math.roundToInt


abstract class CalculatorDialog(private val context: Activity) : Calculator {
    private var storedTextColor = 0
    private var vibrateOnButtonPress = true
    var calc: CalculatorImpl
    private var alertDialog: AlertDialog.Builder
    private var v: View
    private var formula: TextView
    private var result: TextView
    private var btn_done: View

    init {
        alertDialog = AlertDialog.Builder(context)
        v = context.getLayoutInflater().inflate(R.layout.alrt_calculator, null)
        alertDialog.setView(v)
        alertDialog.create()
        formula = v.findViewById(R.id.formula)
        result = v.findViewById(R.id.result)
        btn_done = v.findViewById(R.id.btn_done)
        calc = CalculatorImpl(this, context)

        v.findViewById<View>(R.id.btn_plus)
            .setOnClickListener { calc.handleOperation(PLUS); checkHaptic(it) }
        v.findViewById<View>(R.id.btn_minus)
            .setOnClickListener { calc.handleOperation(MINUS); checkHaptic(it) }
        v.findViewById<View>(R.id.btn_multiply)
            .setOnClickListener { calc.handleOperation(MULTIPLY); checkHaptic(it) }
        v.findViewById<View>(R.id.btn_divide)
            .setOnClickListener { calc.handleOperation(DIVIDE); checkHaptic(it) }
        v.findViewById<View>(R.id.btn_percent)
            .setOnClickListener { calc.handleOperation(PERCENT); checkHaptic(it) }
        v.findViewById<View>(R.id.btn_power)
            .setOnClickListener { calc.handleOperation(POWER); checkHaptic(it) }
        v.findViewById<View>(R.id.btn_root)
            .setOnClickListener { calc.handleOperation(ROOT); checkHaptic(it) }
////
        v.findViewById<View>(R.id.btn_minus).setOnLongClickListener { calc.turnToNegative() }
        v.findViewById<View>(R.id.btn_clear)
            .setOnClickListener { calc.handleClear(); checkHaptic(it) }
        v.findViewById<View>(R.id.btn_clear).setOnLongClickListener { calc.handleReset(); true }
        getButtonIds().forEach {
            it.setOnClickListener { calc.numpadClicked(it.id); checkHaptic(it) }
        }
        v.findViewById<View>(R.id.btn_equals)
            .setOnClickListener { calc.handleEquals(); checkHaptic(it) }
        formula.setOnLongClickListener { chkCopyToClipboard(false) }
        result.setOnLongClickListener { chkCopyToClipboard(true) }
        AutofitHelper.create(result)
        AutofitHelper.create(formula)
    }


    private fun checkHaptic(view: View) {
        if (vibrateOnButtonPress) {
            view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
        }
    }

    private fun getButtonIds() =
        arrayOf(
            v.findViewById<View>(R.id.btn_decimal),
            v.findViewById(R.id.btn_0),
            v.findViewById(R.id.btn_1),
            v.findViewById(R.id.btn_2),
            v.findViewById(R.id.btn_3),
            v.findViewById(R.id.btn_4),
            v.findViewById(R.id.btn_5),
            v.findViewById(R.id.btn_6),
            v.findViewById(R.id.btn_7),
            v.findViewById(R.id.btn_8),
            v.findViewById(R.id.btn_9)
        )

    private fun chkCopyToClipboard(copyResult: Boolean): Boolean {
        var value = formula.text.toString()
        if (copyResult) {
            value = result.text.toString()
        }

        return if (value.isEmpty()) {
            false
        } else {
            copyToClipboard(value)
            true
        }
    }

    fun copyToClipboard(text: String) {
        val clip = ClipData.newPlainText("کپی نتیجه", text)
        (context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).setPrimaryClip(
            clip
        )
        Toast.makeText(context, "کپی شد!", Toast.LENGTH_SHORT).show()
    }

    override fun showNewResult(value: String, context: Context) {
        result.text = value
    }

    override fun showNewFormula(value: String, context: Context) {
        formula.text = value
    }

    override fun  onBtnsNumsClick(onEqual: Boolean){
        btn_done.isEnabled = onEqual
    }

    abstract fun onResult(result: String?)

    companion object {
        fun easyCalculate(c: Activity, et_price: TextView, round: Boolean) {
            easyCalculate(c, et_price, ",", false, round)
        }

        fun easyCalculate(
            c: Activity,
            et_price: TextView,
            spliter: String,
            absRslt: Boolean,
            round: Boolean
        ) {
            var value = 0.0
            try {
                value = et_price.text.toString().trim { it <= ' ' }.replace(spliter.toRegex(), "")
                    .toDouble()
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }
            object : CalculatorDialog(c) {
                override fun onResult(result: String?) {
                    try {
                        val df =
                            DecimalFormat("#.###", DecimalFormatSymbols.getInstance(Locale.ENGLISH))
                        var number = df.parse(result).toDouble()
                        number =
                            if (round) (if (absRslt) Math.abs(Math.round(number)) else Math.round(
                                number
                            )).toDouble() else if (absRslt) Math.abs(
                                number
                            ) else number
                        et_price.text = df.format(number)
                    } catch (e: ParseException) {
                        e.printStackTrace()
                    }
                }
            }.setValue(value).showDIalog()
        }
    }

    open fun setValue(input: Double): CalculatorDialog {
//        showNewResult(input.toString().replace("[^0-9.]".toRegex(),""), context)
        calc.addDefault(input)
        return this
    }

    open fun showDIalog(): CalculatorDialog {
        val ad = alertDialog.show()
/*        val displayMetrics = DisplayMetrics()
        context.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels
        val lp = v.layoutParams;
        if (width < height) {
            //istade
            ad.window?.setLayout(width * 90 / 100, height * 80 / 100)
            lp.height = height * 80 / 100;
        } else {
            //khabide
            ad.window?.setLayout(width * 60 / 100, height * 90 / 100)
            lp.height = height * 90 / 100;
        }
        v.layoutParams = lp
        v.invalidate()*/
        ad.window?.decorView?.setBackgroundResource(R.drawable.bg_corner_for_dialogs)
        v.findViewById<View>(R.id.btn_done).setOnClickListener {
            val finalRslt = result.text.toString().replace("[^0-9.]".toRegex(), "");
            onResult(finalRslt);
            ad.dismiss();
            Log.d("Result:",finalRslt)
        }
        return this
    }
}
