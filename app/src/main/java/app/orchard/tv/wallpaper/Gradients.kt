package app.orchard.tv.wallpaper

import android.graphics.Bitmap
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RadialGradient
import android.graphics.RectF
import android.graphics.Shader
import androidx.core.graphics.createBitmap
import kotlin.math.sin

/**
 * Aerial's own wallpapers, in the manner of the Mac's: layered hills, dunes in side light, a sea
 * at the horizon, Big Sur's stacked colour bands, and soft fields of light. They are drawn on the
 * TV at its real resolution rather than shipped as pictures, so they cost nothing to install and
 * stay sharp on a 4K UI. Apple's own wallpapers cannot ship in an MIT app; these are original.
 *
 * Every shape is laid out on fractions of the picture, so a thumbnail and the full picture match.
 */
class GradientPreset(
    val key: String,
    val nameZh: String,
    val nameEn: String,
    internal val draw: Canvas.(w: Float, h: Float) -> Unit,
)

object Gradients {
    val presets = listOf(
        GradientPreset("hills_dawn", "山丘 · 黎明", "Hills, Dawn") { w, h ->
            hills(w, h, sky(0xFF7B8FD6, 0xFFE9B7C9, 0xFFFFD9B8), 0.7f, 0.6f, 0xFFFFD6AA, 0.7f, listOf(
                0xFFB7A3D8 to 0xFFC9B2D9, 0xFF8F7FC4 to 0xFFA38FC8, 0xFF6A5AA8 to 0xFF7B66AE, 0xFF433878 to 0xFF3A2F66,
            ))
        },
        GradientPreset("hills_dusk", "山丘 · 暮色", "Hills, Dusk") { w, h ->
            hills(w, h, sky(0xFF16244F, 0xFFC25B6B, 0xFFF59B5B), 0.35f, 0.62f, 0xFFFFA05A, 0.65f, listOf(
                0xFF7D3F63 to 0xFF8A4762, 0xFF552D5A to 0xFF5B2F55, 0xFF33204A to 0xFF2C1C40, 0xFF170F2A to 0xFF120B20,
            ))
        },
        GradientPreset("hills_mist", "山丘 · 晨雾", "Hills, Mist") { w, h ->
            hills(w, h, sky(0xFFA9C9D8, 0xFFDBE8E3, 0xFFF3EFE2), 0.3f, 0.55f, 0xFFFFFAEB, 0.7f, listOf(
                0xFFA8C2BB to 0xFFB9D0C6, 0xFF7EA79F to 0xFF8FB3A7, 0xFF4F8178 to 0xFF5D8B7D, 0xFF2A5249 to 0xFF24463D,
            ))
        },
        GradientPreset("hills_night", "山丘 · 夜", "Hills, Night") { w, h ->
            hills(w, h, sky(0xFF04081C, 0xFF14285A, 0xFF2F4D86), 0.62f, 0.58f, 0xFF78AAFF, 0.35f, listOf(
                0xFF1F3566 to 0xFF253C70, 0xFF162851 to 0xFF182B55, 0xFF0D1B3A to 0xFF0B1733, 0xFF050C1F to 0xFF040917,
            ))
        },
        GradientPreset("dunes_gold", "沙丘 · 金色", "Dunes, Gold") { w, h ->
            dunes(w, h, sky(0xFF4F7FBF, 0xFFA7C3DD, 0xFFF5D3A0), 0.8f, 0.45f, 0xFFFFE6B4, 0.5f, listOf(
                0xFFF3C58A to 0xFFE7A96A, 0xFFEEB06C to 0xFFD98E4F, 0xFFE39A52 to 0xFFC9733A, 0xFFD4843F to 0xFFB25D2B,
            ), listOf(0x73A05028, 0x808C411E, 0x8C783419, 0x99642814))
        },
        GradientPreset("dunes_moon", "沙丘 · 月夜", "Dunes, Moonlight") { w, h ->
            dunes(w, h, sky(0xFF0B0F2E, 0xFF2B2F6B, 0xFF6C5A9E), 0.2f, 0.4f, 0xFFB4AAFF, 0.3f, listOf(
                0xFF6D63A8 to 0xFF5A4F93, 0xFF5A4F93 to 0xFF463C7C, 0xFF463C7C to 0xFF352C66, 0xFF352C66 to 0xFF251E4F,
            ), listOf(0x730A0828, 0x800A0828, 0x8C0A0828, 0x990A0828))
        },
        GradientPreset("sea_sunset", "海 · 日落", "Sea, Sunset") { w, h ->
            sea(w, h, sky(0xFF2A2F6E, 0xFFC75A7A, 0xFFFFB070), 0xFFFFAA6E, 0.6f, sky(0xFF6A3D6D, 0xFF2A1F48, 0xFF120C26), 0xFFFFF0D2, 0xFFFFC896)
        },
        GradientPreset("sea_clear", "海 · 晴空", "Sea, Clear Sky") { w, h ->
            sea(w, h, sky(0xFF2F6FD6, 0xFF78AEE8, 0xFFD9ECF7), 0xFFFFFFFF, 0.55f, sky(0xFF2D7FC2, 0xFF155A9A, 0xFF0A3566), 0xFFFFFFFA, 0xFFFFFFFF)
        },
        GradientPreset("waves_blue", "波浪 · 深蓝", "Waves, Blue") { w, h ->
            waves(w, h, 0xFF0A1A4A to 0xFF123A8A, listOf(
                0xFF1F5FD0 to 0xFF0D2F7A, 0xFF2E86E8 to 0xFF1446A8, 0xFF3FB2F0 to 0xFF1A64C4, 0xFF7FD3FF to 0xFF2B85DC,
            ))
        },
        GradientPreset("waves_coral", "波浪 · 珊瑚", "Waves, Coral") { w, h ->
            waves(w, h, 0xFF3A0F2E to 0xFF7A1F45, listOf(
                0xFFC23A6B to 0xFF7A1F4A, 0xFFEC5F6A to 0xFFB0334F, 0xFFFF8A5C to 0xFFD9534A, 0xFFFFC078 to 0xFFF08A52,
            ))
        },
        GradientPreset("waves_violet", "波浪 · 紫罗兰", "Waves, Violet") { w, h ->
            waves(w, h, 0xFF120A3A to 0xFF2A1670, listOf(
                0xFF5B3FD0 to 0xFF2C1A85, 0xFF8A5CF0 to 0xFF4F30B8, 0xFFC27BF5 to 0xFF7B45D8, 0xFFF2A6E8 to 0xFFB56AD8,
            ))
        },
        GradientPreset("glow_pink", "光晕 · 柔粉", "Glow, Pink") { w, h ->
            glows(w, h, 0xFFF6D6E0, listOf(
                Blob(0.2f, 0.3f, 0.35f, 0xFFFFB3C7), Blob(0.75f, 0.25f, 0.3f, 0xFFC9B6FF), Blob(0.55f, 0.85f, 0.4f, 0xFFFFD2A8), Blob(0.9f, 0.8f, 0.25f, 0xFFA8D8FF),
            ))
        },
        GradientPreset("aurora", "光晕 · 极光", "Glow, Aurora") { w, h ->
            glows(w, h, 0xFF071029, listOf(
                Blob(0.2f, 0.35f, 0.3f, 0xFF18C79B), Blob(0.65f, 0.2f, 0.35f, 0xFF5B3FD9), Blob(0.5f, 0.9f, 0.4f, 0xFF0E6FFF), Blob(0.95f, 0.6f, 0.2f, 0xFFC14BD8),
            ))
        },
        GradientPreset("glow_lime", "光晕 · 青柠", "Glow, Lime") { w, h ->
            glows(w, h, 0xFFE3F4EA, listOf(
                Blob(0.25f, 0.3f, 0.3f, 0xFF9EE6B8), Blob(0.75f, 0.3f, 0.3f, 0xFFBFE9FF), Blob(0.5f, 0.85f, 0.4f, 0xFFF2F0A0), Blob(0.05f, 0.85f, 0.25f, 0xFF7FD6C8),
            ))
        },
        GradientPreset("orbit_night", "轨道 · 夜", "Orbit, Night") { w, h -> orbit(w, h) },
        GradientPreset("nebula_deep", "星云 · 深空", "Nebula, Deep Space") { w, h -> nebula(w, h) },
        GradientPreset("aurora_ridge", "极光 · 山脊", "Aurora, Ridge") { w, h -> auroraRidge(w, h) },
        GradientPreset("grid_cyber", "网格 · 赛博", "Grid, Cyber") { w, h -> gridCyber(w, h) },
        GradientPreset("space_daily", "每日 · 深空", "Daily, Deep Space") { w, h -> dailySpace(w, h) },
    )

