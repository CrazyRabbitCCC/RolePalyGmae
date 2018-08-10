package cn.czrbc.rpg

import android.content.Context
import android.graphics.*
import cn.czrbc.rpg.mod.BaseGameView
import cn.czrbc.rpg.mod.Effect
import cn.czrbc.rpg.mod.Map
import cn.czrbc.rpg.mod.Role
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author  Gavin Liu on  2018/08/09.
 */
class GameView( context: Context) : BaseGameView(context) {
    override fun initMap(): Map {
        val bitmap = BitmapFactory.decodeResource(resources,R.drawable.background_test)
        return Map(bitmap)
    }

    override fun initRole(): Role {
        val role = Role(context)
        role.point.x =role.width
        role.point.y = role.height
        return role
    }

    override fun initEffects(): ArrayList<Effect> {
        val effects:ArrayList<Effect> = ArrayList()
        val effect = Effect(context)
        effect.point = PointF(200f,200f)
        val effect1 = Effect(context)
        effect1.point = PointF(800f,400f)
        val effect2 = Effect(context)
        effect2.point = PointF(2000f,800f)
        val effect3 = Effect(context)
        effect3.point = PointF(2500f,400f)
        effects.add(effect)
        effects.add(effect1)
        effects.add(effect2)
        effects.add(effect3)
        return  effects
    }


    private var format = SimpleDateFormat("yyyy/MM/dd hh:mm:ss", Locale.CHINA)


    override fun beforeDraw() {
        Map.loadPoint(role.point)
        for (effect in effects){
            effect.checkShow()
            effect.affect(role)
        }
    }


    override fun drawBackGround(canvas: Canvas, paint: Paint) {
        map.onDraw(canvas,paint)
    }

    override fun drawEffect(canvas: Canvas, paint: Paint) {
        if (role.preMove){
            val roleS = Map.getScreenPoint(role.point)
            val desS = Map.getScreenPoint(destination)
            canvas.drawLine(roleS.x,roleS.y,desS.x,desS.y,textPaint)
        }
        for (effect in effects){
            if (effect.showing)
            effect.onDraw(canvas,paint,textPaint)
        }
    }

    override fun drawRole(canvas: Canvas, paint: Paint) {
        role.onDraw(canvas, paint)
        canvas.drawText(format.format(Date()),10f,40f,textPaint)
    }

    override fun afterDraw() {

    }

    private var moveDistance =PointF(0f,0f)
    override fun roleMove() {
        role.point.x+=moveDistance.x/role.maxMoveTime
        role.point.y+=moveDistance.y/role.maxMoveTime
    }

    override fun getMoveTimeAll():Int {
        moveDistance.x = (destination.x- role.point.x)
        moveDistance.y = (destination.y- role.point.y)
        return Math.sqrt(( moveDistance.x *  moveDistance.x + moveDistance.y * moveDistance.y).toDouble()).toInt() / (10* role.speed)
    }
    
    


   

}