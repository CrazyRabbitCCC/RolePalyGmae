package cn.czrbc.rpg.mod

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import cn.czrbc.rpg.R

/**
 * @author  Gavin Liu on  2018/08/10.
 */
class Effect(val context:Context) {
    var showing = false
    var point = PointF(0f,0f)
    var type  = 0
    var msg ="你好啊，勇士"
    var affected =false
    var bitmap =BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher)
    var height = bitmap.height
    var width = bitmap.width

    fun onDraw(canvas: Canvas,paint: Paint,textPaint: Paint){
        val screen = Map.getScreenPoint(point)
        canvas.drawBitmap(bitmap,screen.x -width/2,screen.y -height/2,paint)
        if (affected)
            canvas.drawText(msg,screen.x -msg.length*40,screen.y -height/2 -40,textPaint )
    }

    fun checkShow():Boolean{
        showing = when{
            point.x !in Map.mapPoint.x-width/2..Map.mapPoint.x+Map.screenWidth+width/2 ->false
            point.y !in Map.mapPoint.y-height/2..Map.mapPoint.y+Map.screenWidth+height/2 ->false
            else -> true
        }
        return showing
    }

    fun affect(role: Role){
        if (!showing||Math.abs(point.x - role.point.x)>100||Math.abs(point.x - role.point.x)>100){
            affected =false
            return
        }
        affected = true
        when (type){
            1 -> role.stopMove()
        }
    }


}