    /**
     * The light fields Aerial shipped before these, so a wallpaper chosen then still resolves: each
     * points at the new one closest in colour. "aurora" kept its key.
     */
    private val renamed = mapOf(
        "sunset" to "sea_sunset", "ocean" to "waves_blue", "violet" to "waves_violet", "forest" to "hills_mist",
        "dune" to "dunes_gold", "rose" to "glow_pink", "graphite" to "hills_night",
    )

    fun byKey(key: String) = presets.firstOrNull { it.key == (renamed[key] ?: key) }

    /** The key a stored choice resolves to today. */
    fun currentKey(key: String) = renamed[key] ?: key

    /** Stands for "Follow App" in the picker. */
    val autoThumb get() = byKey("hills_night")!!

    /** Renders a preset at [width]×[height], dithered so the long gradients do not band. */
    fun render(preset: GradientPreset, width: Int, height: Int): Bitmap {
        val out = createBitmap(width, height)
        Canvas(out).apply { preset.draw(this, width.toFloat(), height.toFloat()) }
        Images.dither(out)
        return out
    }

    // ---- the five kinds ----

    internal class Blob(val x: Float, val y: Float, val r: Float, val color: Long)

    private fun sky(vararg c: Long) = c.map { it.toInt() }.toIntArray()

    private fun paint(shader: Shader) = Paint(Paint.ANTI_ALIAS_FLAG).apply { this.shader = shader }

