package me.manhong2112.mimikkouimod.common

import android.graphics.Color
import android.support.annotation.ColorInt
import org.jetbrains.anko.collections.forEachWithIndex
import org.jetbrains.anko.withAlpha

sealed class TypedKey<T>(val defValue: T, val isList: Boolean = false) {
   object DockSwipeToDrawer : TypedKey<Boolean>(false)
   object DrawerBlurBackground : TypedKey<Boolean>(false)
   object DrawerDarkBackground : TypedKey<Boolean>(false)
   object DrawerBlurBackgroundBlurRadius : TypedKey<Int>(100)
   object DrawerColumnSize : TypedKey<Int>(4)
   object DrawerDrawUnderStatusBar : TypedKey<Boolean>(false)
   object DrawerBatSwipeToSearch : TypedKey<Boolean>(false)

   object GeneralIconPackFallback : TypedKey<List<String>>(listOf("default"), isList = true)
   object GeneralIconPackApplyDrawerButton : TypedKey<Boolean>(false)
   object GeneralIconScale : TypedKey<Int>(100) // in %
   object GeneralIconScaleApplyDrawerButton : TypedKey<Boolean>(false)

   object GeneralTransparentStatusBar : TypedKey<Boolean>(false)
   object GeneralStatusBarColor : TypedKey<@ColorInt Int>(Color.BLACK.withAlpha(0x99))
   object GeneralDarkStatusBarIcon : TypedKey<Boolean>(false)

   object GeneralShortcutTextSize : TypedKey<Float>(10f)
   object GeneralShortcutTextColor : TypedKey<@ColorInt Int>(Color.WHITE)
   object GeneralShortcutTextMaxLine : TypedKey<Int>(1)
   object GeneralShortcutTextOriginalName : TypedKey<Boolean>(false)
   object GeneralShortcutTextShadowColor : TypedKey<@ColorInt Int>(Color.BLACK)
   object GeneralShortcutTextShadowRadius : TypedKey<Float>(10f)
   object GeneralShortcutTextShadowDx : TypedKey<Float>(0f)
   object GeneralShortcutTextShadowDy : TypedKey<Float>(0f)

   val name: String by lazy {
      this::class.java.simpleName
   }
   val ordinal: Int by lazy {
      values.forEachWithIndex { index, value ->
         if (value::class.java === this::class.java) {
            return@lazy index
         }
      }
      throw IllegalStateException()
   }

   override fun equals(other: Any?): Boolean {
      return other !== null && other is TypedKey<*> && this.hashCode() == other.hashCode()
   }

   override fun hashCode(): Int {
      return ordinal
   }

   companion object {
      val size: Int by lazy {
         values.size
      }

      fun valueOf(name: String): TypedKey<Any> {
         values.forEach {
            if (it.name == name) {
               return it
            }
         }
         throw IllegalStateException()
      }

      val values: Array<TypedKey<Any>> by lazy {
         TypedKey::class.java.declaredClasses.mapNotNull {
            if (it.superclass === TypedKey::class.java) {
               it.getField("INSTANCE").get(it) as? TypedKey<Any>
            } else null
         }.toTypedArray()
      }
   }
}