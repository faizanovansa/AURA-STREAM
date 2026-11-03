# AURA — Technical Audit Report

**Repository:** `faizanovansa/AURA-STREAM`  
**Branch audited:** `aura/theme-system`  
**Audited commit:** `704c86d99971849c33a6c59fd94a033dd72e65a5`  
**Date:** 3 November 2026  
**Status:** Static repository audit; build and runtime behavior not yet verified.

## 1. Executive Summary

AURA is not starting from a blank application shell. The repository already contains a mature Android navigation structure, separate phone/TV layouts, Home, Search, Library, Downloads, Settings, a media player, extension management, and a shared Compose theme layer.

The correct strategy is to evolve the existing application incrementally. Replacing navigation or rebuilding core media screens before mapping their dependencies would create unnecessary regression risk.

## 2. Repository and Module Structure

The Gradle project includes:

- `:app`: Android application, activities, fragments, resources, and platform integrations.
- `:shared`: Kotlin Multiplatform/shared Compose code and theme infrastructure.
- `:library`: shared library and core utility functionality.
- `:docs`: documentation module.
- `:desktopApp`: desktop application target.

The root project is still named `CloudStream`. Android namespace and application ID are currently `com.lagradost.cloudstream3`. These are implementation facts, not necessarily final AURA branding decisions.

## 3. Existing Navigation

The current navigation is based on AndroidX Navigation with a Navigation graph at:

`app/src/main/res/navigation/mobile_navigation.xml`

The existing primary menu is:

`app/src/main/res/menu/bottom_nav_menu.xml`

It already contains five primary destinations:

| Existing destination | Current role |
|---|---|
| `navigation_home` | Home |
| `navigation_search` | Search |
| `navigation_library` | Library |
| `navigation_downloads` | Downloads |
| `navigation_settings` | Settings |

The main activity binds both `BottomNavigationView` and `NavigationRailView` to the same NavController. It also contains destination-aware visibility and selected-item handling, navigation state restoration, TV focus handling, and debounce protection.

Separate layouts exist for phone/tablet-style activity presentation and TV presentation:

- `activity_main.xml`
- `activity_main_tv.xml`

### Audit conclusion

The PRD's five-section navigation is broadly compatible with the current application. However, the PRD label **Discover** should not immediately replace the existing **Search** destination.

Recommendation: retain the existing navigation IDs and behavior initially. AURA can present Search as Discover through a carefully scoped UI redesign only after confirming that the existing search flow and provider discovery behavior remain intact.

Do not replace the navigation graph as part of the first visual redesign.

## 4. Existing Screen and Feature Infrastructure

The repository already contains dedicated implementations for:

- Home: `ui/home/HomeFragment.kt` and `HomeViewModel.kt`
- Search: `ui/search/SearchFragment.kt` and supporting search classes
- Library: `ui/library/LibraryFragment.kt` and `LibraryViewModel.kt`
- Downloads: `ui/download/DownloadFragment.kt` and download queue screens
- Player: `ui/player/`
- Results/details: `ui/result/`
- Settings: multiple settings fragments and Compose settings screens
- Extensions: extension and repository management screens

HomeViewModel already accesses persisted resume-watching information, bookmarks, provider APIs, and related state. This means Home redesign should consume existing data and behavior rather than introducing a parallel mock data model.

### Audit conclusion

The first MVP should reuse existing feature logic. UI modernization should be isolated from data and playback behavior wherever practical.

## 5. UI Technology

The codebase is mixed:

- Android Views, XML layouts, Fragments, and ViewBinding remain important in the main application.
- Jetpack Compose and Material 3 are used in shared code and selected settings surfaces.
- The main activity layout includes a ComposeView overlay.
- Settings UI includes Compose-based preference definitions.

### Audit conclusion

AURA should not assume that the whole application is already Compose-based. A full migration would be a major architectural project and is not required to establish a new product identity.

Recommendation: continue the hybrid UI approach for the MVP. Use the existing Compose theme and components where they fit, while retaining mature View-based screens until there is a clear migration case.

## 6. Theme System

The shared theme implementation defines `CloudStreamThemeMode` and resolves modes through `modeToTheme`.

The AURA branch currently includes these four preset modes:

- `AuraMonochrome`
- `AuraSoftNeutral`
- `AuraDarkPremium`
- `AuraMinimalAccent`

The theme settings screen reads its selectable values from Android string arrays and uses the shared theme resolver for previews. The current branch also adds preset-specific color schemes and preference mappings.

The application still contains legacy Android View screens and XML resources that use Android theme attributes and resource colors.

### Audit risks