    private fun vertical(y0: Float, y1: Float, colors: IntArray) =
        LinearGradient(0f, y0, 0f, y1, colors, null, Shader.TileMode.CLAMP)

    private fun pair(p: Pair<Long, Long>) = intArrayOf(p.first.toInt(), p.second.toInt())

    private fun Canvas.glowAt(w: Float, h: Float, x: Float, y: Float, r: Float, color: Long, alpha: Float) {
        val c = color.toInt()
        val a = (alpha * 255).toInt() shl 24
        drawRect(0f, 0f, w, h, paint(RadialGradient(x, y, r, (c and 0xFFFFFF) or a, c and 0xFFFFFF, Shader.TileMode.CLAMP)))
    }

    /**
     * A ridge: three sines, seeded so each layer has its own, the same at every size. Closed down
     * to the bottom of the picture, or with [crestOnly] just the line along the top.
     */
    private fun ridge(w: Float, h: Float, base: Float, amp: Float, seed: Int, crestOnly: Boolean = false): Path {
        var s = seed.toLong()
        fun r(): Float { s = (s * 16807) % 2147483647; return s / 2147483647f }
        val ph = floatArrayOf(r() * 6, r() * 6, r() * 6)
        val f = floatArrayOf(1.3f + r(), 3 + r() * 2, 7 + r() * 3)
        return Path().apply {
            val steps = 240
            for (i in 0..steps) {
                val t = i / steps.toFloat()
                val y = base + amp * (sin(t * f[0] + ph[0]) * 0.6f + sin(t * f[1] + ph[1]) * 0.28f + sin(t * f[2] + ph[2]) * 0.12f)
                if (i == 0) {
                    if (crestOnly) moveTo(0f, y) else { moveTo(0f, h); lineTo(0f, y) }
                } else {
                    lineTo(t * w, y)
                }
            }
            if (!crestOnly) {
                lineTo(w, h)
                close()
            }
        }
    }

