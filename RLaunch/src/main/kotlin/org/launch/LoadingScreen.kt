package org.launch

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import org.launch.events.LoadedObject
import org.launch.settings.Configuration
import java.awt.*
import java.awt.geom.Arc2D
import javax.inject.Inject
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.Timer
import kotlin.math.abs


class LoadingScreen {
    private lateinit var eventBus: EventBus
    private lateinit var configuration: Configuration

    private val frame: JFrame = object : JFrame() {
        override fun paintComponents(g: Graphics) {
            val graphics = g as Graphics2D
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            graphics.color = Color(0, 0, 0, 0)
            graphics.fillRect(0, 0, width, height)
            super.paintComponents(g)
        }
    }

    var percentage: Double = 0.0
    var objectsToLoad = 0
    var loadedUI = false
    var loadedUpdates = false
    @Inject
    constructor(eventBus: EventBus, configuration: Configuration) {
        this.eventBus = eventBus
        this.configuration = configuration
        eventBus.register(this)
        if(configuration.showServerStats)
            objectsToLoad++
        if(configuration.showServerFeatures)
            objectsToLoad++
        if(configuration.showUpdates)
            objectsToLoad++

        JFrame.setDefaultLookAndFeelDecorated(true)
        frame.isUndecorated = true
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.validate()
        frame.setLocationRelativeTo(null)
        frame.contentPane.background = Color(0x25294a)
        frame.foreground = Color.WHITE
        frame.background = Color(0, 0, 0, 255)

        frame.contentPane.add(LoadingPanel())
        frame.pack()
        frame.isVisible = true
    }
    @Subscribe
    fun incrementPercentage(event: LoadedObject) {
        val incrementBy: Double = ((100 / objectsToLoad).toDouble())
        percentage += incrementBy
        if(percentage >= 97.5) {
            //send FinishedLoading object
        }
    }
}

class LoadingPanel : JPanel() {
    private var angle = 0.0
    private var extent = 0.0
    private val angleDelta = -1.0
    private val extentDelta = -5.0
    private var flip = false
    override fun getPreferredSize(): Dimension {
        return Dimension(200, 200)
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        val g2d = g.create() as Graphics2D
        val arcs = Dimension(15, 15)
        val width = width
        val height = height
        val graphics = g as Graphics2D
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        graphics.color = Color(0, 0, 0, 0)
        graphics.fillRect(0, 0, width, height)
        //Draws the rounded opaque panel with borders.


        //Draws the rounded opaque panel with borders.
        graphics.color = background
        graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height) //paint background

        graphics.color = foreground
        graphics.drawRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height) //paint border

        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY)
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY)
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE)
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON)
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR)
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE)
        val arc = Arc2D.Double(50.0, 50.0, 100.0, 100.0, angle, extent, Arc2D.OPEN)
        val stroke = BasicStroke(4F, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND)
        g2d.stroke = stroke
        g2d.color = Color.WHITE
        g2d.draw(arc)
        g2d.dispose()
        toolkit.sync()
    }

    init {
        isOpaque = false
        border = null
        background = Color.BLACK
        val timer = Timer(10) {
            angle += angleDelta
            extent += extentDelta
            if (abs(extent) % 360.0 == 0.0) {
                angle -= extent
                flip = !flip
                extent = if (flip) {
                    360.0
                } else {
                    0.0
                }
            }
            repaint()
        }
        timer.start()
        var l2 = JLabel("Preparing Launcher")
        l2.foreground = Color.white
        add(l2)
    }
}