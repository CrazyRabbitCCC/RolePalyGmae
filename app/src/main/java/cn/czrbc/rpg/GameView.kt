package cn.czrbc.rpg

import android.content.Context
import android.graphics.*
import cn.czrbc.rpg.mod.BaseGameView
import cn.czrbc.rpg.mod.Map
import cn.czrbc.rpg.mod.Role
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author  Gavin Liu on  2018/08/09.
 */
class GameView(context: Context) : BaseGameView(context) {
    
    private val map:Map
    private val role:Role
    private var fromat = SimpleDateFormat("yyyy/MM/dd hh:mm:ss", Locale.CHINA)

    init {
        val bitmap = BitmapFactory.decodeResource(resources,R.drawable.background_test)
        map = Map(bitmap)
        role = Role(context)
        role.point.x =role.width
        role.point.y = role.height
    }


    override fun beforeDraw() {
        Map.loadPoint(role.point)
    }


    override fun drawBackGround(canvas: Canvas, paint: Paint) {
        map.onDraw(canvas,paint)
    }

    override fun drawEffect(canvas: Canvas, paint: Paint) {
        if (preMove){
            val roleS = Map.getScreenPoint(role.point)
            val desS = Map.getScreenPoint(destination)
            canvas.drawLine(roleS.x,roleS.y,desS.x,desS.y,textPaint)
        }
    }

    override fun drawRole(canvas: Canvas, paint: Paint) {
        role.onDraw(canvas, paint)
        canvas.drawText(fromat.format(Date()),10f,40f,textPaint)
    }

    override fun afterDraw() {

    }

    var moveDistance =PointF(0f,0f)
    override fun roleMove() {
        role.point.x+=moveDistance.x/maxMoveTime
        role.point.y+=moveDistance.y/maxMoveTime
    }

    override fun getMoveTimeAll(): Int {
        moveDistance.x = (destination.x- role.point.x)
        moveDistance.y = (destination.y- role.point.y)
        return Math.sqrt(( moveDistance.x *  moveDistance.x + moveDistance.y * moveDistance.y).toDouble()).toInt() / (10* role.speed)
    }
    
    


   

}