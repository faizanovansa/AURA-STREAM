package com.lagradost.cloudstream4.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

/**
 * Maps to the XML custom attrs declared in attrs.xml:
 * TODO: Remove this comment when we migrate fully
 *  and attrs.xml will no longer be used at all.
 *
 * | XML ?attr                | Here               |
 * |--------------------------|--------------------|
 * | primaryBlackBackground   | [background]       |
 * | primaryGrayBackground    | [surfaceVariant]   |
 * | iconGrayBackground       | [surface]          |
 * | boxItemBackground        | [surfaceContainer] |
 * | textColor                | [onBackground]     |
 * | grayTextColor            | [onSurfaceVariant] |
 * | iconColor                | [icon]             |
 * | colorPrimary             | [primary]          |
 * | colorOngoing             | [ongoing]          |
 *
 * All fields are [MutableState] so Compose recomposes automatically
 * if the scheme is swapped at runtime (e.g. user changes theme without restart).
 */
@Stable
class CloudStreamColorScheme(
    background: Color,
    surfaceVariant: Color,
    surface: Color,
    surfaceContainer: Color,
    onBackground: Color,
    onSurfaceVariant: Color,
    icon: Color,
    primary: Color,
    ongoing: Color,
    isLight: Boolean,
) {
    /** primaryBlackBackground */
    var background by mutableStateOf(background)

    /** primaryGrayBackground */
    var surfaceVariant by mutableStateOf(surfaceVariant)

    /** iconGrayBackground */
    var surface by mutableStateOf(surface)

    /** boxItemBackground */
    var surfaceContainer by mutableStateOf(surfaceContainer)

    /** textColor */
    var onBackground by mutableStateOf(onBackground)

    /** grayTextColor */
    var onSurfaceVariant by mutableStateOf(onSurfaceVariant)

    /** iconColor */
    var icon by mutableStateOf(icon)

    /** colorPrimary */
    var primary by mutableStateOf(primary)

    /** colorOngoing */
    var ongoing by mutableStateOf(ongoing)
    var isLight by mutableStateOf(isLight)

    fun copy(
        background: Color = this.background,
        surfaceVariant: Color = this.surfaceVariant,
        surface: Color = this.surface,
        surfaceContainer: Color = this.surfaceContainer,
        onBackground: Color = this.onBackground,
        onSurfaceVariant: Color = this.onSurfaceVariant,
        icon: Color = this.icon,
        primary: Color = this.primary,
        ongoing: Color = this.ongoing,
        isLight: Boolean = this.isLight,
    ) = CloudStreamColorScheme(
        background, surfaceVariant, surface, surfaceContainer,
        onBackground, onSurfaceVariant, icon, primary, ongoing, isLight,
    )
}

internal fun darkScheme() = CloudStreamColorScheme(
    background = CloudStreamPalette.DarkBlackBg,
    surfaceVariant = CloudStreamPalette.DarkPrimaryGrayBg,
    surface = CloudStreamPalette.DarkIconGrayBg,
    surfaceContainer = CloudStreamPalette.DarkBoxItemBg,
    onBackground = CloudStreamPalette.DarkText,
    onSurfaceVariant = CloudStreamPalette.DarkGrayText,
    icon = CloudStreamPalette.DarkIcon,
    primary = CloudStreamPalette.Primary,
    ongoing = CloudStreamPalette.Ongoing,
    isLight = false,
)


internal fun amoledScheme() = darkScheme().copy(
    background = CloudStreamPalette.AmoledBlack,
    surface = CloudStreamPalette.AmoledBlack,
    surfaceVariant = CloudStreamPalette.AmoledBlack,
    surfaceContainer = CloudStreamPalette.AmoledBlack,
)

internal fun amoledLightScheme() = amoledScheme().copy(
    surfaceVariant = CloudStreamPalette.AmoledNearBlack,
)

internal fun lightScheme() = CloudStreamColorScheme(
    background = CloudStreamPalette.LightBlackBg,
    surfaceVariant = CloudStreamPalette.LightPrimaryGrayBg,
    surface = CloudStreamPalette.LightIconGrayBg,
    surfaceContainer = CloudStreamPalette.LightBoxItemBg,
    onBackground = CloudStreamPalette.LightText,
    onSurfaceVariant = CloudStreamPalette.LightGrayText,
    icon = CloudStreamPalette.LightIcon,
    primary = CloudStreamPalette.Primary,
    ongoing = CloudStreamPalette.Ongoing,
    isLight = true,
)

