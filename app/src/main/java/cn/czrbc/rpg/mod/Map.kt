package cn.czrbc.rpg.mod

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF

/**
 * @author  a9573
 * @date 2018/08/09.
 */
class Map(private var bitmap: Bitmap) {

    companion object {
        var mapWidth = 0f
        var mapHeight = 0f
        var roleWidth = 0f
        var roleHeight = 0f
        var screenWidth =0f
        var screenHeight =0f

        var mapPoint = PointF(0f,0f)
        var rolePoint = PointF(0f,0f)

        fun getMapPoint(screen:PointF):PointF{
            return PointF(screen.x+ mapPoint.x,screen.y+ mapPoint.y)
        }

        fun getScreenPoint(map:PointF):PointF{
            return PointF(map.x- mapPoint.x,map.y - mapPoint.y)
        }

        fun loadPoint(x:Float,y:Float){
            loadPoint(PointF(x, y))
        }

        fun loadPoint(roleP:PointF){
            rolePoint = roleP
            when {
                rolePoint.x< screenWidth /2f -> mapPoint.x=0f
                rolePoint.x> (mapWidth - screenWidth /2f) -> mapPoint.x = mapWidth - screenWidth
                else -> mapPoint.x = rolePoint.x- screenWidth /2f
            }
            when {
                rolePoint.y< screenHeight /2f -> mapPoint.y=0f
                rolePoint.y> (mapHeight - screenHeight /2f) -> mapPoint.y = mapHeight - screenHeight
                else -> mapPoint.y = rolePoint.y- screenHeight /2f
            }
        }
    }
    init {
        mapWidth =bitmap.width.toFloat()
        mapHeight = bitmap.height.toFloat()

    }

    fun onDraw(canvas: Canvas,paint: Paint){
        canvas.drawBitmap(bitmap, -mapPoint.x, -mapPoint.y,paint)
    }

}