    private fun Canvas.hills(w: Float, h: Float, sky: IntArray, sunX: Float, sunY: Float, glow: Long, glowA: Float, layers: List<Pair<Long, Long>>) {
        drawRect(0f, 0f, w, h, paint(vertical(0f, h * 0.75f, sky)))
        glowAt(w, h, w * sunX, h * sunY, w * 0.45f, glow, glowA)
        layers.forEachIndexed { i, l ->
            val base = h * (0.45f + i * 0.11f)
            drawPath(ridge(w, h, base, h * (0.09f - i * 0.006f), 7 + i * 13), paint(vertical(base - h * 0.1f, h, pair(l))))
        }
    }

    private fun Canvas.dunes(w: Float, h: Float, sky: IntArray, sunX: Float, sunY: Float, glow: Long, glowA: Float, lit: List<Pair<Long, Long>>, shade: List<Long>) {
        drawRect(0f, 0f, w, h, paint(vertical(0f, h * 0.6f, sky)))
        glowAt(w, h, w * sunX, h * sunY, w * 0.4f, glow, glowA)
        for (i in 0 until 4) {
            val base = h * (0.5f + i * 0.12f)
            // Long, low swells: a ridge line with less amplitude than a hill's.
            val dune = ridge(w, h, base, h * (0.06f - i * 0.008f), 41 + i * 17)
            drawPath(dune, paint(vertical(base - h * 0.08f, h, pair(lit[i]))))
            // Light from the sun's side, the far side falling into shadow: a sideways gradient
            // across the dune, so the turn from lit to shaded is soft, as on real sand.
            val c = shade[i].toInt()
            val fromX = if (sunX > 0.5f) w else 0f
            drawPath(dune, paint(LinearGradient(fromX, 0f, w - fromX, 0f,
                intArrayOf(c and 0xFFFFFF, c and 0xFFFFFF, c), floatArrayOf(0f, 0.35f, 1f), Shader.TileMode.CLAMP)))
            // A bright crest line where the light grazes the top.
            save()
            translate(0f, h * 0.004f)
            drawPath(ridge(w, h, base, h * (0.06f - i * 0.008f), 41 + i * 17, crestOnly = true), Paint(Paint.ANTI_ALIAS_FLAG).apply {
                style = Paint.Style.STROKE
                strokeWidth = h * 0.003f
                color = 0x33FFFFFF
            })
            restore()
        }
    }

    private fun Canvas.sea(w: Float, h: Float, sky: IntArray, glow: Long, glowA: Float, water: IntArray, sun: Long, glint: Long) {
        val horizon = h * 0.58f
        drawRect(0f, 0f, w, horizon, paint(vertical(0f, horizon, sky)))
        save(); clipRect(0f, 0f, w, horizon)
        glowAt(w, h, w * 0.5f, horizon, w * 0.5f, glow, glowA)
        glowAt(w, h, w * 0.5f, horizon, w * 0.06f, sun, 1f)
        restore()
        drawRect(0f, horizon, w, h, paint(vertical(horizon, h, water)))
        // The sun's path on the water: lines that widen and fade toward the viewer.
        val p = Paint(Paint.ANTI_ALIAS_FLAG)
        val unit = h / 270f
        for (i in 0 until 26) {
            val y = horizon + (3 + i * i * 0.18f) * unit
            val half = w * (0.05f + i * 0.008f) / 2
            p.color = (glint.toInt() and 0xFFFFFF) or (((0.55f - i * 0.02f) * 255).toInt() shl 24)
            drawRect(w * 0.5f - half, y, w * 0.5f + half, y + 1.2f * unit, p)
        }
    }