1. Compose theme correctness does not automatically prove that every legacy View screen uses the same palette.
2. Theme selection must be tested across both Compose and View-based screens.
3. Existing theme modes and stored preference values should remain backward compatible.
4. System Default behavior should be verified on both light and dark system configurations.
5. Dynamic color behavior should remain distinct from the named AURA presets.

### Recommendation

Before adding more theme features, perform a screen-by-screen theme verification pass. Avoid changing the persistence format or removing legacy theme modes until migration behavior is understood.

## 7. Android Build Configuration

The inspected version catalog currently specifies:

| Setting | Value |
|---|---:|
| minSdk | 23 |
| compileSdk | 37 |
| targetSdk | 36 |
| JDK toolchain | 17 |
| Kotlin Gradle Plugin | 2.4.0 |
| Android Gradle Plugin | 9.1.1 |
| Media3 | 1.9.3 |
| Compose Multiplatform | 1.12.0 |

The app uses Android product flavors `stable` and `prerelease`, plus debug and release build types. The app module enables BuildConfig and ViewBinding.

These values are a snapshot from the audited branch and should be rechecked before release work.

## 8. Test Infrastructure

The repository contains:

- Local JVM tests under `app/src/test`
- Android instrumented tests under `app/src/androidTest`
- Shared/common tests under `library/src/commonTest`
- JVM and web test source sets in the library module
- An instrumented-test GitHub Actions workflow

The inspected workflow runs `connectedPrereleaseDebugAndroidTest` using an Android emulator after an authorized `/run-tests` comment.

### Validation status

- Static source inspection: completed for the files listed in this report.
- Gradle build: not run.
- Unit tests: not run.
- Instrumented tests: not run.
- Visual/runtime testing: not run.

No build or test success is claimed.

## 9. Main Technical Risks

| Risk | Severity | Reason | Mitigation |
|---|---|---|---|
| Navigation regression | High | MainActivity has destination and device-specific navigation behavior | Preserve destination IDs and test all five primary destinations |
| Legacy theme mismatch | High | View and Compose surfaces use different UI mechanisms | Verify both UI systems for every preset |
| Playback regression | High | Player and result screens have many integrations | Keep player/core behavior unchanged during shell redesign |
| Extension compatibility | High | Existing extension and provider infrastructure is a core capability | Preserve APIs and validate representative compatible extensions |
| TV focus regression | Medium/High | TV navigation uses explicit focus and rail behavior | Keep TV layout separate and test remote navigation |
| Branding migration | Medium | Package and application IDs still use upstream identifiers | Separate visible branding from package migration |
| Build environment drift | Medium | Toolchain and SDK versions are recent | Record CI baseline and use the repository's declared environment |

## 10. Recommended MVP Scope

### P0 — Required for first AURA MVP

1. AURA visible branding and application identity.
2. Reliable theme selection across Compose and legacy Views.
3. Refined existing five-destination navigation.
4. Home UI refresh using existing real data.
5. Search/Discover UI refresh without replacing search infrastructure.
6. Library and Downloads visual consistency.
7. Existing player and extension flows remain functional.
8. Build and regression test baseline.

### P1 — After MVP foundation

1. Improved media detail presentation.
2. More cohesive extension management.
3. Additional motion and transition polish.
4. More complete tablet layout refinement.
5. Accessibility and performance improvements.

### P2 — Later

1. Larger player redesign.
2. Deeper desktop visual changes.
3. Additional personalization and optional dashboard modules.

## 11. Implementation Decisions

Based on this audit:

1. Keep the current AndroidX navigation graph for the first redesign.
2. Keep the five existing primary destination IDs.
3. Treat Discover as a presentation/product-language decision, not a new backend feature.
4. Keep the media player and extension infrastructure stable during the initial UI work.
5. Continue with the mixed View/Compose architecture for the MVP.
6. Verify AURA themes across both Compose and View-based surfaces.
7. Do not change application ID or package namespace as part of the first branding pass.
8. Require a successful build and regression test before calling the theme-system phase complete.

## 12. Next Execution Sequence

1. Establish a clean build and test baseline on the current branch.
2. Verify all four AURA themes on representative Compose and View screens.
3. Finalize the AURA navigation visual specification while retaining current destination IDs.
4. Implement the application shell and navigation styling.
5. Refresh Home using existing ViewModel data.
6. Refresh Search/Discover without changing its underlying behavior.
7. Continue to Library and Downloads.
8. Run regression tests for player, extension, and TV navigation behavior.

---

**Audit boundary:** This report is based on static inspection of repository files at the audited commit. It does not claim runtime verification, build success, or complete behavioral coverage.