internal fun draculaScheme() = CloudStreamColorScheme(
    background = CloudStreamPalette.DraculaBlackBg,
    surfaceVariant = CloudStreamPalette.DraculaPrimaryGrayBg,
    surface = CloudStreamPalette.DraculaIconGrayBg,
    surfaceContainer = CloudStreamPalette.DraculaBoxItemBg,
    onBackground = CloudStreamPalette.DraculaText,
    onSurfaceVariant = CloudStreamPalette.DraculaGrayText,
    icon = CloudStreamPalette.DraculaIcon,
    primary = CloudStreamPalette.Primary,
    ongoing = CloudStreamPalette.Ongoing,
    isLight = false,
)

internal fun lavenderScheme() = CloudStreamColorScheme(
    background = CloudStreamPalette.LavenderBlackBg,
    surfaceVariant = CloudStreamPalette.LavenderPrimaryGrayBg,
    surface = CloudStreamPalette.LavenderIconGrayBg,
    surfaceContainer = CloudStreamPalette.LavenderBoxItemBg,
    onBackground = CloudStreamPalette.LavenderText,
    onSurfaceVariant = CloudStreamPalette.LavenderGrayText,
    icon = CloudStreamPalette.LavenderIcon,
    primary = CloudStreamPalette.Primary,
    ongoing = CloudStreamPalette.Ongoing,
    isLight = true,
)

internal fun silentBlueScheme() = CloudStreamColorScheme(
    background = CloudStreamPalette.SilentBlueBlackBg,
    surfaceVariant = CloudStreamPalette.SilentBluePrimaryGrayBg,
    surface = CloudStreamPalette.SilentBlueIconGrayBg,
    surfaceContainer = CloudStreamPalette.SilentBlueBoxItemBg,
    onBackground = CloudStreamPalette.SilentBlueText,
    onSurfaceVariant = CloudStreamPalette.SilentBlueGrayText,
    icon = CloudStreamPalette.SilentBlueIcon,
    primary = CloudStreamPalette.Primary,
    ongoing = CloudStreamPalette.Ongoing,
    isLight = false,
)

/** AURA's neutral black-and-white theme. */
internal fun auraMonochromeScheme() = CloudStreamColorScheme(
    background = CloudStreamPalette.AuraMonoBackground,
    surfaceVariant = CloudStreamPalette.AuraMonoSurfaceVariant,
    surface = CloudStreamPalette.AuraMonoSurface,
    surfaceContainer = CloudStreamPalette.AuraMonoContainer,
    onBackground = CloudStreamPalette.AuraMonoText,
    onSurfaceVariant = CloudStreamPalette.AuraMonoMuted,
    icon = CloudStreamPalette.AuraMonoMuted,
    primary = Color(0xFFE6E6E6),
    ongoing = CloudStreamPalette.Ongoing,
    isLight = false,
)

/** AURA's warm cream and stone theme. */
internal fun auraSoftNeutralScheme() = CloudStreamColorScheme(
    background = CloudStreamPalette.AuraSoftBackground,
    surfaceVariant = CloudStreamPalette.AuraSoftSurfaceVariant,
    surface = CloudStreamPalette.AuraSoftSurface,
    surfaceContainer = CloudStreamPalette.AuraSoftContainer,
    onBackground = CloudStreamPalette.AuraSoftText,
    onSurfaceVariant = CloudStreamPalette.AuraSoftMuted,
    icon = CloudStreamPalette.AuraSoftMuted,
    primary = Color(0xFF665F53),
    ongoing = CloudStreamPalette.Ongoing,
    isLight = true,
)

/** AURA's graphite and silver cinematic theme. */
internal fun auraDarkPremiumScheme() = CloudStreamColorScheme(
    background = CloudStreamPalette.AuraPremiumBackground,
    surfaceVariant = CloudStreamPalette.AuraPremiumSurfaceVariant,
    surface = CloudStreamPalette.AuraPremiumSurface,
    surfaceContainer = CloudStreamPalette.AuraPremiumContainer,
    onBackground = CloudStreamPalette.AuraPremiumText,
    onSurfaceVariant = CloudStreamPalette.AuraPremiumMuted,
    icon = CloudStreamPalette.AuraPremiumMuted,
    primary = Color(0xFFC4C9CE),
    ongoing = CloudStreamPalette.Ongoing,
    isLight = false,
)

/** AURA's light neutral theme with a restrained violet accent. */
internal fun auraMinimalAccentScheme() = CloudStreamColorScheme(
    background = CloudStreamPalette.AuraAccentBackground,
    surfaceVariant = CloudStreamPalette.AuraAccentSurfaceVariant,
    surface = CloudStreamPalette.AuraAccentSurface,
    surfaceContainer = CloudStreamPalette.AuraAccentContainer,
    onBackground = CloudStreamPalette.AuraAccentText,
    onSurfaceVariant = CloudStreamPalette.AuraAccentMuted,
    icon = CloudStreamPalette.AuraAccentMuted,
    primary = CloudStreamPalette.AuraAccentPrimary,
    ongoing = CloudStreamPalette.Ongoing,
    isLight = true,
)
