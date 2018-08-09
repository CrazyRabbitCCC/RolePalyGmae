package cn.czrbc.rpg.mod

import android.content.Context
import android.graphics.*
import cn.czrbc.rpg.R

/**
 * @author Gavin Liu on  2018/08/09.
 */
class Role(private val context: Context) {
    private val bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher)
    var height =bitmap.height.toFloat()
    var width =bitmap.width.toFloat()

    var point = PointF(0f,0f)
    var speed :Int= 5

    fun onDraw(canvas: Canvas,paint: Paint){
        val screenPoint = Map.getScreenPoint(point)
        canvas.drawBitmap(bitmap,screenPoint.x-width/2,screenPoint.y-height/2,paint)
    }
}
