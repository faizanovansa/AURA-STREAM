# AURA — Application Shell Specification

**Version:** 1.0  
**Branch:** `aura/theme-system`  
**Status:** Implementation specification  
**Scope:** Main application shell only; no media engine or navigation graph replacement.

## 1. Objective

Establish AURA's visual identity around the existing CloudStream application shell while preserving navigation, playback, casting, TV focus, and state restoration behavior.

This phase changes presentation, not product capabilities.

## 2. Existing Shell Contract

The current application shell uses:

- `activity_main.xml` for the standard Android layout.
- `activity_main_tv.xml` for the TV layout.
- `mobile_navigation.xml` as the AndroidX navigation graph.
- `bottom_nav_menu.xml` for the five primary destinations.
- `MainActivity.updateNavBar()` for destination-aware visibility and selected state.
- `MainActivity.onNavDestinationSelected()` for navigation, debounce, animations, and state restoration.

The existing primary destination IDs are:

1. `navigation_home`
2. `navigation_search`
3. `navigation_library`
4. `navigation_downloads`
5. `navigation_settings`

These IDs are compatibility-sensitive. Do not rename or replace them during the shell phase.

## 3. Design Direction

AURA shell should feel restrained, intentional, and premium.

### Visual principles

- Surfaces should have clear hierarchy without excessive borders.
- Navigation should be visually quiet when inactive.
- Selected state should be unmistakable without oversized indicators.
- Iconography should use one consistent visual language.
- Motion should communicate transitions, not decorate every tap.
- Respect system insets, display cutouts, and gesture navigation.
- Do not force a single theme palette into every preset.

### Navigation labels

The product language may use:

| Existing destination | AURA-facing label |
|---|---|
| Home | Home |
| Search | Discover |
| Library | Library |
| Downloads | Downloads |
| Settings | Settings |

The Search destination remains backed by the existing search/provider implementation. The label change must not imply a new discovery backend.

## 4. Device Layout Behavior

### Android phone

- Keep bottom navigation.
- Keep the current NavHost and mini-cast controller constraints.
- Navigation should not cover scrollable content or player controls.
- Selected destination state must remain synchronized with nested destinations.

### Tablet / landscape Android

- Use the existing NavigationRail where current layout logic selects it.
- Preserve current rail width and content margin behavior until visual testing supports a change.
- Avoid switching layouts solely from screen orientation if the existing device classification says otherwise.

### Android TV

- Preserve the dedicated TV activity layout.
- Keep remote focus navigation and focus outline behavior.
- Maintain a visible, predictable selected destination.
- Do not introduce touch-only interactions.
- Test every primary destination using D-pad navigation.

## 5. Shell Components

The first visual pass should standardize:

1. Navigation item icon treatment.
2. Selected and unselected states.
3. Navigation surface and elevation.
4. System status/navigation bar colors.
5. Main content edge spacing.
6. Mini-cast controller integration.
7. Focus and pressed states for TV.

Do not add a new global top bar in this phase. Existing screens have their own app bars and toolbars; a global bar could duplicate controls or conflict with detail/player screens.

## 6. Theme Integration

The shell must resolve colors from the selected AURA theme.

Required verification:
- Monochrome.
- Soft Neutral.
- Dark Premium.
- Minimal Accent.
- System Default.

For each mode, inspect:
- Navigation surface.
- Active icon and label.
- Inactive icon and label.
- Focus/pressed state.
- Status bar.
- Navigation bar.
- Main content background.

Do not hardcode preset-specific colors in layout XML. Prefer existing theme attributes and a documented resource mapping. If a theme attribute cannot represent a required state, extend the theme contract deliberately rather than adding scattered literal colors.

## 7. Behavior That Must Not Change

- NavController graph and destination IDs.
- Back-stack save/restore behavior.
- Navigation debounce.
- Nested Settings destination selection.
- Downloads child/queue destination selection.
- Search query handoff.
- Exit confirmation behavior on Home.
- Cast mini-controller visibility rules.
- TV focus routing and focus outline.
- Player and results screen navigation.

## 8. Implementation Order

### Shell 1 — Audit and design tokens
- Map current navigation colors and dimensions.
- Identify theme attributes consumed by navigation.
- Define selected, unselected, pressed, and focused states.

### Shell 2 — Phone navigation
- Apply AURA visual treatment to BottomNavigationView.
- Verify content constraints and insets.
- Verify selected state on all five destinations.

### Shell 3 — Rail navigation
- Apply the same visual language to NavigationRailView.
- Preserve landscape and TV-specific behavior.
- Verify nested destination selection.

### Shell 4 — System bars and cast controller
- Align system bar surfaces with theme.
- Check mini-cast controller against all presets.
- Ensure it does not overlap navigation or content.

### Shell 5 — QA
- Test all five primary destinations.
- Test nested settings destinations.
- Test Downloads child and queue destinations.
- Test Search query handoff.
- Test back-stack restoration.
- Test rotation and configuration change.
- Test TV D-pad focus.
- Test all five theme modes.

## 9. Acceptance Criteria

The shell phase is complete only when:

1. All five primary destinations remain reachable.
2. Existing destination IDs are unchanged.
3. Selected navigation state is correct for nested destinations.
4. Search and navigation state restoration still work.
5. Cast mini-controller remains correctly positioned and visible.
6. Phone and landscape layouts remain usable.
7. TV D-pad focus remains usable.
8. All theme modes render readable navigation states.
9. Build, lint, and relevant tests pass.
10. No media engine, extension, or playback behavior was unintentionally changed.

## 10. Explicit Non-Goals

This phase does not:
- Replace AndroidX Navigation.
- Migrate the app to all-Compose UI.
- Rebuild Home, Discover, Library, or Downloads content.
- Redesign the media player.
- Change extension APIs.
- Rename package or application IDs.
- Remove upstream features.

## 11. Next Step

After this specification is reviewed, implement the shell as a small, isolated change set. Start with theme-aware navigation styling, then validate phone, landscape, and TV behavior before moving on to Home.
