package cn.czrbc.rpg.mod

import android.graphics.Canvas
import android.graphics.PointF

/**
 * @author a9573
 * @date 2018/08/09.
 */
abstract class BaseGameMod{
    var point = PointF(0f,0f)

    abstract fun onDraw(canvas: Canvas)
}
