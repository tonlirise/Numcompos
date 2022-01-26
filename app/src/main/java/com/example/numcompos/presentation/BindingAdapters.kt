package com.example.numcompos.presentation

import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.numcompos.R
import com.example.numcompos.domain.entity.GameResult

@BindingAdapter("emojiResult")
fun emojiResult(imageView: ImageView, winner: Boolean) {
    val img = if (winner) {
        R.drawable.ic_smile
    } else {
        R.drawable.ic_sad
    }
    imageView.setImageResource(img)
}

@BindingAdapter("requiredAnswers")
fun requiredAnswers(textView: TextView, value: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_score),
        value
    )
}

@BindingAdapter("scoreAnswers")
fun scoreAnswers(textView: TextView, value: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_percentage),
        value
    )
}

@BindingAdapter("requiredPercentage")
fun requiredPercentage(textView: TextView, value: Int) {
    textView.text = String.format(
        textView.context.getString(R.string.required_percentage),
        value
    )
}

@BindingAdapter("scorePercentage")
fun scorePercentage(textView: TextView, gameResult: GameResult) {
    textView.text = String.format(
        textView.context.getString(R.string.score_percentage),
        getPercentOfRightAnswers(gameResult)
    )
}

private fun getPercentOfRightAnswers(gameResult: GameResult): Int {
    if (gameResult.countOfQuestions == 0) {
        return 0
    } else {
        return ((gameResult.countOfRightAnswer / gameResult.countOfQuestions.toDouble()) * 100).toInt()
    }
}

@BindingAdapter("textColor")
fun textColor(textView: TextView, state : Boolean){
    val color = ContextCompat.getColor(textView.context, getColorByState(state))
    textView.setTextColor(color)
}

@BindingAdapter("progressTintList")
fun progressTintList(progressBar: ProgressBar, state: Boolean){
    val color = ContextCompat.getColor(progressBar.context, getColorByState(state))
    progressBar.progressTintList = ColorStateList.valueOf(color)
}

private fun getColorByState(state : Boolean) : Int{
    val idColor = when(state){
        true -> android.R.color.holo_green_light
        false -> android.R.color.holo_red_light
    }
    return idColor
}

@BindingAdapter("numAsText")
fun numAsText(textView: TextView, value: Int){
    textView.text = value.toString()
}

interface OnOptionClickListener{
    fun OnOptionClick(option : Int)
}

@BindingAdapter("onOptionClick")
fun onOptionClick(textView: TextView, onOptionClickListener: OnOptionClickListener){
    textView.setOnClickListener{
        onOptionClickListener.OnOptionClick(textView.text.toString().toInt())
    }
}
