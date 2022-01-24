package com.example.numcompos.presentation

import android.widget.ImageView
import android.widget.TextView
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