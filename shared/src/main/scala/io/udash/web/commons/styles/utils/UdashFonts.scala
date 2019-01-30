package io.udash.web.commons.styles.utils

import io.udash.css.{CssBase, CssStyle}
import scalacss.internal.AV

object UdashFonts extends CssBase {
  import dsl._

  def roboto(fontWeight: AV = FontWeight.Regular, fontStyle: AV = FontStyle.Normal): CssStyle = mixin(
    fontFamily :=! FontFamily.Roboto,
    fontStyle,
    fontWeight
  )
}

object FontFamily {
  val Roboto = "'Roboto', sans-serif"
}

object FontWeight extends CssBase {
  import dsl._
  val ExtraLight: AV = fontWeight._200
  val Light: AV = fontWeight._300
  val Regular: AV  = fontWeight._400
  val Medium: AV  = fontWeight._500
  val SemiBold: AV = fontWeight._600
  val Bold: AV = fontWeight._700
}

object FontStyle extends CssBase {
  import dsl._
  val Normal: AV = fontStyle.normal
  val Italic: AV = fontStyle.italic
}