    private fun Canvas.waves(w: Float, h: Float, bg: Pair<Long, Long>, bands: List<Pair<Long, Long>>) {
        drawRect(0f, 0f, w, h, paint(vertical(0f, h, pair(bg))))
        val unit = w / 480f
        val edge = w * 0.04f
        val shadow = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = 0x59000000
            maskFilter = BlurMaskFilter(24 * unit, BlurMaskFilter.Blur.NORMAL)
        }
        bands.forEachIndexed { i, b ->
            val y0 = h * (0.15f + i * 0.2f)
            val path = Path().apply {
                moveTo(-edge, h + edge); lineTo(-edge, y0 + h * 0.12f)
                cubicTo(w * 0.3f, y0 - h * 0.18f, w * 0.55f, y0 + h * 0.32f, w + edge, y0 - h * 0.02f)
                lineTo(w + edge, h + edge); close()
            }
            // Each band casts a soft shadow up onto the one behind it.
            save()
            translate(0f, -6 * unit)
            drawPath(path, shadow)
            restore()
            drawPath(path, paint(vertical(y0 - h * 0.1f, y0 + h * 0.5f, pair(b))))
        }
    }

    /** Large soft blobs of colour: drawn small, blurred until no edge is left, then scaled up. */
    private fun Canvas.glows(w: Float, h: Float, base: Long, blobs: List<Blob>) {
        val sw = 160
        val sh = 90
        val small = createBitmap(sw, sh)
        Canvas(small).apply {
            drawColor(base.toInt())
            val p = Paint(Paint.ANTI_ALIAS_FLAG)
            for (b in blobs) {
                p.color = b.color.toInt()
                val rx = sw * b.r
                drawOval(RectF(sw * b.x - rx, sh * b.y - rx * 0.7f, sw * b.x + rx, sh * b.y + rx * 0.7f), p)
            }
        }
        val px = IntArray(sw * sh)
        small.getPixels(px, 0, sw, 0, 0, sw, sh)
        repeat(4) { Images.boxBlur(px, sw, sh, 12) }
        small.setPixels(px, 0, sw, 0, 0, sw, sh)
        drawBitmap(small, null, RectF(0f, 0f, w, h), Paint(Paint.FILTER_BITMAP_FLAG))
        small.recycle()
    }

    private fun Canvas.orbit(w: Float, h: Float) {
        val bg = Paint().apply {
            shader = LinearGradient(
                0f, 0f, 0f, h,
                intArrayOf(0xFF01030A.toInt(), 0xFF05101F.toInt(), 0xFF0B1B2E.toInt()),
                floatArrayOf(0f, 0.6f, 1f), Shader.TileMode.CLAMP,
            )
        }
        drawRect(0f, 0f, w, h, bg)
        val rnd = java.util.Random(11L)
        val star = Paint(Paint.ANTI_ALIAS_FLAG)
        repeat((w * h / 7000f).toInt().coerceIn(120, 520)) {
            star.color = 0xFFFFFFFF.toInt()
            star.alpha = 30 + rnd.nextInt(200)
            drawCircle(rnd.nextFloat() * w, rnd.nextFloat() * h * 0.82f, (0.5f + rnd.nextFloat() * 1.5f) * w / 1920f, star)
        }
        val cx = w * 0.5f
        val cy = h * 1.78f
        val r = h * 1.08f
        val planet = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            shader = RadialGradient(
                cx, cy, r,
                intArrayOf(0xFF10263D.toInt(), 0xFF071523.toInt(), 0xFF03080F.toInt()),
                floatArrayOf(0f, 0.65f, 1f), Shader.TileMode.CLAMP,
            )
        }
        drawCircle(cx, cy, r, planet)
        val rim = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = h * 0.009f
            shader = LinearGradient(
                0f, cy - r, 0f, cy - r + h * 0.05f,
                intArrayOf(0x007FE9FF, 0xAA9FF3FF.toInt(), 0x007FE9FF),
                floatArrayOf(0f, 0.45f, 1f), Shader.TileMode.CLAMP,
            )
        }
        drawCircle(cx, cy, r, rim)
        val light = Paint(Paint.ANTI_ALIAS_FLAG)
        repeat(240) {
            val a = Math.PI * (1.12 + rnd.nextDouble() * 0.76)
            val rr = r * (0.975f + rnd.nextFloat() * 0.02f)
            val x = cx + (Math.cos(a) * rr).toFloat()
            val y = cy + (Math.sin(a) * rr).toFloat()
            if (y in (h * 0.55f)..(h * 1.02f)) {
                light.color = 0xFFFFC98A.toInt()
                light.alpha = 30 + rnd.nextInt(170)
                drawCircle(x, y, (0.8f + rnd.nextFloat() * 2.2f) * w / 1920f, light)
            }
        }
    }

    private fun Canvas.nebula(w: Float, h: Float) {
        glows(w, h, 0xFF05060F, listOf(
            Blob(0.3f, 0.35f, 0.45f, 0xFF3B2B7A),
            Blob(0.72f, 0.3f, 0.4f, 0xFF1B4E8A),
            Blob(0.55f, 0.8f, 0.5f, 0xFF6A2A6E),
            Blob(0.15f, 0.75f, 0.3f, 0xFF125F6A),
        ))
        val rnd = java.util.Random(23L)
        val p = Paint(Paint.ANTI_ALIAS_FLAG)
        p.color = 0xFFFFFFFF.toInt()
        repeat(300) {
            p.alpha = 40 + rnd.nextInt(190)
            drawCircle(rnd.nextFloat() * w, rnd.nextFloat() * h, (0.4f + rnd.nextFloat() * 1.4f) * w / 1920f, p)
        }
    }

    private fun Canvas.auroraRidge(w: Float, h: Float) {
        drawRect(0f, 0f, w, h, Paint().apply {
            shader = LinearGradient(
                0f, 0f, 0f, h,
                intArrayOf(0xFF02040C.toInt(), 0xFF07122A.toInt(), 0xFF0B1B33.toInt()),
                floatArrayOf(0f, 0.55f, 1f), Shader.TileMode.CLAMP,
            )
        })
        val rnd = java.util.Random(31L)
        val star = Paint(Paint.ANTI_ALIAS_FLAG)
        star.color = 0xFFFFFFFF.toInt()
        repeat((w * h / 9000f).toInt().coerceIn(90, 380)) {
            star.alpha = 30 + rnd.nextInt(160)
            drawCircle(rnd.nextFloat() * w, rnd.nextFloat() * h * 0.55f, (0.4f + rnd.nextFloat() * 1.2f) * w / 1920f, star)
        }
        val colors = listOf(0xFF3BFFC0.toInt() or (0x66 shl 24), 0xFF5B8CFF.toInt() or (0x55 shl 24), 0xFFB26BFF.toInt() or (0x44 shl 24))
        colors.forEachIndexed { i, c ->
            val path = Path()
            val baseY = h * (0.30f + i * 0.05f)
            path.moveTo(0f, baseY)
            var x = 0f
            while (x <= w) {
                val y = baseY + sin((x / w * 5f + i * 1.7).toFloat()) * h * 0.04f
                path.lineTo(x, y)
                x += w / 48f
            }
            path.lineTo(w, h * 0.62f)
            path.lineTo(0f, h * 0.62f)
            path.close()
            val p = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                shader = LinearGradient(0f, baseY, 0f, h * 0.62f, c and 0x00FFFFFF or (0x88 shl 24), 0x00FFFFFF, Shader.TileMode.CLAMP)
                maskFilter = BlurMaskFilter(w * 0.02f, BlurMaskFilter.Blur.NORMAL)
            }
            drawPath(path, p)
        }
        val ridge = Path().apply {
            moveTo(0f, h * 0.86f)
            var x = 0f
            while (x <= w) {
                val y = h * 0.86f - (sin((x / w * 3.2f).toFloat()) * 0.5f + 0.5f) * h * 0.13f
                lineTo(x, y)
                x += w / 64f
            }
            lineTo(w, h)
            lineTo(0f, h)
            close()
        }
        drawPath(ridge, Paint(Paint.ANTI_ALIAS_FLAG).apply { color = 0xFF04070D.toInt() })
    }

    private fun Canvas.dailySpace(w: Float, h: Float) {
        val day = java.time.LocalDate.now().dayOfYear
        val rnd = java.util.Random(day.toLong() * 7919L)
        val palettes = listOf(
            Triple(0xFF2A1B5E, 0xFF1B4E8A, 0xFF6A2A6E),
            Triple(0xFF123A6B, 0xFF0E6FFF, 0xFF2AD0C0),
            Triple(0xFF4A1B4E, 0xFFB03A8A, 0xFF5B3FD9),
            Triple(0xFF0E2A3A, 0xFF2AB0C0, 0xFF6A8A2E),
            Triple(0xFF3A1B1B, 0xFFC05A3A, 0xFF8A2A6E),
        )
        val p0 = palettes[day % palettes.size]
        glows(w, h, 0xFF04050C, listOf(
            Blob(0.25f + rnd.nextFloat() * 0.2f, 0.3f + rnd.nextFloat() * 0.2f, 0.42f, p0.first),
            Blob(0.7f + rnd.nextFloat() * 0.2f, 0.25f + rnd.nextFloat() * 0.2f, 0.38f, p0.second),
            Blob(0.5f + rnd.nextFloat() * 0.2f, 0.8f + rnd.nextFloat() * 0.1f, 0.5f, p0.third),
            Blob(0.15f + rnd.nextFloat() * 0.2f, 0.75f + rnd.nextFloat() * 0.15f, 0.3f, p0.second),
        ))
        val star = Paint(Paint.ANTI_ALIAS_FLAG)
        star.color = 0xFFFFFFFF.toInt()
        repeat(340) {
            star.alpha = 30 + rnd.nextInt(200)
            drawCircle(rnd.nextFloat() * w, rnd.nextFloat() * h, (0.4f + rnd.nextFloat() * 1.5f) * w / 1920f, star)
        }
        if (day % 3 != 0) {
            val cx = w * (0.35f + rnd.nextFloat() * 0.3f)
            val cy = h * 1.9f
            val r = h * (1.05f + rnd.nextFloat() * 0.2f)
            drawCircle(cx, cy, r, Paint(Paint.ANTI_ALIAS_FLAG).apply {
                shader = RadialGradient(cx, cy, r, intArrayOf(0xFF0C1A2C.toInt(), 0xFF050B14.toInt(), 0xFF010206.toInt()), floatArrayOf(0f, 0.7f, 1f), Shader.TileMode.CLAMP)
            })
            val rim = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                style = Paint.Style.STROKE
                strokeWidth = h * 0.008f
                shader = LinearGradient(0f, cy - r, 0f, cy - r + h * 0.05f, intArrayOf(0x007FE9FF, 0x99AFF6FF.toInt(), 0x007FE9FF), floatArrayOf(0f, 0.5f, 1f), Shader.TileMode.CLAMP)
            }
            drawCircle(cx, cy, r, rim)
        }
    }

    private fun Canvas.gridCyber(w: Float, h: Float) {
        drawRect(0f, 0f, w, h, Paint().apply {
            shader = LinearGradient(
                0f, 0f, 0f, h,
                intArrayOf(0xFF05060D.toInt(), 0xFF0A0A1E.toInt(), 0xFF120A24.toInt()),
                null, Shader.TileMode.CLAMP,
            )
        })
        val horizon = h * 0.62f
        drawRect(0f, horizon - h * 0.16f, w, horizon, Paint().apply {
            shader = LinearGradient(
                0f, horizon - h * 0.16f, 0f, horizon,
                intArrayOf(0x00FF3FA0, 0x55FF3FA0, 0x00FF3FA0),
                floatArrayOf(0f, 0.55f, 1f), Shader.TileMode.CLAMP,
            )
            maskFilter = BlurMaskFilter(h * 0.02f, BlurMaskFilter.Blur.NORMAL)
        })
        val line = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = h * 0.0022f
            color = 0x66FF5FB0
        }
        val verticals = 24
        for (i in 0..verticals) {
            val x = w * i / verticals
            drawLine(x, h, w * 0.5f + (x - w * 0.5f) * 0.06f, horizon, line)
        }
        var y = horizon
        var step = h * 0.012f
        while (y < h) {
            drawLine(0f, y, w, y, line.apply { alpha = (60 + (y - horizon) / h * 160).toInt().coerceIn(40, 200) })
            y += step
            step *= 1.28f
        }
        val glow = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = 0xFF5FE8FF.toInt()
            alpha = 70
            maskFilter = BlurMaskFilter(h * 0.045f, BlurMaskFilter.Blur.NORMAL)
        }
        drawCircle(w * 0.5f, horizon, h * 0.035f, glow)
    }
}
